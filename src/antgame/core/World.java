package antgame.core;


import java.util.ArrayList;

import antgame.AntGame;
import antgame.services.RandomNumber;


public class World {
	//ants stores an array of ants black and red
	private Ant[] ants;
	//noAnts stores the number of ants
	int noAnts;
	//map stores the map
	private Map map;
	//redAntBrain stores the red Ant Brain
	private AntBrain redAntBrain;
	//blackAntBrain stores the black Ant Brain
	private AntBrain blackAntBrain;
	//rAHLoc stores the red ant hill locations, used for finding the number of food particles in the ant hill
	private ArrayList<Cell> rAHLoc = new ArrayList<Cell>();
	//bAHLoc stores the black ant hill locations, used for finding the number of food particles in the ant hill
	private ArrayList<Cell> bAHLoc = new ArrayList<Cell>();
	//foodInBAH stores the number of food particles in the red ant hill
	private int foodInRAH;
	//foodInBAH stores the number of food particles in the black ant hill
	private int foodInBAH;
	
	/**
	 * World constuctor takes the dirctory of the map, it then passes this to the MapInterpreter who returns an instance of map.
	 * It also takes the dirctory of each ant brain and creates a new instance of ant brain for each, at creation the ant brain passes the file through
	 * the AntBrainInterpreter.
	 * The constructor sets up all the ants as well as calles the step function.
	 * @param mapLocation
	 * @param antR
	 * @param antB
	 */
	public World(String mapLocation, String antR, String antB){//, String antR, String antB){
		this.map = MapInterpreter.MapGenerator(mapLocation);
		
		this.redAntBrain = new AntBrain(antR, AntColour.RED);
		this.blackAntBrain = new AntBrain(antB,AntColour.BLACK);
		
		//the following basically counts the number of ant hill cells and thus how many ants there will be.
		for (int y = 0; y < (map.getYSize()); y++) {
			for (int x = 0; x < (map.getXSize()); x++) {
				if(map.getCell(x, y).containsRedAntHill() || map.getCell(x, y).containsBlackAntHill()){
					
					noAnts++;
				}
			}
		}
		//based on the number of ants which the for loop above found the ant array can now be initialised.
		ants = new Ant[noAnts];
		
		
				/* the following loop iterated over each cells in the map looking for cells that contain an anthill
				 * once it finds an anthill it creates an ant and sets its current location there. It then sets that ant's antid to the antPointer,
				 * its direction to zero, its colour dependent on the colour of the ant hill, its initial brain state which is 0.
				 * it then increases the pointer, as well as adding the cell itself to the array of ant hills, respective of colour.
				 * 
				 */
		
		// antPointer is the pointer in the array of ants to point to the next free position also used as the uID
		int antPointer = 0;
		for (int y = 0; y < (map.getYSize()); y++) {
			for (int x = 0; x < (map.getXSize()); x++) {
				if(map.getCell(x, y).containsRedAntHill()){
					ants[antPointer] = new Ant(antPointer, 0, AntColour.RED, 0,map.getCell(x, y),redAntBrain);
					map.getCell(x, y).antMoveIn(ants[antPointer]);
					antPointer ++;
					rAHLoc.add(map.getCell(x, y));
				}
				if(map.getCell(x, y).containsBlackAntHill()){
					ants[antPointer] = new Ant(antPointer, 0, AntColour.BLACK, 0,map.getCell(x, y),blackAntBrain);	
					map.getCell(x, y).antMoveIn(ants[antPointer]);
					antPointer ++;
					bAHLoc.add(map.getCell(x, y));
				}
			}
		}
		
		//calles the step method.
		for(int i=0; i < 300000; i++){
			step();
		}
		
		//just for stats.
		int redAlive =0;
		int blackAlive = 0;
		for(Ant a:ants){
			if(a.isAlive()){
				if(a.getColour() == AntColour.RED){
					redAlive++;
				}
				else if(a.getColour()==AntColour.BLACK){
					blackAlive++;
				}
				
			}
			
		}
		map.printmap();
	}
	
