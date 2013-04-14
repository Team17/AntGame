/**
 * 
 */
package antgame.core;

/**
 * Enumerator for a direction in which an ant can sense
 * @author Alex
 *
 */
public enum SenseCondition {
	FRIEND,FOE,FRIENDWITHFOOD,FOEWITHFOOD,FOOD,ROCK,MARKER,FOEMARKER,HOME,FOEHOME;

	public String toString() {
		switch (this) {
			case FRIEND:
				return "Friend";
			case FOE:
				return "Foe";
			case FRIENDWITHFOOD:
				return "FriendWithFood";
			case FOEWITHFOOD:
				return "FoeWithFood";
			case FOOD:
				return "Food";
			case ROCK:
				return "Rock";
			case MARKER:
				return "Marker";
			case FOEMARKER:
				return "FoeMarker";
			case HOME:
				return "Home";
			case FOEHOME:
				return "FoeHome";
			default:
				// Unreachable block
				return "";
		}
	}
	
}
