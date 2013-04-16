package guiAntGame;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observer;

import javax.swing.JFileChooser;

import antgame.core.AntBrain;
import antgame.core.AntBrainInterpreterCoryn;
import antgame.core.AntColour;
import antgame.core.MapInterpreter;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.Textarea;
import processing.core.*;
public class Tournament extends PApplet{
	private ControlP5 cp5;
	private PImage bgMain;
	controlP5.Button addBrain , selectBrain, selectMap, startButton;
	private PFont arialMain, arialB;
	private File mapFile, toBeAdded;
	private ArrayList<File> listOfBrains;
	private Textarea listBrainsArea;

	public void setup() {

		size(400, 500);

		arialMain = createFont("ArialBold", 32);
		arialB = createFont("Arial", 16);

		listOfBrains = new ArrayList<File>();
		cp5 = new ControlP5(this);

		// Main Menu text
		cp5.addTextlabel("tournament").setPosition(width / 2 - 85, 30)
				.setText("Tournament").setFont(arialMain).setColor(200);

		// Help Button
		selectBrain = createButton("selectBrain", "Select Brain", 40, 85, 25);

		// Help Button
		addBrain = createButton("addBrain", "Add Brain", 40, 125, 125).setSize(325, 35).lock();
		
		// Tournament Button
		selectMap = createButton("selectMap", "Select Map", 205,
				85, 30);

		listBrainsArea = cp5.addTextarea("txt")
                  .setPosition(40,170)
                  .setSize(325,200)
                  .setFont(arialB)
                  .setLineHeight(14)
                  .setColor(color(28))
                  .setColorBackground(color(255,100))
                  .setColorForeground(color(255,100))
                  .setText("You need to select at least 3 antbrains, and a map for the tournament.");
		
		startButton = cp5.addButton("startButton").setPosition(40, 380)
				.setSize(325, 65).setColorBackground(color(115, 73, 73))
				.setColorActive(color(215, 73, 73))
				.setColorForeground(color(115, 73, 73)).lock();

		startButton.getCaptionLabel().setFont(arialB).setText("START")
				.toUpperCase(false).setSize(38).setColor(color(140)).getStyle()
				.setMarginLeft(100);
		
		bgMain = loadImage("bg.jpg");

	}

	public void draw() {
		background(200);
		image(bgMain,0,0);
	}

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


	public void selectBrain() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (AntBrainInterpreterCoryn.antBrainChecker(file
					.getAbsolutePath())) {
				selectBrain.getCaptionLabel().set(
						shortText(file.getName(), 13)).getStyle().setMarginLeft(27);
				setButtonGreen(selectBrain);
				addBrain.unlock();
				toBeAdded = file;
			} else {
				selectBrain.getCaptionLabel().set("Wrong Brain").getStyle().setMarginLeft(27);
				setButtonRed(selectBrain);
			}
		}
	}

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

	public void setListBrainAreaText(){
		String addNames = "";
		for(int i=0;i<listOfBrains.size();i++){
			addNames = addNames + listOfBrains.get(i).getName() + "\n";
		}
		listBrainsArea.setText(addNames);
	}

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
			} else {
				selectMap.getCaptionLabel().set("Wrong Map");
				setButtonRed(selectMap);
			}
			checkStart();
		}
	}

	public void setButtonRed(controlP5.Button darthMaul) {
		darthMaul.setColorBackground(color(115, 73, 74))
				.setColorActive(color(175, 73, 74))
				.setColorForeground(color(215, 73, 74));
	}

	public void setButtonGreen(controlP5.Button yoda) {
		yoda.setColorBackground(color(80, 113, 60))
				.setColorActive(color(73, 113, 74))
				.setColorForeground(color(73, 113, 24));
	}

	public String shortText(String toBeShort, int len) {
		if (toBeShort.length() > len) {
			return toBeShort.substring(0, len);
		} else {
			return toBeShort;
		}
	}
	
	private void checkStart(){
		if(mapFile !=null){
			if(listOfBrains.size()>2 && (MapInterpreter.mapchecker(mapFile.getAbsolutePath()))){
				setButtonGreen(startButton);
				startButton.unlock();
			}else{
				setButtonRed(startButton);
				startButton.lock();
			}	
		}

	}
	
	public void startButton(){
		if(mapFile !=null){
			if(listOfBrains.size()>2 && (MapInterpreter.mapchecker(mapFile.getAbsolutePath()))){
				
				List<AntBrain> a = new ArrayList<AntBrain>();
				for(int i=0;i<listOfBrains.size();i++){
					a.add(new AntBrain(listOfBrains.get(i).getAbsolutePath(), AntColour.RED));
				}
				antgame.Tournament z = new antgame.Tournament(a, MapInterpreter.MapGenerator(mapFile.getAbsolutePath()));
				z.runTournament();
				HashMap<AntBrain, Integer> g = z.getAllScores();
				
				for (Entry<AntBrain, Integer> entry : g.entrySet()) {
			        AntBrain key = entry.getKey();
			        Integer value = entry.getValue();
			        System.out.println(key + " : " + value);
			    }
				
				System.out.println("Fin.");
				
				}else{
				setButtonRed(startButton);
				startButton.setCaptionLabel("ERROR!").lock();
			}	
		}
	}
	

	
	
}
