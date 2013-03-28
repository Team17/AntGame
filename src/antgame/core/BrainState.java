/**
 * 
 */
package antgame.core;

/**
 * Enumerator for a single state of an Ant Brain
 * @author Alex
 *
 */
public class BrainState {

	private Instruction instruction;
	
	public Instruction getInstruction(){
		return instruction;
	}
	public void setInstruction(Instruction ins){
		instruction = ins;
	}
	
	/**
	 * The numerical identifier of this brain state within an ant brain.
	 */
	private int	stateId;
	
	/**
	 * The next brain state the ant will have after this one.
	 * 
	 * In cases where the next brain state depends on some condition, this is the state
	 * where the ant will be when that condition is met.
	 */
	private int nextState;
	
	/**
	 * [Optional] The brain state the ant will be at if the provided condition is not met.
	 */
	private int altNextState;
	
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
	private int marker;
	
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
	 * @return the nextState
	 */
	public int getNextState() {
		return nextState;
	}
	/**
	 * @param nextState the nextState to set
	 */
	public void setNextState(int nextState) {
		this.nextState = nextState;
	}
	/**
	 * @return the altNextState
	 */
	public int getAltNextState() {
		return altNextState;
	}
	/**
	 * @param altNextState the altNextState to set
	 */
	public void setAltNextState(int altNextState) {
		this.altNextState = altNextState;
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
	
	public void setSenseDirection(String dir) {
		if(dir.equals("here")){
			this.senseDirection = SenseDirection.HERE;
		}
		else if (dir.equals("ahead")){
			this.senseDirection = SenseDirection.AHEAD;
		}
		else if (dir.equals("lefthead")){
			this.senseDirection = SenseDirection.LEFTAHEAD;
		}
		else if (dir.equals("rightahead")){
			this.senseDirection = SenseDirection.RIGHTAHEAD;
		}
		
	}
	/**
	 * @return the senseCondition
	 */
	public SenseCondition getSenseCondition() {
		return senseCondition;
	}
	/**
	 * @param senseCondition the senseCondition to set
	 */
	public void setSenseCondition(SenseCondition senseCondition) {
		this.senseCondition = senseCondition;
	}
	public void setSenseCondition(String scon) {
		
	
	if(scon.equals("friend")){
		this.senseCondition = SenseCondition.FRIEND;
	}
	else if(scon.equals("foe")){
		this.senseCondition = SenseCondition.FOE;
	}
	else if(scon.equals("friendwithfood")){
		this.senseCondition = SenseCondition.FRIENDWITHFOOD;
	}
	else if(scon.equals("foewithfood")){
		this.senseCondition = SenseCondition.FOEWITHFOOD;
	}
	else if(scon.equals("food")){
		this.senseCondition = SenseCondition.FOOD;
	}
	else if(scon.equals("rock")){
		this.senseCondition = SenseCondition.ROCK;
	}
	else if(scon.equals("marker")){
		this.senseCondition = SenseCondition.MARKER;
	}
	else if(scon.equals("foemarker")){
		this.senseCondition = SenseCondition.FOEMARKER;
	}
	else if(scon.equals("home")){
		this.senseCondition = SenseCondition.HOME;
	}
	else if(scon.equals("foehome")){
		this.senseCondition = SenseCondition.FOE;
	}
	
	
}
	/**
	 * @return the marker
	 */
	public int getMarker() {
		return marker;
	}
	/**
	 * @param marker the marker to set
	 */
	public void setMarker(int marker) {
		this.marker = marker;
	}
	/**
	 * @return the leftRight
	 */
	public LeftRight getLeftRight() {
		return leftRight;
	}
	/**
	 * @param leftRight the leftRight to set
	 */
	public void setLeftRight(LeftRight leftRight) {
		this.leftRight = leftRight;
	}
	
	public void setLeftRight(String lr) {
		if(lr.equals("left")){
			this.leftRight = LeftRight.LEFT;
		}
		else if(lr.equals("right")){
			this.leftRight = LeftRight.RIGHT;
		}
	}
	
	/**
	 * @return the randomInt
	 */
	public int getRandomInt() {
		return randomInt;
	}
	/**
	 * @param randomInt the randomInt to set
	 */
	public void setRandomInt(int randomInt) {
		this.randomInt = randomInt;
	}
	
	public void print(){
		System.out.println("StateId: " + this.stateId +"\nInstructons: " + this.instruction + "\nNextState: " + this.nextState + "\nAltNextState: "+ this.altNextState + "\nSenseDiection: " + this.senseDirection + "\nSenseCondition: " + this.senseCondition + "\nMarker: " + this.marker +"\nTrun Dir: " + this.leftRight + "\nRandomInt: "+ this.randomInt +"\n");
	}
	

	
}
