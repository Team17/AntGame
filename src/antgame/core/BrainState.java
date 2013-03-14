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

	SENSE, MARK, UNMARK, PICKUP, DROP, TURN, MOVE, FLIP
	
	private int stateId;
	private BrainState nextState;
	private BrainState altNextState;
	private SenseDirection senseDirection;
	private SenseCondition senseCondition;
	private Marker marker;
	private LeftRight leftRight;
	private int randomInt;
	
}
