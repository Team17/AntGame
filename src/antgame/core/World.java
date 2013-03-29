package antgame.core;


import java.util.ArrayList;

import antgame.AntGame;
import antgame.services.RandomNumber;

public class World {

	private Ant[] ants;
	int noAnts;
	private Map map;
	private AntBrain redAntBrain;
	private AntBrain blackAntBrain;
	private ArrayList<Cell> bAHLoc = new ArrayList<Cell>();
	private ArrayList<Cell> rAHLoc = new ArrayList<Cell>();
	private int foodInRAH;
	private int foodInBAH;
	
	
	public World(String mapLocation, String antR, String antB){//, String antR, String antB){
		this.map = new Map(mapLocation);
		
		this.redAntBrain = new AntBrain(antR, AntColour.RED);
		this.blackAntBrain = new AntBrain(antB,AntColour.BLACK);
		
		// antPointer is the pointer in the array of ants to point to the next free positon also used as the uID
		
		/* the following goes trough all of the cells looking for cells that contain an anthill
		 * once it finds an anthill it creates an ant sets its antid to the ant pointer, its direction to zero, its color dependant on the color
		 * ant hill, its state which is 0 and its intial location.
		 * it then adds the ant to the map !!!!!! should this be the constructor or call the method ant.
		 * it then increases the pointer
		 * 
		 */
		for (int y = 0; y < (map.getYSize()); y++) {
			for (int x = 0; x < (map.getXSize()); x++) {
				if(map.getCell(x, y).containsRedAntHill() || map.getCell(x, y).containsBlackAntHill()){
					
					noAnts++;
				}
			}
		}
		ants = new Ant[noAnts];
		int antPointer = 0;
		for (int y = 0; y < (map.getYSize()); y++) {
			for (int x = 0; x < (map.getXSize()); x++) {
				if(map.getCell(x, y).containsRedAntHill()){
					ants[antPointer] = new Ant(antPointer, 0, AntColour.RED, 0,map.getCell(x, y),redAntBrain);
					rAHLoc.add(map.getCell(x, y));
					map.getCell(x, y).antMoveIn(ants[antPointer]);
					antPointer ++;
				}
				if(map.getCell(x, y).containsBlackAntHill()){
					ants[antPointer] = new Ant(antPointer, 0, AntColour.BLACK, 0,map.getCell(x, y),blackAntBrain);	
					bAHLoc.add(map.getCell(x, y));
					map.getCell(x, y).antMoveIn(ants[antPointer]);
					antPointer ++;
				}
			}
		}
		map.printmap();
		for(int i=0; i < 10000; i++){
			step();
		}
		map.printmap();
		System.out.print("Food In Red AntHill: " + foodInRAH + "Food In Black AntHill: " + foodInBAH);
		
	}

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
					cellGoingTo.antMoveIn(curAnt);
					curAnt.setCurrentPos(cellGoingTo);
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
		ArrayList<Cell> adjCells = map.adjacentCells(ant.getCurrentPos());
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
		ArrayList<Cell> adjCells = map.adjacentCells(ant.getCurrentPos());
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
		
		World w1 = new World(workingDir+"\\files\\workingworld.world",workingDir+"\\files\\cleverbrain1.brain",workingDir+"\\files\\cleverbrain2.brain");
	
	}

}

