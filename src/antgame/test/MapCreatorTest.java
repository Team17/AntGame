package antgame.test;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.HashSet;

import org.junit.Test;

import antgame.core.Cell;
import antgame.core.Map;
import antgame.core.MapCreator;

public class MapCreatorTest {
	
	@Test
	public void testGetRandomMap() {
		assertNotNull(MapCreator.getRandomMap());
	}

	@Test
	public void testRandomPoint() {
		assertNotNull(MapCreator.randomPoint(45, 23));
	}

	@Test
	public void testCanDrawElementHere() {
		assertTrue(MapCreator.canDrawElementHere(new Map(new Cell[2][2]), new HashSet<Point>(), 2, 1));
	}

	@Test
	public void testInvertCells() {
		Cell[][] temp = new Cell[2][2];
		temp[0][0] = new Cell(2,3);
		temp[0][1] = new Cell(3,4);
		temp[1][0] = new Cell(0,8);
		temp[1][1] = new Cell(5,6);
		
		Cell[][] temp2 = new Cell[2][2];
		temp[0][0] = temp[0][0];
		temp[0][1] = temp[1][0];
		temp[1][0] = temp[0][1];
		temp[1][1] = temp[1][1];
		
		assertArrayEquals(temp2, MapCreator.invertCells(temp));
	}

	@Test
	public void testGetAnthillPoints() {
		assertNotNull(MapCreator.getAnthillPoints());
	}

	@Test
	public void testGetFoodBlobPoints() {
		assertNotNull(MapCreator.getFoodBlobPoints());
	}

}
