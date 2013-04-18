package antgame.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import antgame.core.Cell;
import antgame.core.MapCreator;

public class MapCreatorTest {

	MapCreator mc = new MapCreator();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetRandomMap() {
		assertNotNull(mc.getRandomMap());
	}

	@Test
	public void testRandomPoint() {
		assertTrue(mc.randomPoint(150, 150).getX()<=150);
		assertTrue(mc.randomPoint(150, 150).getY()<=150);
	}

	@Test
	public void testCanDrawElementHere() {
		fail("Not yet implemented");
	}

	@Test
	public void testInvertCells() {
		Cell[][] cell = new Cell[5][5];

		for (int i = 0; i<cell.length; i++){
			for (int j = 0; j<cell.length; j++){
				cell[i][j] = new Cell(i,j);
			}
		}
		cell[1][3].setRock();
		cell[3][3].setRock();
		cell[3][4].setRock(); 
		cell[0][2].setRock();
		
		
		Cell[][] invCells = mc.invertCells(cell);
		

		assertTrue(invCells[3][1].containsRock());
		assertTrue(invCells[3][3].containsRock());
		assertTrue(invCells[4][3].containsRock());
		assertTrue(invCells[2][0].containsRock());
	}

	@Test
	public void testGetAnthillPoints() {
		
		fail("Not yet implemented");
	}

	@Test
	public void testGetFoodBlobPoints() {
		
		
		HashSet<Point> targetCells = mc.getFoodBlobPoints();
		
		Iterator<Point> it = targetCells.iterator();
		while (it.hasNext()) {
			Point p = (Point) it.next();
			System.out.println("fdgdsfhhh");
			assertEquals(false , mc.getRandomMap().getCell((int)p.getX(),(int)p.getY()).isContainsFood());
			
		}
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}