package antgame.braintrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import antgame.AntGame;
import antgame.FileManager;
import antgame.Game;
import antgame.core.AntBrain;
import antgame.core.AntColour;
import antgame.core.MapCreator;

public class Generation implements Observer {
	
	/**
	 * n is the generation number
	 */
	private int n;

	/**
	 * Array of AntBrains are the "chromosomes"
	 */
	private AntBrain[] chromosomes;
	
	/**
	 * HashMap mapping chromosomes to scores
	 */
	private HashMap<AntBrain, Integer> scores;
	
	/**
	 * Pointer to the best performing AntBrains
	 */
	private AntBrain[] elite;
	
	/**
	 * The total number of games we need to run
	 */
	private int totalGames;
	
	/**
	 * The total number of games we have run
	 */
	private int completedGames;
	
	/**
	 * Array of pointers to Ant Brains used in selecting breeding pairs
	 */
	private AntBrain[] breedingMap;
	
	/**
	 * Random number generator
	 */
	private Random random;
	
	/**
	 * Probability that crossover will occur
	 */
	public static final double P_CROSSOVER = 0.9;
	
	/**
	 * Probability that mutation will occur
	 */
	public static final double P_MUTATION = 0.01;
	
	/**
	 * The number of elite brains to keep
	 */
	public static final int ELITE_BRAINS = 5;
	
	/**
	 * Constructor
	 * @param	chromosomes	An array of AntBrains to use as chromosomes
	 */
	public Generation(AntBrain[] chromosomes) {
		this.n = 0;
		this.chromosomes = chromosomes;
		this.scores = new HashMap<AntBrain, Integer>();
		// Initialise scores to 0
		for (int i = 0; i < chromosomes.length; i++) {
			// Give each Ant Brain an Id
			chromosomes[i].setId(i);
			scores.put(chromosomes[i],0);
		}
		this.completedGames = 0;
		this.totalGames = 0;
		this.random = new Random();
	}
	
	/**
	 * Run a complete fitness assessment and breeding procedure to return
	 * the next Generation from this one.
	 * @return	The next Generation
	 */
	public Generation getNextGeneration() {
		
		// The size of the next generation will equal the size of this one
		AntBrain[] nextChromosomes = new AntBrain[chromosomes.length];
		
		// Score this generation
		scoreGeneration();
		
		// Create the elite brains and the breeding map
		generateElite();
		generateBreedingMap();
		
		// Keep track of how many chromosomes we've added
		int added = 0;
		
		// The elite chromosomes get a free pass
		while (added < elite.length) {
			nextChromosomes[added] = elite[added];
			added++;
		}
		
		// Create the remaining chromosomes
		while (added < nextChromosomes.length) {
			
			// Grab a breeding pair from the current population of chromosomes
			BreedingPair bp = getBreedingPair();
			// Perform crossover
			bp.crossover();
			// Perform mutation
			bp.mutate();
			
			// Add the first child to the new population
			nextChromosomes[added] = bp.getChromosome1();
			added++;
			
			// Add the second child to the new population
			nextChromosomes[added] = bp.getChromosome2();
			
		}
		
		// Create the new generation
		Generation g = new Generation(nextChromosomes);
		g.n = this.n + 1;
		
		// Return the new generation
		return g;
	}
	
	/**
	 * Return the generation
	 * @return
	 */
	public int getN() {
		return n;
	}
	
