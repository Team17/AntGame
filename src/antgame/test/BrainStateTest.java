package antgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import antgame.core.AntColour;
import antgame.core.BrainState;
import antgame.core.Instruction;
import antgame.core.LeftRight;
import antgame.core.SenseCondition;
import antgame.core.SenseDirection;

/**
 * Unit testing for BrainState class
 * @author George
 *
 */
public class BrainStateTest {

	private BrainState bs = new BrainState();
	
	@Test
	public void testGetInstruction() {
		assertNull("Test for NULL Instruction on initialization time", bs.getInstruction());
	}

	@Test
	public void testSetInstruction() {
		
		//test against setting all instruction one at a time
		for(Instruction i: Instruction.values()) {
			bs.setInstruction(i);
			assertEquals("Test for Instruction ==" + i, i, bs.getInstruction());
		}
	}

	@Test
	public void testGetStateId() {
		assertEquals("Test for stateId == 0 on initialization", 0, bs.getStateId());
	}

	@Test
	public void testSetStateId() {

		//update stateId with a certain value
		bs.setStateId(123);
		assertEquals("Test for stateId == 123", 123, bs.getStateId());
	}

	@Test
	public void testGetNextState() {
		assertEquals("Test for nextState == Null on initialization", null, bs.getNextState());
	}

	@Test
	public void testSetNextState() {

		//initialize a new state and connect it with the first one
		BrainState bs2 = new BrainState();
		bs2.setStateId(23);
		bs.setNextState(bs2);
		assertEquals("Test for nextStateId == 23", bs2.getStateId(), bs.getNextState().getStateId());
	}

	@Test
	public void testGetNextIdState() {
		assertEquals("Test for nextStateId == 0 on initialization", 0, bs.getNextIdState());;
	}

	@Test
	public void testSetNextIdState() {
		
		//assign as next state a testing int = 77
		bs.setNextIdState(77);
		assertEquals("Test against next state id == 77", 77, bs.getNextIdState());;
	}

	@Test
	public void testGetAltNextState() {
		assertEquals("Testing against BrainState bs2 == null on initialization", null, bs.getAltNextState());
	}

	@Test
	public void testSetAltNextState() {

		//initialize a new BrainState object for testing purposes
		BrainState bs2= new BrainState();
		//add it to the nextState of bs
		bs.setAltNextState(bs2);
		assertEquals("Test against BrainState object", bs2, bs.getAltNextState());
	}

	@Test
	public void testGetAltNextIdState() {
		assertEquals("Test against next state id == 0 on initialization", 0, bs.getAltNextIdState());
	}

	@Test
	public void testSetAltNextIdState() {

		//pick random numer 45 as next state id
		bs.setAltNextIdState(45);
		assertEquals("Test against state id == 45", 45, bs.getAltNextIdState());
	}

	@Test
	public void testGetSenseDirection() {
		assertNull("Testing for sense direction null at initialization", bs.getSenseDirection());
	}

	@Test
	public void testSetSenseDirectionSenseDirection() {

		//test the set sense diretion method against all enums from SenseDirection
		for(SenseDirection sd: SenseDirection.values()){
			bs.setSenseDirection(sd);
			assertEquals("Test against sense direction " + sd, sd, bs.getSenseDirection());
		}
	}

	@Test
	public void testSetSenseDirectionString() {

		//test by iterating through all values from SenseDirection and cnvert them into String
		for(SenseDirection sd: SenseDirection.values()){
			bs.setSenseDirection(sd);
			assertEquals("Test against sense direction " + sd.toString(), sd.toString(), bs.getSenseDirection().toString());
		}
	}

	@Test
	public void testGetSenseCondition() {
		assertNull("Test against null sense condition on initialization", bs.getSenseCondition());
	}

	@Test
	public void testSetSenseConditionSenseCondition() {

		//test against all values of SenseCodition
		for(SenseCondition sc: SenseCondition.values()){
			bs.setSenseCondition(sc);
			assertEquals("Test for SenseCondition == " + sc, sc, bs.getSenseCondition());
		}
	}

	@Test
	public void testSetSenseConditionString() {
		
		//test against all values of SenseCodition
		for(SenseCondition sc: SenseCondition.values()){
			bs.setSenseCondition(sc.toString());
			assertEquals("Test for SenseCondition == " + sc.toString(), sc.toString(), bs.getSenseCondition().toString());
		}
	}

	@Test
	public void testGetMarker() {
		assertNull("Test against null marker at initialization", bs.getMarker());
	}

	@Test
	public void testSetMarker() {

		for(int i = 0; i < 6; i++){
			for(AntColour ac: AntColour.values()){
				bs.setMarker(i, ac);
				assertEquals("Test for Marker id = " + i, i, bs.getMarker().getId());
				assertEquals("Test for Marker with ant color = " + ac, ac, bs.getMarker().getAntColor());
			}
		}
	}

	@Test
	public void testGetLeftRight() {
		assertNull("Test for Null direction on initialization", bs.getLeftRight());
	}

	@Test
	public void testSetLeftRightLeftRight() {

		//test against all values of LeftRight
		for(LeftRight lr: LeftRight.values()){
			bs.setLeftRight(lr);
			assertEquals("Test against direction = " + lr, lr, bs.getLeftRight());
		}
	}

	@Test
	public void testSetLeftRightString() {
		
		//test against all values of LeftRight
		for(LeftRight lr: LeftRight.values()){
			bs.setLeftRight(lr.toString());
			assertEquals("Test against direction = " + lr.toString(), lr.toString(), bs.getLeftRight().toString());
		}
	}

	@Test
	public void testGetRandomInt() {
		assertEquals("Test against randomInt = 0 on initialization", 0, bs.getRandomInt());
	}

	@Test
	public void testSetRandomInt() {

		bs.setRandomInt(199);
		assertEquals("Test against randomInt = 199", 199, bs.getRandomInt());
	}

//	not implemented, used for developing only
//	@Test
//	public void testPrint() {
//		fail("Not yet implemented");
//	}

}
