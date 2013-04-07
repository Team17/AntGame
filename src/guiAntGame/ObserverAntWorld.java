package guiAntGame;
import java.util.ArrayList;

import antgame.core.Cell;
import antgame.core.World;
import antgame.core.WorldStats;

public class ObserverAntWorld {
	private WorldStats stats;
	private World curW;
	private ArrayList<Cell> toUpdate;
	
	public ObserverAntWorld(World w){
		this.toUpdate = new ArrayList<Cell>();
		this.curW = w;
		this.stats = w.getStats();
	}
	
	public World getWorld(){
		return this.curW;
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
