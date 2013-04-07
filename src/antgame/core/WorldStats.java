package antgame.core;

public class WorldStats {
	//stores the number of food particles in the red ant hill
	private int foodUnitsRedHill=0;
	//stores the number of food particles in the black ant hill
	private int foodUnitsBlackHill=0;
	
	//redAlive is the number of redAntsAlive
	private int redAlive;
	//blackAlive is the number of blackAntsAlive
	private int blackAlive;
	
	//Starting amount of ants
	private int totalRed;
	private int totalBlack;
	
	public WorldStats(int reds, int blacks){
		this.totalBlack = blacks;
		this.blackAlive = blacks;
		this.totalRed = reds;
		this.redAlive = reds;
	}
	
	/**
	 * @return Amount of food collected by Red ants
	 */
	public int getFoodUnitsRedHill() {
		return foodUnitsRedHill;
	}
	/**
	 * Increases amount of food in the red hill by one
	 */
	public void incFoodUnitsRedHill() {
		this.foodUnitsRedHill++;
	}
	
	/**
	 * Decreases amount of food in the red hill by one
	 */
	public void decFoodUnitsRedHill() {
		this.foodUnitsRedHill--;
	}
	
	/**
	 * @return Amount of food collected by Black ants
	 */
	public int getFoodUnitsBlackHill() {
		return foodUnitsBlackHill;
	}
	/**
	 * Increases amount of food in the black hill by one
	 */
	public void incFoodUnitsBlackHill() {
		this.foodUnitsBlackHill++;
	}
	/**
	 * Decreases amount of food in the black hill by one
	 */
	public void decFoodUnitsBlackHill() {
		this.foodUnitsBlackHill--;
	}
	/**
	 * @return the redAlive
	 */
	public int getRedAlive() {
		return redAlive;
	}
	/**
	 * @param Decrease amount of Red ants by one
	 */
	public void decRedAlive() {
		this.redAlive--;
	}
	/**
	 * @return the blackAlive
	 */
	public int getBlackAlive() {
		return blackAlive;
	}
	/**
	 * @param Decrease amount of Red ants by one
	 */
	public void decBlackAlive() {
		this.blackAlive--;
	}
	/**
	 * @return the blackAntsDead
	 */
	public int getBlackAntsDead() {
		return totalBlack - blackAlive;
	}

	/**
	 * @return the redAntsdead
	 */
	public int getRedAntsdead() {
		return totalRed - redAlive;
	}

	
	
	
	
}
