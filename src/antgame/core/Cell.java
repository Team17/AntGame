package antgame.core;


/**
 * @author Coryn
 *
 */
public class Cell {
	private int[] pos;
	private String content;
	private boolean containsFood;
	private int numberOfFoodParticles;
	private boolean containsRock;
	private boolean isClear;
	private boolean containsAnt;
	private boolean containsRedAntHill;
	private boolean containsBlackAntHill;
	
	private Ant antid;
	private int mark;
	
	
	
	public Cell(int x, int y, String content){
		pos = new int[2];
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
		return antid;
	}

	public void setAntid(Ant antid) {
		this.antid = antid;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}
}


