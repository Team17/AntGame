package antgame.services;

import java.util.ArrayList;

/**
 * A psuedo-random number generator for the Ant Game as specified in the requirements document.
 *
 * This class provides a subset of the interface of @see java.util.Random, but uses a different algorithm.
 * 
 * @author Alex
 *
 */
public class RandomNumber {
	
	/**
	 * A class representing a series of numbers where the value of an individual number is
	 * dependent on another number in the same series or another series.
	 * 
	 * This class uses an ArrayList to cache a portion of the number series.  If a request is
	 * made to return a number that does not yet exist in this cache, the number series is
	 * generated up until the number at the requested position in the series.
	 * 
	 * Clearly there are benefits to this approach, as it guarantees that there will be no
	 * re-computation of the same task, for relatively little memory overhead.
	 * 
	 * @author Alex
	 *
	 */
	protected class NumberSeries {
		
		/**
		 ArrayList used to cache the number series that has been generated so far
		 */
		private ArrayList<Long> _series;
		
		/** A pointer to the NumberSeries on which this series of numbers depends
		 * This may be a self-referential pointer if the numbers in this series depend on
		 * numbers at other positions in this series.
		 */
		private NumberSeries _depNumberSeries;
		
		/**
		 * The relative position of the number on which some number in the series depends
		 * For example:
		 * 
		 * 	S(i) = S( i - 2 ) + 5	=>	_relIndex = - 2
		 * 	S(i) = 4 * S( i + 1 )	=>	_relIndex = + 1
		 *  S(i) = T( i + 4 ) - 3	=>	_relIndex = + 4
		 * 	
		 */
		private int _relIndex;
		
		/**
		 * The NumberGenerator class used to generate a number in the series.
		 * See @see antgame.services.NumberGenerator
		 */
		private NumberGenerator _gen;
		
		/**
		 * Constructor for a simple number series where the value of a particular number in the
		 * series is dependent on a number in the same series.
		 * @param	gen			A NumberGenerator class used to generate the next number in the series.
		 * @param	relIndex	A reference to the number in this series upon which the value of a given number depends.
		 * 						More specifically, the i'th value in the series depends on the (i + relIndex)'th value.
		 */
		protected NumberSeries(NumberGenerator gen, int relIndex) {
			_construct( gen , this , relIndex );
		}
		
		/**
		 * Constructor for a number series where the value of a particular number in the series
		 * is dependent on a number in different series.
		 * @param	gen			A NumberGenerator class used to generate the next number in the series.
		 * @param	depNumberSeries	The NumberSeries on which the values in this NumberSeries depend.
		 * @param	relIndex	A reference to the number in this series upon which the value of a given number depends.
		 * 						More specifically, the i'th value in the series depends on the (i + relIndex)'th value.
		 */
		protected NumberSeries(NumberGenerator gen, NumberSeries depNumberSeries, int relIndex) {
			_construct( gen , depNumberSeries , relIndex );
		}
		
		/**
		 * Internal constructor invoked by all NumberSeries constructors
		 * @see	antgame.services.Random.NumberSeries
		 */
		private void _construct(NumberGenerator gen, NumberSeries depNumberSeries, int relIndex) {
			this._gen = gen;
			this._depNumberSeries = depNumberSeries;
			this._relIndex = relIndex;
			_series = new ArrayList<Long>();
		}
		
		/**
		 * Empty the NumberSeries cache and set the first value as specified
		 * @param	firstValue	The first value of the number series
		 */
		public void reInitialise(long firstValue) {
			reset();
			_series.add(0,firstValue);
		}
		
		/**
		 * Empty this NumberSeries' cache
		 */
		public void reset() {
			_series = new ArrayList<Long>();
		}
		
		/**
		 * Add a number to the ArrayList cache
		 * @param	value	The value to add
		 */
		private void add(long value) {
			_series.add(value);
		}
		
		/**
		 * Add a number to the ArrayList cache at a given index
		 * @param	index	The index to add the value at
		 * @param	value	The value itself
		 */
		private void add(int index, long value) {
			_series.add(index,value);
		}
		
