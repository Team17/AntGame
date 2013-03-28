package antgame.services;

import java.math.BigInteger;

/**
 * Pseudo-random number generator used for the "Flip" ant method.
 * Written according to the Project Overview specification
 * @author Alex test
 *
 */
public class RandomNumber {

	/**
	 * Current integer in the "X" series
	 */
	private long x;
	
	/**
	 * Current integer in the "S" series
	 */
	private BigInteger s;

	/**
	 * Constructor for RandomNumber using system time as seed integer
	 */
	public RandomNumber() {
		// java.util.Random uses this same method when no seed integer is passed
		s = BigInteger.valueOf(System.currentTimeMillis());
		_construct();
	}
	
	/**
	 * Constructor for RandomNumber using the specified seed integer
	 * @param	seed	The seed (long) integer
	 */
	public RandomNumber(long seed) {
		s = BigInteger.valueOf(seed);
		_construct();
	}
	
	/**
	 * Constructor method invoked by all RandomNumber constructors
	 */
	private void _construct() {
		// Generate up to S(4)
		for (int i = 0; i < 4; i++) {
			s = nextS();
		}
	}

	/**
	 * Generate the next integer in the "S" series
	 * @return	The next integer in the "S" series
	 */
	private BigInteger nextS() {
		BigInteger _s = s.multiply(BigInteger.valueOf(22695477));
		_s = _s.add(BigInteger.valueOf(1));
		return _s;
	}
	

	/**
	 * Return the next integer in the "X" series
	 * Note that nextS() should be assigned to this.s
	 * before nextX() is assigned to this.x as
	 * the next X value depends on the next S value
	 * @return
	 */
	private long nextX() {
		BigInteger _x = s.divide(BigInteger.valueOf(65536));
		_x = _x.mod(BigInteger.valueOf(16384));
		return _x.longValue();
	}
	
	/**
	 * Return a pseudo-random integer between 0 and n - 1
	 * @param	n	The upper bound on the integer returned
	 * @return		A pseudo-randomly-generated integer
	 */
	public int nextInt(int n) {
		s = nextS();
		x = nextX();
		return (int) (x % n);
	}
	
}
