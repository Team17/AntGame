package antgame.core;


import java.util.ArrayList;

import antgame.AntGame;

public class World {

	private Ant[] ants;
	private Map map;
	private AntBrain redAntBrain;
	private AntBrain blackAntBrain;
	private ArrayList<Cell> bAHLoc = new ArrayList<Cell>();
	private ArrayList<Cell> rAHLoc = new ArrayList<Cell>();
	private int foodInRAH;
	private int foodInBAH;
	
	
	public World(String mapLocation, AntBrain antR, AntBrain antB, int numOfAnts){//, String antR, String antB){
		this.map = new Map(mapLocation);
		ants = new Ant[numOfAnts];
		this.redAntBrain = new AntBrain(antR);
		this.blackAntBrain = new AntBrain(antB);
		
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
		
	}

	public void step(){
		for(int i=0; i < Integer.parseInt(AntGame.CONFIG.getProperty("numRound")); i++){
			
			for(int j = 0; j<ants.length;i++){
				foodInEachAntHill();
				Ant curAnt = ants[j];
				if(isAntSurronded(curAnt)){
					curAnt.killAnt();
				}
				if(curAnt.isAlive()){
				BrainState antsState = curAnt.getBrainState();
				switch (antsState) {
				case SENSE:
					Cell cellTS = sensedCell(curAnt.getCurrentPos(),curAnt.getDir(),antsState.getSenseDirection());
					SenseCondition sCon = antsState.getSenseCondition();
					if(sCon == SenseCondition.MARKER){
						cellTS.senseCheck(curAnt, sCon, sCon.getMarker());
					}
					else{
						cellTS.senseCheck(curAnt, sCon, null);
					}
					break;
				case MARK:
					break;
				case UNMARK:
					break;
				case PICKUP:
					break;
				case DROP:
					break;
				case TURN:
					// DO SOME STUFF
					antsState.getLeftRight();
					break;
				case MOVE:
					break;
				case FLIP:
					break;
				
			}
				}
			}
		}
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
		for(int i = 0; i<=rAHLoc.size();i++){
			redFood = redFood + rAHLoc.get(i).getNumberOfFoodParticles();
		}
		foodInRAH = redFood;
		
		int blackFood = 0;
		for(int i = 0; i<=bAHLoc.size();i++){
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
		World w = new World("C://map6.txt",500);
		w.map.printmap();
		Ant antinquestion = w.map.getCell(6, 2).getAnt();
		System.out.println(w.isAntSurronded(antinquestion));
	}

}

