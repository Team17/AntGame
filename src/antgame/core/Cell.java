package antgame.core;

import java.util.ArrayList;
import java.util.HashMap;

import antgame.AntGame;
import antgame.InvalidMarkerIdException;


/**
 * @author Coryn
 *
 */
public class Cell {
	//pos stores the location of the cell in the map, it is represented by an array of size two so pos[0] = pos[x] and pos[1] = pos[y]
	private int[] pos  = new int[2];
	//content keeps record of what is in the cell (kept for testing no longer keeps up to date post setup)
	
	private String content;
	//containsFood is a boolean of whether or not the cell contains food
	private boolean containsFood;
	//numberOfFoodParticles is an int value of the amount of food contained in the cell
	private int numberOfFoodParticles;
	//containsRock is a boolean of whether or not the cell contains Rock
	private boolean containsRock;
	//isClear is a clear boolean of whether or not the cell is clear
	private boolean isClear;
	//containsAnt is a boolean of whether or not the cell contains an ant
	private boolean containsAnt;
	//antid points to the ant that is currently in the cell is that is the case.
	private Ant ant;
	//containsRedAntHill is a boolean of whether or not the cell contains a Red AntHill
	private boolean containsRedAntHill;
	//containsBlackAntHill is a boolean of whether or not the cell contains a Black AntHill
	private boolean containsBlackAntHill;
	//this field holds the markers as an array of boolean 0-5 is red markers 6-11 is Black markers
	private HashMap<Marker,Boolean>  markers;
//	//this field holds the markers as an array of boolean
//	private ArrayList<Marker> blackMarkers;
	
	
	
