package antgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import antgame.core.AntColour;

/**
 * Test unit for AntColour class
 * @author George
 *
 */
public class AntColourTest {

	@Test
	public void testOtherColourAntColour() {
		assertEquals("Test for colour BLACK", AntColour.BLACK, AntColour.otherColour(AntColour.RED));
		assertEquals("Test for colour RED", AntColour.RED, AntColour.otherColour(AntColour.BLACK));
	}

}
