package antgame.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import antgame.core.Cell;
import antgame.core.Map;

public class MapTest {
	
	
	//the width of the map
	private int xSize;
	//the height of the map
	private int ySize;
	//cells making up map
	private static Cell[][] cell;
	//map instance
	private static Map map;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cell = new Cell[150][150];
		map = new Map(cell);
		
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
	public void testMap() {;
		
		assertNotNull(map);
		
	}

	@Test
	public void testPrintmap() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCellIntInt() {
		assertEquals(cell[10][10], map.getCell(10, 10));
	}

	@Test
	public void testGetCellIntArray() {
		int[] pos = {10,10};
		assertEquals(cell[10][10], map.getCell(pos));
	}

	@Test
	public void testGetXSize() {
		assertEquals(cell.length, map.getXSize());
	}

	@Test
	public void testGetYSize() {
		assertEquals(cell.length, map.getYSize());
	}

	@Test
	public void testAdjacentCell() {
		 Cell tempCell = map.adjacentCell(cell[10][10], 5);
		//assertEquals(cell[10][11], map.adjacentCell(cell[][], 5));
	}

	@Test
	public void testSurrondingCells() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
