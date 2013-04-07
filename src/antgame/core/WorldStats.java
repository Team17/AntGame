package antgame.core;

public class WorldStats {
	private int foodUnitsRed=0;
	//foodInBAH stores the number of food particles in the black ant hill
	private int foodUnitsBlack=0;
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
	public int getFoodUnitsRed() {
		return foodUnitsRed;
	}
	/**
	 * @param Incriment amount of food by Reds by one
	 */
	public void incFoodUnitsRed() {
		this.foodUnitsRed++;
	}
	/**
	 * @return Amount of food collected by Black ants
	 */
	public int getFoodUnitsBlack() {
		return foodUnitsBlack;
	}
	/**
	 * @param Incriment amount of food by Black by one
	 */
	public void incFoodUnitsBlack() {
		this.foodUnitsBlack++;
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
	public void decBlackAlive(int blackAlive) {
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