		/**
		 * Returns the number in the series at the specified index
		 * @param	index	The position of the number in the sequence
		 * @return			A number in the sequence at the specified position
		 */
		public long get(int index) {
			/*
			 * Recursion *

			 There has been a call to this function to return the i'th value in the series.
			 This NumberSeries has an ArrayList which is essentially a cache of every number
			 in the series (up to a point).  One of two things can happen here:
			 
			 		1.	We simply use ArrayList.get(i) and return the i'th value in the series
					2.	Turns out our ArrayList doesn't go up to i because we haven't generated
						the number series this far yet.
						
			In the latter case, we need to generate the i'th value.
			
			To generate the i'th value, we need to generate all the values *up to and including*
			i itself (this is the nature of a number series).  Our method here is not only to
			generate the number series but also to *store* all the values we generated in the
			ArrayList so that in the future we might be able to return an already-generated
			to avoid the need for re-computation.
			*/
			
			// If the i'th number hasn't been generated yet...
			if ( index >= _series.size() ) {
				// Generate the value based on the previous value in the number generator
				// As this is a recursive call, the ( i - 1 )'th value doesn't exist either,
				// the ( i - 2 )'th value will be generated and so on.
				_series.add( index , _gen.nextLong( _depNumberSeries.get( index + _relIndex ) ) );
			}
			// If it turns we've already generated the i'th value, return it
			return _series.get(index);
		}
		
	}
	
	/**
	 * A NumberGenerator class that generates the "S" number series used in this random number generator
	 * @author Alex
	 *
	 */
	private class sGenerator implements NumberGenerator {
		
		/**
		 * @see antgame.services.NumberGenerator.nextLong
		 */
		public long nextLong(long n) {
			return ( ( 22695477 * n ) + 1 );
		}
		
	}
	
	/**
	 * A NumberGenerator class that generates the "X" number series used in this random number generator
	 * @author Alex
	 *
	 */
	private class xGenerator implements NumberGenerator {
		
		/**
		 * @see antgame.services.NumberGenerator.nextLong
		 */
		public long nextLong(long n) {
			return ( ( n / 65536 ) % 16384 );
		}
	}

	/**
	 * The seed integer used in pseudo-random number generating algorithm
	 */
	private long seed;
	
	/**
	 * Integer value initialised at 0 and incremented upon each call to the nextInt method
	 */
	private int invokations;
	
	/**
	 * The "S" number series (see requirements specification)
	 */
	private NumberSeries _s;
	
	/**
	 * The "X" number series (see requirements specification)
	 */
	private NumberSeries _x;
	
	/**
	 * Constructor for RandomNumber using system time as seed integer
	 */
	public Random() {
		// java.util.Random uses this same method when no seed integer is passed
		setSeed(System.currentTimeMillis());
		_construct();
	}
	
	/**
	 * Constructor for RandomNumber using the specified seed integer
	 * @param	seed	The seed (long) integer
	 */
	public Random(long seed) {
		setSeed(seed);
		_construct();
	}

	/**
	 * Internal constructor method invoked by all RandomNumber constructors
	 */
	private void _construct() {
		this.invokations = 0;
		this._s = new NumberSeries( new sGenerator() , -1 );
		this._x = new NumberSeries( new xGenerator() , this._s, 4 );
	}
	
	/**
	 * Sets the seed of this pseudo-random number generator using a single long seed
	 * @param seed the seed to set
	 */
	public void setSeed(long seed) {
		this.seed = seed;
		_s.reInitialise(seed);
		_x.reset();
	}
	
	/**
	 * Returns a pseudo-random integer from 0 to n - 1
	 * Invoking this function increases the internal invokation counter by 1
	 * @param	n	The upper bound on the pseudo-random integer returned
	 * @return		A pseudo-random integer
	 */
	public int nextInt(int n) {
		invokations++;
		return (int) _x.get( getInvokations() ) % n;
	}
	
	/**
	 * Return the seed integer
	 * @return	The seed integer
	 */
	private long getSeed() {
		return seed;
	}

	/**
	 * Return the number of times the nextInt method has been invoked
	 * @return	The number of nextInt invokations during the current execution
	 */
	private int getInvokations() {
		return invokations;
	}
	
}
