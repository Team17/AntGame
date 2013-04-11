package antgame.core;

import guiAntGame.ObserverAntWorld;

import java.util.ArrayList;

import antgame.AntGame;
import antgame.services.RandomNumber;

public class World {
	// ants stores an array of ants black and red
	private Ant[] ants;
	// noAnts stores the number of ants
	int noAnts;
	// map stores the map
	private Map map;
	// redAntBrain stores the red Ant Brain
	private AntBrain redAntBrain;
	// blackAntBrain stores the black Ant Brain
	private AntBrain blackAntBrain;

	// Keeps all the stats of the word
	private WorldStats stats;

	// Observer which stores which cells to update in the SimulatorView
	private ObserverAntWorld obiwan;

	/**
	 * Constructor
	 * 
	 * @param map
	 *            The Map to use
	 * @param redBrain
	 *            The AntBrain to be used by the red colony
	 * @param blackBrain
	 *            The AntBrain to be used by the black colony
	 */
	public World(Map map, AntBrain redBrain, AntBrain blackBrain) {

		this.map = map;
		this.redAntBrain = redBrain;
		this.blackAntBrain = blackBrain;

		// Call to complete construction-time routines
		_construct();

	}

	/**
	 * World constructor takes the directory of the map, it then passes this to
	 * the MapInterpreter who returns an instance of map. It also takes the
	 * directory of each ant brain and creates a new instance of ant brain for
	 * each, at creation the ant brain passes the file through the
	 * AntBrainInterpreter. The constructor sets up all the ants as well as
	 * calls the step function.
	 * 
	 * @param mapLocation
	 *            string representation of the directory path of the map file.
	 * @param antR
	 *            string representation of the directory path of the red ant
	 *            brain file.
	 * @param antB
	 *            string representation of the directory path of the black ant
	 *            brain file.
	 */
	public World(String mapLocation, String antR, String antB) {
		this.map = MapInterpreter.MapGenerator(mapLocation);

		this.redAntBrain = new AntBrain(antR, AntColour.RED);
		this.blackAntBrain = new AntBrain(antB, AntColour.BLACK);

		// Call to complete construction-time routines
		_construct();

	}

	/**
	 * Private constructor method invoked by all World constructors
	 */
	private void _construct() {
		// the following basically counts the number of ant hill cells and thus
		// how many ants there will be.
		for (int y = 0; y < (map.getYSize()); y++) {
			for (int x = 0; x < (map.getXSize()); x++) {
				if (map.getCell(x, y).containsRedAntHill()
						|| map.getCell(x, y).containsBlackAntHill()) {

					noAnts++;
				}
			}
		}
		// based on the number of ants which the for loop above found the ant
		// array can now be initialised.
		ants = new Ant[noAnts];

		/*
		 * the following loop iterated over each cells in the map looking for
		 * cells that contain an anthill once it finds an anthill it creates an
		 * ant and sets its current location there. It then sets that ant's
		 * antid to the antPointer, its direction to zero, its colour dependent
		 * on the colour of the ant hill, its initial brain state which is 0. it
		 * then increases the pointer, as well as adding the cell itself to the
		 * array of ant hills, respective of colour.
		 */

		// antPointer is the pointer in the array of ants to point to the next
		// free position also used as the uID
		int antPointer = 0;
		int reds = 0;
		int blacks = 0;
		for (int y = 0; y < (map.getYSize()); y++) {
			for (int x = 0; x < (map.getXSize()); x++) {
				if (map.getCell(x, y).containsRedAntHill()) {
					ants[antPointer] = new Ant(antPointer, 0, AntColour.RED, 0,
							map.getCell(x, y), redAntBrain);
					map.getCell(x, y).antMoveIn(ants[antPointer]);
					antPointer++;
					reds++;
				}
				if (map.getCell(x, y).containsBlackAntHill()) {
					ants[antPointer] = new Ant(antPointer, 0, AntColour.BLACK,
							0, map.getCell(x, y), blackAntBrain);
					map.getCell(x, y).antMoveIn(ants[antPointer]);
					antPointer++;
					blacks++;
				}
			}
		}
		this.stats = new WorldStats(reds, blacks);
		obiwan = new ObserverAntWorld(this);
		// //calls the step method.
		// for(int i=0; i < 300000; i++){
		// step();
		// }
		//
		//
		// System.out.println("Red Ants Alive: " + redAlive +
		// " Black Ants Ailve: " + blackAlive);
		// map.printmap();
	}

