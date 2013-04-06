package antgame.core;

/**
* A simple ant model. Which can set markers, pick food, move cells.
*@author Doniyor Ulmasov (13158)
*@version 0.1
*/
public class Ant {
	//Unique ID of an ant.
	private int uID;
	
	//Direction facing by the ant (0-5).
	private int dir;
	
	//Colour of the ant. Choose any value, This is just to distinguish. True is red, false is black.
	private AntColour colour;
	
	//Current state of the ant in reference to the AntBrain.
	private BrainState brainState;
	
	// boolean of wether the ant is alive or not	
	private boolean alive;
	
	//How many turns an ant has to rest before it can carry out another step.
	private int numRestingLeft;
	
	//If ant is carrying food.
	private boolean hasFood;
	
	//Current cell of the ant
	private Cell currentPos;
	
	/**
	 * constructor of Ant just sets all the fields based on the parameters.
	 * @param uID
	 * @param dir
	 * @param colour
	 * @param state
	 * @param initialCell
	 * @param brain
	 */
	public Ant(int uID, int dir, AntColour colour, int state, Cell initialCell,AntBrain brain){
		this.uID = uID;
		this.dir = dir;
		this.colour = colour;
		this.brainState = brain.getState(state);
		this.alive = true;
		this.numRestingLeft = 0;
		this.hasFood = false;
		this.currentPos = initialCell;
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
	public AntColour getColour() {
		return colour;
	}

	/**
	*@return BrainState current BrainState of the ant.
	*/
	public BrainState getState() {
		return brainState;
	}

	/**
	*Set the current brainstate(BrainState) of the ant.
	*/
	public void setState(BrainState state) {
		this.brainState = state;
	}

	/**
	 * Sets the ant to be resting by setting the number of rounds left before the ant can take a turn to 14
	 */
	public void setResting(){
		numRestingLeft = 14;
	}
	
	/**
	 * 
	 * @return int the number of rounds left before the ant can have a round.
	 */
	public int getResting(){
		return numRestingLeft;
	}

	/**
	*Decrease by 1 resting period
	*/
	public void decResting() {
		if(this.numRestingLeft>0){
			this.numRestingLeft --;
		}
	}
	
	/**
	*@return true if ant is resting, false otherwise
	*/	
	public boolean isResting()	{
		if(numRestingLeft!=0){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	*@return true if ant has food, else false
	*/
	public boolean isHasFood() {
		return hasFood;
	}

	/**
	*Sets the ant to be carrying food.
	*/
	public void pickupFood() {
		this.hasFood = true;
	}
	
	/**
	 * Sets the ant to not be carrying food
	 */
	public void dropFood() {
			this.hasFood = false;
	}
	
	/**
	 * turns the ants direction based on the current direction and the parameter lr if turing left or right.	
	 * @param lr which direction the ant is going to turn.
	 */
	public void turn(LeftRight lr){
		if(lr == LeftRight.LEFT){
			dir = (dir+5)%6;
		}
		else if(lr == LeftRight.RIGHT){
			dir = (dir+1)%6;	
		}
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
	 * 
	 * @return boolean whether or not the ant is alive or not
	 */
	public boolean isAlive() {
		return alive;
	}
	/**
	 * kills the ant i.e. sets alive to false.
	 */
	public void die() {
		this.alive = false;
	}
	
}
