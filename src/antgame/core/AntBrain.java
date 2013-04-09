package antgame.core;


/**
 * AntBrain class responsible for controlling values in the Ant and the map. 
 * @author Doniyor Ulmasov (13158)
 * @version 0.1
 */
public class AntBrain {
	
	/**
	 * Array of BrainStates representing the Ant Brain
	 */
	private BrainState[] brain;
	
	/**
	 * Constructor for an AntBrain
	 * @param	antLoc	Path to the .brain file from which this AntBrain is to be constructed
	 * @param	colour	The colour assigned to this AntBrain
	 */
	public AntBrain(String antLoc, AntColour colour)
	{
		AntBrainInterpreterCoryn aBI = new AntBrainInterpreterCoryn();
		brain = aBI.antBrainGenerator(antLoc, colour);
		
	}
	
	/**
	 * Return a BrainState from the Ant Brain given an integer id
	 * @param	state	The BrainState id
	 * @return			The BrainState
	 */
	public BrainState getState(int state){
		return brain[state];
	}
	
}
