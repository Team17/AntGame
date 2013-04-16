
package antgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import antgame.InvalidMarkerIdException;
import antgame.core.Ant;
import antgame.core.AntBrain;
import antgame.core.AntColour;
import antgame.core.BrainState;
import antgame.core.Cell;
import antgame.core.Marker;
import antgame.core.SenseCondition;
/**
 * Test unit for Cell class
 * @author George
 *
 */
public class CellTest {
	
	private Cell cell = new Cell(2, 3, "#");
	private Cell cell2 = new Cell(1, 2, "#");
	private static String workingDir = System.getProperty("user.dir");
	private AntBrain redAntBrain = new AntBrain(workingDir + "\\files\\TestBrainGood.brain",AntColour.RED);
	private AntBrain blackAntBrain = new AntBrain(workingDir + "\\files\\TestBrainGood.brain",AntColour.BLACK);
	private Ant redAnt = new Ant(1, 0, AntColour.RED, 0, cell, redAntBrain);
	private Ant blackAnt = new Ant(1, 1, AntColour.BLACK, 1, cell2, blackAntBrain);
	

	@Test
	public void testCell() {
		assertNotNull(cell);
	}

	@Test
	public void testIsInteger() {
		assertTrue(cell.isInteger("2"));
		assertFalse(cell.isInteger("two2"));
	}

	@Test
	public void testAntMoveIn() {
		assertFalse(cell.containsAnt());
		//add ant to the cell
		cell.antMoveIn(null);
		assertTrue(cell.containsAnt());
	}

	@Test
	public void testAntMoveOut() {
		//add ant
		cell.antMoveIn(null);
		assertTrue(cell.containsAnt());
		//remove ant
		cell.antMoveOut();
		assertFalse(cell.containsAnt());
	}

	@Test
	public void testAddFood() {
		assertEquals(0, cell.getNumberOfFoodParticles());
		cell.addFood();
		assertEquals(1, cell.getNumberOfFoodParticles());
	}

	@Test
	public void testAddNumFood() {
		cell.addNumFood(0);
		assertEquals(0, cell.getNumberOfFoodParticles());
		cell.addNumFood(5);
		assertEquals(5, cell.getNumberOfFoodParticles());
		cell.addNumFood(-5);
		assertEquals(5, cell.getNumberOfFoodParticles());
	}

	@Test
	public void testRemoveFood() {
		//add food
		cell.addNumFood(0);
		cell.addNumFood(2);
		assertEquals(2, cell.getNumberOfFoodParticles());
		//remove food
		cell.removeFood();
		assertEquals(1, cell.getNumberOfFoodParticles());
	}

	@Test
	public void testSetMarker() throws InvalidMarkerIdException {
		Marker marker = new Marker(1, AntColour.RED);
		assertFalse(cell.checkMarker(marker));
		//add marker
		cell.setMarker(marker);
		assertTrue(cell.checkMarker(marker));
	}

	@Test
	public void testClearMarker() {
		cell.setMarker(null);
		assertTrue(cell.checkMarker(null));
		//remove marker
		cell.clearMarker(null);
		assertFalse(cell.checkMarker(null));
	}

	@Test
	public void testCheckMarker() {
		cell.setMarker(null);
		assertTrue(cell.checkMarker(null));
	}

	@Test
	public void testCheckAnyMarkerAt() throws InvalidMarkerIdException {
		cell.setMarker(new Marker(2, AntColour.RED));
		assertTrue(cell.checkAnyMarkerAt(AntColour.RED));
	}

	@Test
	public void testContainsRedAnt() {
		cell.antMoveIn(redAnt);
		assertTrue(cell.containsRedAnt());
		cell.antMoveIn(blackAnt);
		assertFalse(cell.containsRedAnt());
	}

	@Test
	public void testContainsBlackAnt() {
		cell.antMoveIn(redAnt);
		assertEquals("red", cell.getAnt().getColour().toString().toLowerCase());
		cell.antMoveOut();
		cell.antMoveIn(blackAnt);
		assertEquals("black", cell.getAnt().getColour().toString().toLowerCase());
	}

	@Test
	public void testSenseCheck() throws InvalidMarkerIdException {
		assertFalse(cell.senseCheck(redAnt, SenseCondition.FRIEND, new Marker(1, AntColour.RED)));
	}

	@Test
	public void testGetContent() {
		cell.antMoveIn(redAnt);
		assertNotNull(cell.getContent());
	}

	@Test
	public void testGetPos() {
		cell.setPos(2, 15);
		int[] temp = new int[2];
		temp[0] = 2;
		temp[1] = 15;
		for(int i = 0; i < temp.length; i++){
			assertEquals(temp[i], cell.getPos()[i]);
		}
	}

	@Test
	public void testIsContainsFood() {
		cell.addNumFood(0);
		assertFalse(cell.isContainsFood());
		cell.addNumFood(3);
		assertTrue(cell.isContainsFood());
	}

	@Test
	public void testGetNumberOfFoodParticles() {
		cell.addNumFood(0);
		assertEquals(0, cell.getNumberOfFoodParticles());
		cell.addNumFood(2);
		assertEquals(2, cell.getNumberOfFoodParticles());
	}

	@Test
	public void testContainsRock() {
		cell.clearRock();
		assertFalse(cell.containsRock());
		cell.setRock();
		assertTrue(cell.containsRock());
	}

	@Test
	public void testIsClear() {
		cell.antMoveIn(redAnt);
		assertFalse(cell.isClear());
		cell.antMoveOut();
		assertTrue(cell.isClear());
	}

	@Test
	public void testContainsAnt() {
		cell.antMoveOut();
		assertFalse(cell.containsAnt());
		cell.antMoveIn(redAnt);
		assertTrue(cell.containsAnt());
	}

	@Test
	public void testGetAnt() {
		cell.antMoveIn(redAnt);
		assertEquals("red", cell.getAnt().getColour().toString().toLowerCase());
	}

	@Test
	public void testContainsRedAntHill() {
		cell.clearAnthill();
		assertFalse(cell.containsRedAntHill());
		cell.setAnthill(AntColour.RED);
		assertTrue(cell.containsRedAntHill());
	}

	@Test
	public void testContainsBlackAntHill() {
		cell.clearAnthill();
		assertFalse(cell.containsBlackAntHill());
		cell.setAnthill(AntColour.BLACK);
		assertTrue(cell.containsBlackAntHill());
	}
}