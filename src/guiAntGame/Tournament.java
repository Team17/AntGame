package guiAntGame;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
import controlP5.ControlP5;
import controlP5.Textarea;

/**
 * The Tournament Menu
 * 
 * @author Doniyor
 */
public class Tournament extends PApplet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cp5. */
	private ControlP5 cp5;

	/** The background image */
	private PImage bgMain;

	/** Buttons variables */
	controlP5.Button addBrain, selectBrain, selectMap, startButton, createMap;

	/** Fonts */
	private PFont arialMain, arialB;

	/** Map and to be added brain files */
	private File mapFile, toBeAdded;

	/** The list of brains. */
	private ArrayList<File> listOfBrains;

	/** The list brains text area. */
	private Textarea listBrainsArea;

	/** The random map. */
	private Map mapMap;

	/**
	 * Sets up the window, loads all the images and fonts, positions the
	 * buttons.
	 * 
	 * @see processing.core.PApplet#setup()
	 */
	public void setup() {

		size(400, 500);

		arialMain = createFont("ArialBold", 32);
		arialB = createFont("Arial", 16);

		listOfBrains = new ArrayList<File>();
		cp5 = new ControlP5(this);

		// Tournament Label
		cp5.addTextlabel("tournament").setPosition(width / 2 - 85, 30)
				.setText("Tournament").setFont(arialMain).setColor(200);

		// Select Brain Button
		selectBrain = createButton("selectBrain", "Select Brain", 40, 85, 25);

		// Add Brain Button
		addBrain = createButton("addBrain", "Add Brain", 40, 125, 35).lock();

		// Select Map Button
		selectMap = createButton("selectMap", "Select Map", 205, 85, 30);

		// Create Random Map Button
		createMap = createButton("createMap", "Random Map", 205, 125, 20);

		// text area to display the ant brains
		listBrainsArea = cp5
				.addTextarea("txt")
				.setPosition(40, 170)
				.setSize(325, 200)
				.setFont(arialB)
				.setLineHeight(14)
				.setColor(color(28))
				.setColorBackground(color(255, 100))
				.setColorForeground(color(255, 100))
				.setText(
						"You need to select at least 3 antbrains, and a map for the tournament.");

		//Start Button
		startButton = cp5.addButton("startButton").setPosition(40, 380)
				.setSize(325, 65).setColorBackground(color(115, 73, 73))
				.setColorActive(color(215, 73, 73))
				.setColorForeground(color(115, 73, 73)).lock();

		startButton.getCaptionLabel().setFont(arialB).setText("START")
				.toUpperCase(false).setSize(38).setColor(color(140)).getStyle()
				.setMarginLeft(100);

		//Load the background image
		bgMain = loadImage("bg.jpg");

	}

	/**
	 * Displays the menu 
	 * @see processing.core.PApplet#draw()
	 */
	public void draw() {
		background(200);
		image(bgMain, 0, 0);
	}

	/**
	 * Creates a standard button.
	 * 
	 * @param name
	 *            of the button
	 * @param text
	 *            to display on the button
	 * @param x
	 *            position of the button
	 * @param y
	 *            position of the button
	 * @param m
	 *            left margin of the text
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
	 * Select brain button listener. Activates a file picker on click, and
	 * checks if the brain is valid. If not makes the button red, else green.
	 */
	public void selectBrain() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (AntBrainInterpreterCoryn
					.antBrainChecker(file.getAbsolutePath())) {
				selectBrain.getCaptionLabel()
						.set(shortText(file.getName(), 13)).getStyle()
						.setMarginLeft(27);
				setButtonGreen(selectBrain);
				addBrain.unlock();
				toBeAdded = file;
			} else {
				selectBrain.getCaptionLabel().set("Wrong Brain").getStyle()
						.setMarginLeft(27);
				setButtonRed(selectBrain);
			}
		}
	}

	/**
	 * Add brain button listener. Adds the brain picked via the Select Brain
	 * button to the array list and clears the button
	 */
	public void addBrain() {
		listOfBrains.add(toBeAdded);
		addBrain.lock();
		selectBrain.setColorBackground(color(0, 50))
				.setColorActive(color(0, 200))
				.setColorForeground(color(0, 150));
		selectBrain.getCaptionLabel().setText("Select Brain");
		setListBrainAreaText();
		checkStart();
	}

	/**
	 * Select map button listener. Activates a file picker on click, and checks
	 * if the map is valid. If not makes the button red, else green.
	 */
	public void selectMap() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			if (MapInterpreter.mapchecker(file.getAbsolutePath())) {
				selectMap.getCaptionLabel().set(shortText(file.getName(), 15));
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
	 * Start button listener. Checks if all inputs are valid, if yes creates a
	 * new simulator view with the inputs.
	 */
	public void startButton() {

		List<AntBrain> a = new ArrayList<AntBrain>();
		for (int i = 0; i < listOfBrains.size(); i++) {
			a.add(new AntBrain(listOfBrains.get(i).getAbsolutePath(),
					AntColour.RED));
		}
		antgame.Tournament z = new antgame.Tournament(a,
				MapInterpreter.MapGenerator(mapFile.getAbsolutePath()));
		z.runTournament();
		HashMap<AntBrain, Integer> g = z.getAllScores();

		for (Entry<AntBrain, Integer> entry : g.entrySet()) {
			AntBrain key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println(key + " : " + value);
		}

		System.out.println("Fin.");

	}

	/**
	 * A random map button listener. Creates a new random map, locks the button,
	 * assigns the random map to the mapMap variable and nulls the mapFile. The
	 * button becomes green and reverts the select map button back to grey if it
	 * was green or red.
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
				.setMarginLeft(30);
		checkStart();
	}

	/**
	 * Sets the button red.
	 * 
	 * @param darthMaul
	 *            a button to make red
	 */
	public void setButtonRed(controlP5.Button darthMaul) {
		darthMaul.setColorBackground(color(115, 73, 74))
				.setColorActive(color(175, 73, 74))
				.setColorForeground(color(215, 73, 74));
	}

	/**
	 * Sets the button green.
	 * 
	 * @param yoda
	 *            a button to make green
	 */
	public void setButtonGreen(controlP5.Button yoda) {
		yoda.setColorBackground(color(80, 113, 60))
				.setColorActive(color(73, 113, 74))
				.setColorForeground(color(73, 113, 24));
	}

	/**
	 * Sets the button grey.
	 * 
	 * @param darthVader
	 *            a button to make grey
	 */
	private void setButtonGrey(controlP5.Button darthVader) {
		darthVader.setColorBackground(color(0, 50))
				.setColorActive(color(0, 200))
				.setColorForeground(color(0, 150));
	}

	/**
	 * Shortens the string according to the int, used for the buttons to display
	 * the text within the buttons.
	 * 
	 * @param toBeShort
	 *            String that needs to get shortened
	 * @param len
	 *            the length the string needs to be shortened to
	 * @return Shortened String
	 */
	public String shortText(String toBeShort, int len) {
		if (toBeShort.length() > len) {
			return toBeShort.substring(0, len);
		} else {
			return toBeShort;
		}
	}

	/**
	 * Updates the text area in middle with the list of ant brain names.
	 */
	public void setListBrainAreaText() {
		String addNames = "";
		for (int i = 0; i < listOfBrains.size(); i++) {
			addNames = addNames + listOfBrains.get(i).getName() + "\n";
		}
		listBrainsArea.setText(addNames);
	}

	/**
	 * Checks if all inputs are valid, if yes sets the start button green else
	 * red.
	 */
	private void checkStart() {
		if (mapFile != null) {
			if ((MapInterpreter.mapchecker(mapFile.getAbsolutePath()))
					&& listOfBrains.size() > 2) {
				startButton.unlock();
				setButtonGreen(startButton);
			} else {
				setButtonRed(startButton);
				startButton.lock();
			}
		} else if (mapMap != null) {
			if (listOfBrains.size() > 2) {
				startButton.unlock();
				setButtonGreen(startButton);
			} else {
				setButtonRed(startButton);
				startButton.lock();
			}
		}

	}

}
