
package antgame.test;

import static org.junit.Assert.*;

import org.junit.Test;

import antgame.InvalidMarkerIdException;
import antgame.core.Ant;
import antgame.core.AntColour;
import antgame.core.Cell;
import antgame.core.Marker;
/**
 * Test unit for Cell class
 * @author George
 *
 */
public class CellTest {
	
	private Cell cell = new Cell(2, 3, "#");

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
		cell.antMoveIn(new Ant(1, 1, AntColour.RED, 1, null, null));
		assertTrue(cell.containsRedAnt());
		cell.antMoveIn(new Ant(1, 1, AntColour.BLACK, 1, null, null));
		assertFalse(cell.containsRedAnt());
	}

	@Test
	public void testContainsBlackAnt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSenseCheck() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetContent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPos() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsContainsFood() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfFoodParticles() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainsRock() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsClear() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainsAnt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAnt() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainsRedAntHill() {
		fail("Not yet implemented");
	}

	@Test
	public void testContainsBlackAntHill() {
		fail("Not yet implemented");
	}
}