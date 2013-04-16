/**
 * 
 */
package antgame.core;

import antgame.InvalidMarkerIdException;

/**
 * Enumerator for a single state of an Ant Brain
 * @author Alex
 *
 */
public class BrainState implements Cloneable {

	private Instruction instruction;

	/**
	 * The numerical identifier of this brain state within an ant brain.
	 */
	private int	stateId;


	/**
	 * The next brain state the ant will have after this one as an integer.
	 * 
	 * In cases where the next brain state depends on some condition, this is the state
	 * where the ant will be when that condition is met.
	 */
	private int nextIdState;

	/**
	 * The next brain state the ant will have after this one as BrainState object.
	 * 
	 * In cases where the next brain state depends on some condition, this is the state
	 * where the ant will be when that condition is met.
	 */
	private BrainState nextState;

	/**
	 * [Optional] The brain state the ant will be at if the provided condition is not met as an Integer.
	 */
	private int altNextIdState;

	/**
	 * [Optional] The brain state the ant will be at if the provided condition is not met as a BrainState
	 */
	private BrainState altNextState;

	/**
	 * [Optional] For the SENSE instruction, this property denotes the cell that is being
	 * sensed relative to the ant.
	 */
	private SenseDirection senseDirection;

	/**
	 * [Optional] For the SENSE instruction, this property denotes the condition that is
	 * being sensed.
	 */
	private SenseCondition senseCondition;

	/**
	 * [Optional] For the MARK and UNMARK instructions, this property denotes the
	 * marker that is being deposited/removed.
	 */
	private Marker marker;

	/**
	 * [Optional] For the TURN instruction, this property denotes whether the ant is to
	 * turn left or right.
	 */
	private LeftRight leftRight;

	/**
	 * [Optional] For the FLIP instruction, this property represents the upper bound for
	 * the range of possible random numbers to generate.
	 */
	private int randomInt;

	/**
	 * Constructor.  Defaults all fields to null or -1 if they are ints.
	 */
	public BrainState() {
		this.altNextIdState = -1;
		this.altNextState = null;
		this.instruction = null;
		this.leftRight = null;
		this.marker = null;
		this.nextIdState = -1;
		this.nextState = null;
		this.randomInt = -1;
		this.senseCondition = null;
		this.senseDirection = null;
		this.stateId = -1;
	}
	
	@Override
	public BrainState clone() {
		BrainState clone;
		try {
			clone = (BrainState) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clone = null;
		}
		return clone;
	}
	
	/**
	 * @return the instruction of the brainstate i.e. Sense Move PickUp.
	 */
	public Instruction getInstruction(){
		return instruction;
	}

	/**
	 * Sets the current instruction of the state.
	 * @param ins the instruction of the brain state.
	 */
	public void setInstruction(Instruction ins){
		instruction = ins;
	}

	/**
	 * @return the stateId
	 */
	public int getStateId() {
		return stateId;
	}

	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return BrainState of the next brainstate.
	 */
	public BrainState getNextState() {
		return nextState;
	}

	/**
	 * sets the next state after the brainstate as BrainState object.
	 * @param nextState the next state after this state
	 */
	public void setNextState(BrainState nextState) {
		this.nextState = nextState;
	}

	/**
	 * Get the next state
	 * @return int
	 */
	public int getNextIdState() {
		return nextIdState;
	}

	/**
	 * Set the next state id
	 * @param int
	 */
	public void setNextIdState(int nextState) {
		this.nextIdState = nextState;
	}

	/**
	 * get the alternative next step as BrainState
	 * @return BrainState
	 */
	public BrainState getAltNextState() {
		return altNextState;
	}

	/**
	 * sets the alternative next state as BrainState
	 * @param altNextState of the alternative next state.
	 */
	public void setAltNextState(BrainState altNextState) {
		this.altNextState = altNextState;
	}

	/**
	 * @return int the altNextState as integer
	 */
	public int getAltNextIdState() {
		return altNextIdState;
	}

	/**
	 * sets the alternative next state as integer.
	 * @param altNextState the altNextState to set
	 */
	public void setAltNextIdState(int altNextState) {
		this.altNextIdState = altNextState;
	}

	/**
	 * @return the senseDirection
	 */
	public SenseDirection getSenseDirection() {
		return senseDirection;
	}

	/**
	 * @param senseDirection the senseDirection to set
	 */
	public void setSenseDirection(SenseDirection senseDirection) {
		this.senseDirection = senseDirection;
	}

	/**
	 * sets the sense direction based on a string passed to it and 
	 * converts it to a SenseDirection enum.
	 * @param dir string of the direction to sense
	 */
	public void setSenseDirection(String dir) {
		
		//make sure the string is in lower case
		dir.toLowerCase();
		
		if(dir.equals("here")){
			this.senseDirection = SenseDirection.HERE;
		}
		else if (dir.equals("ahead")){
			this.senseDirection = SenseDirection.AHEAD;
		}
		else if (dir.equals("leftahead")){
			this.senseDirection = SenseDirection.LEFTAHEAD;
		}
		else if (dir.equals("rightahead")){
			this.senseDirection = SenseDirection.RIGHTAHEAD;
		}
	}
	
