package antgame.braintrain;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import antgame.InvalidMarkerIdException;
import antgame.core.AntBrain;
import antgame.core.AntColour;
import antgame.core.BrainState;
import antgame.core.Instruction;
import antgame.core.LeftRight;
import antgame.core.Marker;
import antgame.core.SenseCondition;
import antgame.core.SenseDirection;

/**
 * AntBrainGenerator for generating random AntBrains
 * @author Alex
 *
 */
public class AntBrainGenerator {
	
	/**
	 * Token-type enumerator
	 * @author Alex
	 *
	 */
	public enum TokenType {
		NEXTSTATE,ALTNEXTSTATE,SENSEDIRECTION,SENSECONDITION,LEFTRIGHT,RANDINT,MARKER;
	}
	
	/**
	 * Maximum number of States
	 */
	private static final int STATE_MAX = 10000;
	
	/**
	 * Maximum number of Markers
	 */
	private static final int MARKER_MAX = 6;
	
	/**
	 * Maximum value of "Flip" random number
	 */
	private static final int FLIP_MAX = 10000;
	
	/**
	 * Line-break character
	 */
	private static final String LINEBREAK = System.getProperty("line.separator");
	
	/**
	 * Random number generator
	 */
	private static final Random r = new Random();
	
	/**
	 * Returns a random value from the given enumerator
	 * @param	Class<T>	A class of type Enum
	 * @return	T			A random value from the input enumerator
	 */
	public static <T extends Enum<T>> T getRandom(Class<T> enumType) {
		return getRandom(enumType.getEnumConstants());
	}
	
	/**
	 * Returns an array of tokens that must exist for a given instruction
	 * @param	Instruction	The instruction
	 * @return	Token[]		An array of the tokens that must exist for the instruction
	 */
	public static TokenType[] getTokens(Instruction i) {
		switch (i) {
			case SENSE:
				return (new TokenType[] {TokenType.SENSEDIRECTION,TokenType.NEXTSTATE,TokenType.ALTNEXTSTATE,TokenType.SENSECONDITION});
			case MARK:
				return (new TokenType[] {TokenType.MARKER,TokenType.NEXTSTATE});
			case UNMARK:
				return (new TokenType[] {TokenType.MARKER,TokenType.NEXTSTATE});
			case PICKUP:
				return (new TokenType[] {TokenType.NEXTSTATE,TokenType.ALTNEXTSTATE});
			case DROP:
				return (new TokenType[] {TokenType.NEXTSTATE});
			case TURN:
				return (new TokenType[] {TokenType.LEFTRIGHT,TokenType.NEXTSTATE});
			case MOVE:
				return (new TokenType[] {TokenType.NEXTSTATE,TokenType.ALTNEXTSTATE});
			case FLIP:
				return (new TokenType[] {TokenType.RANDINT,TokenType.NEXTSTATE,TokenType.ALTNEXTSTATE});
			default:
				// This block will never be reached
				return new TokenType[] {};
		}
			
	}
	
	/**
	 * Return a random item from an array
	 * @param	T[]	The input array
	 * @return	T	A random item from the array
	 */
	public static <T> T getRandom(T[] array) {
		return array[r.nextInt(array.length-1)];
	}
	
	public static BrainState getRandomBrainState(AntColour colour) {
		
		// Create a new BrainState with i as the stateId
		BrainState brainState = new BrainState();
		
		// Set the Instruction
		Instruction instruction = getRandom(Instruction.class);
		brainState.setInstruction(instruction);
		
		// Determine the additional Tokens needed
		TokenType[] tokenTypes = getTokens(instruction);
		
		// Loop through the additional tokens that we require
		for (int j = 0; j < tokenTypes.length; j++) {
			switch (tokenTypes[j]) {
				case NEXTSTATE:
					brainState.setNextIdState(r.nextInt(STATE_MAX));
					break;
				case ALTNEXTSTATE:
					brainState.setAltNextIdState(r.nextInt(STATE_MAX));
					break;
				case SENSEDIRECTION:
					brainState.setSenseDirection(getRandom(SenseDirection.class));
					break;
				case SENSECONDITION:
					SenseCondition sc = getRandom(SenseCondition.class);
					brainState.setSenseCondition(sc);
					if (sc.equals(SenseCondition.MARKER)) {
						brainState.setMarker(r.nextInt(MARKER_MAX), colour);
					}
					break;
				case LEFTRIGHT:
					brainState.setLeftRight(getRandom(LeftRight.class));
					break;
				case RANDINT:
					brainState.setRandomInt(r.nextInt(FLIP_MAX));
					break;
				case MARKER:
					brainState.setMarker(r.nextInt(MARKER_MAX), colour);
					break;
			}
		}
		
		return brainState;
		
	}
	
	public void saveGeneration() {
		String folderName = "Generation-" + System.nanoTime();
		
		
		
	}
	
	/**
	 * Returns a random AntBrain of the specified colour
	 * @param	colour	The colour of the random AntBrain to return
	 * @return			The randomly-generated AntBrain
	 */
	public static AntBrain getRandomAntBrain(AntColour colour) {
		
		// Initialise the collection of BrainStates that comprise the AntBrain
		BrainState[] brainStates = new BrainState[STATE_MAX];
		
		// Add [STATE_MAX] number of BrainStates
		for (int i = 0; i < STATE_MAX; i++) {
			
			// Create a new BrainState with i as the stateId
			BrainState brainState = getRandomBrainState(colour);
			brainState.setStateId(i);
			brainStates[i] = brainState;

		}
		
		// Update the pointers
		brainStates = cleanStates(brainStates);
		
		// Return the AntBrain
		return new AntBrain(brainStates,colour);
	}
	
	/**
	 * Updates BrainState pointers so they correspond with their built-in integer pointers
	 * @param	brainStates	The clean BrainStates
	 */
	public static BrainState[] cleanStates(BrainState[] brainStates) {
		// Loop through, updating pointers between BrainState objects
		// based on the now-established Ids
		for (BrainState bs: brainStates) {
			// Every BrainState has a next state, add the pointer
			bs.setNextState( brainStates[ bs.getNextIdState() ] );
			// Not every BrainState has an alternate next state, add the pointer conditionally
			if ( bs.getAltNextIdState() != -1 ) {
				bs.setAltNextState( brainStates[ bs.getAltNextIdState() ] );
			}
		}
		return brainStates;
	}
	
	public static void main (String[] args) {
		AntBrain randomBrain = getRandomAntBrain(AntColour.BLACK);
		System.out.println(randomBrain.toString());
	}
	
}
