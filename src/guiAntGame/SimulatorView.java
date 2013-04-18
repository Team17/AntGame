package guiAntGame;

import java.util.ArrayList;

import antgame.core.Cell;
import antgame.core.Map;
import antgame.core.World;
import processing.core.*;
import org.gicentre.utils.move.*;
import controlP5.*;


/**
 * The class which displays a world.
 * WARNING: once the class is created, before calling the init() method, 
 * the world needs to be set via setWorld(World w) method.
 * @author Doniyor
 */
public class SimulatorView extends PApplet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The board. */
	private Hexagon[][] board;
	
	/** All images used in the simulation */
	private PImage rock, food, antB, antR, antHR, antHB, clear,
			scoreboard_round, scoreboard_red, scoreboard_black;
	
	/** The dimensions of the world */
	private int xSize, ySize;
	
	/** The the Observer of the world. */
	private ObserverAntWorld obiwan;
	
	/** The map used by the world */
	private Map curMap;
	
	/** The World the SimulatorView showing*/
	private World w;
	
	/** The ZoomPan, allows to zoom and pan */
	private ZoomPan zoomer;
	
	/** Current round of the round */
	private int round = 0;
	
	/** The fonts in use */
	private PFont f, f2;
	
	/** The cp5 library controler responsbile for all the buttons */
	private ControlP5 cp5;
	
	/** The frame rate */
	private float frameRateInt = 60;
	
	/** The did the game finish boolean. */
	private boolean didthegamefinallyend = false;
	
	/**
	 * Sets the world.
	 *
	 * @param w the world to show
	 */
	public void setWorld(World w){
		this.w = w;
	}
	
	/**
	 * Called when the object is initiated, Sets up the window, loads the images, creates and updates the board.
	 * Add the buttons
	 * @see processing.core.PApplet#setup()
	 */
	public void setup() {
		//To reduce pixelation
		smooth();
		noStroke();

		//Create an PApplet ssize of 800x700
		size(800, 700);

		//Zooming and Panning Libaries
		zoomer = new ZoomPan(this);
		zoomer.setZoomMouseButton(PConstants.RIGHT);

		//Get the observer, map and sizes
		this.obiwan = w.getObserver();
		curMap = w.getMap();
		this.xSize = curMap.getXSize();
		this.ySize = curMap.getYSize();
		
		//Load all the cell images
		rock = loadImage("rock.png");
		food = loadImage("food.png");
		antHR = loadImage("redH.png");
		antHB = loadImage("blackH.png");
		clear = loadImage("clear.png");
		antB = loadImage("blackAnt.png");
		antR = loadImage("redAnt.png");

		//Load the score board images
		scoreboard_round = loadImage("scoreboard_round.png");
		scoreboard_red = loadImage("scoreboard_red.png");
		scoreboard_black = loadImage("scoreboard_black.png");

		//Load the fonts
		f = createFont("Arial", 20, true);
		f2 = createFont("Arial", 34, true);
		
		//Create a new board with the map sizes
		board = new Hexagon[xSize][ySize];
		
		//Fill the board with Hexagons and appropriate images loaded into each hexagon
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



		//Load the library and create the buttons
		cp5 = new ControlP5(this);

		cp5.addSlider("speed").setPosition(10, 640).setSize(150, 20)
				.setRange(30, 500).setValue(60);
		cp5.addFrameRate().setInterval(10).setPosition(130, 645);
		cp5.addButton("endgame").setPosition(10, 670).setSize(150, 20);

	}

	/** 
	 * Loops continuously to display the world
	 * @see processing.core.PApplet#draw()
	 */
	public void draw() {
		//CurrentFrame
		frameRate(frameRateInt);
		//Set background at 170 grey
		background(170);
		
		//Everything that comes after this is zoom and pannable
		pushMatrix();
		zoomer.transform();

		//Displays the board
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
		
		//Anything after this point is non zoomable and non pannable
		popMatrix();
		
		//If the game has ended, or user pressed end game, finishes the game, updates the board and shows the winnder
		if (didthegamefinallyend) {
			// Displayed when the game ends
			// End the game
			finishGame();
			updateAllBoard();
			showTheWinner();

		}
		
		//If the game did not end yet, step in the world, update the world via the observer
		//clear the observerm increase the round and display the score board
		if (round <= 300000 && w.getStats().getBlackAlive() > 0
				&& w.getStats().getRedAlive() > 0) {
			// Game simulation display
			w.step();
			updateBoard(obiwan.getToUpdate());
			obiwan.clearList();
			round++;
			showScoreBoard();
		} else if (w.getStats().getBlackAlive() == 0
				|| w.getStats().getRedAlive() == 0) {
			// Displayed when one of the teams wins prior 300000 rounds
			showTheWinner();
		} else if (!didthegamefinallyend)
		// Displayed when EndGame is clicked
		{
			fill(20, 230);
			rect(width / 2 - 300, height / 2 - 200, 600, 400);
			fill(255);
			textAlign(CENTER);
			textFont(f);

			text("Please wait, ants are busy having a war.", width / 2,
					height / 2 - 100);

			didthegamefinallyend = true;
		}

	}

	/**
	 * Updates the board cells.
	 *
	 * @param an ArrayList of cells to update
	 */
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

	/**
	 * Update all board.
	 */
	public void updateAllBoard() {
		Cell cCell;
		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				cCell = curMap.getCell(y, x);
				if (cCell.containsRock()) {
					board[x][y] = new Hexagon(this, rock);
				} else if (cCell.containsBlackAnt()) {
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
	}

	/**
	 * Listener for the slider, sets the frame rate of the simulation
	 *
	 * @param the input by the slider 
	 */
	public void speed(float rate) {
		frameRateInt = rate;
	}
	
	/**
	 *Displays the ScoreBoard
	 */
	private void showScoreBoard(){
		fill(40);
		rect(0, 0, width, 70);
		image(scoreboard_round, width / 2 - 75, 0);
		image(scoreboard_red, 0, 0);
		image(scoreboard_black, width - 189, 0);
		textAlign(CENTER);
		fill(200);
		textFont(f);
		text(round, width / 2, 65);
		textAlign(RIGHT);
		text("Food: " + obiwan.getWorld().getStats().getFoodUnitsRedHill(),
				width / 2 - 90, 32);
		text("Alive: " + obiwan.getWorld().getStats().getRedAlive(),
				width / 2 - 90, 57);
		textAlign(LEFT);
		text("Food: "
				+ obiwan.getWorld().getStats().getFoodUnitsBlackHill(),
				width / 2 + 90, 32);
		text("Alive: " + obiwan.getWorld().getStats().getBlackAlive(),
				width / 2 + 90, 57);

		image(scoreboard_red, 0, 0);
		image(scoreboard_black, width - 189, 0);
	}
	
	/**
	 * Shows the winner.
	 */
	private void showTheWinner(){
		frameRateInt = 30;
		// Create a rectable box
		fill(20, 230);
		rect(width / 2 - 300, height / 2 - 200, 600, 300);
		fill(225);

		// Styling for the text
		textAlign(CENTER);
		textFont(f2);
		String winner;
		// Check who won
		if (w.whoWon() != null) {
			winner = w.whoWon().toString();
		} else {
			winner = "TIE";
		}

		// Display the winner
		text("Result: " + winner, width / 2, height / 2 - 150);
		textFont(f);
		text("Rounds: " + obiwan.getWorld().getStats().getRound(),
				width / 2, height / 2 - 130);
		textFont(f2);
		// Add some stats:
		textAlign(RIGHT);
		// Red Team
		text("Red:", width / 2 - 100, height / 2 - 60);
		textFont(f);
		text("Food: " + obiwan.getWorld().getStats().getFoodUnitsRedHill(),
				width / 2 - 100, height / 2 - 30);
		text("Alive: " + obiwan.getWorld().getStats().getRedAlive(),
				width / 2 - 100, height / 2);
		text("Dead: " + obiwan.getWorld().getStats().getRedAntsdead(),
				width / 2 - 100, height / 2 + 30);

		textAlign(LEFT);
		// Black Team
		textFont(f2);
		text("Black:", width / 2 + 100, height / 2 - 60);

		textFont(f);
		text("Food: "
				+ obiwan.getWorld().getStats().getFoodUnitsBlackHill(),
				width / 2 + 100, height / 2 - 30);
		text("Alive: " + obiwan.getWorld().getStats().getBlackAlive(),
				width / 2 + 100, height / 2);
		text("Dead: " + obiwan.getWorld().getStats().getBlackAntsDead(),
				width / 2 + 100, height / 2 + 30);
	}

	/**
	 * Endgame button listener. Simply sets the round number to 300000
	 */
	public void endgame() {
		if (round < 300000) {
			round = 300000;
		}

	}

	/**
	 * If the game has not reached 300000, it keeps stepping till 300000 rounds is reached or 
	 * one of the teams wins
	 */
	private void finishGame() {
		int curR = w.getStats().getRound();
		while (curR < 300000 && w.getStats().getBlackAlive() > 0
				&& w.getStats().getRedAlive() > 0) {
			w.step();
			curR++;
		}
	}
}
