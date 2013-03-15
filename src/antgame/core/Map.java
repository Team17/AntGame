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
		System.out.println(ySize);
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
