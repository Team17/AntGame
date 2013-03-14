package antgame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

public class PropertiesGenerator {
	
	private static void updateProps() {
		
		// Load in the properties file
		try {
			
			AntGame.CONFIG.loadFromXML(new FileInputStream(AntGame.PROPFILE));
			
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
}
