/**
 * 
 */
package antgame.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import antgame.AntGame;

/**
 * @author Alex
 *
 */
public class AntGameTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link antgame.AntGame#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		AntGame instance = AntGame.getInstance();
		if (instance == null) {
			fail("Could not retrieve AntGame instance");
		}
	}
	
	@Test
	public void testAntGame() {

		try {
			
			// Check that all paths actually exist
			if (!AntGame.CONFIG_FILE.exists()) {
				fail("AntGame.PATH does not exist");
			}

			// Check that the CONFIG file loaded
			if (AntGame.CONFIG.isEmpty()) {
				fail("Properties file is empty!");
			}

		} catch (Exception e) {

			fail("Exception caught: " + e);
			e.printStackTrace();

		}
	}

}
