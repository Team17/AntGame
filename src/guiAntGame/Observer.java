package guiAntGame;
import java.util.ArrayList;

import antgame.core.Cell;

public class Observer {
	private int redAnts;
	private int blackAnts;
	private int foodUnitsOnMap;
	private ArrayList<Cell> toUpdate;
	
	public Observer(int redAnts, int blackAnts){
		this.redAnts= redAnts;
		this.blackAnts=blackAnts;
		this.toUpdate = new ArrayList<Cell>();
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

	public int getFoodUnitsOnMap() {
		return foodUnitsOnMap;
	}

	public void setFoodUnitsOnMap(int foodUnitsOnMap) {
		this.foodUnitsOnMap = foodUnitsOnMap;
	}
	
	public ArrayList<Cell> getToUpdate() {
		return toUpdate;
	}

	public void addToUpdate(Cell addCell) {
		this.toUpdate.add(addCell);
	}
	
	public void clearList(){
		this.toUpdate.clear();
	}
}
