package antgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import antgame.core.AntBrain;
import antgame.core.AntColour;

/**
 * Test class for AntBrain class
 * @author George
 * 
 */

public class AntBrainTest {
	
	private String workingDir = System.getProperty("user.dir");
	private AntBrain brain = new AntBrain(workingDir + "\\files\\TestBrainGood.brain",AntColour.RED);

	/**
	 * asserting that the constructor is not building a null brain
	 */
	@Test
	public void testAntBrain() {
		assertNotNull(brain);
	}

	/**
	 * asserting that the returned brain state is not null
	 */
	@Test
	public void testGetState() {
		assertNotNull(brain.getState(0));
	}

}
