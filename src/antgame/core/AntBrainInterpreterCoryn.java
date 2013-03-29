package antgame.core;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AntBrainInterpreterCoryn {
	private String regSense = "sense\\s(here|ahead|leftahead|rightahead)\\s[0-9]{1,4}\\s[0-9]{1,4}\\s(friend|foe|friendwithfood|foewithfood|food|rock|marker\\s\\d|foemarker|home|foehome)(|\\s;.*)";
	private String regMark = "mark\\s(0|1|2|3|4|5)\\s[0-9]{1,4}(|\\s;.*)";
	private String regUnMark = "unmark\\s(0|1|2|3|4|5)\\s[0-9]{1,4}(|\\s;.*)";
	private String regPickUp = "pickup\\s[0-9]{1,4}\\s[0-9]{1,4}(|\\s;.*)";
	private	String regDrop = "drop\\s[0-9]{1,4}(|\\s;.*)";
	private	String regTurn = "turn\\s(left|right)\\s[0-9]{1,4}(|\\s;.*)";
	private	String regMove = "move\\s[0-9]{1,4}\\s[0-9]{1,4}(|\\s;.*)";
	private	String regFlip = "flip\\s[0-9]{1,4}\\s[0-9]{1,4}\\s[0-9]{1,4}(|\\s;.*)";
	private ArrayList<String> regExpressions = new ArrayList<String>();
	
	private Pattern pattSense = Pattern.compile("sense\\s(here|ahead|leftahead|rightahead)\\s([0-9]{1,4})\\s([0-9]{1,4})\\s(friend|foe|friendwithfood|foewithfood|food|rock|marker\\s\\d|foemarker|home|foehome)(|\\s;.*)");
	private Pattern pattMark = Pattern.compile("mark\\s(0|1|2|3|4|5)\\s([0-9]{1,4})(|\\s;.*)");
	private Pattern pattUnMark = Pattern.compile("unmark\\s(0|1|2|3|4|5)\\s([0-9]{1,4})(|\\s;.*)");
	private Pattern pattPickUp = Pattern.compile("pickup\\s([0-9]{1,4})\\s([0-9]{1,4})(|\\s;.*)");
	private	Pattern pattDrop = Pattern.compile("drop\\s([0-9]{1,4})(|\\s;.*)");
	private	Pattern pattTurn = Pattern.compile("turn\\s(left|right)\\s([0-9]{1,4})(|\\s;.*)");
	private	Pattern pattMove = Pattern.compile("move\\s([0-9]{1,4})\\s([0-9]{1,4})(|\\s;.*)");
	private	Pattern pattFlip = Pattern.compile("flip\\s([0-9]{1,4})\\s([0-9]{1,4})\\s([0-9]{1,4})(|\\s;.*)");

	int stateInt;
	
	
	public AntBrainInterpreterCoryn(){
		
		regExpressions.add(regSense);
		regExpressions.add(regMark);
		regExpressions.add(regUnMark);
		regExpressions.add(regPickUp);
		regExpressions.add(regDrop);
		regExpressions.add(regTurn);
		regExpressions.add(regMove);
		regExpressions.add(regFlip);
		

	}
	
	public BrainState[] antBrainGenerator(String antBrainTextFile){
		if(antBrainChecker(antBrainTextFile)){
			BrainState[] states = new BrainState[stateInt];
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(antBrainTextFile));
			int ptr = 0;
			String curLine = reader.readLine();
			while(curLine != null){
				curLine.toLowerCase();
				String instruction; 
				
				
				
				if(curLine.matches(regSense)){
					BrainState bs =  new BrainState();
					bs.setInstruction(Instruction.SENSE);
					bs.setStateId(ptr);
			
					Matcher mattSense = pattSense.matcher(curLine);
					if (mattSense.find())
					{
						bs.setSenseDirection(mattSense.group(1));
						bs.setNextIdState(Integer.parseInt(mattSense.group(2)));
					    bs.setAltNextIdState(Integer.parseInt(mattSense.group(3)));
					    if(mattSense.group(4).startsWith("marker")){
					    	bs.setSenseCondition("marker");
					    	bs.setMarker(Integer.parseInt(mattSense.group(4).substring(7, 8)));
					    }
					    else{
					    	bs.setSenseCondition(mattSense.group(4));
					    }
					}
					states[ptr] = bs;
				}
				else if(curLine.matches(regMark)){
					BrainState bs =  new BrainState();
					bs.setInstruction(Instruction.MARK);
					bs.setStateId(ptr);
			
					Matcher mattMark = pattMark.matcher(curLine);
					if (mattMark.find())
					{
						bs.setMarker(Integer.parseInt(mattMark.group(1)));
						bs.setNextIdState(Integer.parseInt(mattMark.group(2)));
					}
					states[ptr] = bs;
				}
				else if(curLine.matches(regUnMark)){
					BrainState bs =  new BrainState();
					bs.setInstruction(Instruction.UNMARK);
					bs.setStateId(ptr);
			
					Matcher mattUnMark = pattUnMark.matcher(curLine);
					if (mattUnMark.find())
					{
						bs.setMarker(Integer.parseInt(mattUnMark.group(1)));
						bs.setNextIdState(Integer.parseInt(mattUnMark.group(2)));
					}
					states[ptr] = bs;
				}
				else if(curLine.matches(regPickUp)){
					BrainState bs =  new BrainState();
					bs.setInstruction(Instruction.PICKUP);
					bs.setStateId(ptr);
			
					Matcher mattPickUp = pattPickUp.matcher(curLine);
					if (mattPickUp.find())
					{
						bs.setNextIdState(Integer.parseInt(mattPickUp.group(1)));
						bs.setAltNextIdState(Integer.parseInt(mattPickUp.group(2)));
					}
					states[ptr] = bs;
					
				}
				else if(curLine.matches(regDrop)){
					BrainState bs =  new BrainState();
					bs.setInstruction(Instruction.DROP);
					bs.setStateId(ptr);
			
					Matcher mattDrop = pattDrop.matcher(curLine);
					if (mattDrop.find())
					{
						bs.setNextIdState(Integer.parseInt(mattDrop.group(1)));
					}
					states[ptr] = bs;
					
				}
				else if(curLine.matches(regTurn)){
					BrainState bs =  new BrainState();
					bs.setInstruction(Instruction.TURN);
					bs.setStateId(ptr);
			
					Matcher mattTurn = pattTurn.matcher(curLine);
					if (mattTurn.find())
					{
						
						bs.setLeftRight(mattTurn.group(1));
						bs.setNextIdState(Integer.parseInt(mattTurn.group(2)));
					}
					states[ptr] = bs;
					
				}
				else if(curLine.matches(regMove)){
					BrainState bs =  new BrainState();
					bs.setInstruction(Instruction.MOVE);
					bs.setStateId(ptr);
			
					Matcher mattMove = pattMove.matcher(curLine);
					if (mattMove.find())
					{
						
						bs.setNextIdState(Integer.parseInt(mattMove.group(1)));
						bs.setAltNextIdState(Integer.parseInt(mattMove.group(2)));
					}
					states[ptr] = bs;
					
				}
				else if(curLine.matches(regFlip)){
					BrainState bs =  new BrainState();
					bs.setInstruction(Instruction.FLIP);
					bs.setStateId(ptr);
			
					Matcher mattFlip = pattFlip.matcher(curLine);
					if (mattFlip.find())
					{
						bs.setRandomInt(Integer.parseInt(mattFlip.group(1)));
						bs.setNextIdState(Integer.parseInt(mattFlip.group(2)));
						bs.setAltNextIdState(Integer.parseInt(mattFlip.group(3)));
					}
					states[ptr] = bs;
					
				}
				
				
				ptr++;
				curLine = reader.readLine();
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return states;	
		
		}
		else{
			
			System.out.print("syntac Incorrect");
			return null;
		}
	}
	public boolean antBrainChecker(String antBrainTextFile){
		boolean legit = false;
		try {
			legit = true;
			stateInt = 0;
			BufferedReader reader = new BufferedReader(new FileReader(antBrainTextFile));
		
			String curLine = reader.readLine();
		while(curLine != null && legit)
		{
			stateInt ++;
			if(regChecker(curLine) == false){
				legit = false;
				System.out.println(curLine);
				
				
			}
			
			curLine = reader.readLine();
			
		}
		
		
		
	
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
		return legit;
	}
	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean regChecker(String s){
		for(String reg: regExpressions){
			
			String sLower = s.toLowerCase();
			
			if(sLower.matches(reg)){
				return true;
			}	
		}
		return false;
	}
	public static void main (String[] args){
		AntBrainInterpreterCoryn aBI = new AntBrainInterpreterCoryn();
		BrainState[] st = aBI.antBrainGenerator("C://brain.txt");
		System.out.println(aBI.stateInt);
		for(BrainState s:st){
			s.print();
		}
	}
}
