package guiAntGame;

import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import antgame.core.AntBrain;
import antgame.core.AntBrainInterpreterCoryn;
import antgame.core.AntColour;
import antgame.core.Map;
import antgame.core.MapCreator;
import antgame.core.MapInterpreter;
import antgame.core.World;

import controlP5.ControlP5;
import controlP5.Group;


/**
 * The main menu class  creates all the buttons and listeners for it. 
 * @author Doniyor
 */
public class MainMenu extends PApplet {

    /**
     * To make eclipse happy.
     */
    private static final long serialVersionUID = -5804888051306006862L;

    // The library controler
    /** The cp5. */
    private ControlP5 cp5;

    // The images for the backgrounds
    /** The bg main. */
    private PImage bg, bgMain;

    // All the buttons on the main menu
    /** The start button. */
    controlP5.Button buttonHelp, buttonTournament, createMap, bgBrain,
	    selectRedBrain, selectBlackBrain, selectMap, startButton;

    // Normal and Bold Arial fonts
    /** The arial b. */
    private PFont arialMain, arialB;

    // Files for the ant brains and the map file.
    /** The map file. */
    private File redBrain, blackBrain, mapFile;

    // Map object to store the random Map
    /** The map map. */
    private Map mapMap;

    // Temporary directory to store the files at
    /** The tmp_1. */
    private Path tmp_1;

    // File version of the tmp_1
    /** The as file. */
    private File asFile;

    /**
     * Creates the window, sets up the fonts and the libraries. Aligns all the
     * buttons, backgrounds and the text.
     */
    public void setup() {

	// Create window
	size(600, 500);

	// Select Fonts
	arialMain = createFont("ArialBold", 32);
	arialB = createFont("Arial", 16);

	// Set the controller
	cp5 = new ControlP5(this);

	// Main Menu text
	cp5.addTextlabel("mainText").setPosition(width / 2 - 85, 30)
		.setText("Main Menu").setFont(arialMain).setColor(200);

	// Help Button
	buttonHelp = createButton("buttonHelp", "Help", 55, 85, 60);

	// Tournament Button
	buttonTournament = createButton("buttonTournament", "Tournament", 220,
		85, 27);

	// Random Map Button
	createMap = createButton("createMap", "Random Map", 385, 85, 22);

	// A group to store all the brain related objects
	Group antBrains = cp5.addGroup("antBrains")
		.setBackgroundColor(color(0, 64)).setBackgroundHeight(270)
		.setWidth(490).setTitle("Ant brain, you choose.")
		.setColorBackground(color(0, 100)).setPosition(55, 125)
		.hideArrow().hideBar();

	// Load the background image for the brain group
	bg = loadImage("brainsbg.png");

	// A button is used to store the background image for the brain group.
	bgBrain = cp5.addButton("bgBrain").setPosition(0, 0)
		.setImages(bg, bg, bg).updateSize().moveTo(antBrains).setOff();

	// Select Red Brain Button
	selectRedBrain = createSelectButton("selectRedBrain", "Select Brain",
		490 / 4 - 50, 140).moveTo(antBrains);

	// Select Black Brain Button
	selectBlackBrain = createSelectButton("selectBlackBrain",
		"Select Brain", 490 / 4 * 3 - 50, 140).moveTo(antBrains);

	// Select Map Button
	selectMap = cp5.addButton("selectMap").setPosition(0, 232)
		.setSize(490, 35).setColorBackground(color(0, 200))
		.setColorActive(color(0, 200))
		.setColorForeground(color(0, 150)).moveTo(antBrains);

	// Set text and font options for the Select Map Button
	selectMap.getCaptionLabel().setFont(arialB).setText("Select Map")
		.toUpperCase(false).setSize(18).setColor(color(140)).getStyle()
		.setMarginLeft(190);

	// Start Button
	startButton = cp5.addButton("startButton").setPosition(55, 400)
		.setSize(490, 65).setColorBackground(color(115, 73, 73))
		.setColorActive(color(215, 73, 73))
		.setColorForeground(color(115, 73, 73)).lock();

	// Set text and font options for the Start Button
	startButton.getCaptionLabel().setFont(arialB).setText("START")
		.toUpperCase(false).setSize(38).setColor(color(140)).getStyle()
		.setMarginLeft(180);

	// Load the main background image
	bgMain = loadImage("bg.jpg");



	// Creates all the temporary files;
	createTempDir();
    }
    
    public void keyPressed() {
    	  if (key == CODED) {
    		    if (keyCode == KeyEvent.VK_F1) {
    		      buttonHelp();
    		    } 
    		  }
    	}

    /* 
     * Responsible for visializing the whole class
     * @see processing.core.PApplet#draw()
     */
    public void draw() {
	background(200);
	image(bgMain, 0, 0);
    }

