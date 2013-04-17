package antgame;
import guiAntGame.MainMenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Sub-class of Java.util.Properties with Ant Game specific named constants for convenience.
 * @author Alex
 *
 */
public class AntGameProperties extends Properties {

	/**
	 * Alex: Not entirely sure what this is for, something to do with Object
	 * serialisation which I don't know anything about.  Keeps Eclipse happy, though.
	 */
	private static final long serialVersionUID = 6760091941922810995L;

	/**
	 * The number of different types of Markers an Ant can place in a Cell
	 */
	public final int NUM_MARKERS;
	
	/**
	 * The x/y dimension of a Map than conforms to Contest requirements (measured in number of Cells)
	 */
	public final int CONTEST_MAP_DIMENSION;

	/**
	 * The number of Rocks that must be present in a Contest Map
	 * (excluding the perimeter cells which are always rocky)
	 */
	public final int CONTEST_MAP_ROCKS;

	/**
	 * The number of Food Blobs that must be present in a Contest Map
	 */
	public final int CONTEST_MAP_FOOD_BLOBS;

	/**
	 * The side-length of an Anthill in a Contest Map (measured in cells).
	 * Anthills are hexagons with the specified side-length.
	 */
	public final int CONTEST_MAP_ANTHILL_SIDE_LENGTH;

	/**
	 * The side-length of a Food Blob in a Contest Map (measured in cells).
	 * Food blobs are rectangles with the specified side-length.
	 */
	public final int CONTEST_MAP_FOOD_BLOB_SIDE_LENGTH;

	/**
	 * The number of food particles in a Cell comprising a Food Blob.
	 */
	public final int CONTEST_MAP_FOOD_PER_BLOB_CELL;
	
	/**
	 * The maximum number of lines an Ant Brain file can consist of.
	 * This is also the maximum number of BrainStates and Ant Brain can consist of.
	 */
	public final int MAX_ANT_BRAIN_LINES;
	
	/**
	 * The number of rounds an individual game will last.
	 * This is the number of times the World.step() method is invoked.
	 */
	public final int GAME_ROUNDS;
	
	/**
	 * The number of rounds an Ant needs to rest before it can move again.
	 */
	public final int RESTING_ROUNDS;
	
	/**
	 * The default direction that every Ant will be facing at the start of a Game.
	 */
	public final int DEFAULT_ANT_DIRECTION;

	/**
	 * The directory Ant Game uses to store user preferences.
	 */
	public final File USER_PREFS_DIR;

	/**
	 * The minimum number of AntBrains required for a Tournament
	 */
	public final int MIN_TOURANAMENT_BRAINS;

	/**
	 * The maximum number of AntBrains that can partake in the same Tournament
	 */
	public final int MAX_TOURANAMENT_BRAINS;
	
	/**
	 * The score awarded to an AntBrain that won a Game
	 */
	public final int SCORE_FOR_WIN;
	
	/**
	 * The score awarded to an AntBrain that drew a Game
	 */
	public final int SCORE_FOR_DRAW;
	
	/**
	 * The score awarded to an AntBrain that LOST THE GAME
	 */
	public final int SCORE_FOR_LOSS;
	
	/**
	 * The initial total score of every AntBrain at the beginning of a Tournament
	 */
	public final int INITIAL_TOURNAMENT_SCORE;
	
	/**
	 * The default encoding used for Strings
	 */
	public final String DEFAULT_ENCODING;
	
	/**
	 * Constructor
	 * @param	antGame	The AntGame object that is creating this AntGameProperties object
	 * @throws Exception
	 */
	public AntGameProperties() throws Exception {
		File asFile = null;
	    try {
	    	   
	    	Path tmp_1 = Files.createTempDirectory(null);
		    asFile = tmp_1.toFile();
		    asFile.deleteOnExit();

	    	InputStream copy_from = MainMenu.class.getResourceAsStream("/CONFIG.xml");
		    Path copy_to= Paths.get(tmp_1.toString(),"CONFIG.xml");
		    Files.copy(copy_from, copy_to);
	    } catch (Exception e) {
	    System.err.println(e);
	    }
		
		
		loadFromXML(new FileInputStream(new File(asFile.getAbsoluteFile()+"/CONFIG.xml")));
		// ** (Optional) Pull individual properties into named constants for easier access **
		// This is optional as properties are still accessible using the Properties.getProperty(String key) : String method.
		NUM_MARKERS = Integer.parseInt(this.getAntGameProperty("numAntMarkers"));
		CONTEST_MAP_DIMENSION = Integer.parseInt(this.getAntGameProperty("contestMapDimension"));
		MAX_ANT_BRAIN_LINES = Integer.parseInt(this.getAntGameProperty("maxAntBrainLines"));
		GAME_ROUNDS = Integer.parseInt(this.getAntGameProperty("gameRounds"));
		RESTING_ROUNDS = Integer.parseInt(this.getAntGameProperty("restingRounds"));
		CONTEST_MAP_ROCKS = Integer.parseInt(this.getAntGameProperty("contestMapRocks"));
		CONTEST_MAP_FOOD_BLOBS = Integer.parseInt(this.getAntGameProperty("contestMapFoodBlobs"));
		CONTEST_MAP_ANTHILL_SIDE_LENGTH = Integer.parseInt(this.getAntGameProperty("contestMapAnthillSideLength"));
		CONTEST_MAP_FOOD_BLOB_SIDE_LENGTH = Integer.parseInt(this.getAntGameProperty("contestMapFoodBlobSideLength"));
		CONTEST_MAP_FOOD_PER_BLOB_CELL = Integer.parseInt(this.getAntGameProperty("contestMapFoodPerBlobCell"));
		DEFAULT_ANT_DIRECTION = Integer.parseInt(this.getAntGameProperty("defaultAntDirection"));
		USER_PREFS_DIR = new File(AntGame.APP_DATA_DIR + "\\" + this.getAntGameProperty("userPrefsDir"));
		MIN_TOURANAMENT_BRAINS = Integer.parseInt(this.getAntGameProperty("minTournamentBrains"));
		MAX_TOURANAMENT_BRAINS = Integer.parseInt(this.getAntGameProperty("maxTournamentBrains"));
		SCORE_FOR_WIN = Integer.parseInt(this.getAntGameProperty("scoreForWin"));
		SCORE_FOR_DRAW = Integer.parseInt(this.getAntGameProperty("scoreForDraw"));
		SCORE_FOR_LOSS = Integer.parseInt(this.getAntGameProperty("scoreForLoss"));
		INITIAL_TOURNAMENT_SCORE = Integer.parseInt(this.getAntGameProperty("initialTournamentScore"));
		DEFAULT_ENCODING = this.getAntGameProperty("defaultEncoding");
	}

	/**
	 * Retrieve a property.  Can be used instead of one of the named constants of
	 * this class, or to retrieve properties that do not have named constants.
	 * 
	 * Will throw a NoSuchPropertyException if the named property does not exist.
	 * @param	key						The name of the property
	 * @return							The property value
	 * @throws	NoSuchPropertyException	If the named property does not exist.
	 */
	public String getAntGameProperty(String key) throws NoSuchPropertyException {
		String _prop = super.getProperty(key);
		if (_prop == null) {
			throw new NoSuchPropertyException();
		}
		return _prop;
	}
	
}
