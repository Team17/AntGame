package antgame.braintrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import antgame.AntGame;
import antgame.Game;
import antgame.core.AntBrain;
import antgame.core.AntColour;
import antgame.core.Map;
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
	 * Pointer to the best performing AntBrain
	 */
	private AntBrain elite;
	
	/**
	 * The total number of games we need to run
	 */
	private int totalGames;
	
	/**
	 * The total number of games we have run
	 */
	private int completedGames;
	
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
	}
	
	public Generation getNextGeneration() {
		// TODO: Score up current gen, breed and return next gen
		return null;
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
	
	public static void main(String[] args) {
		
		int POPSIZE = 6;
		
		AntBrain[] antBrains = new AntBrain[POPSIZE];
		long startTime = System.nanoTime();
		for (int i = 0; i < POPSIZE; i++) {
			antBrains[i] = AntBrainGenerator.getRandomAntBrain(AntColour.BLACK);
		}
		
		System.out.println("Generated " + POPSIZE + " ant brains in " + AntGame.timeSeconds(startTime) + " seconds");
		
		Generation g = new Generation(antBrains);
		
		startTime = System.nanoTime();
		g.scoreGeneration();

		System.out.println("All done!");
		
		
		
	}
	
}
