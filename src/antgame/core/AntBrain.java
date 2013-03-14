package antgame.core;

/**
 * AntBrain class responsible controling values in the Ant and the map. 
 * @author Doniyor Ulmasov (13158)
 * @version 0.1
 */
public class AntBrain {
	private String[] brain;
	
	public int[] adjacentCell(int[] pos,int d){
		switch(d) {
			case 0: 
				pos[0]++;
				return pos;
			case 1: 
				if(pos[1]%2==0){
					pos[1]++;
				}
				else{
					pos[0]++;
					pos[1]++;
				}
				break;
			case 2: 
				if(pos[1]%2==0){
					pos[0]--;
					pos[1]++;
				}
				else{
					pos[1]++;
				}
				break;
			case 3: 
				pos[0]--;
				break;
			case 4: 
				if(pos[1]%2==0){
					pos[0]--;
					pos[1]--;
				}
				else{
					pos[1]--;
				}
				break;
			case 5: 
				if(pos[1]%2==0){
					pos[1]--;
				}
				else{
					pos[0]++;
					pos[1]--;
				}
				break;
			default:
				break;
		}
		return pos;
	}
	
	public int turn(String leftOrRight, int dir){
		if(leftOrRight.equals("left")){
			return (dir+5)%6;
		}
		else{
			return (dir+1)%6;			
		}
	}
	
	public int[] sensed_cell(int[] pos, int dir, String sense_dir){
		switch (sense_dir){
			case "here":
				return pos;
			case "ahead":
				return adjacentCell(pos,dir);
			case "leftahead":
				return adjacentCell(pos,turn("left",dir));
			case "rightahead":
				return adjacentCell(pos,turn("right",dir));
			default:
				return null;
		}
	}
}
