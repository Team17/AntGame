package antgame.core;

public class World {

	private Ant[] ants;
	private Map map;
	private AntBrain redAntBrain;
	private AntBrain blackAntBrain;
	
	
	public World(String mapLocation, String antR, String antB){
		this.map = new Map(mapLocation);
		this.redAntBrain = new AntBrain(antR);
		this.blackAntBrain = new AntBrain(antB);
		
		// antPointer is the pointer in the array of ants to point to the next free positon also used as the uID
		int antPointer = 0;
		for (int y = 0; y < (map.getySize()); y++) {
			for (int x = 0; x < (map.getxSize()); x++) {
				if(map.getCell(x, y).ContainsRedAntHill()){
					ants[antPointer] = new Ant(antPointer, 0, true, 0);
					antPointer ++;
				}
				if(map.getCell(x, y).ContainsBlackAntHill()){
					ants[antPointer] = new Ant(antPointer, 0, false, 0);
					antPointer ++;
				}
			}
		}
		
	}
}

