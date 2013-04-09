package antgame.core;

import static org.junit.Assert.*;

import org.junit.Test;

public class AntBrainInterpreterCorynTest {
	
	private AntBrainInterpreterCoryn ABInt = new AntBrainInterpreterCoryn();
	private String workingDir = System.getProperty("user.dir");

	/**
	 * test if the array of regex is empty or not
	 */
	@Test
	public void testAntBrainInterpreterCoryn() {
		int regexLength; // = AntBrainInterpreterCoryn.getRegExpressionsLength();

		if (AntBrainInterpreterCoryn.getRegExpressions().size() > 0)
			regexLength = 1;
		else 
			regexLength = 0;
		assertEquals(1, regexLength);
	}

	/**
	 * test if an ant brain it can or cannot be generated
	 */
	@Test
	public void testAntBrainGenerator() {
		
		assertNotNull(ABInt.antBrainGenerator(workingDir + "\\files\\TestBrainGood.brain", AntColour.RED));
		assertNull(ABInt.antBrainGenerator(workingDir + "\\files\\TestBrainBad.brain", AntColour.RED));
	}

	/**
	 * is asserting if one brain is valid and the other is not valid
	 */
	@Test
	public void testAntBrainChecker() {

		assertTrue(AntBrainInterpreterCoryn.antBrainChecker(workingDir + "\\files\\TestBrainGood.brain"));
		assertFalse(AntBrainInterpreterCoryn.antBrainChecker(workingDir + "\\files\\TestBrainBad.brain"));
	}

	/**
	 * instantiate an Array of BrainState and two BrainState objects
	 * copy the first object into the second and the second into array
	 * make manually changes performed automatically by the tested method on object1
	 * call tested method on Array (which will perform changes on object2)
	 * compare if the two objects are equal == tested method performs as needed 
	 */
	
	@Test
	public void testUpdateBrainStates() {
				
		BrainState[] states = new BrainState[1];
		BrainState bs1 = new BrainState();
		BrainState bs2 = new BrainState();
		
		bs2 = bs1;
		
		states[0] = bs2;
		
		bs1.setNextState(bs1);
		bs1.setAltNextState(bs1);
		
		ABInt.updateBrainStates(states);
		
		assertEquals(bs1, states[0]);
	}
	
	/**
	 * test the method against both true and false returns
	 */
	@Test
	public void testIsInteger() {
		
		boolean temp = ABInt.isInteger("112");
		boolean temp2 = ABInt.isInteger("one112");

		assertEquals(true, temp);
		assertEquals(false, temp2);
	}

	/**
	 * is testing two regex (one it is into the arraylist and the other is not
	 */
	@Test
	public void testRegChecker() {
		final String regSense = "sense\\s(here|ahead|leftahead|rightahead)\\s[0-9]{1,4}\\s[0-9]{1,4}\\s(friend|foe|friendwithfood|foewithfood|food|rock|marker\\s\\d|foemarker|home|foehome)(|\\s;.*)";
		final String regWrong = "mark\\s(0|1|2|7|4|5)\\s[0-9]{1,4}(|\\s;.*)"; //replace 7 with 3 for this to work

		assertEquals(true, AntBrainInterpreterCoryn.getRegExpressions().contains(regSense));
		assertEquals(false, AntBrainInterpreterCoryn.getRegExpressions().contains(regWrong));
	}

}
