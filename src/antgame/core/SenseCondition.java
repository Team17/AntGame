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
	
	private Marker marker;

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}
	
}
