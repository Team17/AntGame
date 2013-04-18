package antgame.test;

import static org.junit.Assert.*;

import guiAntGame.ObserverAntWorld;



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
import antgame.core.WorldStats;
//
public class WorldTest {

	
	static Ant ant1;
	static Ant ant2;

	
	private static String workingDir = System.getProperty("user.dir");
	
	World w1 = new World(workingDir+"\\files\\workingworld.world",workingDir+"\\files\\cleverbrain1.brain",workingDir+"\\files\\cleverbrain2.brain");
	private Map theMap = w1.getMap();
	private static BrainState bs = new BrainState();
	private WorldStats stats = w1.getStats();
	private ObserverAntWorld obs = w1.getObserver();
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
		Cell cell = map[coordinates[0]][coordinates[1]];

		if(cell.containsAnt()){
			assertTrue("Ant is found in cell", cell.containsAnt());

			if (!(cell.getAnt() == ant1)){
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
		
		Ant ant7 = new Ant(7, 7, AntColour.RED, bs.getStateId(), map[4][1], brain);
		map[4][1].antMoveIn(ant7);
		
		int[] coordinates = {4,1};
		Cell cell = map[coordinates[0]][coordinates[1]];
		
		assertTrue("Correct ant is in cell", (ant7 ==cell.getAnt()));
		
		
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
		
		Ant lAnt1 = new Ant(1, 1, AntColour.BLACK, bs.getStateId(), map[2][3], brain);
		Ant lAnt2 = new Ant(1, 1, AntColour.RED, bs.getStateId(), map[2][3], brain);
		Ant lAnt3 = new Ant(1, 1, AntColour.RED, bs.getStateId(), map[2][3], brain);
		Ant lAnt4 = new Ant(1, 1, AntColour.RED, bs.getStateId(), map[2][3], brain);
		Ant lAnt5 = new Ant(1, 1, AntColour.RED, bs.getStateId(), map[2][3], brain);
		Ant lAnt6 = new Ant(1, 1, AntColour.RED, bs.getStateId(), map[2][3], brain);

		w1.getMap().getCell(20, 20).antMoveIn(lAnt1);
		w1.getMap().getCell(21, 20).antMoveIn(lAnt2);
		w1.getMap().getCell(21, 19).antMoveIn(lAnt3);
		w1.getMap().getCell(20, 19).antMoveIn(lAnt4);
		w1.getMap().getCell(20, 21).antMoveIn(lAnt5);
		w1.getMap().getCell(19, 20).antMoveIn(lAnt6);

	}

	@Test
	public void testGetMap() {
		assertEquals(theMap, w1.getMap());
	}

	@Test
	public void testGetStats() {
		assertEquals(stats, w1.getStats());
	}

	@Test
	public void testGetObserver() {
		assertEquals(obs, w1.getObserver());
	}

	@Test
	public void testWhoWon() {
		// Red has more food than black
		for (int i =0; i<=3; i++){
		stats.incFoodUnitsRedHill();
		}
		
		
		assertEquals(AntColour.RED, w1.whoWon());
		
		//Equal number of food and ants alive
		for (int i =0; i<=3; i++){
			stats.incFoodUnitsBlackHill();
			}		
		assertEquals(null, w1.whoWon());
		
		//Equal number of food
		stats.decBlackAlive();
		assertEquals(AntColour.RED, w1.whoWon());
		
		
		
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

}
