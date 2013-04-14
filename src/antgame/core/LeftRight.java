package antgame.core;

/**
 * Enumerated type for the Left/Right token used by the Turn command
 * @author Alex
 *
 */
public enum LeftRight {
	LEFT,RIGHT;
	
	public String toString() {
		switch (this) {
			case LEFT:
				return "Left";
			case RIGHT:
				return "Right";
			default:
				// Unreachable block
				return "";
		}
	}
	
}
