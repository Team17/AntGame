package antgame.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
			ArrayList<int[]> redAntHillLocations = new ArrayList<int[]>();
			ArrayList<int[]> blackAntHillLocations = new ArrayList<int[]>();
			while (linea != null) {
				linea = linea.replaceAll("\\s+","");;
				if (linea.length() != (xcomparesize)) {
					System.err.println("Map not the same width as in header1");
					rightsize = false;
				}
				for (int i = 0; i < linea.length(); i++) {
					String c = String.valueOf(linea.charAt(i));
					if (c.equals("#") || c.equals(".")|| (isInteger(c) ||c.equals(" "))) {

					} 
					else if(c.equals("+")){
						int[] red = new int[2];
						red[0] = i;
						red[1] = nolines;
						redAntHillLocations.add(red);
					}
					else if(c.equals("-")){
						int[] black = new int[2];
						black[0] = i;
						black[1] = nolines;
						blackAntHillLocations.add(black);
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
			
			if(!antHillChecker(redAntHillLocations) || !antHillChecker(blackAntHillLocations)){
				rightsize = false;
			}
			
			
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
	
	public static boolean antHillChecker(ArrayList<int[]> locations){
		String standard = "(0, 0)(1, 0)(2, 0)(3, 0)(4, 0)(5, 0)(6, 0)(0, 1)(1, 1)(2, 1)(3, 1)(4, 1)(5, 1)(6, 1)(7, 1)(-1, 2)(0, 2)(1, 2)(2, 2)(3, 2)(4, 2)(5, 2)(6, 2)(7, 2)(-1, 3)(0, 3)(1, 3)(2, 3)(3, 3)(4, 3)(5, 3)(6, 3)(7, 3)(8, 3)(-2, 4)(-1, 4)(0, 4)(1, 4)(2, 4)(3, 4)(4, 4)(5, 4)(6, 4)(7, 4)(8, 4)(-2, 5)(-1, 5)(0, 5)(1, 5)(2, 5)(3, 5)(4, 5)(5, 5)(6, 5)(7, 5)(8, 5)(9, 5)(-3, 6)(-2, 6)(-1, 6)(0, 6)(1, 6)(2, 6)(3, 6)(4, 6)(5, 6)(6, 6)(7, 6)(8, 6)(9, 6)(-2, 7)(-1, 7)(0, 7)(1, 7)(2, 7)(3, 7)(4, 7)(5, 7)(6, 7)(7, 7)(8, 7)(9, 7)(-2, 8)(-1, 8)(0, 8)(1, 8)(2, 8)(3, 8)(4, 8)(5, 8)(6, 8)(7, 8)(8, 8)(-1, 9)(0, 9)(1, 9)(2, 9)(3, 9)(4, 9)(5, 9)(6, 9)(7, 9)(8, 9)(-1, 10)(0, 10)(1, 10)(2, 10)(3, 10)(4, 10)(5, 10)(6, 10)(7, 10)(0, 11)(1, 11)(2, 11)(3, 11)(4, 11)(5, 11)(6, 11)(7, 11)(0, 12)(1, 12)(2, 12)(3, 12)(4, 12)(5, 12)(6, 12)";
		String standard2 = "(0, 0)(1, 0)(2, 0)(3, 0)(4, 0)(5, 0)(6, 0)(-1, 1)(0, 1)(1, 1)(2, 1)(3, 1)(4, 1)(5, 1)(6, 1)(-1, 2)(0, 2)(1, 2)(2, 2)(3, 2)(4, 2)(5, 2)(6, 2)(7, 2)(-2, 3)(-1, 3)(0, 3)(1, 3)(2, 3)(3, 3)(4, 3)(5, 3)(6, 3)(7, 3)(-2, 4)(-1, 4)(0, 4)(1, 4)(2, 4)(3, 4)(4, 4)(5, 4)(6, 4)(7, 4)(8, 4)(-3, 5)(-2, 5)(-1, 5)(0, 5)(1, 5)(2, 5)(3, 5)(4, 5)(5, 5)(6, 5)(7, 5)(8, 5)(-3, 6)(-2, 6)(-1, 6)(0, 6)(1, 6)(2, 6)(3, 6)(4, 6)(5, 6)(6, 6)(7, 6)(8, 6)(9, 6)(-3, 7)(-2, 7)(-1, 7)(0, 7)(1, 7)(2, 7)(3, 7)(4, 7)(5, 7)(6, 7)(7, 7)(8, 7)(-2, 8)(-1, 8)(0, 8)(1, 8)(2, 8)(3, 8)(4, 8)(5, 8)(6, 8)(7, 8)(8, 8)(-2, 9)(-1, 9)(0, 9)(1, 9)(2, 9)(3, 9)(4, 9)(5, 9)(6, 9)(7, 9)(-1, 10)(0, 10)(1, 10)(2, 10)(3, 10)(4, 10)(5, 10)(6, 10)(7, 10)(-1, 11)(0, 11)(1, 11)(2, 11)(3, 11)(4, 11)(5, 11)(6, 11)(0, 12)(1, 12)(2, 12)(3, 12)(4, 12)(5, 12)(6, 12)";
		int xStart = locations.get(0)[0];
		int yStart = locations.get(0)[1];
		String locString = "";
		for(int[] a:locations){
			locString += ("(" + (a[0]-xStart) + ", " + (a[1]-yStart) + ")");
		}
		//System.out.println(standard);
		//System.out.println(locString);
		//System.out.println(standard.equals(locString) +" "+ standard2.equals(locString));
		if(standard.equals(locString)){
			return true;
		}
		if(standard2.equals(locString)){
			return true;
		}
		return false;
	}
}
