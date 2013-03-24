package antgame.core;


public class Map {
	private int xSize;
	private int ySize;
	private Cell[][] map;


	public Map( String mapTextFile) {
		MapInterpreter mapItp = new MapInterpreter();
		map = mapItp.MapGenerator(mapTextFile);
		xSize = mapItp.getxSize();
		ySize = mapItp.getySize();
		

		
		
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
				System.out.print(map[i][j].getContent());

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
		//System.out.println(ySize);
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

	public Cell getCell(int x, int y){
		return map[x][y];
	}
	
	public Cell getCell(int[] pos){
		return map[pos[0]][pos[1]];
	}
	

	public int getXSize() {
		return xSize;
	}

	public int getYSize() {
		return ySize;
	}
	public Cell adjacentCell(Cell cell, int dir){
		int x = cell.getPos()[0];
		int y = cell.getPos()[1];
		int adjX = cell.getPos()[0];
		int adjY = cell.getPos()[1];
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
				break;
		}
		return getCell(adjX,adjY);
	}

	public static void main(String[] args) {
		Map m2 = new Map("C://map.txt");
		m2.normprintmap();

	}
}
