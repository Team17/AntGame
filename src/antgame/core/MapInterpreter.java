package antgame.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapInterpreter {
	
	/**
	 * mapGenertator uses the translator method to produce a 2d array of strings based on the map that it is given it then creates a new cell for each string, and passes the
	 * coordinates and the string to the cell constructor, and adds the cell to a 2d array of cells and returns a new instance of map by passing this array to the constructor 
	 * for map
	 * @param textmap the string representation the location of map in path
	 * @return a map object.
	 */
	public static Map MapGenerator(String textmap){
		
		Cell[][] mapArray;
		String[][] stringMapArray = translator(textmap);
		mapArray = new Cell[stringMapArray.length][stringMapArray.length];
		for (int y = 0; y < (stringMapArray.length); y++) {
			for (int x = 0; x < (stringMapArray.length); x++) {
				mapArray[x][y] = new Cell(x, y, (stringMapArray[x][y]));
			}
		}
		return new Map(mapArray);
		
	}
	
	/**
	 * translator takes a text file of a map and converts it to a 2d array of strings representing the map.
	 * @param textmap
	 * @return
	 */
	public static String[][] translator(String textmap) {
		int xSize;
		int ySize;
		String[][] stringMapArray = null;
		//first we need to check that the map is legitimate by calling mapchecker.
		if (mapchecker(textmap)) {
			try {
				//Initiate a buffered reader to read form the file
				BufferedReader reader = new BufferedReader(new FileReader(
						textmap));
				//takes the size of the map from the first two lines the first line being the xsize and the second line being the ysize. also removing spaces
				xSize = Integer.parseInt(reader.readLine().replaceAll("\\s",""));
				ySize = Integer.parseInt(reader.readLine().replaceAll("\\s",""));
				// now we know the size of the map we can set up the 2d array of strings.
				stringMapArray = new String[xSize][ySize];
				// the following for loop finds reads each line and adds each character as to the array
				for (int y = 0; y < (ySize); y++) {
					String lineinput = reader.readLine().replaceAll("\\s","");
					for (int x = 0; x < (xSize); x++) {
						stringMapArray[x][y] = String.valueOf(lineinput
								.charAt(x));
					}
				}
				//closes the reader
				reader.close();

			}
			
			catch (FileNotFoundException e) {
				System.err.println("unable to open");

			} catch (IOException e) {
				System.err.println("a problem was encountered reading");
			}

		} 
		//if the checker does not accept the map then it prints "map not acceptable".
		else {
			System.err.println("Map not accectable");
		}
		
		return stringMapArray;
	}

	/**
	 * mapchecker literally check the map, it takes the dimensions of the map and then compares each line to the xdimention and checks that there are not more lines than the 
	 * dimension allows.
	 * @param textmap string representation of the path directory of the map file
	 * @return boolean if the map is legitimate
	 */
	public static Boolean mapchecker(String textmap) {
		int xcomparesize = 0;
		int ycomparesize = 0;
		
		Boolean rightsize = true;

		try {

			BufferedReader reader = new BufferedReader(new FileReader(textmap));
			//this gets the stated width of the map. removing spaces
			String firstline = reader.readLine().replaceAll("\\s","");
			//if the first line is an integer and is greater than zero and less than or equal to 150 then we set the comparexsize to this value.
			if ((isInteger(firstline)) && (0 < Integer.parseInt(firstline))	&& (Integer.parseInt(firstline) <= 150)) {
				xcomparesize = Integer.parseInt(firstline);
			} 
			else {
				System.err.println("Map Structure not accectable - width not in first line or not an accectable size or integer ");
				rightsize = false;
			}
			//this gets the stated height of the map. removing spaces
			String secondline = reader.readLine().replaceAll("\\s","");
			//if the second line is an integer and is greater than zero and less than or equal to 150 then we set the compareysize to this value.
			if ((isInteger(secondline)) && (0 < Integer.parseInt(secondline)) && (Integer.parseInt(secondline) <= 150)) {
				ycomparesize = Integer.parseInt(secondline);
			} 
			else{
				System.err.println("Map Structure not accectable - height not in second line or not an accectable size or integer ");
				rightsize = false;
			}
			
			//this is the counter for the number of lines
			int nolines = 0;
			String linea = reader.readLine().replaceAll("\\s","");
			
			while (linea != null) {
				linea = linea.replaceAll("\\s+","");;
				if (linea.length() != (xcomparesize)) {
					System.err.println("Map not the same width as in header1");
					rightsize = false;
				}
				for (int i = 0; i < linea.length(); i++) {
					String c = String.valueOf(linea.charAt(i));
					if (c.equals("#") || c.equals(".") || c.equals("+")
							|| c.equals("-") || (isInteger(c) ||c.equals(" "))) {

					} 
					else {
						System.err.println("map contains unknown character: " + c);
						rightsize = false;
					}
				}
				nolines++;
				linea = reader.readLine();
			}
			if (nolines != ycomparesize) {
				System.err.println("Map not the same height as in header");
				rightsize = false;
			}

			reader.close();

		}

		catch (FileNotFoundException e) {
			System.err.println("unable to open");

		} catch (IOException e) {
			System.err.println("a problem was encountered reading");
		}

		return rightsize;

	}
	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
