package antgame.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import antgame.AntGame;

public class MapCreator {

	/**
	 * Random number generator used by random map generator function
	 */
	private static final Random RANDOM = new Random();

	/**
	 * Return a randomly-generated Map that conforms to Contest Map specifications
	 * @return	A randomly-generated Map
	 */
	public static Map getRandomMap() {

		// Number of times to run a while loop before failing
		// (prevents infinite loops)
		int LOOP_MAX = 100;
		
		// Pull out the contest world dimension parameter
		int dim = AntGame.CONFIG.CONTEST_MAP_DIMENSION;

		// Initialise two-dimensional array representing cell grid
		Cell[][] cells = new Cell[dim][dim];

		// Initialise cells
		for (int y = 0; y < cells.length; y++) {
			for (int x = 0; x < cells[y].length; x++) {
				// Initialise cell
				cells[y][x] = new Cell(x, y);
				// Detect a border cell, and set it to be rocky
				if (x == 0 || y == 0 || x == (cells[y].length - 1)
						|| y == (cells.length - 1)) {
					cells[y][x].setRock();
				}
			}
		}

		// Generate a Map
		
		// Note that Coryn has written Map to index a cell as:
		//
		// 		Cell c = cells[x][y]
		//
		// Alex's convention is to index a cell as:
		//
		// 		Cell c = cells[y][x]
		//
		// So the cell grid we have is run through an inverter function
		// before a Map object can be created
		Map map = new Map(invertCells(cells));

		// Pull out some more constants
		int numFoodBlobs = AntGame.CONFIG.CONTEST_MAP_FOOD_BLOBS;
		int foodParticles = AntGame.CONFIG.CONTEST_MAP_FOOD_PER_BLOB_CELL;
		int numRocks = AntGame.CONFIG.CONTEST_MAP_ROCKS;

		// Anthills
		
		boolean drawnRed = false;
		boolean drawnBlack = false;
		// Pull up the Set of Points that represents an anthill
		HashSet<Point> antHill = getAnthillPoints();
		int i = 0;
		while ( !( drawnRed && drawnBlack ) && i < LOOP_MAX) {
			
			// Decide which colour anthill we are drawing today
			AntColour colour = null;
			if (!drawnRed) {
				colour = AntColour.RED;
			} else {
				colour = AntColour.BLACK;
			}

			// Generate a starting point at random
			Point start = randomPoint(dim, dim);
			int xOffset = (int) start.getX();
			int yOffset = (int) start.getY();

			// If we can draw the anthill at the randomly generated position...
			if (canDrawElementHere(map, antHill, xOffset, yOffset)) {
				
				// Loop through the Points, draw the anthill
				Iterator<Point> it = antHill.iterator();
				while (it.hasNext()) {
					Point p = it.next();
					Cell c = map.getCell(xOffset + (int) p.getX(), yOffset
							+ (int) p.getY());
					c.setAnthill(colour);

				}
				
				// Flag the specified colour anthill as drawn
				switch (colour) {
				case RED:
					drawnRed = true;
					break;
				case BLACK:
					drawnBlack = true;
					break;
				}

			}
			
			i++;

		}
		
		// Food blobs
		
		int foodBlobsDrawn = 0;
		// Pull up the Set of Points that represents a food blob
		HashSet<Point> foodBlob = getFoodBlobPoints();
		i = 0;
		while ( (foodBlobsDrawn < numFoodBlobs ) && i < LOOP_MAX) {
			
			// Generate a starting point at random
			Point start = randomPoint(dim, dim);
			int xOffset = (int) start.getX();
			int yOffset = (int) start.getY();
			
			// If we can draw the food blob at the randomly generated position...
			if (canDrawElementHere(map, foodBlob, xOffset, yOffset)) {
				
				// Loop through the Points, draw the food blob
				Iterator<Point> it = foodBlob.iterator();
				while (it.hasNext()) {
					Point p = it.next();
					Cell c = map.getCell(xOffset + (int) p.getX(), yOffset
							+ (int) p.getY());
					c.setFood(foodParticles);

				}
				
				// Increment the number of food blobs drawn
				foodBlobsDrawn++;
				
			}
			
			
			i++;
			
		}
		
		// Rocks
		
		int rocksDrawn = 0;
		i = 0;
		while ( ( rocksDrawn < numRocks ) && i < LOOP_MAX) {
			
			// Generate a starting point at random
			Point rock = randomPoint(dim, dim);
			int xOffset = (int) rock.getX();
			int yOffset = (int) rock.getY();
			
			HashSet<Point> rockSet = new HashSet<Point>();
			rockSet.add(rock);
			
			// If we can draw the rock at the randomly generated position...
			if (canDrawElementHere(map, rockSet, xOffset, yOffset)) {
				Cell c = map.getCell(xOffset, yOffset);
				c.setRock();
				// Increment the number of rocks drawn
				rocksDrawn++;
				
			}
			
			i++;
			
		}
		
		return map;
	}

