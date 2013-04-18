package antgame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import antgame.core.AntBrain;

public class FileManager {

	/**
	 * Saves or creates a text file with the specified path with the specified content
	 * @param	path		Path to the file
	 * @param	content		Content of the file
	 * @throws	IOException
	 */
	public static void saveFile(String path, String content) throws IOException {
		
		// Create the file
		File file = new File (AntGame.SYS_DIR + "\\" + path);
		file.createNewFile();
		
		// Write the content
		FileOutputStream fos = new FileOutputStream(file,false);
		fos.write(content.getBytes(AntGame.CONFIG.DEFAULT_ENCODING));
		fos.close();
		
	}
	
	/**
	 * Read in a text file
	 * @param	path		Path to the file
	 * @return				Content of the file
	 * @throws 	IOException
	 */
	public static String readFile(String path) throws IOException {
		// Find the file
		File file = new File (AntGame.SYS_DIR + "\\" + path);
		// Pull out the contents
		byte[] bytes = Files.readAllBytes(file.toPath());
		// Return as a string
		return new String(bytes,AntGame.CONFIG.DEFAULT_ENCODING);
	}
	
	/**
	 * Create a new folder in the AntGame system directory
	 * @param	folderName	The name of the folder to create
	 */
	public static void createFolder(String folderName) {
		File file = new File(AntGame.SYS_DIR + "\\" + folderName);
		file.mkdir();
	}
	
	/**
	 * Save an AntBrain to the specified location
	 * @param	antBrain		The AntBrain to save
	 * @param	pathFileName	Specified file name and path relative to the AntGame system path
	 */
	public static void saveBrain(AntBrain antBrain, String pathFileName) {
		try {
			saveFile(pathFileName + ".brain",antBrain.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
