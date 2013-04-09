package antgame;

import java.util.ArrayList;
import java.util.List;

import antgame.core.AntBrain;
import antgame.core.Map;
import antgame.core.World;

/**
 * Class representing a Tournament.  A Tournament a is a collection of games (Worlds) to be played
 * between three or more Brains on a single Map.  Tournaments follow a Round Robin format and each
 * pair of Brains plays each other twice, with their colours inverted on the second game.
 * @author Alex
 *
 */
public class Tournament {
	
	/**
	 * The List of AntBrains to compete in the Tournament
	 */
	private List<AntBrain> brains;
	
	/**
	 * The Map on which each match of the Tournament is held
	 */
	private Map map;
	
	/**
	 * List of matches (Worlds) to be played in the Tournament
	 */
	private List<World> games;
	
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
		
		// Go for construction
		this.brains = brains;
		this.map = map;
		this.games = new ArrayList<World>();

		// Generate the individual games
		_generateGames();
	}
	
	/**
	 * Internal method used to generate the list of games that are to be played
	 */
	private void _generateGames() {
		// For each brain...
		for (int i = 0; i < brains.size(); i++) {
			// ... let's generate the games this Brain will play
			// First, make a note of the Brain we're on (call it "brain_1")
			AntBrain brain_1 = brains.get(i);
			// For all the Brains it needs to play...
			for (int j = i; j < brains.size(); j++) {
				// ... make a note of brain_1's opponent (call it "brain_2")
				AntBrain brain_2 = brains.get(j);
				// Push to our Stack of Worlds a game in which brain_1 and brain_2
				// play each other on our map
				games.add(new World(map,brain_1,brain_2));
				// Now push one more game where we swap the colours of brain_1 and
				// brain_2
				games.add(new World(map,brain_2,brain_1));
			}
		}
	}
	
	/**
	 * Return the total number of games in this Tournament
	 * @return	The number of games in this Tournament
	 */
	public int getNumberGames() {
		return games.size();
	}
	
	/**
	 * Return a specified World (a game to be played) by index
	 * @param	index	The index
	 * @return			The specified World
	 */
	public World getGame(int index) {
		return games.get(index);
	}
	
	

}
