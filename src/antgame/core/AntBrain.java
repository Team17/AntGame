package antgame.core;

import antgame.AntGame;


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
	 * The colour of this AntBrain
	 */
	public AntColour colour;
	
	/**
	 * Id of the AntBrain (for testing purposes)
	 */
	private int id;
	
	/**
	 * Constructor for an AntBrain
	 * @param	antLoc	Path to the .brain file from which this AntBrain is to be constructed
	 * @param	colour	The colour assigned to this AntBrain
	 */
	public AntBrain(String antLoc, AntColour colour)
	{
		this.colour = colour;
		AntBrainInterpreterCoryn aBI = new AntBrainInterpreterCoryn();
		brain = aBI.antBrainGenerator(antLoc, colour);
		
	}
	
	/**
	 * Return the Id of the AntBrain
	 * @return	The Id of the AntBrain
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set the Id of the AntBrain
	 * @param id	The Id of the AntBrain
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Constructor.
	 * @param	brainStates	The BrainStates comprising this AntBrain
	 * @param	colour		The colour of this AntBrain
	 */
	public AntBrain (BrainState[] brainStates, AntColour colour) {
		this.colour = colour;
		this.brain = brainStates;
	}
	
	/**
	 * Return a BrainState from the Ant Brain given an integer id
	 * @param	state	The BrainState id
	 * @return			The BrainState
	 */
	public BrainState getState(int state){
		return brain[state];
	}
	
	/**
	 * Change the colour of this AntBrain
	 * @param colour
	 */
	public void changeColour(AntColour colour) {
		this.colour = colour;
		for (BrainState bs : brain) {
			bs.setMarkerColourIfExists(colour);
		}
	}
	
	/**
	 * Return the core BrainState array (for testing)
	 * @return	The BrainStates
	 */
	public BrainState[] getBrainStates() {
		return brain;
	}
	
	/**
	 * Obtain a string representation of the Ant Brain, which can
	 * be directly written to an Ant Brain file
	 */
	public String toString() {
		String s = "";
		
		for (BrainState bs : brain) {	
			// Pull out the Instruction
			s += bs.getInstruction().toString() + " ";
			
			// Write the line of the AntBrain file appropriately
			switch (bs.getInstruction()) {
			case SENSE:
				s += bs.getSenseDirection().toString() + " ";
				s += bs.getNextIdState() + " ";
				s += bs.getAltNextIdState() + " ";
				s += bs.getSenseCondition().toString() + " ";
				// A SenseCondition of MARKER must be followed by the Id of the Marker
				if (bs.getSenseCondition().equals(SenseCondition.MARKER)) {
					s += bs.getMarker().getId();
				}
				break;
			case MARK:
				s += bs.getMarker().getId() + " ";
				s += bs.getNextIdState();
				break;
			case UNMARK:
				s += bs.getMarker().getId() + " ";
				s += bs.getNextIdState();
				break;
			case PICKUP:
				s += bs.getNextIdState() + " ";
				s += bs.getAltNextIdState() + " ";
				break;
			case DROP:
				s += bs.getNextIdState() + " ";
				break;
			case TURN:
				s += bs.getLeftRight().toString() + " ";
				s += bs.getNextIdState();
				break;
			case MOVE:
				s += bs.getNextIdState() + " ";
				s += bs.getAltNextIdState();
				break;
			case FLIP:
				s += bs.getRandomInt() + " ";
				s += bs.getNextIdState();
				break;
			}
			
			// Add a line break character
			s += AntGame.LINEBREAK;
			
		}
		
		// Trim trailing linebreak
		return s.trim();
		
	}
	
}
