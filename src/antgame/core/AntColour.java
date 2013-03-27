/**
 *
 */
package antgame.core;

/**
 * Enumerator for Ant colour
 * @author Alex
 *
 */
public enum AntColour {
	
	BLACK,RED;
	
	/**
	 * Returns the opposite colour to the one provided
	 * @param	color	The colour for which the opposite colour is requested
	 * @return			The opposite coluor to the one provided	
	 */
	public static AntColour otherColour(AntColour color) {
		switch (color) {
		case RED:
			return BLACK;
		case BLACK:
			return RED;
		default:
			// logically impossible to reach this block, but it keeps the compiler happy
			return null;
		}
	}
	
}