	/**
	 * Get te sensed condition
	 * @return SenseCondition
	 */
	public SenseCondition getSenseCondition() {
		return senseCondition;
	}
	
	/**
	 * Set senseCondition
	 * @param SenseCondition
	 */
	public void setSenseCondition(SenseCondition senseCondition) {
		this.senseCondition = senseCondition;
	}

	/**
	 * sets the sense condition based on a string passed to it and converts it to a SenseCondition enum.
	 * @param String
	 */
	public void setSenseCondition(String senseCondition) {
		
		//make sure is to lower case
		senseCondition.toLowerCase();
		
		if(senseCondition.equals("friend")){
			this.senseCondition = SenseCondition.FRIEND;
		}
		else if(senseCondition.equals("foe")){
			this.senseCondition = SenseCondition.FOE;
		}
		else if(senseCondition.equals("friendwithfood")){
			this.senseCondition = SenseCondition.FRIENDWITHFOOD;
		}
		else if(senseCondition.equals("foewithfood")){
			this.senseCondition = SenseCondition.FOEWITHFOOD;
		}
		else if(senseCondition.equals("food")){
			this.senseCondition = SenseCondition.FOOD;
		}
		else if(senseCondition.equals("rock")){
			this.senseCondition = SenseCondition.ROCK;
		}
		else if(senseCondition.equals("marker")){
			this.senseCondition = SenseCondition.MARKER;
		}
		else if(senseCondition.equals("foemarker")){
			this.senseCondition = SenseCondition.FOEMARKER;
		}
		else if(senseCondition.equals("home")){
			this.senseCondition = SenseCondition.HOME;
		}
		else if(senseCondition.equals("foehome")){
			this.senseCondition = SenseCondition.FOE;
		}

	}
	
	/**
	 * @return the marker
	 */
	public Marker getMarker() {
		return marker;
	}
	
	/**
	 * Set the marker as marker id and ant colour
	 * @param int
	 * @param AntColour
	 */
	public void setMarker(int marker, AntColour colour) {
		try {
			this.marker = new Marker(marker,colour);
		} catch (InvalidMarkerIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the leftRight
	 */
	public LeftRight getLeftRight() {
		return leftRight;
	}
	
	/**
	 * Set direction
	 * @param LeftRight
	 */
	public void setLeftRight(LeftRight leftRight) {
		this.leftRight = leftRight;
	}

	/**
	 * sets the left right condition based on a string passed to it and converts it to a LeftRight enum.
	 * @param String
	 */
	public void setLeftRight(String lr) {
		
		//string to lower case
		lr.toLowerCase();
		
		if(lr.equals("left")){
			this.leftRight = LeftRight.LEFT;
		}
		else if(lr.equals("right")){
			this.leftRight = LeftRight.RIGHT;
		}
	}

	/**
	 * Get randomInt
	 * @return int
	 */
	public int getRandomInt() {
		return randomInt;
	}
	
	/**
	 * Set randomInt
	 * @param int
	 */
	public void setRandomInt(int randomInt) {
		this.randomInt = randomInt;
	}
	
	/**
	 * Sets the Colour of the attached Marker *if* the attached Marker is not null
	 * @param	colour	Sets the Marker colour if a marker exists
	 */
	public void setMarkerColourIfExists(AntColour colour) {
		if (this.marker != null) {
			marker.setColour(colour);
		}
	}

	/**
	 * print method not used other than for testing.
	 */
	public void print(){
		System.out.println("StateId: " + this.stateId +"\nInstructons: " + this.instruction + "\nNextiDState: " + this.nextIdState + "\nNextStateptr: " + this.nextState.stateId + "\nAltNextidState: "+ this.altNextIdState +"\nAltNextState: "+ this.altNextState.stateId +  "\nSenseDiection: " + this.senseDirection + "\nSenseCondition: " + this.senseCondition + "\nMarker: " + this.marker  +"\nTrun Dir: " + this.leftRight + "\nRandomInt: "+ this.randomInt +"\n");
	}
	public void print1(){
		System.out.println("StateId: " + this.stateId +"\nInstructons: " + this.instruction + "\nNextiDState: " + this.nextIdState /*+ "\nNextStateptr: " + this.nextState.stateId */+ "\nAltNextidState: "+ this.altNextIdState /*+"\nAltNextState: "+ this.altNextState.stateId */+  "\nSenseDiection: " + this.senseDirection + "\nSenseCondition: " + this.senseCondition + "\nMarker: " + this.marker  +"\nTrun Dir: " + this.leftRight + "\nRandomInt: "+ this.randomInt +"\n");
	}
}
