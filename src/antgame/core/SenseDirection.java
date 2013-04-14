/**
 * 
 */
package antgame.core;

/**
 * Enumerator for the cells is it possible for one Ant to Sense
 * @author Alex
 *
 */
public enum SenseDirection {
	HERE,AHEAD,LEFTAHEAD,RIGHTAHEAD;
	
	public String toString() {
		switch (this) {
			case HERE:
				return "Here";
			case AHEAD:
				return "Ahead";
			case LEFTAHEAD:
				return "LeftAhead";
			case RIGHTAHEAD:
				return "RightAhead";
			default:
				// Unreachable block
				return "";
		}
	}
	
}
