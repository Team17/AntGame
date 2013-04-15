package antgame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Main class for the Ant Game which follows the Singleton design pattern.
 * @author Alex
 *
 */
public class AntGame {
	
	// Core paths
	
	/**
	 * Path to execution directory
	 */
	public static final File SYS_DIR = new File(System.getProperty("user.dir"));
	
	/**
	 * Path to the user's Application Data folder.  Equivalent to %APPDATA% environmental variable in Windows.
	 */
	public static final File APP_DATA_DIR = new File(System.getenv("APPDATA"));
	
	/**
	 * Path to the CONFIG file
	 */
	public static final File CONFIG_FILE = new File("CONFIG.xml");
	
	// Core properties
	
	/**
	 * Instance object of the AntGame class
	 */
	private static final AntGame instance = new AntGame();
	
	/**
	 * AntGame system properties
	 */
	public static final AntGameProperties CONFIG = _initCONFIG();
	
	/**
	 * Line break character
	 */
	public static final String LINEBREAK = System.getProperty("line.separator");
	
	/**
	 * Returns the number of seconds passed since startTime (from System.nanoTime())
	 * @param	startTime	The start time (from System.nanoTime());
	 * @return				The number of seconds passed since startTime
	 */
	public static double timeSeconds(long startTime) {
		long endTime = System.nanoTime();
		long timePassed = endTime - startTime;
		Long t = new Long(timePassed);
		return t.doubleValue() / (1000000000);
	}
	
	/**
	 * AntGame initialiser
	 */
	private static void _init() {
		
	}
	
	/**
	 * Return the one and only instantiated AntGame object.
	 * @return	The AntGame instance.
	 */
	public static AntGame getInstance() {
		return instance;
	}
	
	private void wipeUserSettings() {
		
	}
	
	/**
	 * Determine whether the current user has played the game before.
	 * The decision is based on whether a User Preferences directory for
	 * AntGame exists and/or is empty.
	 * @return	True if the user has played before, false otherwise.
	 */
	public static boolean firstTimeUser() {
		if (!CONFIG.USER_PREFS_DIR.exists()) {
			return true;
		} else if (CONFIG.USER_PREFS_DIR.listFiles().length == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Creates a User Preferences directory and a user preferences file.
	 */
	private static void setUpUser() {
		CONFIG.USER_PREFS_DIR.mkdirs();
		// TODO: Generate user preferences file
	}
	
	/**
	 * Constructor used in place for main method for testing purposes
	 */
	private AntGame() {
		_init();
	}
	
	/**
	 * CONFIG initialiser
	 * @return
	 */
	private static AntGameProperties _initCONFIG() {
		try {
			return new AntGameProperties();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
	}
	
}
