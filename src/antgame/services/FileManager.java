package antgame.services;

import java.io.File;
import java.io.FileNotFoundException;

public class FileManager {

	/**
	 * Delete a directory and all it's contents.
	 * Note: to delete a file, see {@see #deleteFile(File)}
	 * @param	directory					The directory to delete
	 * @throws	FileNotFoundException		If the directory does not exist
	 */
	public static void delete(File file) throws FileNotFoundException {
		// Throw exception if file is non-existent
		if (!file.exists()) {
			throw new FileNotFoundException();
		// If the file is a directory, recursively delete it's contents
		} else if (file.isDirectory()) {
			for (File f: file.listFiles()) {
				delete(f);
			}
		}
		// If the file is a file, delete it
		file.delete();
	}
	
}
