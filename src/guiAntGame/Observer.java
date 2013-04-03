package guiAntGame;
import java.util.ArrayList;

import antgame.core.Cell;

public class Observer {
	private int redAnts;
	private int blackAnts;
	private ArrayList<Cell> toUpdate;
	
	public Observer(int redAnts, int blackAnts){
		this.redAnts= redAnts;
		this.blackAnts=blackAnts;
	}
	
	public int getRedAnts() {
		return redAnts;
	}

	public void setRedAnts(int redAnts) {
		this.redAnts = redAnts;
	}

	public int getBlackAnts() {
		return blackAnts;
	}

	public void setBlackAnts(int blackAnts) {
		this.blackAnts = blackAnts;
	}

	public ArrayList<Cell> getToUpdate() {
		return toUpdate;
	}

	public void setToUpdate(ArrayList<Cell> toUpdate) {
		this.toUpdate = toUpdate;
	}
}
