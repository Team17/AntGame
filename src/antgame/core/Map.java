package antgame.core;

import java.util.ArrayList;

/**
 * @author Coryn
 *
 */
/**
 * @author Coryn
 * 
 */
public class Map {
	// xSize is the width of the map
	private int xSize;
	// ySize is the height of the map
	private int ySize;
	// map is the map in as a 2d array of cells
	private Cell[][] map;

	public Map(String mapTextFile) {
		// Creates an instance of map interpreter
		MapInterpreter mapItp = new MapInterpreter();
		// generates the map from the interpreter
		map = mapItp.MapGenerator(mapTextFile);
		// sets the size of the map based on info from the interpreter
		xSize = mapItp.getxSize();
		ySize = mapItp.getySize();
	}

	/**
	 * adjacentCell return the cell that is adjacent to the cell given in the
	 * parameters based on the direction.
	 */
	public Cell adjacentCell(Cell pos, int dir) {
		int x = pos.getXPos();
		int y = pos.getYPos();
		int xAdj = x;
		int yAdj = y;

		switch (dir) {
		case 0:
			xAdj = x + 1;
			yAdj = y;
			break;
		case 1:
			if (y % 2 == 0) {
				xAdj = x;
				yAdj = y + 1;

			} else {
				xAdj = x + 1;
				yAdj = y + 1;
			}
			break;
		case 2:
			if (y % 2 == 0) {
				xAdj = x - 1;
				yAdj = y + 1;

			} else {
				xAdj = x;
				yAdj = y + 1;
			}
			break;
		case 3:
			xAdj = x - 1;
			yAdj = y;
			break;
		case 4:
			if (y % 2 == 0) {
				xAdj = x - 1;
				yAdj = y - 1;

			} else {
				xAdj = x;
				yAdj = y - 1;
			}
			break;
		case 5:
			if (y % 2 == 0) {
				xAdj = x;
				yAdj = y - 1;

			} else {
				xAdj = x + 1;
				yAdj = y - 1;
			}
			break;
		}
		// the if statment just checks that it is inside the boundarys
		if (xAdj >= 0 && xAdj <= xSize && yAdj >= 0 && yAdj <= ySize) {
			return map[xAdj][yAdj];
		}
		// ////// !!!!!!!!!!!!! how are we going to handle out of bounds?
		else {
			System.err.println("out of Bounds");
			return null;
		}
	}

	/**
	 * adjacentCells returns an arraylist of cells that surround the cell in the
	 * parameter
	 */

	public ArrayList<Cell> adjacentCells(Cell cell) {
		ArrayList<Cell> adjCells = new ArrayList<Cell>();
		int x = cell.getXPos();
		int y = cell.getYPos();

		for (int dir = 0; dir < 6; dir++) {
			int xAdj = x;
			int yAdj = y;
			switch (dir) {
			case 0:
				xAdj = x + 1;
				yAdj = y;
				break;
			case 1:
				if (y % 2 == 0) {
					xAdj = x;
					yAdj = y + 1;

				} else {
					xAdj = x + 1;
					yAdj = y + 1;
				}
				break;
			case 2:
				if (y % 2 == 0) {
					xAdj = x - 1;
					yAdj = y + 1;

				} else {
					xAdj = x;
					yAdj = y + 1;
				}
				break;
			case 3:

				xAdj = x - 1;
				yAdj = y;
				break;
			case 4:
				if (y % 2 == 0) {

					xAdj = x - 1;
					yAdj = y - 1;

				} else {
					xAdj = x;
					yAdj = y - 1;
				}

				break;
			case 5:
				if (y % 2 == 0) {
					xAdj = x;
					yAdj = y - 1;

				} else {
					xAdj = x + 1;
					yAdj = y - 1;
				}
				break;
			}
			if (xAdj >= 0 && xAdj <= xSize && yAdj >= 0 && yAdj <= ySize) {
				adjCells.add(map[xAdj][yAdj]);

			}
		}

		return adjCells;
	}

	/**
	 * printmap recurrsively looks throught the cells and prints the content.
	 */
	public void printMap() {

		for (int j = 0; j < (ySize); j++) {
			if (j % 2 != 0) {
				System.out.print(" ");
			}
			for (int i = 0; i < (xSize); i++) {
				System.out.print(map[i][j].getContent());
				System.out.print(" ");
			}
			System.out.println("");
		}

	}

	/**
	 * getCell returns a cell based on the x and y coordinates.
	 */
	public Cell getCell(int x, int y) {
		return map[x][y];
	}

	/**
	 * getCell returns a cell based on the pos[] array dimensions.
	 */
	public Cell getCell(int[] pos) {
		int x = pos[0];
		int y = pos[1];
		return map[x][y];
	}

	/**
	 * getXSize returns the width.
	 */
	public int getXSize() {
		return xSize;
	}

	/**
	 * getYSize returns the height.
	 */
	public int getYSize() {
		return ySize;
	}

	public static void main(String[] args) {
		Map m2 = new Map("C://map.txt");

		m2.printMap();
		ArrayList<Cell> adjCells = m2.adjacentCells(m2.getCell(4, 4));
		System.out.println("actual " + m2.getCell(4, 4).getContent());
		for (int i = 0; i < adjCells.size(); i++) {
			Cell cell = adjCells.get(i);
			System.out.println("x: " + cell.getXPos() + " y: " + cell.getYPos()
					+ " content: " + cell.getContent());
		}

	}
}
