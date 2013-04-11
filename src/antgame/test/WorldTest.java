package antgame.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import antgame.core.Ant;
import antgame.core.AntBrain;
import antgame.core.AntColour;
import antgame.core.BrainState;
import antgame.core.Cell;
import antgame.core.Map;
import antgame.core.World;

public class WorldTest {

	
	static Ant ant1;
	static Ant ant2;

	
	private static String workingDir = System.getProperty("user.dir");
	
	World w1 = new World(workingDir+"\\files\\workingworld.world",workingDir+"\\files\\cleverbrain1.brain",workingDir+"\\files\\cleverbrain2.brain");
	private Map theMap = w1.getMap();
	private static BrainState bs = new BrainState();
	private static Cell[][] map = new Cell[10][10];

	private static AntBrain brain = new AntBrain(workingDir + "\\files\\TestBrainGood.brain",AntColour.RED);


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		for (int y = 0; y < (map.length); y++) {
			for (int x = 0; x < (map.length); x++) {
				map[x][y] = new Cell(x, y, ".");
			}
		}

		ant1 = new Ant(1, 1, AntColour.RED, bs.getStateId(), map[2][3], brain);
		map[2][3].antMoveIn(ant1);
		
		ant2 = new Ant(1, 1, AntColour.RED, bs.getStateId(), map[4][3], brain);
		map[4][3].antMoveIn(ant1);


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
	public void testWorld() {
		fail("Not yet implemented");
	}

	@Test
	public void testStep() {
		fail("Not yet implemented");
	}

	@Test
	public void testSensedCell() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAntCell() {
		Cell cell = map[2][3];		
		
		if(cell.containsAnt()){
			assertTrue("Ant is found in cell", cell.containsAnt());

			if (!(cell.getAnt() == ant1)){
				fail("incorrect ant found in cell");
			}
		}else {
			fail("No ant in cell");
		}
	}

	@Test
	public void testGetAntIntArray() {
	
		int[] coordinates = {2,3};
		Cell cell2 = w1.getMap().getCell(coordinates);
		Cell cell = w1.getMap().getCell(coordinates);
		
		System.out.println("Outcome: " + (cell == cell2));
		
		System.out.println(cell.getPos());
		System.out.println(cell2.getPos());
		if(cell2.containsAnt()){
			System.out.println("1");
			assertTrue("Ant is found in cell", cell2.containsAnt());

			if (!(cell2.getAnt() == ant1)){
				System.out.println("2");
				fail("incorrect ant found in cell");
			}
		}else {
			System.out.println("3");
			fail("No ant in cell");
		}
		
		
	}

	@Test
	public void testIsAntAtCell() {
		Ant ant3 = new Ant(2, 2, AntColour.RED, bs.getStateId(), map[3][3], brain);
		map[3][3].antMoveIn(ant3);
	
		assertTrue("Correct ant is in cell", (ant3 == map[3][3].getAnt()));

	}

	@Test
	public void testIsAntAtIntArray() {
		Ant ant5 = new Ant(5, 5, AntColour.RED, bs.getStateId(), map[1][4], brain);
		map[1][4].antMoveIn(ant5);
		System.out.println("sdgfsa: gds" );
		int[] coordinate = new int[2];
		coordinate[0] = 1;
		coordinate[1] = 4;
		System.out.println("sdgfsa: dfhfhfhfhfhgds" );
		Cell cells = theMap.getCell(coordinate);
		//Cell cell1 = theMap[4][1];
		System.out.println("sdgfsa: " );
		assertTrue("Correct ant is in cell", cells.containsAnt());
	}

	@Test
	public void testKillAnt() {
		Ant ant4 = new Ant(3, 3, AntColour.RED, bs.getStateId(), map[1][1], brain);
		Cell tempCell = ant4.getCurrentPos();
		int numFood = tempCell.getNumberOfFoodParticles();
		ant4.die();
		ant4.getCurrentPos().addNumFood(3);
		ant4.getCurrentPos().antMoveOut();
		
		
		assertFalse("Ant still alive", ant4.isAlive());
		assertTrue("Food correctly added to cell", tempCell.getNumberOfFoodParticles() == numFood + 3);
		assertTrue("cell correctly dealth with dead ant", !tempCell.containsAnt()&&tempCell.isClear());
		
		
	}

	@Test
	public void testIsAntSurronded() {
		
		
		
//		ArrayList<Cell> adjCells = theMap.surrondingCells(ant1.getCurrentPos());
//		int surrounded = 0;
//		
//		for(Cell c:adjCells){
//			if(c.containsAnt()){
//				if(AntColour.otherColour(ant1.getColour()) == c.getAnt().getColour()){
//					surrounded++;
//				}
//			}
//		}
//		if (5<=surrounded){
//			
//	}
		
		
		
		
		fail("Not yet implemented");
	}

	@Test
	public void testGetMap() {
		assertEquals(theMap, w1.getMap());
	}

	@Test
	public void testGetStats() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetObserver() {
		fail("Not yet implemented");
	}

	@Test
	public void testWhoWon() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
