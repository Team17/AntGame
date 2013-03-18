package antgame.core;

public class World {

	private Ant[] ants;
	private Map map;
	private AntBrain redAntBrain;
	private AntBrain blackAntBrain;
	
	
	public World(String mapLocation, int numOfAnts){//, String antR, String antB){
		this.map = new Map(mapLocation);
		ants = new Ant[numOfAnts];
	//	this.redAntBrain = new AntBrain(antR);
	//	this.blackAntBrain = new AntBrain(antB);
		
		// antPointer is the pointer in the array of ants to point to the next free positon also used as the uID
		int antPointer = 0;
		/* the following goes trough all of the cells looking for cells that contain an anthill
		 * once it finds an anthill it creates an ant sets its antid to the ant pointer, its direction to zero, its color dependant on the color
		 * ant hill, its state which is 0 and its intial location.
		 * it then adds the ant to the map !!!!!! should this be the constructor or call the method ant.
		 * it then increases the pointer
		 * 
		 */
		for (int y = 0; y < (map.getYSize()); y++) {
			for (int x = 0; x < (map.getXSize()); x++) {
				if(map.getCell(x, y).isContainsRedAntHill()){
					ants[antPointer] = new Ant(antPointer, 0, true, 0,map.getCell(x, y),redAntBrain);
					
					map.getCell(x, y).antMoveIn(ants[antPointer]);
					antPointer ++;
				}
				if(map.getCell(x, y).isContainsBlackAntHill()){
					ants[antPointer] = new Ant(antPointer, 0, false, 0,map.getCell(x, y),blackAntBrain);			
					map.getCell(x, y).antMoveIn(ants[antPointer]);
					antPointer ++;
				}
			}
		}
		
	}
	public Ant getAnt(Cell cell){
		if(cell.isContainsAnt()){
			return cell.getAnt();
			
		}
		else{
			return null;
		}
		
	}
	public Ant getAnt(int[] pos){
		Cell cell = map.getCell(pos);
		if(cell.isContainsAnt()){
			
			return cell.getAnt();
			
			}
			else{
				return null;
			}
		
	}
	public boolean isAntAt(Cell cell){
		return cell.isContainsAnt();
	}
	public boolean isAntAt(int[] pos){
		
		Cell cell = map.getCell(pos);
		return cell.isContainsAnt();
	}
	
	public void killAntAt(Cell cell){
		if(cell.isContainsAnt()){
			cell.getAnt().die();
			cell.addFood(3);
			ants[cell.getAnt().getuID()] = null;
			cell.antMoveOut();
		}
		else{
			System.err.print("No ant in Cell");
			
		}
	}
	
	public static void main (String[] args){
		World w = new World("C://a.world",500);
		w.map.printMap();
	}
}

