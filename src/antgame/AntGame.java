package antgame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class AntGame {
	
	/**
	 * Set the path to the properties file here.
	 * !!! TAKE CARE BEFORE CHANGING THIS LINE !!!
	 */
	private static final File PROPFILE = new File("Properties.xml");

	/**
	 * Core properties
	 * !!! DO NOT MODIFY THE FOLLOWING LINE !!!
	 */
	public static final Properties CONFIG = new Properties();
	
	private static void init() {
		
		// Load in the properties file
		try {
			
			CONFIG.loadFromXML(new FileInputStream(PROPFILE));
			
		} catch (InvalidPropertiesFormatException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (FileNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

		
	}
	
	public static void main(String[] args) {
		init();
	}
	
}
