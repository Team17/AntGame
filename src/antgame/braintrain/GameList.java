package antgame.braintrain;

import antgame.Game;

import java.util.Observable;
import java.util.Observer;

public class GameList extends Observable implements Runnable {
	
	/**
	 * The id of this GameList
	 */
	private int id;
	
	/**
	 * The list of games to run
	 */
	private Game[] games;
	
	/**
	 * Constructor
	 * @param	games		The list of Games to execute
	 * @param	observer	The object observing this one
	 */
	public GameList(int id, Game[] games, Observer observer) {
		this.id = id;
		this.games = games;
		addObserver(observer);
	}
	
	/**
	 * Return the Id of this GameList
	 * @return	The Id of this GameList
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Method invoked upon Thread.start()
	 */
	public void run() {
		System.out.println("-> GameList " + id + " starting");
		for (Game game : games) {
			game.run();
			setChanged();
			notifyObservers(game);
		}
		System.out.println("-> GameList " + id + " finished");
	}
	
}