	/**
	 * step is the function that is called 300,000 times 
	 */
	public void step(){
		
		
			for(int j = 0; j<noAnts;j++){
				foodInEachAntHill();
				
				Ant curAnt = ants[j];
				if(isAntSurronded(curAnt)){
					curAnt.killAnt();
				}
				if(curAnt.isAlive()){
				BrainState antsState = curAnt.getBrainState();
				//System.out.println(curAnt.getState().getInstruction());
				//antsState.print();
				if(curAnt.getResting()>0){
					curAnt.decResting();
				}
				else{
				switch (antsState.getInstruction()) {
				case SENSE:
				//	antsState.print();
					Cell cellTS = sensedCell(curAnt.getCurrentPos(),curAnt.getDir(),antsState.getSenseDirection());
					SenseCondition sCon = antsState.getSenseCondition();
					if(sCon == SenseCondition.MARKER){
						//antsState.print();
						//System.out.println(antsState.getSenseCondition());
						if(cellTS.senseCheck(curAnt, sCon, antsState.getMarker())){
							curAnt.setBrainState(antsState.getNextState());
						}
						else{
							curAnt.setBrainState(antsState.getAltNextState());
						}
					}
					else{
						if(cellTS.senseCheck(curAnt, sCon, null)){
							curAnt.setBrainState(antsState.getNextState());
						}
						else{
							curAnt.setBrainState(antsState.getAltNextState());
						}
					}
					break;
				case MARK:
					curAnt.getCurrentPos().setMarker(antsState.getMarker());
					curAnt.setBrainState(antsState.getNextState());
					break;
				case UNMARK:
					curAnt.getCurrentPos().clearMarker(antsState.getMarker());
					curAnt.setBrainState(antsState.getNextState());
					break;
				case PICKUP:
					if(curAnt.getCurrentPos().isContainsFood()){
						curAnt.getCurrentPos().removeFood();
						curAnt.pickupFood();
						curAnt.setBrainState(antsState.getNextState());
					}
					else{
						curAnt.setBrainState(antsState.getAltNextState());
					}
					break;
				case DROP:
						if(curAnt.isHasFood()){
							curAnt.dropFood();
							curAnt.getCurrentPos().addFood();
						}
						
						curAnt.setBrainState(antsState.getNextState());	
						
					break;
				case TURN:
					curAnt.turn(antsState.getLeftRight());
					curAnt.setBrainState(antsState.getNextState());	
					break;
				case MOVE:
					Cell cellGoingTo = map.adjacentCell(curAnt.getCurrentPos(), curAnt.getDir());
					if(cellGoingTo.isClear()){
					curAnt.getCurrentPos().antMoveOut();
					curAnt.setResting();
					cellGoingTo.antMoveIn(curAnt);
					curAnt.setCurrentPos(cellGoingTo);
				//	curAnt.isResting();!!!!!!!!!!!!!!!!!!
					curAnt.setBrainState(antsState.getNextState());
					}
					else{
						curAnt.setBrainState(antsState.getAltNextState());
					}
					break;
				case FLIP:
					RandomNumber rN = new RandomNumber();
					if(rN.nextInt(antsState.getRandomInt()) ==0){
						curAnt.setBrainState(antsState.getNextState());
					}
					else{
						curAnt.setBrainState(antsState.getAltNextState());
					}
					
					break;
				
			}
				}
				
			}}
	}
	public Cell sensedCell(Cell cell, int dir, SenseDirection senseDir){
		switch (senseDir){
			case HERE:
				return cell;
			case AHEAD:
				return map.adjacentCell(cell,dir);
			case LEFTAHEAD:
				return map.adjacentCell(cell,((dir+5)%6));
			case RIGHTAHEAD:
				return map.adjacentCell(cell,(dir+1));
			default:
				return null;
		}
	}
		
	public void foodInEachAntHill(){
		int redFood = 0;
		for(int i = 0; i<rAHLoc.size();i++){
			redFood = redFood + rAHLoc.get(i).getNumberOfFoodParticles();
		}
		foodInRAH = redFood;
		
		int blackFood = 0;
		for(int i = 0; i<bAHLoc.size();i++){
			blackFood = blackFood + bAHLoc.get(i).getNumberOfFoodParticles();
		}
		foodInBAH = blackFood;
	}
	
	public Ant getAnt(Cell cell){
		if(cell.containsAnt()){
			return cell.getAnt();
			
		}
		else{
			return null;
		}
		
	}
	public Ant getAnt(int[] pos){
		Cell cell = map.getCell(pos);
		if(cell.containsAnt()){
			
			return cell.getAnt();
			
			}
			else{
				return null;
			}
		
	}
	public boolean isAntAt(Cell cell){
		return cell.containsAnt();
	}
	public boolean isAntAt(int[] pos){
		
		Cell cell = map.getCell(pos);
		return cell.containsAnt();
	}
	
	public void killAntAt(Cell cell){
		if(cell.containsAnt()){
			//cell.getAnt().die();
			cell.addNumFood(3);
			ants[cell.getAnt().getuID()] = null;
			cell.antMoveOut();
		}
		else{
			System.err.print("No ant in Cell");
			
		}
	}
	
	public void setAntAt(Ant ant, Cell cell){
		if(cell.isClear()){
			ant.setCurrentPos(cell);
			cell.antMoveIn(ant);
		}
	}
	
	public boolean nieveIsAntSurronded(Ant ant){
		ArrayList<Cell> adjCells = map.surrondingCells(ant.getCurrentPos());
		boolean surrounded = true;
		for(Cell c:adjCells){
			if(c.isClear()){
				//
				surrounded = false;
			}
		}
		return surrounded;
	}
	public boolean isAntSurronded(Ant ant){
		ArrayList<Cell> adjCells = map.surrondingCells(ant.getCurrentPos());
		boolean surrounded = true;
		for(Cell c:adjCells){
			if(c.isClear()){
				surrounded = false;
			}
			else if(ant.getColour() ==  AntColour.RED && c.containsRedAnt()){
				//if red ant
				surrounded = false;
			}
			else if(ant.getColour() == AntColour.BLACK && c.containsBlackAnt()){
				//if red ant
				surrounded = false;
			}
		}
		return surrounded;
	}
	
	
	public static void main (String[] args){
		String workingDir = System.getProperty("user.dir");
		
		World w1 = new World("C:/workingworld.world"/*workingDir+"\\files\\workingworld.world"**/,workingDir+"\\files\\cleverbrain1.brain",workingDir+"\\files\\cleverbrain2.brain");
	
	}

}

