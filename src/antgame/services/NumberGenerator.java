package antgame.services;

/**
 * A NumberGenerator class is one which implements a function to generate the next
 * number in a series given a dependent number.
 * 
 * A NumberGenerator must be passed into a Random.NumberSeries class at construction
 * 
 * @author Alex
 *
 */
public interface NumberGenerator {
	
	/**
	 * Return the next (long) integer in a series from some dependent number.
	 * 
	 * For a simple series, this may simply be the previous number in the series.
	 * 
	 * @param	n		The dependent number
	 * @return	long	The next number in the series
	 */
	public long nextLong(long n);
	
}
