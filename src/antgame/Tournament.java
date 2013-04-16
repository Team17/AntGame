package antgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import antgame.braintrain.AntBrainGenerator;
import antgame.braintrain.GameList;
import antgame.core.AntBrain;
import antgame.core.AntColour;
import antgame.core.Map;
import antgame.core.MapCreator;
import antgame.core.World;

/**
 * Class representing a Tournament.  A Tournament a is a collection of games (Worlds) to be played
 * between three or more Brains on a single Map.  Tournaments follow a Round Robin format and each
 * pair of Brains plays each other twice, with their colours inverted on the second game.
 * 
 * The Tournament is Observable by a class that needs notifying of changes to the state
 * of the Tournament (e.g. another Game has finished, update the UI layer).  The Tournament
 * is also Observable by the Threads it internally creates.  Hence, Tournament is both
 * an Observer and Observable!
 * 
 * @author Alex
 *
 */
public class Tournament extends Observable implements Observer {
	
	/**
	 * The List of AntBrains to compete in the Tournament
	 */
	private AntBrain[] brains;
	
	/**
	 * The Map on which each match of the Tournament is held
	 */
	private Map map;
	
	/**
	 * List of matches Games to be played in the Tournament
	 */
	private Game[] games;
	
	/**
	 * Score tracker
	 */
	private HashMap<AntBrain,Integer> scores;
	
	/**
	 * Keeps track of the number of games that have been played
	 */
	private int gamesPlayed;
	
	/**
	 * Constructor
	 * @param	brains	A List of Brains to play in the Tournament
	 * @param	map		The Map on which the Tournament is to be played
	 */
	public Tournament(List<AntBrain> brains, Map map) {
		
		// Brain list underflow check
		if ( brains.size() < AntGame.CONFIG.MIN_TOURANAMENT_BRAINS ) {
			throw new IllegalArgumentException("Attempt to instantiate a Tournament with " + brains.size() + " AntBrains, " + AntGame.CONFIG.MIN_TOURANAMENT_BRAINS + " minimum.");
		}
		// Brain list overflow check
		else if ( brains.size() > AntGame.CONFIG.MAX_TOURANAMENT_BRAINS ) {
			throw new IllegalArgumentException("Attempt to instantiate a Tournament with " + brains.size() + " AntBrains, " + AntGame.CONFIG.MAX_TOURANAMENT_BRAINS + " maximum.");
		}
		
		// Pull out Arrays for Lists
		AntBrain[] _brains = new AntBrain[brains.size()];
		for (int i = 0; i < _brains.length; i++) {
			_brains[i] = brains.get(i);
		}
		
		// Go for construction
		this.brains = _brains;
		this.map = map;
		this.gamesPlayed = 0;
		this.scores = new HashMap<AntBrain,Integer>();
		
		// Initialise all scores to 0
		for (AntBrain ab : brains) {
			scores.put(ab, AntGame.CONFIG.INITIAL_TOURNAMENT_SCORE);
		}

		// Generate the individual games
		generateGames();
	}
	
	/**
	 * Internal method used to generate the list of games that are to be played
	 */
	private void generateGames() {
		
		// Initialise an ArrayList to store our games while we're generating them
		ArrayList<Game> _games = new ArrayList<Game>();
		// Initialise a counter to keep track of every sub-loop
		int k = 0;
		// For each brain...
		for (int i = 0; i < brains.length; i++) {
			// ... let's generate the games this Brain will play
			// First, make a note of the Brain we're on (call it "brain_1")
			AntBrain brain_1 = brains[i];
			// For all the Brains it needs to play...
			for (int j = (i + 1); j < brains.length; j++) {
				// ... make a note of brain_1's opponent (call it "brain_2")
				AntBrain brain_2 = brains[j];
				// Push to our Stack of Worlds a game in which brain_1 and brain_2
				// play each other on our map
				_games.add(k, new Game(map,brain_1,brain_2) );
				_games.get(k).setId(k);
				k++;
				// Now push one more game where we swap the colours of brain_1 and
				// brain_2
				_games.add(k, new Game(map,brain_2,brain_1) );
				_games.get(k).setId(k);
				k++;
			}
		}
		// Load into the games cache
		games = new Game[k];
		games = (Game[]) _games.toArray(games);
	}
	