	public Cell(int x, int y, String content){
		pos[0] = x;
		pos[1] = y;
		markers = new HashMap<Marker, Boolean>();
		//blackMarkers = new ArrayList<Marker>();
		// Integer.parseInt(AntGame.CONFIG.getProperty("numAntMarkers"))
		for(AntColour c: AntColour.values()){
			for(int i = 0 ; i<6; i++){
				try {
					markers.put(new Marker(i,c), false);
				} catch (InvalidMarkerIdException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	    this.content = content;
	    if (content.equals("#")){
	    	containsRock = true;
	    	isClear = false;
	    }
	    else if (content.equals(".")){
	    	isClear = true;
		}
		else if(content.equals("+")){
			containsRedAntHill = true;
			isClear = true;
	    	
	    }
		else if(content.equals("-")){
			containsBlackAntHill = true;
			isClear = true;
			
		}
		else if(isInteger(content)){
			containsFood = true;
			numberOfFoodParticles = Integer.parseInt(content);
			isClear = true;
			
		}
	    
	        
}

	//	source for method isInteger http://stackoverflow.com/questions/237159/whats-the-best-way-to-check-to-see-if-a-string-represents-an-integer-in-java
	public boolean isInteger( String input ) {
		    try {
		        Integer.parseInt( input );
		        return true;
		    }
		    catch( Exception e ) {
		        return false;
		    }
		}
	
	
	// gameplay setters
	public void antMoveIn(Ant id){
		containsAnt = true;
		ant = id;
		isClear = false;
		
		
	}
	public void antMoveOut(){
		//** do we need to check whether or no it is clear?
		containsAnt = false;
		ant = null;
		isClear = true;
	
		
		
	}
	/**
	 * addFood() increments the number of food particles by 1
	 */
	public void addFood(){
		numberOfFoodParticles++;
		containsFood = true;

	}
	public void addNumFood(int n){
		numberOfFoodParticles = numberOfFoodParticles + n;
		containsFood = true;
	}
		
	public void removeFood(){
		if(numberOfFoodParticles>0){
			numberOfFoodParticles--;
		}
		if(numberOfFoodParticles==0){
			containsFood = false;
		}
	}
	/*
	 * color is true if red and false is black
	 */
	public void setMarker(Marker marker){
		markers.put(marker, true);
	}
	
	
	public void clearMarker( Marker marker){
		markers.put(marker, false);
	}

	public boolean checkMarker(Marker marker){
		return markers.get(marker);
	}
		
	/*
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * Note the final function, check_any_marker_at. Ants of a given color can individually sense,
	 *  set, and clear all 6 of their own markers, but are only able to detect the presence of some marker
	 *   belonging to the other species.
	 *   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 *   
	 */
	
	public boolean checkAnyMarkerAt(AntColour c){
		
		
		
		
		Marker[] _m = new Marker[6];
		for (int i = 0; i < _m.length; i++) {
			try {
				_m[i] = new Marker(i,c);
			} catch (InvalidMarkerIdException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int ptr = 0;
		
		while(ptr<markers.size()){
			if(markers.get(_m[ptr])) {
				return true;
			}
			ptr++;
		}
		
		return false;
		
	}
	
	
	public boolean containsRedAnt(){
		boolean containsRedAnt = false;
		if(containsAnt){
			if(ant.getColour() == AntColour.RED){
				containsRedAnt =  true;
			}
		}
		return containsRedAnt;
		
	}
	public boolean containsBlackAnt(){
		boolean containsBlackAnt = false;
		if(containsAnt){
			if(ant.getColour() == AntColour.BLACK){
				containsBlackAnt =  true;
			}
		}
		return containsBlackAnt;
		
	}
	
	public boolean senseCheck(Ant a, SenseCondition sc, Marker m){
		switch (sc){
		case FRIEND:
			if(containsAnt){
				if(ant.getColour() == a.getColour()){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		
		
		case FOE:
			if(containsAnt){
				if(ant.getColour() != a.getColour()){
					return true;
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		
		
		case FRIENDWITHFOOD:
			if(containsAnt){
				if(ant.getColour() == a.getColour()){
					if(ant.isHasFood()){
						return true;
					}
					else{
						return false;
					}
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		case FOEWITHFOOD:
			if(containsAnt){
				if(ant.getColour() != a.getColour()){
					if(ant.isHasFood()){
						return true;
					}
					else{
						return false;
					}
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
		case FOOD:
			return containsFood;
		case ROCK:
			return containsRock;
		case MARKER:
			return checkMarker(m);
			
			
		case FOEMARKER:
			return this.checkAnyMarkerAt(a.getColour().otherColour());
			
		case HOME:
			if(a.getColour() == AntColour.RED){
				return containsRedAntHill;
			}
			
			else if(a.getColour() == AntColour.BLACK){
				return containsBlackAntHill;
			}
			else{
				return false;
			}
			
		case FOEHOME:
			if(a.getColour() == AntColour.BLACK){
				return containsRedAntHill;
			}
			
			else if(a.getColour() == AntColour.RED){
				return containsBlackAntHill;
			}
			else{
				return false;
			}
			
		default:
			return false;	
		}
		
		}
		
	

	


public String getContent() {
		return content;
	}

public int[] getPos() {
	return pos;
}

public boolean isContainsFood() {
	return containsFood;
}

public int getNumberOfFoodParticles() {
	return numberOfFoodParticles;
}

public boolean containsRock() {
	return containsRock;
}

public boolean isClear() {
	return isClear;
}

public boolean isContainsAnt() {
	return containsAnt;
}

public Ant getAnt() {
	return ant;
}

public boolean containsRedAntHill() {
	return containsRedAntHill;
}

public boolean containsBlackAntHill() {
	return containsBlackAntHill;
}



public static void main (String[] args) throws InvalidMarkerIdException{
	Cell c = new Cell(0,0,"9");
	System.out.println(c.getContent());
	c.antMoveIn(new Ant(0, 0, AntColour.RED, 0, c,new AntBrain("C://brain2.txt", AntColour.RED)));
	System.out.println(c.getContent());
	c.antMoveOut();
	System.out.println(c.getContent());
}
}


