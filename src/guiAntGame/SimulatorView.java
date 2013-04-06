package guiAntGame;

import java.util.ArrayList;

import antgame.core.Cell;
import antgame.core.Map;
import antgame.core.World;
import processing.core.*;

public class SimulatorView extends PApplet {
	private Hexagon[][] board;
	private PImage rock, food, antB, antR, antHR, antHB, clear;
	private int xSize, ySize;
//	private Observer obiwan;
	private Map curMap;
	private World w;
	
//	public void setup(Observer obiwan,int xSize, int ySize) {
	public void setup() {
		
		String workingDir = System.getProperty("user.dir");
		
		World w1 = new World("C:/workingworld.world"/*workingDir+"\\files\\workingworld.world"**/,workingDir+"\\files\\cleverbrain1.brain",workingDir+"\\files\\cleverbrain2.brain");

		size(800, 800);
		background(0);
		this.xSize=150;
		this.ySize=150;
//		this.obiwan = obiwan;
		this.w = w1;
		curMap = w.getMap();
		
		rock = loadImage("rock.png");
		food = loadImage("food.png");
		antHR = loadImage("antHR.png");
		antHB = loadImage("antHB.png");
		clear = loadImage("clear.png");
		antB = loadImage("antB.png");
		antR = loadImage("antR.png");
		
		board =  new Hexagon[xSize][ySize];
		Cell cCell;
		for(int x=0; x<xSize;x++){
			for(int y=0;y<ySize;y++){
				cCell = curMap.getCell(x, y);
					if(cCell.containsRock()){
						board[x][y] = new Hexagon(this, rock);
					}
					else if(cCell.isClear()){
						board[x][y] = new Hexagon(this, clear);
					}
					else if(cCell.containsBlackAnt()){
						board[x][y] = new Hexagon(this, antB);
					}
					else if(cCell.containsRedAnt()){
						board[x][y] = new Hexagon(this, antR);
					}
					else if(cCell.containsBlackAntHill()){
						board[x][y] = new Hexagon(this, antHB);
					}
					else if(cCell.containsRedAntHill()){
						board[x][y] = new Hexagon(this, antHR);
					}
					else if(cCell.isContainsFood()){
						board[x][y] = new Hexagon(this, food);
					}					
			}
		}
	}

	public void draw() {
		for(int x=0; x<xSize;x++){
			for(int y=0;y<ySize;y++){
				board[x][y].display(x, y, 40, 47);	
						
				if(x%2==0){
					board[x][y].display(40*y, x*35, 40, 47);
				}else{
					board[x][y].display(40*y+20, x*35, 40, 47);
				}
			}
		}
		updateBoard();
		w.step();
	}
	
	public void updateBoard(){
		Cell cCell;
		for(int x=0; x<xSize;x++){
			for(int y=0;y<ySize;y++){
				cCell = curMap.getCell(x, y);
					if(cCell.containsRock()){
						board[x][y] = new Hexagon(this, rock);
					}
					else if(cCell.isClear()){
						board[x][y] = new Hexagon(this, clear);
					}
					else if(cCell.containsBlackAnt()){
						board[x][y] = new Hexagon(this, antB);
					}
					else if(cCell.containsRedAnt()){
						board[x][y] = new Hexagon(this, antR);
					}
					else if(cCell.containsBlackAntHill()){
						board[x][y] = new Hexagon(this, antHB);
					}
					else if(cCell.containsRedAntHill()){
						board[x][y] = new Hexagon(this, antHR);
					}
					else if(cCell.isContainsFood()){
						board[x][y] = new Hexagon(this, food);
					}					
			}
		}
	}

}
