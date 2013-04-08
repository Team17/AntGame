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

	// public void setup(Observer obiwan,int xSize, int ySize) {
	public void setup() {


		size(600, 700);

		cp5 = new ControlP5(this);
		cp5.addFrameRate().setInterval(10).setPosition(0, height - 10);

		cp5.addTextlabel("mainText")
				.setPosition(width/2-80,20 )
				.setText("MAIN MENU")
				.setFont(createFont("Calibri", 30))
				.setColor(255);
		

		cp5.addButton("help").setPosition(50, 50).setSize(150, 20);

		cp5.addButton("endgame").setPosition(500, 670).setSize(60, 20);
		draft = loadImage("draft.jpg");
		
		

	}

	public void draw() {
		image(draft,0,0);
		
	}

}
