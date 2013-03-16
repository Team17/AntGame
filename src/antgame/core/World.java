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
		for (int y = 0; y < (map.getySize()); y++) {
			for (int x = 0; x < (map.getxSize()); x++) {
				if(map.getCell(x, y).isContainsRedAntHill()){
					ants[antPointer] = new Ant(antPointer, 0, true, 0,map.getCell(x, y));
					
					map.getCell(x, y).antMoveIn(ants[antPointer]);
					antPointer ++;
				}
				if(map.getCell(x, y).isContainsBlackAntHill()){
					ants[antPointer] = new Ant(antPointer, 0, false, 0,map.getCell(x, y));			
					map.getCell(x, y).antMoveIn(ants[antPointer]);
					antPointer ++;
				}
			}
		}
		
	}
	public static void main (String[] args){
		World w = new World("C://a.world",500);
		w.map.printmap();
	}
}

