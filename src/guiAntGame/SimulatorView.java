package guiAntGame;

import java.util.ArrayList;

import antgame.core.Cell;
import antgame.core.Map;
import antgame.core.World;
import processing.core.*;
import org.gicentre.utils.move.*;

public class SimulatorView extends PApplet {
	private Hexagon[][] board;
	private PImage rock, food, antB, antR, antHR, antHB, clear,scoreboard;
	private int xSize, ySize;
	private ObserverAntWorld obiwan;
	private Map curMap;
	private World w;
	private ZoomPan zoomer;
	private int round = 0;
	private PFont f;
	
//	public void setup(Observer obiwan,int xSize, int ySize) {
	public void setup() {
		smooth();
		noStroke();
		
		String workingDir = "..";
		World w1 = new World("C:/workingworld2.world"/*workingDir+"\\files\\workingworld.world"**/,workingDir+"\\files\\cleverbrain1.brain",workingDir+"\\files\\cleverbrain2.brain");

		size(800, 800);
		
		zoomer = new ZoomPan(this);
		zoomer.allowZoomButton(false);

		
		this.w = w1;
		this.obiwan = w.getObserver();
		curMap = w.getMap();
		this.xSize=curMap.getXSize();
		this.ySize=curMap.getYSize();
		rock = loadImage("rock.png");
		food = loadImage("food.png");
		antHR = loadImage("redH.png");
		antHB = loadImage("blackH.png");
		clear = loadImage("clear.png");
		antB = loadImage("blackAnt.png");
		antR = loadImage("redAnt.png");
		
		scoreboard = loadImage("scorebaord.png");

		
		board =  new Hexagon[xSize][ySize];
		Cell cCell;
		for(int y=0; y<ySize;y++){
			for(int x=0;x<xSize;x++){
				cCell = curMap.getCell(y, x);
					if(cCell.containsRock()){
						board[x][y] = new Hexagon(this, rock);
					}

					else if(cCell.containsBlackAnt()){
						board[x][y] = new Hexagon(this, antB);
					}
					else if(cCell.containsRedAnt()){
						board[x][y] = new Hexagon(this, antR);
					}
					else if(cCell.isContainsFood()){
						board[x][y] = new Hexagon(this, food);
					}	
					else if(cCell.containsBlackAntHill()){
						board[x][y] = new Hexagon(this, antHB);
					}
					else if(cCell.containsRedAntHill()){
						board[x][y] = new Hexagon(this, antHR);
					}
					else if(cCell.isClear()){
						board[x][y] = new Hexagon(this, clear);
					}
				
			}
		}

		f = createFont("Arial",160,true); 
		
		frameRate(2000);
	}

	public void draw() {
		
	background(170);
	pushMatrix();
	zoomer.transform();

	
		for(int x=0; x<xSize;x++){
			for(int y=0;y<ySize;y++){
				if(x%2==0){
					if(!board[x][y].toCompare(clear)){
						board[x][y].display(40*y, x*35, 40, 47);
					}
					
				}else{
					if(!board[x][y].toCompare(clear)){
						board[x][y].display(40*y+20, x*35, 40, 47);
					}		
				}
			}
		}
		w.step();
		
		updateBoard(obiwan.getToUpdate());
		obiwan.clearList();
		round++;
		popMatrix();
		
		image(scoreboard,0,0);
		textAlign(CENTER);
		text((frameRate) + " fps",width/2,60); 
		text("Round: "+round,width/2,80); 
		text("Red have " + obiwan.getWorld().getStats().getFoodUnitsRedHill() + " units of food",width/2,100); 
		text("Red have " + obiwan.getWorld().getStats().getRedAlive() + " ants alive",width/2,120); 
		text("Blacks have " + obiwan.getWorld().getStats().getFoodUnitsBlackHill() + " units of food",width/2,140); 
		text("Blacks have " + obiwan.getWorld().getStats().getBlackAlive() + " ants alive",width/2,160); 
		
	}
	
	public void updateBoard(ArrayList<Cell> toUpdate){
		for(Cell cCell:toUpdate){
					if(cCell.containsBlackAnt()){
						board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(antB);
					}
					else if(cCell.containsRedAnt()){
						board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(antR);
					}
					else if(cCell.isContainsFood()){
						board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(food);
					}	
					else if(cCell.containsBlackAntHill()){
						board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(antHB);
					}
					else if(cCell.containsRedAntHill()){
						board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(antHR);
					}
					else if(cCell.isClear()){
						board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(clear);
					}					
			}
	}

}
