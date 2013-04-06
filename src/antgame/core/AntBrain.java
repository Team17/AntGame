package antgame.core;

/**
 * AntBrain class responsible controling values in the Ant and the map. 
 * @author Doniyor Ulmasov (13158)
 * @version 0.1
 */
public class AntBrain {
	private BrainState[] brain;
	
	public AntBrain(String antLoc, AntColour colour)
	{
		AntBrainInterpreterCoryn aBI = new AntBrainInterpreterCoryn();
		brain = aBI.antBrainGenerator(antLoc, colour);
		
	}
	
	public BrainState getState(int state){
		return brain[state];
	}
	
//	public int[] adjacent1Cell(int[] pos,int d){
//		switch(d) {
//			case 0: 
//				pos[0]++;
//				return pos;
//			case 1: 
//				if(pos[1]%2==0){
//					pos[1]++;
//				}
//				else{
//					pos[0]++;
//					pos[1]++;
//				}
//				break;
//			case 2: 
//				if(pos[1]%2==0){
//					pos[0]--;
//					pos[1]++;
//				}
//				else{
//					pos[1]++;
//				}
//				break;
//			case 3: 
//				pos[0]--;
//				break;
//			case 4: 
//				if(pos[1]%2==0){
//					pos[0]--;
//					pos[1]--;
//				}
//				else{
//					pos[1]--;
//				}
//				break;
//			case 5: 
//				if(pos[1]%2==0){
//					pos[1]--;
//				}
//				else{
//					pos[0]++;
//					pos[1]--;
//				}
//				break;
//			default:
//				break;
//		}
//		return pos;
//	}
	
//	public int turn(String leftOrRight, int dir){
//		if(leftOrRight.equals("left")){
//			return (dir+5)%6;
//		}
//		else{
//			return (dir+1)%6;	
//		}
//	}
	
//	public int[] sensed_cell(int[] pos, int dir, SenseDirection senseDir){
//		switch (senseDir){
//			case HERE:
//				return pos;
//			case AHEAD:
//				return adjacentCell(pos,dir);
//			case LEFTAHEAD:
//				return adjacentCell(pos,turn("left",dir));
//			case RIGHTAHEAD:
//				return adjacentCell(pos,turn("right",dir));
//			default:
//				return null;
//		}
//	}
	public static void main(String[] args){
		AntBrain ab1 = new AntBrain("C://cleverbrain1.brain",AntColour.RED);
		for(BrainState bs: ab1.brain){
			bs.print();
		}
	}
}
