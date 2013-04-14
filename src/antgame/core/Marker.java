/**
 * 
 */
package antgame.core;

import antgame.InvalidMarkerIdException;
import antgame.AntGame;

/**
 * Class representing a chemical marker
 * @author Alex
 *
 */
public class Marker {

	/**
	 * An integer Id for this particular marker
	 * (this must be no greater than n - 1 where n = the number of markers as defined in the config file)
	 */
	private int id;
	
	/**
	 * The colour of the Ant colony to which this marker belongs
	 */
	private AntColour color;
	
	/**
	 * Extraction of maximum legal number of ant markers from Properties file
	 */
	//private static int NUM_ANT_MARKERS = Integer.parseInt(AntGame.CONFIG.getProperty("numAntMarkers"));
	
	
	/**
	 * Hashcode generated based on this class's fields
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + id;
		return result;
	}

	/** Two markers are considered equal if their Ids and AntColors match
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Marker other = (Marker) obj;
		if (color != other.color) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		return true;
	}

	/**
	 * Construct a marker of particular Id and for a specified ant colony
	 * @param	id							The numerical ID of this marker ( where id is in the range 0 to ( numAntMarkers - 1 ) )
	 * @param	color						The colour of the ant colony to which this marker belongs
	 * @throws	InvalidMarkerIdException 	Exception thrown if the marker value lies outside the specified legal range
	 */
	public Marker (int id, AntColour color) throws InvalidMarkerIdException {
		
		// validity check
		if (id < 0 || id >6){ //NUM_ANT_MARKERS) {
			System.err.print("ime");
			throw new InvalidMarkerIdException();
			
		}
		
		this.id = id;
		this.color = color;
		
	}

	/**
	 * Get the Id of this marker
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Return the colour of the colony to which this marker belongs
	 * @return AntColour
	 */
	public AntColour getAntColor() {
		return color;
	}
	
	/**
	 * Set the Colour of this Marker
	 * @param	The Colour of this Marker
	 */
	public void setColour(AntColour colour) {
		this.color = colour;
	}
	
	public static void main(String[] args) throws InvalidMarkerIdException{
		
			Marker m1 = new Marker(1,AntColour.RED);
		
	}
}
