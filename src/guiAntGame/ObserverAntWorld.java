package guiAntGame;
import java.util.ArrayList;
import antgame.core.Cell;
import antgame.core.World;


/**
 * A simple observer for the world class. It keeps the cells which had change in them which get
 * be retrieved or cleared.
 * @author Doniyor
 */
public class ObserverAntWorld {
	
	/** The world object which the Observer is overlooking */
	private World curW;
	
	/** A list of cells which have changed since last clear */
	private ArrayList<Cell> toUpdate;
	
	/**
	 * Instantiates a new observer ant world.
	 *
	 * @param w is the world to observe
	 */
	public ObserverAntWorld(World w){
		this.toUpdate = new ArrayList<Cell>();
		this.curW = w;
	}
	
	/**
	 * Gets the world.
	 *
	 * @return the world
	 */
	public World getWorld(){
		return this.curW;
	}
	
	/**
	 * Gets the the arrays of cells which have changed since the last clear
	 *
	 * @return array of cells which have changed since last clear
	 */
	public ArrayList<Cell> getToUpdate() {
		return toUpdate;
	}

	/**
	 * Adds a cell the to ArrayList.
	 *
	 * @param addCell the cell to be Added
	 */
	public void addToUpdate(Cell addCell) {
		this.toUpdate.add(addCell);
	}
	
	/**
	 * Clear list.
	 */
	public void clearList(){
		this.toUpdate.clear();
	}
}
