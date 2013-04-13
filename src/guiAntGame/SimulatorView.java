package guiAntGame;

import java.util.ArrayList;

import antgame.core.Cell;
import antgame.core.Map;
import antgame.core.World;
import processing.core.*;
import org.gicentre.utils.move.*;
import controlP5.*;

public class SimulatorView extends PApplet {
	private Hexagon[][] board;
	private PImage rock, food, antB, antR, antHR, antHB, clear, scoreboard_round,scoreboard_red,scoreboard_black;
	private int xSize, ySize;
	private ObserverAntWorld obiwan;
	private Map curMap;
	private World w;
	private ZoomPan zoomer;
	private int round = 0;
	private PFont f, f2;
	private ControlP5 cp5;
	private float frameRateInt = 60;
	private boolean didthegamefinallyend = false;

	public void setup() {
		smooth();
		noStroke();

		String workingDir = System.getProperty("");
		World w1 = new World(workingDir+"\\curFiles\\map.world",workingDir+"\\curFiles\\r.brain",workingDir+"\\curFiles\\b.brain");

		size(800, 700);

		zoomer = new ZoomPan(this);
		zoomer.allowZoomButton(false);

		this.w = w1;
		this.obiwan = w.getObserver();
		curMap = w.getMap();
		this.xSize = curMap.getXSize();
		this.ySize = curMap.getYSize();
		rock = loadImage("rock.png");
		food = loadImage("food.png");
		antHR = loadImage("redH.png");
		antHB = loadImage("blackH.png");
		clear = loadImage("clear.png");
		antB = loadImage("blackAnt.png");
		antR = loadImage("redAnt.png");

		scoreboard_round = loadImage("scoreboard_round.png");
		scoreboard_red = loadImage("scoreboard_red.png");
		scoreboard_black = loadImage("scoreboard_black.png");
		
		board = new Hexagon[xSize][ySize];
		Cell cCell;
		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				cCell = curMap.getCell(y, x);
				if (cCell.containsRock()) {
					board[x][y] = new Hexagon(this, rock);
				}

				else if (cCell.containsBlackAnt()) {
					board[x][y] = new Hexagon(this, antB);
				} else if (cCell.containsRedAnt()) {
					board[x][y] = new Hexagon(this, antR);
				} else if (cCell.isContainsFood()) {
					board[x][y] = new Hexagon(this, food);
				} else if (cCell.containsBlackAntHill()) {
					board[x][y] = new Hexagon(this, antHB);
				} else if (cCell.containsRedAntHill()) {
					board[x][y] = new Hexagon(this, antHR);
				} else if (cCell.isClear()) {
					board[x][y] = new Hexagon(this, clear);
				}

			}
		}

		f = createFont("Arial", 24, true);
		f2 = createFont("Arial", 34, true);
		
		cp5 = new ControlP5(this);
		cp5.addFrameRate().setInterval(10).setPosition(0, height - 10);
		
		
		
		
		cp5.addSlider("speed").setPosition(50, 670).setSize(150, 20)
				.setRange(40, 500).setValue(60);

		cp5.addButton("endgame").setPosition(700, 670)
				.setSize(60, 20);

	}

	public void draw() {
		frameRate(frameRateInt);
		background(170);
		pushMatrix();
		zoomer.transform();

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				if (x % 2 == 0) {
					if (!board[x][y].toCompare(clear)) {
						board[x][y].display(40 * y, x * 35, 40, 47);
					}

				} else {
					if (!board[x][y].toCompare(clear)) {
						board[x][y].display(40 * y + 20, x * 35, 40, 47);
					}
				}
			}
		}
		popMatrix();
		if(didthegamefinallyend){
			finishGame();
			fill(20,230);
			rect(100,100,600,400);
			fill(255);
			
			textAlign(CENTER);
			textFont(f2);
			String winner;
			if(w.whoWon() != null){
				winner = w.whoWon().toString();
			}
			else{
				winner = "TIE";
			}
			
			text("Result: " + winner ,400, 200);
			
			textAlign(CENTER);
			

			textAlign(RIGHT);
			text("Red:",270, 250); 
			textFont(f);
			text("Food: " + obiwan.getWorld().getStats().getFoodUnitsRedHill(),
					270, 285);
			text("Alive: " + obiwan.getWorld().getStats().getRedAlive(), 270, 310);
			text("Dead: " + obiwan.getWorld().getStats().getRedAntsdead(), 270, 335);
			
			textAlign(LEFT);
			textFont(f2);
			text("Black:",530, 250); 
			textFont(f);
			
			text("Food: " + obiwan.getWorld().getStats().getFoodUnitsBlackHill(),
					530, 285);
			text("Alive: " + obiwan.getWorld().getStats().getBlackAlive(), 530, 310);
			text("Dead: " + obiwan.getWorld().getStats().getBlackAntsDead(), 530, 335);
		}
		if(round<=300000 && w.getStats().getBlackAlive() >0 && w.getStats().getRedAlive()>0){
			w.step();
			updateBoard(obiwan.getToUpdate());
			obiwan.clearList();
			round++;
			
			image(scoreboard_round, 0, width-75);
			image(scoreboard_red, 0, 0);
			image(scoreboard_red, 0, width-189);
			fill(40);
			rect(0,0,70,width);
			textAlign(CENTER);
			fill(255);
			textFont(f);
			text(round, width / 2, 75);
			textAlign(RIGHT);
			text("Food: " + obiwan.getWorld().getStats().getFoodUnitsRedHill(),
					270, 35);
			text("Alive: " + obiwan.getWorld().getStats().getRedAlive(), 270, 60);
			textAlign(LEFT);
			text("Food: " + obiwan.getWorld().getStats().getFoodUnitsBlackHill(),
					530, 35);
			text("Alive: " + obiwan.getWorld().getStats().getBlackAlive(), 530, 60);
			
		}else if(w.getStats().getBlackAlive() ==0 || w.getStats().getRedAlive()==0){
			fill(20,230);
			rect(100,100,600,400);
			fill(255);
			textAlign(CENTER);
			textFont(f2);
			text("Winner is: " + w.whoWon(),400, 200);
			
			textAlign(CENTER);
			textFont(f);
			text(round, width / 2, 75);
			textAlign(RIGHT);
			text("Food: " + obiwan.getWorld().getStats().getFoodUnitsRedHill(),
					270, 335);
			text("Alive: " + obiwan.getWorld().getStats().getRedAlive(), 270, 360);
			textAlign(LEFT);
			text("Food: " + obiwan.getWorld().getStats().getFoodUnitsBlackHill(),
					530, 335);
			text("Alive: " + obiwan.getWorld().getStats().getBlackAlive(), 530, 360);
		}
		else if(!didthegamefinallyend)
		{
			fill(20,230);
			rect(100,100,600,400);
			fill(255);
			textAlign(CENTER);
			textFont(f);
			
			text("Please wait, ants are busy having a war.",400, 200);
			didthegamefinallyend = true;
		}

		



	}

	public void updateBoard(ArrayList<Cell> toUpdate) {
		for (Cell cCell : toUpdate) {
			if (cCell.containsBlackAnt()) {
				board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(antB);
			} else if (cCell.containsRedAnt()) {
				board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(antR);
			} else if (cCell.isContainsFood()) {
				board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(food);
			} else if (cCell.containsBlackAntHill()) {
				board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(antHB);
			} else if (cCell.containsRedAntHill()) {
				board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(antHR);
			} else if (cCell.isClear()) {
				board[cCell.getPos()[1]][cCell.getPos()[0]].setIcon(clear);
			}
		}
	}

	void speed(float rate) {
		frameRateInt = rate;
	}
	
	public void endgame() {
		if(round<300000){
			round = 300000;
		}
		
	}
	
	public void finishGame() {
		int curR = w.getStats().getRound();
		while(curR<300000){
			w.step();
			curR++;
		}
	}
}
