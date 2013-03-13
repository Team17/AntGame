package antgame.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Map {
	private int mapId;
	private int xSize;
	private int ySize;
	private Cell[][] mapArray;
	private String[][] stringMapArray;

	public Map(int id, String mapTextFile){
		setMapId(id);
		
		translator(mapTextFile);
		mapArray = new Cell[xSize][ySize];
		for (int j=0;j<(ySize); j++){
			for(int i=0;i<(xSize); i++){
				mapArray[i][j] = new Cell(i,j,(stringMapArray[i][j]));

			}
		}

	}

	public String[][] translator(String textmap){
		if(mapchecker(textmap)){
		try{

			BufferedReader reader = new BufferedReader(new FileReader(textmap));

			xSize = Integer.parseInt(reader.readLine());
			ySize = Integer.parseInt(reader.readLine());
			
			stringMapArray = new String[xSize][ySize];

			for (int j=0;j<(ySize); j++){
				String lineinput = reader.readLine();
				for(int i=0;i<(xSize); i++){
					stringMapArray[i][j] = String.valueOf(lineinput.charAt(i));
				}
			}
			reader.close();


		}



		catch(FileNotFoundException e){
			System.err.println("unable to open");

		}
		catch(IOException e ){
			System.err.println("a problem was encountered reading");
		}
		

	}
		else{
			System.err.println("Map not accectable");
		}
		return stringMapArray;
	}

	public Boolean mapchecker(String textmap){
		int xcomparesize = 0;
		int ycomparesize = 0;
		Boolean rightsize = true;

		try{

			BufferedReader reader = new BufferedReader(new FileReader(textmap));

			String firstline = reader.readLine();
			if((isInteger(firstline)) && (0<Integer.parseInt(firstline)) && (Integer.parseInt(firstline)<=150)){
				xcomparesize = Integer.parseInt(firstline);
			}
			else{
				System.err.println("Map Structure not accectable - width not in first line or not an accectable size or integer ");
				rightsize = false;
			}

			String secondline = reader.readLine();
			if((isInteger(secondline)) && (0<Integer.parseInt(secondline)) && (Integer.parseInt(secondline)<=150)){
				ycomparesize = Integer.parseInt(secondline);
			}
			else{
				System.err.println("Map Structure not accectable - height not in second line or not an accectable size or integer ");
				rightsize = false;
			}

			int nolines = 0;
			String linea = reader.readLine() ;
			while (linea != null){

				if(linea.length() != xcomparesize){
					System.err.println("Map not the same width as in header");
					rightsize = false;
				}
				for(int i=0; i < linea.length(); i++){
					String c = String.valueOf(linea.charAt(i));
					if(c.equals("#") || c.equals(".")|| c.equals("+")|| c.equals("-")||( isInteger(c)) ){
						
					}
					else{
						
						System.err.println("map contains unknown character: " + c);
						rightsize = false;
					}

				}



				nolines++;
				linea = reader.readLine() ;
			}
			
			if(nolines != ycomparesize){
				System.err.println("Map not the same height as in header");
				rightsize = false;
			}

			reader.close();


		}



		catch(FileNotFoundException e){
			System.err.println("unable to open");

		}
		catch(IOException e ){
			System.err.println("a problem was encountered reading");
		}


		return rightsize;

	}

	public boolean isInteger( String input ) {
		try {
			Integer.parseInt( input );
			return true;
		}
		catch( Exception e ) {
			return false;
		}
	}
	public void printmap()
	{

		for (int j=0;j<(ySize); j++){
			if(j%2 != 0){
				System.out.print(" ");
			}
			System.out.println("/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\");
			if(j%2 != 0){
				System.out.print(" ");
			}
			for(int i=0;i<(xSize); i++){
				System.out.print("|");
				System.out.print(mapArray[i][j].getContent());

			}
			System.out.print("|");
			System.out.println(" ");
			if(j%2 != 0){
				System.out.print(" ");
			}
		}

		System.out.println("\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		normprintmap();
	}
	public void normprintmap()
	{

		for (int j=0;j<(ySize); j++){
			if(j%2 != 0){
				System.out.print(" ");
			}
			for(int i=0;i<(xSize); i++){
				System.out.print(mapArray[i][j].getContent());
				System.out.print(" ");
			} 
			System.out.println("");	
		}


	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public static void main(String[] args){
		Map m2 = new Map(5,"C://map.txt");
		m2.normprintmap();


	}
}

