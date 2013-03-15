package antgame.core;


/**
 * @author Coryn
 *
 */
public class Cell {
	//pos stores the location of the cell in the map, it is represented by an array of size two so pos[0] = pos[x] and pos[1] = pos[y]
	private int[] pos  = new int[2];
	//content keeps record of what is in the cell (kept for testing
	
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
	private Ant antId;
	//containsRedAntHill is a boolean of whether or not the cell contains a Red AntHill
	private boolean containsRedAntHill;
	//containsBlackAntHill is a boolean of whether or not the cell contains a Black AntHill
	private boolean containsBlackAntHill;
	
	
	private int mark;
	
	
	
	public Cell(int x, int y, String content){
		pos[0] = x;
		pos[1] = y;
	    this.content = content;
	    if (content.equals("#")){
	    	containsRock = true;
	    }
	    else if (content.equals(".")){
	    	isClear = true;
		}
		else if(content.equals("+")){
			containsRedAntHill = true;		
	    	
	    }
		else if(content.equals("-")){
			containsBlackAntHill = true;
			
		}
		else if(isInteger(content)){
			containsFood = true;
			numberOfFoodParticles = Integer.parseInt(content);
			
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
		//** do we need to check whether or no it is clear?
		containsAnt = true;
		antId = id;
		isClear = false;
		
		
	}
	/**
	 * addFood() increments the number of food particles by 1
	 */
	public void addFood(){
		numberOfFoodParticles++;
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
	
	
	// Getters and setters
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean ContainsFood() {
		return containsFood;
	}
	
	public void setContainsFood(boolean containsFood) {
		this.containsFood = containsFood;
	}
	
	public int getNumberOfFoodParticles() {
		return numberOfFoodParticles;
	}
	
	public void setNumberOfFoodParticles(int numberOfFoodParticles) {
		this.numberOfFoodParticles = numberOfFoodParticles;
	}
	
	public boolean ContainsRock() {
		return containsRock;
	}
	
	public void setContainsRock(boolean containsRock) {
		this.containsRock = containsRock;
	}
	
	public boolean isClear() {
		return isClear;
	}
	
	public void setClear(boolean isClear) {
		this.isClear = isClear;
	}
	
	public boolean ContainsAnt() {
		return containsAnt;
	}
	
	public void setContainsAnt(boolean containsAnt) {
		this.containsAnt = containsAnt;
	}
	
	public boolean ContainsRedAntHill() {
		return containsRedAntHill;
	}
	
	public void setContainsRedAntHill(boolean containsRedAntHill) {
		this.containsRedAntHill = containsRedAntHill;
	}
	
	public boolean ContainsBlackAntHill() {
		return containsBlackAntHill;
	}
	
	public void setContainsBlackAntHill(boolean containsBlackAntHill) {
		this.containsBlackAntHill = containsBlackAntHill;
	}
	
	public int[] getCellPos(){
		return pos;
	}

	public Ant getAntid() {
		return antId;
	}

	public void setAntid(Ant antid) {
		this.antId = antid;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
}


