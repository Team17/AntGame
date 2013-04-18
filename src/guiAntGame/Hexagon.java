package guiAntGame;
import processing.core.*;


/**
 *A simple Hexagon class which stores the PImage file inside it.
 *@author Doniyor
 */
public class Hexagon {
	
	/** The PImage icon file. */
	private PImage iconFile;
	
	/** The parent Processing object */
	private PApplet parent;
	
	/**
	 * Instantiates a new hexagon.
	 *
	 * @param parent the parent
	 * @param iconFile the Pimage icon file
	 */
	public Hexagon(PApplet parent, PImage iconFile){
		this.parent = parent;
		this.iconFile = iconFile;
	}
	
	/**
	 * Display the hexagon at:
	 * @param x the x
	 * @param y the y
	 * With the Following dimensions:
	 * @param xS the x s
	 * @param yS the y s
	 */
	public void display(int x, int y, int xS, int yS){
		parent.image(iconFile,x,y,xS,yS);
	}
	
	/**
	 * Sets the icon.
	 * @param newImage the new icon
	 */
	public void setIcon(PImage newImage){
		iconFile = newImage;
	}
	
	/**
	 * To compare.
	 *
	 * @param toCompare the to compare
	 * @return true, if same
	 */
	public boolean toCompare(PImage toCompare){
		return iconFile.equals(toCompare);
	}
}
