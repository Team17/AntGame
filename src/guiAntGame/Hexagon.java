package guiAntGame;
import processing.core.*;

public class Hexagon {
	private PImage iconFile;
	private PApplet parent;
//	private int units;
//	private int dir;
	
	public Hexagon(PApplet parent, PImage iconFile){
		this.parent = parent;
		this.iconFile = iconFile;
//		this.units = units;
//		this.dir = 0;
	}
	
	public void display(int x, int y, int xS, int yS){
//		parent.rotate(PApplet.radians(60*dir));
		parent.image(iconFile,x,y,xS,yS);
	}
	
	public void setIcon(PImage newImage){
		iconFile = newImage;
	}
	public boolean toCompare(PImage toCompare){
		return iconFile.equals(toCompare);
	}
}
