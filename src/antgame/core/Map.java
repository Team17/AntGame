package antgame.core;

import java.util.ArrayList;

/**
 * Map is the representation of the environment.
 * @author Coryn
 *
 */
public class Map {
	//the width of the map
	private int xSize;
	//the height of the map
	private int ySize;
	//the map itself
	private Cell[][] map;

	/**
	 * Map Constructor sets the field map to the parameter, it also sets the size of the width and height (xSize and ySize).
	 * @param map the array that is passed from the interpreter.
	 */
	public Map(Cell[][] map) {
		this.map = map;
		this.xSize = map.length;
		this.ySize = map.length;
	}

	/**
	 * printMap logically prints each cell, the only complex part is that it indents every odd row.
	 */
	public void printmap() {
		for (int j = 0; j < (ySize); j++) {
			System.out.print("");
			if (j % 2 != 0) {
				System.out.print(" ");
			}
			for (int i = 0; i < (xSize); i++) {

				System.out.print(map[i][j].getContent());
				System.out.print("|");

				System.out.print("");
			}
			System.out.println("");
		}

	}

	/**
	 * getCell returns a specific cell in the map, based on x and y coordinates
	 * @param x the xCoordinate of the cell that is requested
	 * @param y the yCoordinate of the cell that is requested
	 * @return Cell a cell that you have requested based on the x and y coordinates.
	 */
	public Cell getCell(int x, int y){
		return map[x][y];
	}

	/**
	 * getCell returns a specific cell in the map, based on an int array
	 * @param pos is an int array of x and y coordinates of the cell that is requested
	 * @return Cell a cell that you have requested based on the pos.
	 */
	public Cell getCell(int[] pos){
		return map[pos[0]][pos[1]];
	}

	/**
	 * getXSize
	 * @return int of the xSize (width)
	 */
	public int getXSize() {
		return xSize;
	}

	/**
	 * getYSize
	 * @return int of the ySize (height)
	 */
	public int getYSize() {
		return ySize;
	}

	/**
	 * adjcentCell finds the an adjacent cell based on the parameters passed. It is passed the current cell and the direction from that cell that is wanted.
	 * @param cell is the current cell so to speak, it is the cell from which is wanted to find its adjacent cell.
	 * @param dir the direction from the cell as above that is wanted.
	 * @return Cell the adjcent cell
	 */
	public Cell adjacentCell(Cell cell, int dir){
		// the x and y coordinates of the current pos.
		int x = cell.getPos()[0];
		int y = cell.getPos()[1];
		// the x and y coordinates of the cell we want to find.
		int adjX;
		int adjY;
		//swich based on the current direction
		switch(dir) {
		case 0: 
			adjX = x+1;
			adjY = y;
			break;
		case 1: 
			if(y%2==0){
				adjX = x;
				adjY = y+1;
			}
			else{
				adjX = x+1;
				adjY = y+1;
			}
			break;
		case 2: 
			if(y%2==0){
				adjX = x-1;
				adjY = y+1;

			}
			else{
				adjX = x;
				adjY = y+1;
			}
			break;
		case 3: 
			adjX = x-1;
			adjY = y;
			break;
		case 4: 
			if(y%2==0){
				adjX = x-1;
				adjY = y-1;
			}
			else{
				adjX = x;
				adjY = y-1;
			}
			break;
		case 5: 
			if(y%2==0){
				adjX = x;
				adjY = y-1;
			}
			else{
				adjX = x+1;
				adjY = y-1;
			}
			break;
		default:
			adjX = cell.getPos()[0];
			adjY = cell.getPos()[1];
			break;
		}
		// if the current pos is out of bounds then it returns null
		if(adjX>=0 && adjX<=xSize && adjY>=0 && adjY<=ySize){


			return getCell(adjX,adjY);
		}
		else{
			return null;
		}
	}

	/**
	 * surroundingCells is very similar to adjacentCell except that instead of returning an individual cell, it returns and ArrayList of Cells that surround a specific cell.
	 * The method simply adds a cell for each direction from that cell checking that it is within the boundary.
	 * @param cell is the cell that we want to return the surrounding cells for.
	 * @return ArrayList<Cell> of the cells that surround cell as above.
	 */

	public ArrayList<Cell> surrondingCells(Cell cell){
		ArrayList<Cell> adjacentCells = new ArrayList<Cell>();
		int x = cell.getPos()[0];
		int y = cell.getPos()[1];

		for(int dir=0;dir<6;dir++){
			int adjX;
			int adjY;

			switch(dir) {
			case 0: 
				adjX = x+1;
				adjY = y;
				break;
			case 1: 
				if(y%2==0){
					adjX = x;
					adjY = y+1;
				}
				else{
					adjX = x+1;
					adjY = y+1;
				}
				break;
			case 2: 
				if(y%2==0){
					adjX = x-1;
					adjY = y+1;

				}
				else{
					adjX = x;
					adjY = y+1;
				}
				break;
			case 3: 
				adjX = x-1;
				adjY = y;
				break;
			case 4: 
				if(y%2==0){
					adjX = x-1;
					adjY = y-1;
				}
				else{
					adjX = x;
					adjY = y-1;
				}
				break;
			case 5: 
				if(y%2==0){
					adjX = x;
					adjY = y-1;
				}
				else{
					adjX = x+1;
					adjY = y-1;
				}
				break;
			default:
				adjX = cell.getPos()[0];
				adjY = cell.getPos()[1];

				break;

			}

			if(adjX>=0 && adjX<=xSize && adjY>=0 && adjY<=ySize){
				adjacentCells.add(getCell(adjX,adjY));
			}
		}
		return adjacentCells;


	}
	public static void main(String [] args){
		Map m1 = MapInterpreter.MapGenerator("C:/workingworld.world");
		m1.printmap();
	}
}
