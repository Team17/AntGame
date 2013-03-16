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
	public Cell adjacentCell(Cell pos, int dir)
	{
		int x = pos.getXPos();
		int y = pos.getYPos();
		int xAdj = x;
		int yAdj = y;
		
		switch(dir){
		case 0:
			xAdj = x +1;
			yAdj = y;
			break;
		case 1:
			if(y%2 == 0){
				xAdj = x;
				yAdj = y+1;
				
			}
			else{
				xAdj = x+1;
				yAdj = y+1;
			}
			break;
		case 2:
			if(y%2 == 0){
				xAdj = x-1;
				y = y+1;
				
			}
			else{
				xAdj = x;
				yAdj = y+1;
			}
			break;
		case 3:
				xAdj = x-1;
				yAdj = y;	
			break;
		case 4:
			if(y%2 == 0){
				xAdj = x-1;
				yAdj = y-1;
				
			}
			else{
				xAdj = x;
				yAdj = y-1;
			}
			break;
		case 5:
			if(y%2 == 0){
				xAdj = x;
				yAdj = y-1;
				
			}
			else{
				xAdj = x+1;
				yAdj = y-1;
			}
			break;
		}
		return map[xAdj][yAdj];
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
