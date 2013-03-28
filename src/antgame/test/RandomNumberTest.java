/**
 * 
 */
package antgame.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import antgame.services.RandomNumber;

/**
 * @author Alex
 *
 */
public class RandomNumberTest {

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
	 * Test method for {@link antgame.services.RandomNumber#nextInt(int)}.
	 * 
	 * This test determines whether the RandomNumber class generates a random number sequence equal
	 * to the client's random number generator where the following conditions are met:
	 * 
	 * -> Initial seed is 12345
	 * -> Value of n is greater than 16383
	 * 
	 */
	@Test
	public void testNextInt() {
		
		Random random = new Random();
		
		int testSeed = 12345;
		int testN = random.nextInt() + 16384;
		
		int[] testSequence = {2932, 10386, 5575, 100, 15976, 430, 9740, 9449, 1636, 11030, 9848, 13965, 16051, 14483, 6708, 5184, 15931,
				7014, 461, 11371, 5856, 2136, 9139, 1684, 15900, 10236, 13297, 1364, 6876, 15687, 14127, 11387, 13469, 11860,
				15589, 14209, 16327, 7024, 3297, 3120, 842, 12397, 9212, 5520, 4983, 7205, 7193, 4883, 7712, 6732, 7006, 10241,
				1012, 15227, 9910, 14119, 15124, 6010, 13191, 5820, 14074, 5582, 5297, 10387, 4492, 14468, 7879, 8839, 12668,
				5436, 8081, 4900, 10723, 10360, 1218, 11923, 3870, 12071, 3574, 12232, 15592, 12909, 9711, 6638, 2488, 12725,
				16145, 9746, 9053, 5881, 3867, 10512, 4312, 8529, 1576, 15803, 5498, 12730, 7397};
		
		RandomNumber randomNumber = new RandomNumber(testSeed);
		
		for (int i = 0; i < testSequence.length; i++) {
			
			int theoretical = testSequence[i];
			int actual = randomNumber.nextInt(testN);
			
			if (theoretical != actual) {
				fail("Expected " + theoretical + ", retrieved " + actual);
			}
		}
	}
	
	public static void main(String[] args) {
		RandomNumberTest rnt = new RandomNumberTest();
		rnt.testNextInt();
	}

}