	/**
	 * Return the total number of games in this Tournament
	 * @return	The number of games in this Tournament
	 */
	public int getNumberGames() {
		return games.length;
	}
	
	/**
	 * Return a specified World (a game to be played) by index
	 * @param	index	The index
	 * @return			The specified World
	 */
	public Game getGame(int index) {
		return games[index];
	}
	
	/**
	 * Check to see if the Tournament has Games left to play
	 * @return	True if there are Games left to play, false otherwise
	 */
	@Deprecated
	public boolean hasNextGame() {
		return (gamesPlayed <= games.length);
	}
	
	/**
	 * Runs the whole Tournament using concurrency where possible
	 */
	public HashMap<AntBrain,Integer> runTournament() {
		
		// We'll do this using threads, let's get the number of
		// processors available
		int THREADS = Runtime.getRuntime().availableProcessors();
		
		// For now, we will have a set of ArrayLists.  The number of ArrayLists
		// will be equal to the number of processors available
		
		// Each ArrayList will store a number of Games to be run
		ArrayList<ArrayList<Game>> _games = new ArrayList<ArrayList<Game>>();
		for (int i = 0; i < THREADS; i++) {
			_games.add(0,new ArrayList<Game>());
		}
		
		// Now, let's generate all the Games we need to score this Generation
		// We'll add the Games evenly across all the ArrayLists we have
		for (int i = 0; i < games.length; i++) {
			// Add the game to one of the game lists
			_games.get(i % THREADS).add(games[i]);
			
		}

		// Now let's prepare some GameList objects
		GameList[] gameLists = new GameList[THREADS];
		// For each ArrayList of Games...
		for (int i = 0; i < _games.size(); i++) {
			// Initialise and array of Games
			Game[] g = new Game[_games.get(i).size()];
			// Populate this array of Games with the Games in our ArrayList
			g = (Game[]) _games.get(i).toArray(g);
			// Create a new GameList from this array of Games
			gameLists[i] = new GameList(i,g,this);
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
		
		System.out.println("Here");
		
		return scores;
		
	}
	
	
	
	/**
	 * Runs the next game and returns it
	 * @return	The next game in a completed state
	 */
	@Deprecated
	public Game runNextGame() {
		// Get the next game (index = gamesPlayed)
		Game g = getGame(gamesPlayed);
		// Run the game to completion
		g.run();
		// Increment the number of Games Played
		gamesPlayed++;
		// Update the scoreboard
		handleScores(g);
		// Return it
		return g;
	}
	
	/**
	 * Return a HashMap of scores for all AntBrains
	 * @return	The HashMap of scores
	 */
	public HashMap<AntBrain,Integer> getAllScores() {
		return scores;
	}
	
	/**
	 * Return the score for an individual AntBrain
	 * @param	antBrain	The AntBrain whose score is requested
	 * @return				The score of that AntBrain
	 */
	public int getScore(AntBrain antBrain) {
		return scores.get(antBrain);
	}
	
	/**
	 * Internal method used to populate the scoreboard
	 * @param	game	A completed game to process
	 */
	private void handleScores(Game game) {
		if (game.isFinished()) {
			AntBrain winner = game.getWinningBrain();
			AntBrain loser = game.getLosingBrain();
			if (game.isDrawn()) {
				scores.put(winner, AntGame.CONFIG.SCORE_FOR_DRAW);
				scores.put(loser, AntGame.CONFIG.SCORE_FOR_DRAW);
			}
			else {
				scores.put(winner, AntGame.CONFIG.SCORE_FOR_WIN);
				scores.put(loser, AntGame.CONFIG.SCORE_FOR_LOSS);
			}
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
			gamesPlayed++;
			handleScores(game);
		}
		
		// TODO: This is where a notification call is made to the UI layer
		
	}
	
	public static void main(String[] args) {
		
		int NUMBRAINS = 8;
		
		ArrayList<AntBrain> antBrains = new ArrayList<AntBrain>();
		
		for (int i = 0; i < NUMBRAINS; i++) {
			antBrains.add(i,AntBrainGenerator.getRandomAntBrain(AntColour.RED));
		}
		
		Map map = MapCreator.getRandomMap();
		
		Tournament t = new Tournament(antBrains,map);
		
		HashMap<AntBrain,Integer> scores = t.runTournament();
		
		System.out.println("All done");
		
	}

}
