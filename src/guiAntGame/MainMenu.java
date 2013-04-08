package guiAntGame;

import java.util.ArrayList;

import antgame.core.Cell;
import antgame.core.Map;
import antgame.core.World;
import processing.core.*;

import org.gicentre.utils.move.*;
import controlP5.*;

public class MainMenu extends PApplet {

	private ControlP5 cp5;
	private PImage draft;
	controlP5.Button buttonHelp, buttonTournament, buttonQuit;
	private PFont arialMain, arialB;

	// public void setup(Observer obiwan,int xSize, int ySize) {
	@SuppressWarnings("deprecation")
	public void setup() {

		size(600, 700);
		

		arialMain = createFont("ArialBold", 32);
		arialB = createFont("Arial", 16);

		cp5 = new ControlP5(this);

		//Main Menu text
		cp5.addTextlabel("mainText").setPosition(width / 2 - 85, 30)
				.setText("Main Menu").setFont(arialMain).setColor(40);

		//Help Button
		buttonHelp = createButton("buttonHelp", "Help",55,85,60);
	
		//Tournament Button
		buttonTournament = createButton("buttonTournament", "Tournament",220,85,27);

		//Quit Button
		buttonQuit = createButton("buttonQuit", "Quit",385,85,27);
		
		draft = loadImage("draftUI.jpg");

	}

	public void draw() {
	//	background(200);
		image(draft, 0, 0);

	}
	
	@SuppressWarnings("deprecation")
	private controlP5.Button createButton(String name,String text, int x, int y, int m){
		//Tournament Button
		controlP5.Button b = cp5.addButton(name)
				.setPosition(x, y)
				.setSize(160, 35)
				.setColorBackground(color(0, 100))
				.setColorActive(color(0, 200))
				.setColorForeground(color(0, 150));

		b.getCaptionLabel().setFont(arialB)
				.setText(text).toUpperCase(false).setSize(18).setColor(color(40));
		b.captionLabel().getStyle().marginLeft = m;
		
		return b;
	}

}