    /**
     * Select red brain button listener. Activates a file picker on click, and checks if the brain is valid. If not makes the button red, else green.
     */
    public void selectRedBrain() {
	JFileChooser fc = new JFileChooser();
	int returnVal = fc.showOpenDialog(null);

	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    File file = fc.getSelectedFile();
	    if (AntBrainInterpreterCoryn
		    .antBrainChecker(file.getAbsolutePath())) {
		selectRedBrain.getCaptionLabel().set(
			shortText(file.getName(), 13));
		setButtonGreen(selectRedBrain);
		blackBrain = file;
	    } else {
		selectRedBrain.getCaptionLabel().set("Wrong Brain");
		setButtonRed(selectRedBrain);
	    }
	    checkStart();
	}
    }

    /**
     * Select black brain button listener. Activates a file picker on click, and checks if the brain is valid. If not makes the button red, else green.
     */
    public void selectBlackBrain() {
	JFileChooser fc = new JFileChooser();
	int returnVal = fc.showOpenDialog(null);

	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    File file = fc.getSelectedFile();
	    if (AntBrainInterpreterCoryn
		    .antBrainChecker(file.getAbsolutePath())) {
		selectBlackBrain.getCaptionLabel().set(
			shortText(file.getName(), 13));
		setButtonGreen(selectBlackBrain);
		redBrain = file;
	    } else {
		selectBlackBrain.getCaptionLabel().set("Wrong Brain");
		setButtonRed(selectBlackBrain);
	    }

	    checkStart();
	}
    }

    /**
     * Select map button listener. Activates a file picker on click, and checks if the map is valid. If not makes the button red, else green.
     */
    public void selectMap() {
	JFileChooser fc = new JFileChooser();
	int returnVal = fc.showOpenDialog(null);

	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    File file = fc.getSelectedFile();
	    boolean mapTest = false;
	    try {
		mapTest = MapInterpreter.mapchecker(file.getAbsolutePath());
	    } catch (Exception e) {

	    }
	    if (mapTest) {
		selectMap.getCaptionLabel().set(shortText(file.getName(), 30));
		selectMap.getCaptionLabel().getStyle().setMarginLeft(10);
		setButtonGreen(selectMap);
		mapFile = file;
		mapMap = null;
		createMap.unlock();
		setButtonGrey(createMap);
		createMap.getCaptionLabel().setText("Random Map").getStyle()
			.setMarginLeft(22);

	    } else {
		selectMap.getCaptionLabel().set("Wrong Map");
		setButtonRed(selectMap);
	    }
	    checkStart();
	}
    }

    /**
     * A random map button listener. Creates a new random map, locks the button, assigns the random map to the mapMap variable and nulls the mapFile. 
     * The button becomes green and reverts the select map button back to grey if it was green or red.
     */
    public void createMap() {
	mapMap = MapCreator.getRandomMap();
	setButtonGreen(createMap);
	createMap.getCaptionLabel().setText("Map Generated").getStyle()
		.setMarginLeft(15);
	createMap.lock();
	mapFile = null;
	setButtonGrey(selectMap);
	selectMap.getCaptionLabel().setText("Select Map").getStyle()
		.setMarginLeft(190);
	checkStart();
    }

    /**
     * Shortens the string according to the int, used for the buttons to display the text within the buttons.
     * @param toBeShort String that needs to get shortened
     * @param len the length the string needs to be shortened to
     * @return Shortened String
     */
    private String shortText(String toBeShort, int len) {
	if (toBeShort.length() > len) {
	    return toBeShort.substring(0, len);
	} else {
	    return toBeShort;
	}
    }

    /**
     * Start button listener. Checks if all inputs are valid, if yes creates a new simulator view with the inputs.
     */
    public void startButton() {
	if (mapFile != null && redBrain != null && blackBrain != null) {
	    if ((MapInterpreter.mapchecker(mapFile.getAbsolutePath()))
		    && AntBrainInterpreterCoryn.antBrainChecker(blackBrain
			    .getAbsolutePath())
		    && AntBrainInterpreterCoryn.antBrainChecker(redBrain
			    .getAbsolutePath())) {
		try {

		    new DisplayFrame(new World(mapFile.getAbsolutePath(),
			    redBrain.getAbsolutePath(),
			    blackBrain.getAbsolutePath()));
		} catch (Exception e) {
		    selectMap.getCaptionLabel().set(e.getMessage());
		}
	    }
	} else if (mapMap != null && redBrain != null && blackBrain != null) {
	    if (AntBrainInterpreterCoryn.antBrainChecker(blackBrain
		    .getAbsolutePath())
		    && AntBrainInterpreterCoryn.antBrainChecker(redBrain
			    .getAbsolutePath())) {
		try {

		    new DisplayFrame(new World(mapMap, new AntBrain(
			    redBrain.getAbsolutePath(), AntColour.RED),
			    new AntBrain(blackBrain.getAbsolutePath(),
				    AntColour.BLACK)));
		} catch (Exception e) {

		}
	    }
	}
    }

    /**
     * Button help listener, on click opens the help file in the default OS browser.
     */
    public void buttonHelp() {

	try {
	    Desktop.getDesktop().open(
		    new File(asFile.getAbsoluteFile() + "/index.html"));
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    /**
     * Button tournament listener, which opens the tournament menu on click.
     */
    public void buttonTournament() {
	try {
	    new DisplayFrame(1);
	} catch (Exception e) {
	}
    }

    /**
     * Creates a standard button.
     *
     * @param name of the button
     * @param text to display on the button
     * @param x position of the button
     * @param y position of the button
     * @param m left margin of the text
     * @return a button object
     */
    @SuppressWarnings("deprecation")
    private controlP5.Button createButton(String name, String text, int x,
	    int y, int m) {
	controlP5.Button b = cp5.addButton(name).setPosition(x, y)
		.setSize(160, 35).setColorBackground(color(0, 50))
		.setColorActive(color(0, 200))
		.setColorForeground(color(0, 150));

	b.getCaptionLabel().setFont(arialB).setText(text).toUpperCase(false)
		.setSize(18).setColor(color(200));
	b.captionLabel().getStyle().marginLeft = m;

	return b;
    }

    /**
     * Creates a thin, dark standard button.
     *
     * @param name of the button
     * @param text to display on the button
     * @param x position of the button
     * @param y position of the button
     * @param m left margin of the text
     * @return a button object
     */
    private controlP5.Button createSelectButton(String name, String text,
	    int x, int y) {
	controlP5.Button b = cp5.addButton(name).setPosition(x, y)
		.setSize(100, 20).setColorBackground(color(0, 200))
		.setColorActive(color(0, 200))
		.setColorForeground(color(0, 150));

	b.getCaptionLabel().setFont(arialMain).toUpperCase(false).setSize(12)
		.setText(text).setColor(color(140));

	return b;
    }

    /**
     * Sets the button red.
     *
     * @param darthMaul a button to make red
     */
    private void setButtonRed(controlP5.Button darthMaul) {
	darthMaul.setColorBackground(color(115, 73, 74))
		.setColorActive(color(175, 73, 74))
		.setColorForeground(color(215, 73, 74));
    }

    /**
     * Sets the button green.
     *
     * @param yoda  a button to make green
     */
    private void setButtonGreen(controlP5.Button yoda) {
	yoda.setColorBackground(color(80, 113, 60))
		.setColorActive(color(73, 113, 74))
		.setColorForeground(color(73, 113, 24));
    }

    /**
     * Sets the button grey.
     *
     * @param darthVader a button to make grey
     */
    private void setButtonGrey(controlP5.Button darthVader) {
	darthVader.setColorBackground(color(0, 50))
		.setColorActive(color(0, 200))
		.setColorForeground(color(0, 150));
    }

    /**
     * Checks if all inputs are valid, if yes sets the start button green else red.
     */
    public void checkStart() {
	if (mapFile != null && redBrain != null && blackBrain != null) {
	    if ((MapInterpreter.mapchecker(mapFile.getAbsolutePath()))
		    && AntBrainInterpreterCoryn.antBrainChecker(blackBrain
			    .getAbsolutePath())
		    && AntBrainInterpreterCoryn.antBrainChecker(redBrain
			    .getAbsolutePath())) {
		startButton.unlock();
		setButtonGreen(startButton);
	    } else {
		setButtonRed(startButton);
		startButton.lock();
	    }
	} else if (mapMap != null && redBrain != null && blackBrain != null) {
	    if (AntBrainInterpreterCoryn.antBrainChecker(blackBrain
		    .getAbsolutePath())
		    && AntBrainInterpreterCoryn.antBrainChecker(redBrain
			    .getAbsolutePath())) {
		startButton.unlock();
		setButtonGreen(startButton);
	    } else {
		setButtonRed(startButton);
		startButton.lock();
	    }
	}

    }

    /**
     * Creates the temp directory for the help files.
     */
    public void createTempDir() {
	try {
	    tmp_1 = Files.createTempDirectory(null);
	    Files.createDirectory(Paths.get(tmp_1.toString(), "img"));
	    asFile = tmp_1.toFile();
	    asFile.deleteOnExit();
	    filesToTemp("index.html");
	    filesToTemp("img/bg.png");
	    filesToTemp("img/bg25.png");
	    filesToTemp("img/bg50.png");
	    filesToTemp("img/bg80.png");
	    filesToTemp("img/buttons.jpg");
	    filesToTemp("img/team17.png");
	    filesToTemp("img/main_menu.jpg");
	    filesToTemp("img/random.jpg");
	    filesToTemp("img/ready.jpg");
	    filesToTemp("img/result.jpg");
	    filesToTemp("img/simulator.jpg");
	    filesToTemp("img/tokens.jpg");
	    filesToTemp("img/tourna.jpg");
	    filesToTemp("img/wait.jpg");
	} catch (Exception e) {
	    System.err.println(e);
	}
    }

    /**
     * Copies the file from the root to the temp directory
     * @param filename or path of the file to be copied. Make sure directory exists.
     */
    private void filesToTemp(String name) {
	try {
	    InputStream copy_from = MainMenu.class
		    .getResourceAsStream("/userGuide/" + name);
	    Path copy_to = Paths.get(tmp_1.toString(), name);
	    Files.copy(copy_from, copy_to);
	} catch (Exception e) {
	    System.err.println(e);
	}
    }
}
