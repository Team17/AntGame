package antgame.core;

import java.util.ArrayList;
import java.util.HashMap;

import antgame.AntGame;
import antgame.InvalidMarkerIdException;


/**
 * The Cell class is a representation of each cell in the map.
 * @author Coryn
 *
 */
public class Cell {
	//pos stores the location of the cell in the map, it is represented by an array of size two so pos[0] = pos[x] and pos[1] = pos[y]
	private int[] pos  = new int[2];
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
	//this field holds the markers as an HashMap
	private HashMap<Marker,Boolean>  markers;


	/**
	 * The Cell Constructor takes the x and y coordinate of the cell in respect to the map 
	 * (the map passes the location to the constructor on creation of each cell)
	 * The constructor also takes the content of the cell as a string, based on the string 
	 * it updates all of the booleans.
	 * 
	 * The constructor stores the coordinates in the field pos.
	 * It creates the HashMap of markers.
	 * @param x the x coordinate in respect to the map
	 * @param y the y coordinate in respect to the map
	 * @param content the content of each cell.
	 */
	public Cell(int x, int y, String content){
		pos[0] = x;
		pos[1] = y;


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

		_construct();

	}
	
	/**
	 * Constructor
	 * @param	x	The x-position of the cell
	 * @param	y	The y-position of the cell
	 */
	public Cell(int x, int y) {
		pos[0] = x;
		pos[1] = y;
		
		_construct();
		
	}
	
