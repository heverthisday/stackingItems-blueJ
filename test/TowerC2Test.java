package test;

import static org.junit.Assert.*;
import org.junit.Test;
import tower.Tower;

/**
 * Pruebas de unidad para Tower - Ciclo 2.
 */
public class TowerC2Test {

    @Test
    public void shouldCreateTowerWithNCups() {
        Tower t = new Tower(4, 100); // Ajustado al constructor con ancho y alto
        String[][] s = t.stackingItems();
        assertEquals(4, s.length);
        assertTrue(t.ok());
    }

    @Test
    public void shouldCreateCupsNumbered1ToN() {
        Tower t = new Tower(3, 100);
        String[][] s = t.stackingItems();
        assertEquals("cup", s[0][0]);
        assertEquals("1", s[0][1]);
        assertEquals("cup", s[1][0]);
        assertEquals("2", s[1][1]);
        assertEquals("cup", s[2][0]);
        assertEquals("3", s[2][1]);
    }

    @Test
    public void shouldReverseTower() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        t.reverseTower();
        String[][] s = t.stackingItems();
        assertEquals("3", s[0][1]);
        assertEquals("2", s[1][1]);
        assertEquals("1", s[2][1]);
    }

    @Test
    public void shouldReturnCorrectHeight() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        assertTrue(t.height() > 0);
    }

    @Test
    public void shouldReturnZeroHeightForEmptyTower() {
        Tower t = new Tower(10, 100);
        assertEquals(0, t.height());
    }

    @Test
    public void shouldNotPopLidFromEmptyTower() {
        Tower t = new Tower(10, 100);
        t.popLid();
        assertFalse(t.ok());
    }
}