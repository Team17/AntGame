package antgame.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map {
	private int xSize;
	private int ySize;
	private Cell[][] mapArray;
	private String[][] stringMapArray;

	public Map( String mapTextFile) {
		

		translator(mapTextFile);
		mapArray = new Cell[xSize][ySize];
		for (int y = 0; y < (ySize); y++) {
			for (int x = 0; x < (xSize); x++) {
				mapArray[x][y] = new Cell(x, y, (stringMapArray[x][y]));
				
			}
		}
		
	}

	public String[][] translator(String textmap) {
		if (mapchecker(textmap)) {
			try {

				BufferedReader reader = new BufferedReader(new FileReader(
						textmap));

				xSize = Integer.parseInt(reader.readLine().replaceAll("\\s",""));
				ySize = Integer.parseInt(reader.readLine().replaceAll("\\s",""));

				stringMapArray = new String[xSize][ySize];

				for (int y = 0; y < (ySize); y++) {
					String lineinput = reader.readLine().replaceAll("\\s","");
					for (int x = 0; x < (xSize); x++) {
						stringMapArray[x][y] = String.valueOf(lineinput
								.charAt(x));
					}
				}
				reader.close();

			}

			catch (FileNotFoundException e) {
				System.err.println("unable to open");

			} catch (IOException e) {
				System.err.println("a problem was encountered reading");
			}

		} 
		else {
			System.err.println("Map not accectable");
		}
		return stringMapArray;
	}

	public Boolean mapchecker(String textmap) {
		int xcomparesize = 0;
		int ycomparesize = 0;
		
		Boolean rightsize = true;

		try {

			BufferedReader reader = new BufferedReader(new FileReader(textmap));

			String firstline = reader.readLine().replaceAll("\\s","");
			if ((isInteger(firstline)) && (0 < Integer.parseInt(firstline))
					&& (Integer.parseInt(firstline) <= 150)) {
				xcomparesize = Integer.parseInt(firstline);
			} else {
				System.err
						.println("Map Structure not accectable - width not in first line or not an accectable size or integer ");
				rightsize = false;
			}

			String secondline = reader.readLine().replaceAll("\\s","");
			if ((isInteger(secondline)) && (0 < Integer.parseInt(secondline))
					&& (Integer.parseInt(secondline) <= 150)) {
				ycomparesize = Integer.parseInt(secondline);
			} else {
				System.err
						.println("Map Structure not accectable - height not in second line or not an accectable size or integer ");
				rightsize = false;
			}

			int nolines = 0;
			String linea = reader.readLine().replaceAll("\\s","");
			System.out.println(xcomparesize*2);
			while (linea != null) {
				System.out.println(linea);
				linea = linea.replaceAll("\\s+","");
				System.out.println(linea);
				if (linea.length() != (xcomparesize)) {
					System.err.println("Map not the same width as in header1");
					rightsize = false;
				}
				for (int i = 0; i < linea.length(); i++) {
					String c = String.valueOf(linea.charAt(i));
					if (c.equals("#") || c.equals(".") || c.equals("+")
							|| c.equals("-") || (isInteger(c) ||c.equals(" "))) {

					} else {

						System.err.println("map contains unknown character: "
								+ c);
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

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void printmap() {

		for (int j = 0; j < (ySize); j++) {
			if (j % 2 != 0) {
				System.out.print(" ");
			}
			System.out.println("/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\");
			if (j % 2 != 0) {
				System.out.print(" ");
			}
			for (int i = 0; i < (xSize); i++) {
				System.out.print("|");
				System.out.print(mapArray[i][j].getContent());

			}
			System.out.print("|");
			System.out.println(" ");
			if (j % 2 != 0) {
				System.out.print(" ");
			}
		}

		System.out.println("\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		normprintmap();
	}

	public void normprintmap() {

		for (int j = 0; j < (ySize); j++) {
			if (j % 2 != 0) {
				System.out.print(" ");
			}
			for (int i = 0; i < (xSize); i++) {
				System.out.print(mapArray[i][j].getContent());
				System.out.print(" ");
			}
			System.out.println("");
		}

	}

	public Cell getCell(int x, int y){
		return mapArray[x][y];
	}

	

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}

	public static void main(String[] args) {
		Map m2 = new Map("C://map.txt");
		m2.normprintmap();

	}
}