	/**
	 * Private method invoked by all Cell constructors
	 */
	private void _construct() {
		markers = new HashMap<Marker, Boolean>();
		for(AntColour c: AntColour.values()){
			for(int i = 0 ; i<6/* i<Integer.parseInt(AntGame.CONFIG.getProperty("numAntMarkers"))*/; i++){
				try {
					markers.put(new Marker(i,c), false);
				} catch (InvalidMarkerIdException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Set this cell as rocky
	 */
	public void setRock() {
		containsRock = true;
		isClear = false;
	}
	
	/**
	 * Set this cell as non-rocky
	 */
	public void clearRock() {
		containsRock = false;
		_clearCheck();
	}
	
	/**
	 * Add food particles to this cell
	 * @param	food	The total number of food particles this Cell is to contain
	 */
	public void setFood(int food) {
		numberOfFoodParticles = food;
		containsFood = true;
	}
	
	/**
	 * Clear all food particles to this cell
	 */
	public void clearFood() {
		numberOfFoodParticles = 0;
		containsFood = false;
	}
	
	/**
	 * Set this cell to be part of an anthill
	 * @param	colour	The colour of the colony to which this anthill is associated
	 */
	public void setAnthill(AntColour colour) {
		switch(colour) {
		case RED:
			containsRedAntHill = true;
			containsBlackAntHill = false;
			break;
		case BLACK:
			containsRedAntHill = false;
			containsBlackAntHill = true;
			break;
		}
	}
	
	/**
	 * Clears any anthill associations this Cell has
	 */
	public void clearAnthill() {
		containsRedAntHill = false;
		containsBlackAntHill = false;
	}
	
	/**
	 * Sets the isClear variable depending on cell conditions
	 */
	private void _clearCheck() {
		isClear = !( containsRock || containsAnt );
	}
	
	/**
	 * IsInteger is a method used to check if a string is an integer.
	 * @param input - the string to check
	 * @return true if is integer false if not
	 */
	public boolean isInteger( String input ) {
		try {
			Integer.parseInt( input );
			return true;
		}
		catch( Exception e ) {
			return false;
		}
	}


	/**
	 * AntMoveIn simulates an Ant moving in, it sets the containsAnt field to true, sets the ant field to the ant that is passed in the parameter.
	 * @param ant this is the ant that is moving into the cell
	 */
	public void antMoveIn(Ant ant){
		this.containsAnt = true;
		this.ant = ant;
		this.isClear = false;
	}

	/**
	 * AntMoveOut simulates the Ant moving out, sets the field containsAnt to false, ant field to null and is clear to true.
	 */
	public void antMoveOut(){
		containsAnt = false;
		ant = null;
		isClear = true;
	}

	/**
	 * addFood() increments the number of food particles by 1, and contains food to true.
	 */
	public void addFood(){
		numberOfFoodParticles++;
		containsFood = true;
	}

	/**
	 * addNumFood is similar to addFood but this adds a number of particles of food rather than just incrementing it.
	 * @param n the number of food particles that is being added.
	 */
	public void addNumFood(int n){
		numberOfFoodParticles = numberOfFoodParticles + n;
		containsFood = true;
	}

	/**
	 * removeFood takes one particle of food out of the cell, if there is any food in the cell, else it prints an err (this should never be reached),
	 * if all the food has been removed then it sets the containsFood to false.
	 */
	public void removeFood(){
		if(numberOfFoodParticles>0){
			numberOfFoodParticles--;
		}
		else{
			// should never be reached as the step function in world checks if there is food.
			System.err.print("No Food in Cell");
		}
		if(numberOfFoodParticles==0){
			containsFood = false;
		}
	}

	/**
	 * setMarker places a marker in the cell based on the marker passed to it in the parameter.
	 * @param marker the marker to place
	 */
	public void setMarker(Marker marker){
		markers.put(marker, true);
	}

	/**
	 * clearMarker unmarks a marker in the cell based on the marker passed to it in the parameter.
	 * @param marker the marker to unmark
	 */
	public void clearMarker( Marker marker){
		markers.put(marker, false);
	}

	/**
	 * check marker checks if a specific marker is present in the cell bases on the marker passed in the parameter, if it is present it returns true else false.
	 * @param marker the marker to check
	 * @return true if the marker is present, if not it is false.
	 */
	public boolean checkMarker(Marker marker){
		return markers.get(marker);
	}

	/**
	 * checkAnyMarkerAt checks whether there is any marker of the colour passed in the parameter.
	 * The method creates a new set of markers of colour c and then attempts get each marker, if any marker successfully returns then the method returns true and terminates
	 * otherwise it returns false.	
	 * @param AntColour
	 * @return boolean
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

	/**
	 * containsRedAnt returns true if there is ant present that is red.
	 * @return true if there is ant present that is red, false if no ant is present or a black ant
	 */
	public boolean containsRedAnt(){
		boolean containsRedAnt = false;
		if(containsAnt){
			if(ant.getColour() == AntColour.RED){
				containsRedAnt =  true;
			}
		}
		return containsRedAnt;
	}

	/**
	 * containsBlackAnt returns true if there is ant present that is black.
	 * @return true if there is ant present that is black, false if no ant is present or a red ant
	 */
	public boolean containsBlackAnt(){
		boolean containsBlackAnt = false;
		if(containsAnt){
			if(ant.getColour() == AntColour.BLACK){
				containsBlackAnt =  true;
			}
		}
		return containsBlackAnt;
	}

	/**
	 * senseCheck is used when by the step function in the world class, for when an ant is sensing. it takes three parameters.
	 * @param a is the Ant that is sensing
	 * @param sc is the SenseCondition i.e. FRIEND, FOOD etc
	 * @param m is marker that is being checked, it is only used when the SenseCondition is MARKER
	 * @return
	 */
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
				if(ant.getColour() == a.getColour() && ant.isHasFood()){
					return true;
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
				if(ant.getColour() != a.getColour() && ant.isHasFood()){
					return true;
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

	/**
	 * getContent returns a string of the current content of the map bases on the boolean fields
	 * @return String of the current content.
	 */
	public String getContent(){
		String content = "";
		if(containsRock){
			content  = content + "#";
		}
		else if(containsRedAntHill){
			content  = content + "+";
		}
		else if(containsBlackAntHill){
			content  = content + "-";
		}

		if(isClear){
			content = content + ".";
		}

		if(containsAnt){
			if(ant.getColour() == AntColour.BLACK){
				content = content + "(b)";
			}
			else if (ant.getColour() == AntColour.RED){
				content = content + "(r)";
			}
		}

		if(containsFood){
			content  = content + this.numberOfFoodParticles;
		}

		return content;
	}

	/**
	 * getPos
	 * @return int[] the current position as an int array
	 */
	public int[] getPos() {
		return pos;
	}
	
	/**
	 * Return the x-position of this Cell
	 * @return	The x-position of this Cell
	 */
	public int getX() {
		return pos[0];
	}
	
	/**
	 * Return the y-position of this Cell
	 * @return	The y-position of this Cell
	 */
	public int getY() {
		return pos[1];
	}

	/**
	 * isContainsFood
	 * @return boolean whether or not the cell contains food
	 */
	public boolean isContainsFood() {
		return containsFood;
	}

	/**
	 * getNumberOfFoodParticles
	 * @return int the number of food particles in the cell
	 */
	public int getNumberOfFoodParticles() {
		return numberOfFoodParticles;
	}

	/**
	 * containsRock
	 * @return boolean whether or not the cell contains a rock
	 */
	public boolean containsRock() {
		return containsRock;
	}

	/**
	 * isClear
	 * @return  boolean whether or not the cell is clear or not - Clear = anything but a ant or a rock
	 */
	public boolean isClear() {
		return isClear;
	}

	/**
	 * containsAnt
	 * @return boolean whether or not the cell contains an Ant
	 */
	public boolean containsAnt() {
		return containsAnt;
	}

	/**
	 * getAnt returns the ant currently in the cell
	 * @return Ant the current ant in the cell
	 */
	public Ant getAnt() {
		return ant;
	}

	/**
	 * containsRedAntHill 
	 * @return boolean whether or not the cell contains a red ant hill
	 */
	public boolean containsRedAntHill() {
		return containsRedAntHill;
	}

	/**
	 * containsBlackAntHill
	 * @return boolean whether or not the cell contains a black ant hill
	 */
	public boolean containsBlackAntHill() {
		return containsBlackAntHill;
	}


	public static void main(String [] args){

	}

}