	/**
	 * Returns the AntBrain assigned to the red colony
	 * 
	 * @return The red AntBrain
	 */
	public AntBrain getRedBrain() {
		return redAntBrain;
	}

	/**
	 * Returns the AntBrain assigned to the black colony
	 * 
	 * @return The black AntBrain
	 */
	public AntBrain getBlackBrain() {
		return blackAntBrain;
	}

	/**
	 * step is the function that is called 300,000 times, it starts of by
	 * calling foodInEachAntHill which calculates how many particles of food
	 * there are in each ant hill. It then starts a for loop for each ant which
	 * it calls curAnt. It checks if the ant is surrounded if it is it kills the
	 * ant. otherwise it checks if the ant is alive if the ant is alive it gets
	 * is current brain state called antsState. if the ant is resting then it
	 * decreases the resting period, otherwise it calls a switch statement based
	 * on the instruction of the antsState. it carries on for each ant.
	 */
	public void step() {
		stats.incRound();
		for (Ant curAnt : ants) {
			if (isAntSurronded(curAnt) && curAnt.isAlive()) {
				switch (curAnt.getColour()) {
				case RED:
					stats.decRedAlive();
					break;
				case BLACK:
					stats.decBlackAlive();
					break;
				default:
					break;
				}
				obiwan.addToUpdate(curAnt.getCurrentPos());
				killAnt(curAnt);

			}
			if (curAnt.isAlive()) {
				BrainState antsState = curAnt.getBrainState();

				if (curAnt.getResting() > 0) {
					curAnt.decResting();
				} else {
					switch (antsState.getInstruction()) {
					case SENSE:
						// finds the cell that we are sensing HERE AHEAD
						// LEFTAHEAD RIGHTAHEAD
						Cell cellTS = sensedCell(curAnt.getCurrentPos(),
								curAnt.getDir(), antsState.getSenseDirection());
						// gets the condition of the Sense i.e. Marker Friend
						// Foe etc.
						SenseCondition sCon = antsState.getSenseCondition();
						// if the sensecondition is marker then we require the
						// marker information to be passed to the senseCheck
						// method in cell.
						if (sCon == SenseCondition.MARKER) {
							// if the condition is met i.e. the marker is
							// present in the cell then we set the current brain
							// state to the next brainstate.
							if (cellTS.senseCheck(curAnt, sCon,
									antsState.getMarker())) {
								curAnt.setBrainState(antsState.getNextState());
							}
							// if the condition is not met i.e. the marker is
							// not present in the cell then we set the current
							// brain state to the next alternative brainstate.
							else {
								curAnt.setBrainState(antsState
										.getAltNextState());
							}
						}
						// if we are not sensing a marker then we just pass null
						// as the marker info as it is not required for the
						// senseCheck
						else {
							// if the condition is met i.e. there is a friend in
							// the cell then we set the current brain state to
							// the next brainstate.
							if (cellTS.senseCheck(curAnt, sCon, null)) {
								curAnt.setBrainState(antsState.getNextState());
							}
							// if the condition is not met i.e. there is not a
							// friend in the cell then we set the current brain
							// state to the next alternative brainstate.
							else {
								curAnt.setBrainState(antsState
										.getAltNextState());
							}
						}
						break;

					/*
					 * if the condition is mark then we mark the cell with the
					 * antsState marker and set the current brain state to the
					 * next brainstate.
					 */
					case MARK:
						curAnt.getCurrentPos().setMarker(antsState.getMarker());
						curAnt.setBrainState(antsState.getNextState());
						break;

					/*
					 * if the condition is unmark then we clear that specific
					 * marker and set the current brain state to the next
					 * brainstate.
					 */
					case UNMARK:
						curAnt.getCurrentPos().clearMarker(
								antsState.getMarker());
						curAnt.setBrainState(antsState.getNextState());
						break;

					/*
					 * if the condition is pickup then we check the if the cell
					 * contains food if it does we remove the food from the cell
					 * and set the ant to be carrying food by calling pickupFood
					 * and set the current brain state to the next brainstate.
					 * if there is no food in the cell then we set the brain
					 * state to the alternative state.
					 */
					case PICKUP:
						if (curAnt.getCurrentPos().isContainsFood()) {
							curAnt.getCurrentPos().removeFood();
							curAnt.pickupFood();
							curAnt.setBrainState(antsState.getNextState());
							obiwan.addToUpdate(curAnt.getCurrentPos());
							if (curAnt.getCurrentPos().containsBlackAntHill()) {
								stats.decFoodUnitsBlackHill();
							}
							if (curAnt.getCurrentPos().containsRedAntHill()) {
								stats.decFoodUnitsRedHill();
							}
						} else {
							curAnt.setBrainState(antsState.getAltNextState());
						}
						break;

					/*
					 * if the condition is Drop (drop food) then we check if the
					 * ant has food and we set the ant to not be carrying food
					 * (dropFood) and add food to the current cell the ant is
					 * in. we set the current brain state to the next
					 * brainstate, irrelevant of whether food is dropped or not
					 */
					case DROP:
						if (curAnt.isHasFood()) {
							curAnt.dropFood();
							curAnt.getCurrentPos().addFood();
							obiwan.addToUpdate(curAnt.getCurrentPos());
							if (curAnt.getCurrentPos().containsBlackAntHill()) {
								stats.incFoodUnitsBlackHill();
							}
							if (curAnt.getCurrentPos().containsRedAntHill()) {
								stats.incFoodUnitsRedHill();
							}
						}

						curAnt.setBrainState(antsState.getNextState());

						break;

					/*
					 * if the condition is turn then we literally turn the ant
					 * based on the antsState Left or right condition and set
					 * the current brain state to the next brainstate.
					 */
					case TURN:
						curAnt.turn(antsState.getLeftRight());
						curAnt.setBrainState(antsState.getNextState());
						break;

					/*
					 * for the move condition we have to first find the cell
					 * that the ant is going to be moving into, for this we find
					 * adjacent cell using its current position and the current
					 * direction the ant is facing, we check if the cell is
					 * clear if it is then we move the ant out of the current
					 * cell, set the cell the ant is moving to to contain the
					 * ant, then set the ants new location, set the ant to be
					 * resting and finally set the current brainstate to be the
					 * next brain state. if the cell is not clear then we set
					 * the current brain state to be the alternative brain
					 * state.
					 */
					case MOVE:
						Cell cellGoingTo = map.adjacentCell(
								curAnt.getCurrentPos(), curAnt.getDir());
						if (cellGoingTo.isClear()) {
							obiwan.addToUpdate(curAnt.getCurrentPos());
							curAnt.getCurrentPos().antMoveOut();
							cellGoingTo.antMoveIn(curAnt);
							curAnt.setCurrentPos(cellGoingTo);
							obiwan.addToUpdate(curAnt.getCurrentPos());
							curAnt.setResting();
							curAnt.setBrainState(antsState.getNextState());
						} else {
							curAnt.setBrainState(antsState.getAltNextState());
						}
						break;

					/*
					 * if the condition is flip then we check if the random
					 * number matched if so we set the brain state to the next
					 * brain state if not we set the brain state to the
					 * alternative brain state.
					 */
					case FLIP:
						RandomNumber rN = new RandomNumber();
						if (rN.nextInt(antsState.getRandomInt()) == 0) {
							curAnt.setBrainState(antsState.getNextState());
						} else {
							curAnt.setBrainState(antsState.getAltNextState());
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * sensedCell is used by the step method when the instruction is SENSE, it
	 * is used to return the cell that the ant is wanting to sense, based on the
	 * senseLoc.
	 * 
	 * @param cell
	 *            the ants current cell,
	 * @param dir
	 *            the direction the ant is facing
	 * @param senseLoc
	 *            the location that the ant is trying to sense so HERE, AHEAD,
	 *            RIGHTAHEAD and LEFTAHEAD
	 * @return Cell, it returns the cell that is trying to be sensed using the
	 *         adjacentCell method with the ant current cell and direction as
	 *         parameters.
	 */
	public Cell sensedCell(Cell cell, int dir, SenseDirection senseLoc) {
		switch (senseLoc) {
		case HERE:
			return cell;
		case AHEAD:
			return map.adjacentCell(cell, dir);
		case LEFTAHEAD:
			return map.adjacentCell(cell, ((dir + 5) % 6));
		case RIGHTAHEAD:
			return map.adjacentCell(cell, (dir + 1));
		default:
			return null;
		}
	}

	/**
	 * getAnt literally returns an ant in a cell so long as there is an ant in
	 * the cell, otherwise it returns null.
	 * 
	 * @param cell
	 *            this is the cell we want to get the ant that is in it
	 * @return Ant the ant in cell.
	 */
	public Ant getAnt(Cell cell) {
		if (cell.containsAnt()) {
			return cell.getAnt();
		} else {
			return null;
		}
	}

	/**
	 * getAnt is identical to the other getAnt method instead of taking a cell
	 * object it takes the pos i.e. the array of x and y value that represent
	 * the coordinates of the cell.
	 * 
	 * @param pos
	 *            this is the array that contains the x and y coordinates of the
	 *            cell we want to get the ant that is in it
	 * @return Ant the ant in cell.
	 */
	public Ant getAnt(int[] pos) {
		Cell cell = map.getCell(pos);
		if (cell.containsAnt()) {
			return cell.getAnt();
		} else {
			return null;
		}
	}

	/**
	 * is ant check if there is an ant in a cell
	 * 
	 * @param cell
	 *            that is being checked to see if there is an ant in it.
	 * @return boolean true if there is an ant in the cell, false if not.
	 */
	public boolean isAntAt(Cell cell) {
		return cell.containsAnt();
	}

	/**
	 * isAntAt is an alternative method to the other isAntAt but rather than
	 * cell as the parameter it is an array representing the x and y
	 * coordinates.
	 * 
	 * @param pos
	 *            array of two representing x and y coordinates
	 * @return boolean true if there is an ant in the cell, false if not.
	 */
	public boolean isAntAt(int[] pos) {
		Cell cell = map.getCell(pos);
		return cell.containsAnt();
	}

	/**
	 * killAnt literally kills the ant, it does not remove the ant from the ant
	 * array, but it sets the ants alive field to false (die), adds food to the
	 * cell and removes the ant from the cell (antMoveOut)
	 * 
	 * @param ant
	 *            the ant that is being killed
	 */
	public void killAnt(Ant ant) {
		ant.die();
		ant.getCurrentPos().addNumFood(3);
		ant.getCurrentPos().antMoveOut();
	}

	/**
	 * isAntSurrounded finds out if a specific ant is surrounded by getting all
	 * the cells surrounding the ant, if one of the cells is clear then
	 * surrounded is set to false, if one of the cells contains an ant of the
	 * same colour then surrounded is also set to false otherwise the ant is
	 * assumed to be surrounded.
	 * 
	 * @param ant
	 *            the ant that is being checked if it is surrounded
	 * @return true if the ant is surrounded false otherwise.
	 */
	public boolean isAntSurronded(Ant ant) {
		ArrayList<Cell> adjCells = map.surrondingCells(ant.getCurrentPos());
		int surrounded = 0;

		for (Cell c : adjCells) {
			if (c.containsAnt()) {
				if (AntColour.otherColour(ant.getColour()) == c.getAnt()
						.getColour()) {
					surrounded++;
				}
			}
		}
		return 5 <= surrounded;
	}

	/**
	 * @return current map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @return current map
	 */
	public WorldStats getStats() {
		return this.stats;
	}

	/**
	 * @return current map
	 */
	public ObserverAntWorld getObserver() {
		return this.obiwan;
	}

	/**
	 * @return the team with most food, if equal, with most alive, if a tie
	 *         returns null
	 */
	public AntColour whoWon() {
		if (stats.getFoodUnitsRedHill() == stats.getFoodUnitsBlackHill()) {
			if (stats.getBlackAlive() == stats.getRedAlive()) {
				return null;
			} else if (stats.getBlackAlive() > stats.getRedAlive()) {
				return AntColour.BLACK;
			} else if (stats.getBlackAlive() < stats.getRedAlive()) {
				return AntColour.RED;
			}
		} else if (stats.getFoodUnitsRedHill() > stats.getFoodUnitsBlackHill()) {
			return AntColour.RED;
		} else if (stats.getFoodUnitsRedHill() < stats.getFoodUnitsBlackHill()) {
			return AntColour.BLACK;
		}
		return null;
	}

	public static void main(String[] args) {
		String workingDir = System.getProperty("user.dir");

		World w1 = new World("C:/workingworld.world"/*
													 * workingDir+
													 * "\\files\\workingworld.world"
													 * *
													 */, workingDir
				+ "\\files\\cleverbrain1.brain", workingDir
				+ "\\files\\cleverbrain2.brain");

	}

}
