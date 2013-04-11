package antgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import antgame.core.Ant;
import antgame.core.AntBrain;
import antgame.core.AntColour;
import antgame.core.BrainState;
import antgame.core.Cell;
import antgame.core.LeftRight;

/**
 * Test unit for Ant class
 * @author George
 *
 */
public class AntTest {

	private String workingDir = System.getProperty("user.dir");
	private static BrainState bs = new BrainState();
	private static Cell cell = new Cell(5, 5, "#");
	private AntBrain brain = new AntBrain(workingDir + "\\files\\TestBrainGood.brain",AntColour.RED);
	private Ant testAnt = new Ant(123, 3, AntColour.RED, bs.getStateId(), cell, brain);
	
	@Test
	public void testAnt() {
		assertNotNull(testAnt);
	}

	@Test
	public void testGetuID() {
		assertEquals(123, testAnt.getuID());
	}

	@Test
	public void testGetDir() {
		assertEquals(3, testAnt.getDir());
	}

	@Test
	public void testSetDir() {
		testAnt.setDir(1);
		assertEquals(1, testAnt.getDir());
	}

	@Test
	public void testGetColour() {
		assertEquals(AntColour.RED, testAnt.getColour());
	}

	@Test
	public void testGetBrainState() {
		assertEquals(bs.getStateId(), testAnt.getBrainState().getStateId());
	}

	@Test
	public void testSetBrainState() {
		
		BrainState bs2 = new BrainState();
		testAnt.setBrainState(bs2);
		assertEquals(bs2, testAnt.getBrainState());
	}

	@Test
	public void testSetResting() {
		assertEquals("Check to see if the initial number of rest rounds is zero", 0, testAnt.getResting());
		//make the ant rest
		testAnt.setResting();
		assertEquals("Check to see if is setting the correct number of rest rounds", Ant.getRestingLimit(), testAnt.getResting());
	}

	@Test
	public void testGetResting() {
		testAnt.setResting();
		assertEquals(Ant.getRestingLimit(), testAnt.getResting());
	}

	@Test
	public void testDecResting() {
		testAnt.setResting();
		testAnt.decResting();
		assertEquals(Ant.getRestingLimit() - 1, testAnt.getResting());
	}

	@Test
	public void testIsResting() {
		
		if (testAnt.getResting() != 0)
			assertTrue("Asserting that the Ant is resting", testAnt.isResting());
		else 
			assertFalse("Asserting that the Ant is NOT resting", testAnt.isResting());
	}

	@Test
	public void testIsHasFood() {
		assertFalse("Initially it does NOT have food", testAnt.isHasFood());
	}

	@Test
	public void testPickupFood() {
		//make the ant pickup food
		testAnt.pickupFood();
		assertTrue("Now it should have food", testAnt.isHasFood());
	}

	@Test
	public void testDropFood() {
		//make the ant drop food
		testAnt.dropFood();
		assertFalse("Does NOT have food", testAnt.isHasFood());
	}

	@Test
	public void testTurn() {
		//first test turn left
		testAnt.turn(LeftRight.LEFT);
		assertEquals(2, testAnt.getDir());
		
		//then turn right
		testAnt.turn(LeftRight.RIGHT);
		assertEquals(3, testAnt.getDir());
	}

	@Test
	public void testGetCurrentPos() {
		assertEquals(cell, testAnt.getCurrentPos());
	}

	@Test
	public void testSetCurrentPos() {
		//create a new Cell for testin purposes
		Cell testCell = new Cell(2, 2, "+");
		testAnt.setCurrentPos(testCell);
		assertEquals(testCell, testAnt.getCurrentPos());
	}

	@Test
	public void testIsAlive() {
		assertTrue(testAnt.isAlive());
	}

	@Test
	public void testDie() {
		//kill the ant
		testAnt.die();
		assertFalse(testAnt.isAlive());
	}

}
