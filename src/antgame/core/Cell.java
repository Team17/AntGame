package antgame.core;


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
	@SuppressWarnings("unused")
	private boolean containsFood;
	//numberOfFoodParticles is an int value of the amount of food contained in the cell
	private int numberOfFoodParticles;
	//containsRock is a boolean of whether or not the cell contains Rock
	@SuppressWarnings("unused")
	private boolean containsRock;
	//isClear is a clear boolean of whether or not the cell is clear
	@SuppressWarnings("unused")
	private boolean isClear;
	//containsAnt is a boolean of whether or not the cell contains an ant
	@SuppressWarnings("unused")
	private boolean containsAnt;
	//antid points to the ant that is currently in the cell is that is the case.
	@SuppressWarnings("unused")
	private Ant antId;
	//containsRedAntHill is a boolean of whether or not the cell contains a Red AntHill
	@SuppressWarnings("unused")
	private boolean containsRedAntHill;
	//containsBlackAntHill is a boolean of whether or not the cell contains a Black AntHill
	@SuppressWarnings("unused")
	private boolean containsBlackAntHill;
	//this field holds the markers as an array of boolean 0-5 is red markers 6-11 is Black markers
	private Boolean[] redMarkers;
	//this field holds the markers as an array of boolean
	private Boolean[] blackMarkers;
	
	
	
	public Cell(int x, int y, String content){
		pos[0] = x;
		pos[1] = y;
		redMarkers = new Boolean[6];
		blackMarkers = new Boolean[6];
		for(int i=0; i<6; i++){
			redMarkers[i]=false;
			blackMarkers[i]=false;
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
		//** do we need to check whether or no it is clear?
		containsAnt = true;
		antId = id;
		isClear = false;
		
		
	}
	public void antMoveOut(){
		//** do we need to check whether or no it is clear?
		containsAnt = false;
		antId = null;
		isClear = true;
		
		
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
	/*
	 * color is true if red and false is black
	 */
	public void setMarker(boolean color, int marker){
		if(color == true){//red
			redMarkers[marker-1] = true;
		}
		if(color == false){//black
			blackMarkers[marker-1] = true;
		}
	}

	public void clearMarker(boolean color, int marker){
		if(color == true){//red
			redMarkers[marker-1] = false;
		}
		if(color == false){//black
			blackMarkers[marker-1] = false;
		}
	}


	public boolean checkMarker(boolean color, int marker){
		if(color == true){//red
			return redMarkers[marker-1];
		}
		else{//black
			return blackMarkers[marker-1];
		}

	}
	/*
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 * Note the final function, check_any_marker_at. Ants of a given color can individually sense,
	 *  set, and clear all 6 of their own markers, but are only able to detect the presence of some marker
	 *   belonging to the other species.
	 *   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	public boolean checkAnyMarker(boolean color){
		if(color == true){//red
			boolean redAnyTrue = false;
			for(int i = 0; i <6; i++){
				if(redMarkers[i]){
					redAnyTrue = true;
				}
			}
			return redAnyTrue;
		}

		else{//black
			boolean blackAnyTrue = false;
			for(int i = 0; i <6; i++){
				if(blackMarkers[i]){
					blackAnyTrue = true;
				}
			}
			return blackAnyTrue;
		}
	}

	


public String getContent() {
		return content;
	}

public static void main (String[] args){
	Cell c = new Cell(0,0,".");
	c.setMarker(false, 6);
	System.out.println(c.checkMarker(false, 6));
	System.out.println(c.checkAnyMarker(false));
	c.clearMarker(true, 6);
	System.out.println(c.checkMarker(false, 6));
}


}
