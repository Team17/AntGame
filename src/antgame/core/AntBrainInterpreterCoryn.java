package antgame.core;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class AntBrainInterpreterCoryn {
	//regexStrings are used for checking the ant brain
	private final static String regSense = "sense\\s(here|ahead|leftahead|rightahead)\\s[0-9]{1,4}\\s[0-9]{1,4}\\s(friend|foe|friendwithfood|foewithfood|food|rock|marker\\s[0-5]|foemarker|home|foehome)(|\\s*\\t*;.*)";
	private final static String regMark = "mark\\s(0|1|2|3|4|5)\\s[0-9]{1,4}(|\\s*\\t*;.*)";
	private final static String regUnMark = "unmark\\s(0|1|2|3|4|5)\\s[0-9]{1,4}(|\\s*\\t*;.*)";
	private final static String regPickUp = "pickup\\s[0-9]{1,4}\\s[0-9]{1,4}(|\\s*\\t*;.*)";
	private final static String regDrop = "drop\\s[0-9]{1,4}(|\\s*\\t*;.*)";
	private final static String regTurn = "turn\\s(left|right)\\s[0-9]{1,4}(|\\s*\\t*;.*)";
	private final static String regMove = "move\\s[0-9]{1,4}\\s[0-9]{1,4}(|\\s*\\t*;.*)";
	private final static String regFlip = "flip\\s[0-9]{1,4}\\s[0-9]{1,4}\\s[0-9]{1,4}(|\\s*\\t*;.*)";
	//the array stores all the string regexpressions
	private static ArrayList<String> regExpressions = new ArrayList<String>();

	//patterns are used for extracting the relevant information
	private static Pattern pattSense = Pattern.compile("sense\\s(here|ahead|leftahead|rightahead)\\s([0-9]{1,4})\\s([0-9]{1,4})\\s(friend|foe|friendwithfood|foewithfood|food|rock|marker\\s\\d|foemarker|home|foehome)(|\\s*\\t*;.*)");
	private static Pattern pattMark = Pattern.compile("mark\\s(0|1|2|3|4|5)\\s([0-9]{1,4})(|\\s*\\t*;.*)");
	private static Pattern pattUnMark = Pattern.compile("unmark\\s(0|1|2|3|4|5)\\s([0-9]{1,4})(|\\s*\\t*;.*)");
	private static Pattern pattPickUp = Pattern.compile("pickup\\s([0-9]{1,4})\\s([0-9]{1,4})(|\\s*\\t*;.*)");
	private static	Pattern pattDrop = Pattern.compile("drop\\s([0-9]{1,4})(|\\s;.*)");
	private static	Pattern pattTurn = Pattern.compile("turn\\s(left|right)\\s([0-9]{1,4})(|\\s*\\t*;.*)");
	private static	Pattern pattMove = Pattern.compile("move\\s([0-9]{1,4})\\s([0-9]{1,4})(|\\s*\\t*;.*)");
	private static	Pattern pattFlip = Pattern.compile("flip\\s([0-9]{1,4})\\s([0-9]{1,4})\\s([0-9]{1,4})(|\\s*\\t*;.*)");

	
	//stores the number of states.
	static int stateInt;

	/**
	 * all the constructor does is add the regular expressions to the array.
	 */
	public AntBrainInterpreterCoryn(){



	}
	
	/**
	 * antBrainGenerator is used to convert a string ant brain file into an array of brain states. it starts by calling the antBrainChecker to check that the ant brain is
	 * legitimate, it then creates an array of brainstates of size stateInt which had be set by the checker. it then reads the string ant brain line by line converts it to 
	 * lower case. then based on the instruction it sets all of the brain states fields.
	 * @param antBrainTextFile the location of the ant brain text file
	 * @param colour the colour of the ant brain
	 * @return an array of brain states.
	 */

	public BrainState[] antBrainGenerator(String antBrainTextFile, AntColour colour) {
		if(antBrainChecker(antBrainTextFile)){
			BrainState[] states = new BrainState[stateInt];
			try {
				BufferedReader reader = new BufferedReader(new FileReader(antBrainTextFile));
				int ptr = 0;
				String curLineup = reader.readLine();
				while(curLineup != null){
					String curLine = curLineup.toLowerCase();	
					
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
								bs.setMarker(Integer.parseInt(mattSense.group(4).substring(7, 8)),colour);
							}
							else{
								bs.setSenseCondition(mattSense.group(4));
							}
						}
						states[ptr] = bs;
						ptr++;
					}
					else if(curLine.matches(regMark)){
						BrainState bs =  new BrainState();
						bs.setInstruction(Instruction.MARK);
						bs.setStateId(ptr);

						Matcher mattMark = pattMark.matcher(curLine);
						if (mattMark.find())
						{
							bs.setMarker(Integer.parseInt(mattMark.group(1)),colour);
							bs.setNextIdState(Integer.parseInt(mattMark.group(2)));
						}
						states[ptr] = bs;
						ptr++;
					}
					else if(curLine.matches(regUnMark)){
						BrainState bs =  new BrainState();
						bs.setInstruction(Instruction.UNMARK);
						bs.setStateId(ptr);

						Matcher mattUnMark = pattUnMark.matcher(curLine);
						if (mattUnMark.find())
						{
							bs.setMarker(Integer.parseInt(mattUnMark.group(1)),colour);
							bs.setNextIdState(Integer.parseInt(mattUnMark.group(2)));
						}
						states[ptr] = bs;
						ptr++;
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
						ptr++;

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
						ptr++;

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
						ptr++;

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
						ptr++;

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
						ptr++;
					}
					
					curLineup = reader.readLine();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updateBrainStates(states);
			return states;
			
		}
		else{
			return null;
		}
	}

	
	/**
	 * antBrainChecker checks that an antbrain is legitimate, by checking that each line is an acceptable instruction based on the regex array list.
	 * @param antBrainTextFile this is the location of the file for the ant brain
	 * @return true if the ant brain is acceptable false if not.
	 */
	public static boolean antBrainChecker(String antBrainTextFile) {

		regExpressions.add(regSense);
		regExpressions.add(regMark);
		regExpressions.add(regUnMark);
		regExpressions.add(regPickUp);
		regExpressions.add(regDrop);
		regExpressions.add(regTurn);
		regExpressions.add(regMove);
		regExpressions.add(regFlip);
		
		boolean legit = false;
		//counter for the number of states
		int numStates = 0;
		try {
			legit = true;
			BufferedReader reader = new BufferedReader(new FileReader(antBrainTextFile));

			String curLine = reader.readLine();
			
			while(curLine != null && legit)
			{
				
				//calls the regChecker that checks that the curLine fits one of the regular expressions. if it doesn't legitimate is set to false
				if(curLine.equals("")){
				}
				else if(regChecker(curLine) == false){
					legit = false;
					//System.err.print("error in brain as follows: " +curLine);
				}
				else{
					numStates ++;
				}
				
				
				curLine = reader.readLine();
			}
			
			reader.close();
			//this set the number of states which has just been found by iterating over all of the lines of the ant brain.
			stateInt = numStates;
			if(numStates==0){
				return false;
			}
			BufferedReader reader1 = new BufferedReader(new FileReader(antBrainTextFile));

			String curLine1 = reader1.readLine();
			while(curLine1 != null && legit)
			{
				
				
				if(!checkvalidstate(curLine1.toLowerCase())){
					legit = false;
				}
				curLine1 = reader1.readLine();
			}
			reader1.close();
			
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
	
	
	
	/**
	 * updatebrainstates is used to convert each brainstate next state and alt next state in integers to the brainstates as brainstate objects.
	 * @param states array of brainstates
	 */
	public void updateBrainStates(BrainState[] states){
		for(BrainState s:states){
			if(s.getNextIdState() != -1){
			//	System.out.println(s.getNextIdState());
				s.setNextState(states[s.getNextIdState()]);
			}
			if(s.getAltNextIdState() != -1){
			//	System.out.println(s.getAltNextIdState());
				s.setAltNextState(states[s.getAltNextIdState()]);
			}
					
			
		}
	}
	
	public static boolean checkvalidstate(String line){
		if(line.matches(regSense)){
			Matcher mattSense = pattSense.matcher(line);
			if (mattSense.find())
			{
				if(Integer.parseInt(mattSense.group(2))<stateInt && Integer.parseInt(mattSense.group(3))<stateInt){
					return true;
				}
				
			}
		}
			
			
		
		else if(line.matches(regMark)){
			Matcher mattMark = pattMark.matcher(line);
			if (mattMark.find())
			{
				if(Integer.parseInt(mattMark.group(2))<stateInt){
					return true;
				}
				
				
			}
			
		}
		else if(line.matches(regUnMark)){
		

			Matcher mattUnMark = pattUnMark.matcher(line);
			if (mattUnMark.find())
			{
				if(Integer.parseInt(mattUnMark.group(2))<stateInt){
					return true;
				}
			
			}
		}
		else if(line.matches(regPickUp)){

			Matcher mattPickUp = pattPickUp.matcher(line);
			if (mattPickUp.find())
			{
				if(Integer.parseInt(mattPickUp.group(1))<stateInt && Integer.parseInt(mattPickUp.group(2))<stateInt){
					return true;
				}
			}
			

		}
		else if(line.matches(regDrop)){
			Matcher mattDrop = pattDrop.matcher(line);
			if (mattDrop.find())
			{
				if(Integer.parseInt(mattDrop.group(1))<stateInt){
					return true;
				}
			}
			

		}
		else if(line.matches(regTurn)){
			Matcher mattTurn = pattTurn.matcher(line);
			if (mattTurn.find())
			{
				if(Integer.parseInt(mattTurn.group(2))<stateInt){
					return true;
				}
			}
			

		}
		else if(line.matches(regMove)){
			Matcher mattMove = pattMove.matcher(line);
			if (mattMove.find())
			{
				if(Integer.parseInt(mattMove.group(1))<stateInt && Integer.parseInt(mattMove.group(2))<stateInt){
					return true;
				}
			}

		}
		else if(line.matches(regFlip)){

			Matcher mattFlip = pattFlip.matcher(line);
			if (mattFlip.find())
			{
				if(Integer.parseInt(mattFlip.group(2))<stateInt && Integer.parseInt(mattFlip.group(3))<stateInt){
					return true;
				}
			}
		}
		else if(line.equals("")){

					return true;
				
		}
		return false;
		
	
	}
	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * checks that a string fits one of the regular expressions
	 * @param s a string of the ant brain
	 * @return true if it fits one of the regex false if not
	 */
	public static boolean regChecker(String s){
		for(String reg: regExpressions){
			String sLower = s.toLowerCase();
			if(sLower.matches(reg)){
				return true;
			}	
		}
		return false;
	}
	
	/**
	 * returns the length of the array with the regular expressions
	 * used in testing to see if the class constructor is adding all the regex to the array
	 * @return int
	 */
	public static ArrayList<String> getRegExpressions() {
		
		return regExpressions;
	}
	public static void corynTest(String loc){
		String workingDir = System.getProperty("user.dir");
		System.out.print(loc+" \t");
		System.out.println(antBrainChecker(workingDir+"\\files\\"+loc));
		//System.out.println(loc);
		
	
		AntBrainInterpreterCoryn abi = new AntBrainInterpreterCoryn();
		
		BrainState[] abibs= abi.antBrainGenerator(workingDir+ "\\files\\" + loc, AntColour.BLACK);
	

		
		}
	
	public static void main(String [] args){
		corynTest("cleverbrain1.brain");
		corynTest("cleverbrain2.brain");
		corynTest("cleverbrain3.brain");
		corynTest("cleverbrain4.brain");
		corynTest("cleverbrain6.brain");
		corynTest("dumbbrain1.brain");
		corynTest("emptybrain.brain");
		corynTest("horseshoe.brain");
		corynTest("sampleant.brain");
		corynTest("snakebrain.brain");
		
	}
}
