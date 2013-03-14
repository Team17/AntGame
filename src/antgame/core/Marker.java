/**
 * 
 */
package antgame.core;

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
	private AntColor color;
	
	/**
	 * Extraction of maximum legal number of ant markers from Properties file
	 */
	private static int NUM_ANT_MARKERS = Integer.parseInt(AntGame.CONFIG.getProperty("numAntMarkers"));
	
	/**
	 * Construct a marker of particular Id and for a specified ant colony
	 * @param	id							The numerical ID of this marker ( where id is in the range 0 to ( numAntMarkers - 1 ) )
	 * @param	color						The colour of the ant colony to which this marker belongs
	 * @throws	InvalidMarkerIdException 	Exception thrown if the marker value lies outside the specified legal range
	 */
	public Marker (int id, AntColor color) throws InvalidMarkerIdException {
		
		// validity check
		if (id < 0 || id > NUM_ANT_MARKERS) {
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
	 * @return the color
	 */
	public AntColor getAntColor() {
		return color;
	}
	
}
