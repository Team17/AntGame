package antgame.test;

import static org.junit.Assert.*;

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

public class WorldTest {

	static Ant ant1;

	private static String workingDir = System.getProperty("user.dir");
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
	fail("Not yet implemented");
}

@Test
public void testIsAntAtCell() {
	fail("Not yet implemented");
}

@Test
public void testIsAntAtIntArray() {
	fail("Not yet implemented");
}

@Test
public void testKillAnt() {
	fail("Not yet implemented");
}

@Test
public void testIsAntSurronded() {
	fail("Not yet implemented");
}

@Test
public void testGetMap() {
	fail("Not yet implemented");
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