	/**
	 * Return a random point in the range 0 <= x <= xMax, 0 <= y <= yMax
	 * 
	 * @param xMax
	 *            The upper bound on the x-position (inclusive)
	 * @param yMax
	 *            The upper bound on the y-position (inclusive)
	 * @return The random point
	 */
	public static Point randomPoint(int xMax, int yMax) {
		return new Point(RANDOM.nextInt(xMax + 1), RANDOM.nextInt(yMax + 1));
	}

	/**
	 * Determines whether a Map element defined by the points provided can be
	 * drawn on the specified Map at the specified x and y offsets. Method will
	 * return true if the Cells specified in the Set provided are all clear, as
	 * well as the cells immediately adjacent to those specified in the set.
	 * 
	 * @param map
	 *            The Map to check
	 * @param targetCells
	 *            The Set of Points defining an element (e.g. ant hill, food
	 *            blob)
	 * @param xOffset
	 *            The x-position from which the element will be drawn
	 * @param yOffset
	 *            The y-position from which the element will be drawn
	 * @return
	 */
	public static boolean canDrawElementHere(Map map,
			HashSet<Point> targetCells, int xOffset, int yOffset) {

		// Flag to determine whether or not the element provided can be drawn in
		// the Map provided
		boolean canDrawHere = true;

		try {

			// Create a list of Cells to check from the provided Set of Points
			ArrayList<Cell> checkList = new ArrayList<Cell>();
			Iterator<Point> it = targetCells.iterator();
			// For each provided Point...
			while (it.hasNext()) {
				Point p = (Point) it.next();
				// Get the corresponding Cell from the Map (including offsets)
				Cell c = map.getCell(((int) p.getX() + xOffset),
						((int) p.getY() + yOffset));
				// Pull out the cells surrounding this one
				ArrayList<Cell> cellsIncSurrounding = map.getAdjacentCells(c);
				// Merge the cell under inspection to the list of its
				// surrounding cells
				// cellsIncSurrounding now contains the cells surrounding c and
				// c itself
				cellsIncSurrounding.add(c);
				// For all the cells we're dealing with here
				for (Cell _c : cellsIncSurrounding) {
					// If we haven't already added this cell to the list of
					// cells affected by this element...
					if (!checkList.contains(_c)) {
						// ...add it
						checkList.add(_c);
					}
				}
			}

			// Cycle through all the cells that will be affected by this
			// element,
			// check for their emptiness. If we find a non-empty cell, break the
			// loop and return false.
			while (!checkList.isEmpty() && canDrawHere) {

				// Pop a Cell from the list of Cells to check
				Cell c = checkList.remove(0);

				// If this Cells is anything but clear, return false
				if (c.containsBlackAntHill() || c.containsRedAntHill()
						|| c.containsRock() || c.getNumberOfFoodParticles() > 0) {
					canDrawHere = false;
				}

			}
		// If we ever tried to reference a Cell outside the range of the grid,
		// we cannot draw the element here
		} catch (ArrayIndexOutOfBoundsException e) {
			canDrawHere = false;
		}

		// Return the verdict on whether this element can be drawn on the Map
		return canDrawHere;
	}