	/**
	 * Scores the current generation by making the AntBrains fight for our amusement.
	 * This is achieved concurrently.
	 */
	public void scoreGeneration() {
		
		// We'll do this using threads, let's get the number of
		// processors available
		int THREADS = Runtime.getRuntime().availableProcessors();
		
		// For now, we will have a set of ArrayLists.  The number of ArrayLists
		// will be equal to the number of processors available
		
		// Each ArrayList will store a number of Games to be run
		ArrayList<ArrayList<Game>> games = new ArrayList<ArrayList<Game>>();
		for (int i = 0; i < THREADS; i++) {
			games.add(0,new ArrayList<Game>());
		}
		
		System.out.println("Generating maps...");
		
		// Now, let's generate all the Games we need to score this Generation
		// We'll add the Games evenly across all the ArrayLists we ahve
		int x = 0;
		for (int i = 0; i < chromosomes.length; i++) {
			for (int j = (i + 1) ; j < chromosomes.length; j++) {
				// Generate the game
				Game g = new Game(MapCreator.getRandomMap(),chromosomes[i],chromosomes[j]);
				// Add the game to one of the game lists
				games.get(x % THREADS).add(g);
				g.setId(x);
				x++;
			}
		}
		
		// We now know how many games we need to run
		totalGames = x;
		
		System.out.println("Done generating maps");
		
		// Now let's prepare some GameList objects
		GameList[] gameLists = new GameList[THREADS];
		// For each ArrayList of Games...
		for (int i = 0; i < gameLists.length; i++) {
			// Initialise and array of Games
			Game[] _games = new Game[games.get(i).size()];
			// Populate this array of Games with the Games in our ArrayList
			for (int j = 0; j < games.get(i).size(); j++) {
				_games[j] = games.get(i).get(j);
			}
			// Create a new GameList from this array of Games
			gameLists[i] = new GameList(i,_games,this);
		}
		
		// Concurrently execute the GameLists
		ExecutorService es = Executors.newCachedThreadPool();
		for (GameList gl : gameLists) {
			es.execute(gl);
		}
		es.shutdown();
		try {
			es.awaitTermination(5, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Generate the list of elite brains
	 */
	public void generateElite() {
		
		// We might have fewer than 5 chromosomes
		int length = (ELITE_BRAINS > chromosomes.length) ? chromosomes.length : ELITE_BRAINS;
		
		// Initialise list of elite brains
		elite = new AntBrain[length];
		
		// Initialise list with any old brains
		for (int i = 0; i < elite.length; i++) {
			elite[i] = chromosomes[i];
		}
		
		// Loop through the brains
		for (AntBrain ab : chromosomes) {
			
			// Loop through the current elite list.
			// If we find a brain that scores lower than this one,
			// replace it with this one.
			boolean addToList = false;
			int toReplace = -1;
			for (int i = 0; i < elite.length; i++) {
				int lowestScoreSeen = Integer.MAX_VALUE;
				if (scores.get(elite[i]) < scores.get(ab)) {
					addToList = true;
					if (scores.get(elite[i]) < lowestScoreSeen) {
						lowestScoreSeen = scores.get(elite[i]);
						toReplace = i;
					}
					
				}
			}
			if (addToList) {
				elite[toReplace] = ab;
			}
			
			
		}
		
	}
	
	/**
	 * Play two AntBrains off against each other on a random Contest Map and return the winner
	 * @param	brain1	The first competing AntBrain
	 * @param	brain2	The second competing AntBrain
	 * @return			The winning AntBrain
	 */
	public AntBrain match(AntBrain brain1, AntBrain brain2) {
		Game game = new Game(MapCreator.getRandomMap(),brain1,brain2);
		game.run();
		if (game.isFinished()) {
			return game.getWinningBrain();
		} else {
			return null;
		}
	}
	 
	/**
	 * Method invoked when a Game is finished
	 * @param	Observable o	The object being observed
	 * @param	Object arg		The argument provided
	 */
	@Override
	public synchronized void update(Observable o, Object arg) {
		// Pull out the Game from the arg
		Game game = (Game) arg;
		// Confirm the game is finished
		if (game.isFinished()) {
			// Increment completed games counter
			completedGames++;
			// Pull out the winning brain
			AntBrain winner = game.getWinningBrain();
			if (winner != null) {
				// Add 2 to the score of this AntBrain
				scores.put( winner, (scores.get(winner) + 2) );
			} else {
				// Both Brains receive 1 point in the event of a draw
				AntBrain redBrain = game.getRedBrain();
				AntBrain blackBrain = game.getBlackBrain();
				scores.put( redBrain, (scores.get(redBrain) + 1) );
				scores.put( blackBrain, (scores.get(blackBrain) + 1) );
			}
		}
		
		printScores();
		
	}
	
	/**
	 * Saves this generation to a series of AntBrain files
	 */
	public void saveGeneration() {
		
		String folderName = "Generation-" + n + "-" + System.nanoTime();
		FileManager.createFolder(folderName);
		for (int i = 0; i < chromosomes.length; i++) {
			FileManager.saveBrain(chromosomes[i], folderName + "\\" + scores.get(chromosomes[i]) + "-" + i + System.nanoTime());
		}
		
	}
	
	/**
	 * Determine if a chromosome is part of the elite set for this generation
	 * @param	antBrain	The AntBrain whose eliteness we are testing
	 * @return				True if this AntBrain is elite, false otherwise
	 */
	public boolean isElite(AntBrain antBrain) {
		boolean isElite = false;
		int i = 0;
		while (!isElite) {
			isElite = (elite[i].equals(antBrain));
			i++;
		}
		return isElite;
	}
	
	/**
	 * Print the current scores to the console
	 */
	public void printScores() {
		System.out.println("** Printing Scores (" + completedGames + " of " + totalGames + " games run)***");
		Set<AntBrain> antBrains = scores.keySet();
		Iterator<AntBrain> it = antBrains.iterator();
		while(it.hasNext()) {
			AntBrain ab = it.next();
			int score = scores.get(ab);
			System.out.println("-> Ant Brain " + ab.getId() + ": " + score);
			
		}
		System.out.println("*** Done Printing Scores ***");
		
	}
	
	/**
	 * Generate the breeding map (post run)
	 * The breeding map is an array of pointers to the chromosomes.
	 * The more pointers there are to one chromosomes, the higher the probability
	 * that it will be selected to breed.
	 */
	public void generateBreedingMap() {
		
		// Initialise an ArrayList
		ArrayList<AntBrain> _breedMap = new ArrayList<AntBrain>();
		// Iterate over chromosomes
		for (int i = 0; i < chromosomes.length; i++) {
			// The number of times the chromosome will be added depends on it's rank
			// Number of times added = (total number of chromosomes) - rank
			for (int j = 0; j < (chromosomes.length - i); j++) {
				_breedMap.add(chromosomes[i]);
			}
		}
		// Set the breeding map
		breedingMap = new AntBrain[_breedMap.size()];
		breedingMap = _breedMap.toArray(breedingMap);
		
	}
	
	/**
	 * Select and return a BreedingPair
	 * The probability of a chromosome being selected is proportional to its fitness
	 * @return	A breeding pair
	 */
	public BreedingPair getBreedingPair() {
		AntBrain c1 = breedingMap[ random.nextInt(breedingMap.length) ];
		AntBrain c2 = breedingMap[ random.nextInt(breedingMap.length) ];
		return new BreedingPair(c1,c2);
	}
	
	/**
	 * Re-ordered the chromosomes array so they appear in descending order of score
	 */
	public void rankChromosomes() {
		
		// Create an ordered, inverse mapping of score values to a List of AntBrains
		// that have that score
		TreeMap<Integer,ArrayList<AntBrain>> inverted = new TreeMap<Integer,ArrayList<AntBrain>>();
		for (AntBrain ab : chromosomes) {
			int score = scores.get(ab);
			// Initialise the ArrayList<AntBrain>s as and when
			if (inverted.get(score) == null) {
				inverted.put(score, new ArrayList<AntBrain>());
			}
			// Add this AntBrain to the List of AntBrains with this score
			inverted.get(score).add(ab);
		}
		
		// We'll be pulling AntBrains out in ascending score order so
		// we'll be inverting this array at the end
		AntBrain[] reversed = new AntBrain[chromosomes.length];

		// Pull out an Iterator
		Iterator<Integer> it = inverted.keySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			ArrayList<AntBrain> brainsWithScore = inverted.get(it.next());
			for (AntBrain ab : brainsWithScore) {
				reversed[i] = ab;
				i++;
			}
		}
		
		// Reverse the array
		AntBrain[] ordered = new AntBrain[chromosomes.length];
		for (i = 0; i < ordered.length; i++) {
			ordered[i] = reversed[ (chromosomes.length - i) ];
		}
		
		// Set the chromosomes as the ordered array
		chromosomes = ordered;
		
	}
	
	public static void main(String[] args) {
		
		int POPSIZE = 4;
		
		AntBrain[] antBrains = new AntBrain[POPSIZE];
		long startTime = System.nanoTime();
		for (int i = 0; i < POPSIZE; i++) {
			antBrains[i] = AntBrainGenerator.getRandomAntBrain(AntColour.BLACK);
		}
		
		System.out.println("Generated " + POPSIZE + " ant brains in " + AntGame.timeSeconds(startTime) + " seconds");
		
		Generation g = new Generation(antBrains);
		
		while (true) {
			Generation _g = g.getNextGeneration();
			_g.saveGeneration();
		}
		
		
		
	}
	
}
