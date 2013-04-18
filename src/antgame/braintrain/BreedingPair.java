package antgame.braintrain;

import java.util.Random;

import antgame.AntGame;
import antgame.AntGameProperties;
import antgame.braintrain.AntBrainGenerator.TokenType;
import antgame.core.AntBrain;
import antgame.core.AntColour;
import antgame.core.BrainState;
import antgame.core.LeftRight;
import antgame.core.SenseCondition;
import antgame.core.SenseDirection;

public class BreedingPair {

	/**
	 * The first chromosome
	 */
	private AntBrain chromosome1;
	
	/**
	 * The second chromosome
	 */
	private AntBrain chromosome2;
	
	/**
	 * Probability that mutation will occur
	 */
	private static final double P_MUTATION = Generation.P_MUTATION;
	
	/**
	 * A random number generator
	 */
	private static final Random random = new Random();
	
	/**
	 * Constructor
	 * @param	chromosome1	
	 * @param	chromosome2
	 */
	public BreedingPair (AntBrain chromosome1, AntBrain chromosome2) {
		this.chromosome1 = chromosome1;
		this.chromosome2 = chromosome2;
	}

	/**
	 * @return the chromosome1
	 */
	public AntBrain getChromosome1() {
		return chromosome1;
	}

	/**
	 * @param chromosome1 the chromosome1 to set
	 */
	public void setChromosome1(AntBrain chromosome1) {
		this.chromosome1 = chromosome1;
	}

	/**
	 * @return the chromosome2
	 */
	public AntBrain getChromosome2() {
		return chromosome2;
	}

	/**
	 * @param chromosome2 the chromosome2 to set
	 */
	public void setChromosome2(AntBrain chromosome2) {
		this.chromosome2 = chromosome2;
	}
	
	/**
	 * Perform a crossover operation on this breeding pair
	 */
	public void crossover() {
		
		// Chromosome lengths
		int chr_length_1 = chromosome1.getBrainStates().length;
		int chr_length_2 = chromosome2.getBrainStates().length;
		
		// Longest length
		int shortest = (chr_length_1 < chr_length_2) ? chr_length_1 : chr_length_2;
		
		// Determine crossover point
		int cr_point = random.nextInt(shortest);
		
		// Lengths will invert
		BrainState[] _bs1 = new BrainState[chr_length_2];
		BrainState[] _bs2 = new BrainState[chr_length_1];
		
		// Pull out the original set of BrainStates
		BrainState[] bs1 = chromosome1.getBrainStates();
		BrainState[] bs2 = chromosome2.getBrainStates();
		
		// Create new chromosome1
		for (int i = 0; i < _bs1.length; i++) {
			if (i < cr_point) {
				_bs1[i] = bs1[i].clone();
			} else {
				_bs1[i] = bs2[i].clone();
			}
		}
		
		// Create new chromosome2
		for (int i = 0; i < _bs2.length; i++) {
			if (i < cr_point) {
				_bs2[i] = bs2[i].clone();
			} else {
				_bs2[i] = bs1[i].clone();
			}
		}
		
		// Update the inter-BrainState pointers to match their index values
		_bs1 = AntBrainGenerator.cleanStates(_bs1);
		_bs2 = AntBrainGenerator.cleanStates(_bs2);
		
		// Update the chromosomes
		chromosome1 = new AntBrain(_bs1,AntColour.RED);
		chromosome2 = new AntBrain(_bs2,AntColour.RED);
		
	}
	
	/**
	 * Perform a mutation operation on the chromosomes
	 */
	public void mutate() {
		chromosome1 = _mutate(chromosome1);
		chromosome2 = _mutate(chromosome2);
	}
	
	/**
	 * Return a mutated chromosome
	 * @param	chromosome	The chromosome to mutate
	 * @return				The mutated chromosome
	 */
	private AntBrain _mutate(AntBrain chromosome) {
		
		// Pull out the BrainStates
		BrainState[] brainStates = chromosome.getBrainStates();
		
		// Loop over the BrainStates
		for (BrainState bs : brainStates) {
			// Instruction mutation
			if (mutating()) {
				// Put in an whole new BrainState
				bs = AntBrainGenerator.getRandomBrainState(AntColour.RED);
			} else {
				// Determine the mutable tokens
				TokenType[] tokenTypes = AntBrainGenerator.getTokens(bs.getInstruction());
				
				// Loop through the additional tokens we can mutate
				for (int j = 0; j < tokenTypes.length; j++) {
					if (mutating()) {
						switch (tokenTypes[j]) {
							case NEXTSTATE:
								bs.setNextIdState(random.nextInt(brainStates.length));
								break;
							case ALTNEXTSTATE:
								bs.setAltNextIdState(random.nextInt(brainStates.length));
								break;
							case SENSEDIRECTION:
								bs.setSenseDirection(AntBrainGenerator.getRandom(SenseDirection.class));
								break;
							case SENSECONDITION:
								SenseCondition sc = AntBrainGenerator.getRandom(SenseCondition.class);
								bs.setSenseCondition(sc);
								if (sc.equals(SenseCondition.MARKER)) {
									bs.setMarker(random.nextInt(AntGame.CONFIG.NUM_MARKERS), AntColour.RED);
								}
								break;
							case LEFTRIGHT:
								bs.setLeftRight(AntBrainGenerator.getRandom(LeftRight.class));
								break;
							case RANDINT:
								bs.setRandomInt(random.nextInt(AntGameProperties.getFlipMax()));
								break;
							case MARKER:
								bs.setMarker(random.nextInt(AntGame.CONFIG.NUM_MARKERS), AntColour.RED);
								break;
							default:
								break;
						}
					}
				}
			}
		}
		// Clean and return
		return new AntBrain(AntBrainGenerator.cleanStates(brainStates),AntColour.RED);
		
	}
	
	/**
	 * Return true if a mutation should occur.  Control the probability
	 * that this method will return true with P_MUTATION
	 * @return	True if a mutation should occur, false otherwise
	 */
	private boolean mutating() {
		return (random.nextDouble() < P_MUTATION);
	}
	
	
}