	/**
	 * Given a two-dimensional array A representing a grid of elements in the
	 * where an element is represented as A[y][x], this method returns
	 * the inverse of this array where an element is represented as A[x][y]
	 * and vice-versa
	 * @param	input	The input grid
	 * @return			The output grid (inverse of input)
	 */
	public static Cell[][] invertCells(Cell[][] input) {
		
		Cell[][] output = new Cell[input[0].length][input.length];
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length; j++) {
				output[j][i] = input[i][j];
			}
		}
		
		return output;
		
	}
	
	
	/**
	 * Get a set of Points that define an anthill
	 * 
	 * The anthill is drawn from the point (0,0)
	 * 
	 * @return A HashSet of points defining an anthill
	 */
	public static HashSet<Point> getAnthillPoints() {

		/*
		 * The first part of the algorithm traces the outline of a hexagon on
		 * the grid
		 */

		// Pull out side length
		int sideLength = AntGame.CONFIG.CONTEST_MAP_ANTHILL_SIDE_LENGTH;
		
		// Height of hexagon = (2n - 1) where n = side length
		int height = ( 2 * sideLength ) - 1;

		// Initialise collection of points to be included in the anthill
		HashSet<Point> targetCells = new HashSet<Point>();

		// First cell is the one [sidelength - 1] units vertically ( -1 as we
		// start at 0)
		int drawYOffset = sideLength - 1;
		targetCells.add(new Point(0, drawYOffset));
		
		int curX = 0;
		
		int maxY = drawYOffset + 1;
		int minY = drawYOffset - 1;

		/*
		 * I started writing a good algorithm, it turned ugly, and this is the result.
		 * I don't even follow it but it seems to work. 
		 */
		
		// Open angled bit
		boolean done = false;
		while (!done) {
			
			if ( (maxY - minY) >= height ) {
				done = true;
				maxY--;
				minY++;
			}
			
			for (int curY = minY; curY <= maxY; curY++) {
				targetCells.add(new Point(curX, curY));
			}
			
			maxY += 2;
			minY -= 2;
			
			curX++;
			
		}
		
		maxY -= 2;
		minY += 2;
		
		// Straight bit
		int i;
		for (i = curX; i < ( (sideLength - 1) + curX ); i++) {
			for (int curY = minY; curY <= maxY; curY++) {
				targetCells.add(new Point(i, curY));
			}
		}
		
		curX = i;
		
		done = false;
		
		maxY = height - 3;
		minY = 2;
		
		// Closed angle bit
		while (!done) {
			
			if ( (maxY - minY) <= 1 ) {
				done = true;
			}
			for (int curY = minY; curY <= maxY; curY++) {
				targetCells.add(new Point(curX, curY));
			}
			
			maxY -= 2;
			minY += 2;
			
			curX++;
			
		}

		return targetCells;
/*		
		// Make a note of peak x and y offsets reached (to be used later)
		int peakX = -1;
		int peakY = -1;

		for (int i = 0; i < sideLength - 1; i++) {
			targetCells.add(new Point((i / 2), drawYOffset - i));
			targetCells.add(new Point((i / 2), drawYOffset + i));
			// If this is the last time the loop will run, record the peak
			// offsets
			if ((i + 1) == sideLength) {
				peakX = (i / 2);
				peakY = drawYOffset + i;
			}
		}

		for (int i = 0; i < sideLength - 1; i++) {
			targetCells.add(new Point(peakX + i, 0));
			targetCells.add(new Point(peakX + i, peakY));
		}

		// Record the new "peak x"
		peakX = peakX + (sideLength - 1);

		// Record our current x-position
		int curX = -1;
		for (int i = 0; i < sideLength - 1; i++) {
			curX = peakX + (i / 2);
			// If our two tracelines have converged, draw one point (convergence
			// point)
			if (i == (peakY - i)) {
				targetCells.add(new Point(curX, i));
				// Otherwise, continue to trace the lines
			} else {
				targetCells.add(new Point(curX, i));
				targetCells.add(new Point(curX, (peakY - i)));
			}
		}

		// Update peakX to reflect the maximum x-position reached
		peakX = curX;

		for (int y = 0; y <= peakY; y++) {
			// Flag to see if we're filling in the cells we're coming across
			boolean filling = false;
			// Flag to see if we're done filling in anthill cells in this row
			boolean reachedEnd = false;
			// We use a while loop as we may not iterate to the end of the row
			int x = 0;
			while (!reachedEnd) {
				// Check if this cell is already part of the anthill
				if (targetCells.contains(new Point(x, y))) {
					// If we weren't already filling, this cell is the start of
					// the anthill on this row,
					// so let's start filling
					if (!filling) {
						filling = true;
						// If we've already been filling, we've reached the end
						// of the row
					} else {
						reachedEnd = true;
					}
					// If this cell is empty and we're filling in cells, fill
					// this one
				} else if (filling) {
					targetCells.add(new Point(x, y));
				}
				// Increment loop counter
				x++;
			}
		}

		// Return the target cells
		return targetCells;*/
	}

	/**
	 * Get a set of Points that define an food blob
	 * 
	 * The food blob is drawn from the point (0,0)
	 * 
	 * @return A HashSet of points defining a food blob
	 */
	public static HashSet<Point> getFoodBlobPoints() {

		// Pull out the side length
		int sideLength = AntGame.CONFIG.CONTEST_MAP_FOOD_BLOB_SIDE_LENGTH;

		// Initialise a collection of cells to be included in the food blob
		HashSet<Point> targetCells = new HashSet<Point>();

		/*
		 * Food blob definition looks like this:
		 * 
		 * x x x x x x x x x x x x x x x x
		 * 
		 * [sideLength] number of cells per row, indentation increments on even
		 * row numbers (odd indices) [sideLength] number of rows
		 */

		// Initialise x offset at 0
		int curXOffset = 0;

		// Draw the blob
		for (int y = 0; y < sideLength; y++) {
			// Increment x-offset on rows of odd indicies
			if (y % 2 == 1) {
				curXOffset++;
			}
			// Draw the row
			for (int x = 0; x < sideLength; x++) {
				targetCells.add(new Point((x + curXOffset), y));
			}
		}

		// Return the HashSet
		return targetCells;

	}
	
	public static void main (String[] args) {
		
		for (int i = 0; i < 10; i++) {
			Map m = getRandomMap();
			m.printStandard();
		}
		
	}

}
