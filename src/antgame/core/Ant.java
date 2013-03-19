package antgame.core;

/**
* A simple ant model. Which can set markers, pick food, move cells.
*@author Doniyor Ulmasov (13158)
*@version 0.2
*/
public class Ant {
	//Unique ID of an ant.
	private int uID;
	
	//Direction facing by the ant (0-5).
	private int dir;
	
	//Colour of the ant. Choose any value, This is just to distinguish. True is red, false is black.
	private boolean colour;
	
	//Current state of the ant in reference to the AntBrain.
	private int state;
	
	//How many turns an ant has to rest before moving.
	private int resting;
	
	//If ant has picked food, yet.
	private boolean hasFood;
	
	//Current cell of the ant
	private Cell currentPos;
	
	//Ant's brain
	private AntBrain myBrain;
	
	//Creates an ant with given values
	public Ant(int uID, int dir, boolean colour, int state, Cell initialPos, AntBrain brain){
		this.uID = uID;
		this.dir = dir;
		this.colour = colour;
		this.state = state;
		this.resting = 0;
		this.hasFood = false;
		this.currentPos = initialPos;
		this.myBrain = brain;
	}
	
	/**
	*@return int ID of the ant.
	*/
	public int getuID() {
		return uID;
	}
	
	/**
	*@return int Direction of the ant.
	*/
	public int getDir() {
		return dir;
	}

	/**
	*Set the direction of the ant.
	*@param dir a value from 0 to 5.
	*/
	public void setDir(int dir) {
		this.dir = dir;
	}

	/**
	*@return int colour of the ant.
	*/
	public boolean getColour() {
		return colour;
	}

	/**
	*@return Int state of the ant.
	*/
	public int getState() {
		return state;
	}

	/**
	*Set the state of the ant in int.
	*/
	public void setState(int state) {
		this.state = state;
	}

	/**
	*@return int of rounds remaining resting
	*/
	public int getResting() {
		return resting;
	}

	/**
	*Decrease by 1 resting period
	*/
	public void decResting() {
		if(this.resting%14<=0){
			this.resting=14;
		}
		else{
			this.resting--;
		}
	}
	
	/**
	*@return true if ant is resting, false otherwise
	*/	
	public boolean isResting()	{
		if(resting%14==0){
			return false;
		}
		else {
			return true;
		}
	}

	/**
	*@return true if ant has food, else false
	*/
	public boolean isHasFood() {
		return hasFood;
	}

	/**
	*Toggles if ant has food.
	*/
	public void setHasFood() {
		this.hasFood = !hasFood;
	}

	/**
	* @return current cell ant at.
	*/
	public Cell getCurrentPos() {
		return currentPos;
	}

	/**
	 * @param a new cell of the ant
	 */
	public void setCurrentPos(Cell currentPos) {
		this.currentPos = currentPos;
	}
	
	/**
	 * @return ant's brain 
	 */
	public AntBrain getBrain(){
		return this.brain;
	}
}
