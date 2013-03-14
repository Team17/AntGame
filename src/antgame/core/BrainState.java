/**
 * 
 */
package antgame.core;

/**
 * Enumerator for a single state of an Ant Brain
 * @author Alex
 *
 */
public enum BrainState {

	SENSE, MARK, UNMARK, PICKUP, DROP, TURN, MOVE, FLIP;
	
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
	private BrainState nextState;
	
	/**
	 * [Optional] The brain state the ant will be at if the provided condition is not met.
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
	public BrainState getNextState() {
		return nextState;
	}
	/**
	 * @param nextState the nextState to set
	 */
	public void setNextState(BrainState nextState) {
		this.nextState = nextState;
	}
	/**
	 * @return the altNextState
	 */
	public BrainState getAltNextState() {
		return altNextState;
	}
	/**
	 * @param altNextState the altNextState to set
	 */
	public void setAltNextState(BrainState altNextState) {
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
	/**
	 * @return the marker
	 */
	public Marker getMarker() {
		return marker;
	}
	/**
	 * @param marker the marker to set
	 */
	public void setMarker(Marker marker) {
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
	

	
}
