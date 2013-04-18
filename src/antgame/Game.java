package antgame;

import antgame.core.AntBrain;
import antgame.core.AntColour;
import antgame.core.Map;
import antgame.core.World;

public class Game {

	/**
	 * A numerical identifier for testing and debugging purposes
	 */
	private int id;
	
	/**
	 * The World associated with this Game
	 */
	private World world;

	/**
	 * The AntBrain used by the red colony
	 */
	private AntBrain redBrain;

	/**
	 * The AntBrain used by the black colony
	 */
	private AntBrain blackBrain;

	/**
	 * The colour of the winning ant colony
	 */
	private AntColour winner;

	/**
	 * The round the game is currently at
	 */
	private int round;

	/**
	 * Constructor
	 * 
	 * @param map
	 *            The Map on which this Game is to be played
	 * @param redBrain
	 *            The AntBrain used by the red colony
	 * @param blackBrain
	 *            The AntBrain used by the black colony
	 */
	public Game(Map map, AntBrain redBrain, AntBrain blackBrain) {
		redBrain.changeColour(AntColour.RED);
		blackBrain.changeColour(AntColour.BLACK);
		this.world = new World(map, redBrain, blackBrain);
		this.redBrain = redBrain;
		this.blackBrain = blackBrain;
		this.winner = null;
		this.round = 0;
	}
	
	/**
	 * Return the Id of this Game
	 * @return	The Id of this Game
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set the Id of this Game
	 * @param id	The Id of this Game
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Determines whether this Game is finished. A game is finished if one
	 * colony is extinct, or the maximum number of rounds have passed.
	 * 
	 * @return True if the Game has finished, false otherwise
	 */
	public boolean isFinished() {
		// If we have a winner, return true
		if (winner != null) {
			return true;
		}
		// Check for premature colony death
		if (getRedAlive() == 0) {
			winner = AntColour.BLACK;
			return true;
		} else if (getBlackAlive() == 0) {
			winner = AntColour.RED;
			return true;
		}
		// Check game rounds condition
		else if (round == AntGame.CONFIG.GAME_ROUNDS) {
			// Food check
			if (getRedFood() > getBlackFood()) {
				winner = AntColour.RED;
			} else if (getRedFood() < getBlackFood()) {
				winner = AntColour.BLACK;
				// Food draw, compare number of alive ants
			} else {
				if (getRedAlive() > getBlackAlive()) {
					winner = AntColour.RED;
				} else if (getRedAlive() < getBlackAlive()) {
					winner = AntColour.BLACK;
					// We have a draw!
				} else {
					winner = null;
				}
			}
			// Finished because the maximum number of rounds have passed
			return true;
		}
		// No win condition detected, return false
		return false;
	}

	/**
	 * Test to see if the game resulted in a drawn
	 * @return	True if the game was drawn, null if the game has yet to finish, false otherwise
	 */
	public boolean isDrawn() {
		if (isFinished()) {
			return (getWinningBrain() == null);
		} else {
			return (Boolean) null;
		}
	}
	
	/**
	 * Generate and return a new Game based on this one but the colour assignments switched
	 * @return	The new Game
	 */
	public Game getColourSwitched() {
		return new Game(world.getMap(),getBlackBrain(),getRedBrain());
	}

	/**
	 * Return the number of red ants still alive
	 * 
	 * @return The number of red ant still alive
	 */
	public int getRedAlive() {
		return world.getStats().getRedAlive();
	}

	/**
	 * Return the AntBrain used by the red colony
	 * @return	The AntBrain used by the red colony
	 */
	public AntBrain getRedBrain() {
		return redBrain;
	}
	
	/**
	 * Return the number of food particles in the red anthill
	 * 
	 * @return The number of food particles in the red anthill
	 */
	public int getRedFood() {
		return world.getStats().getFoodUnitsRedHill();
	}

	/**
	 * Return the number of black ants still alive
	 * 
	 * @return The number of black ant still alive
	 */
	public int getBlackAlive() {
		return world.getStats().getRedAlive();
	}

	/**
	 * Return the AntBrain used by the black colony
	 * @return	The AntBrain used by the black colony
	 */
	public AntBrain getBlackBrain() {
		return blackBrain;
	}
	
	/**
	 * Return the number of food particles in the black anthill
	 * 
	 * @return The number of food particles in the black anthill
	 */
	public int getBlackFood() {
		return world.getStats().getFoodUnitsBlackHill();
	}

	/**
	 * Returns the colour of the ant colony that won this game, or null if the game has not yet finished
	 * @return	The colour of the ant colony that won this game, or null if the game has not yet finished
	 */
	public AntColour getWinningColour() {
		return winner;
	}

	/**
	 * Return the AntBrain that won the game
	 * @return	Return the AntBrain that won the game or null if the game has yet to finish or the Game is drawn
	 */
	public AntBrain getWinningBrain() {
		// Winner might be null if we have a draw
		if (winner == null) {
			return null;
		} else {
			switch (winner) {
			case RED:
				return redBrain;
			case BLACK:
				return blackBrain;
			// unreachable
			default:
				return null;
			}
		}
	}
	
	/**
	 * Return the AntBrain that lost the game
	 * @return	Return the AntBrain that lost the game or null if the game has yet to finish or the Game is drawn
	 */
	public AntBrain getLosingBrain() {
		// Winner might be null if we have a draw
		if (winner == null) {
			return null;
		} else {
			switch (winner) {
			case RED:
				return blackBrain;
			case BLACK:
				return redBrain;
			// unreachable
			default:
				return null;
			}
		}
	}
	
	/**
	 * Determine whether this Game has another round
	 * 
	 * @return True if this Game has another round, false otherwise
	 */
	public boolean hasNext() {
		// If the game is finished, there are no more rounds
		if (isFinished()) {
			return false;
		} else {
			// If we are at the 300,000th round, there are no more rounds
			return (round <= AntGame.CONFIG.GAME_ROUNDS);
		}
	}
	
	/**
	 * Move the Game forward one round if there is one
	 */
	public void nextRound() {
		if (hasNext()) {
			world.step();
			round++;
		}
	}
	
	/**
	 * Run the Game to completion
	 */
	public void run() {
		AntGame.debug("Running game..." + getId());
		while (hasNext()) {
			nextRound();
		}
		isFinished();
		AntGame.debug("Game " + getId() + " complete");
	}
	
	public static void main(String[] args) {
		
		
		
	}

}
