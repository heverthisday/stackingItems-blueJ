package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tower.Tower;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de unidad para Tower - Ciclo 2.
 * Migrado a JUnit 5.
 */
public class TowerC2Test {

    private Tower tower;

    @BeforeEach
    void setUp() {
        // Tower vacía reutilizada en cada test
        tower = new Tower(10, 100);
    }

    // --- Constructor con N tazas ---

    @Test
    void shouldCreateTowerWithNCups() {
        Tower t = new Tower(4, 100);
        String[][] s = t.stackingItems();
        assertEquals(4, s.length);
        assertTrue(t.ok());
    }

    @Test
    void shouldCreateCupsNumbered1ToN() {
        Tower t = new Tower(3, 100);
        String[][] s = t.stackingItems();
        assertEquals("cup", s[0][0]);
        assertEquals("1",   s[0][1]);
        assertEquals("cup", s[1][0]);
        assertEquals("2",   s[1][1]);
        assertEquals("cup", s[2][0]);
        assertEquals("3",   s[2][1]);
    }

    // --- pushCup / popCup ---

    @Test
    void shouldPushCupAndIncreaseSize() {
        tower.pushCup(1);
        assertEquals(1, tower.stackingItems().length);
        assertTrue(tower.ok());
    }

    @Test
    void shouldNotPushDuplicateCup() {
        tower.pushCup(2);
        tower.pushCup(2); // duplicado
        assertFalse(tower.ok());
    }

    @Test
    void shouldPopLastCup() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.popCup();
        assertEquals(1, tower.stackingItems().length);
        assertTrue(tower.ok());
    }

    @Test
    void shouldFailPopCupOnEmptyTower() {
        tower.popCup();
        assertFalse(tower.ok());
    }

    // --- pushLid / popLid ---

    @Test
    void shouldPushLid() {
        tower.pushCup(1);
        tower.pushLid(1);
        assertEquals(2, tower.stackingItems().length);
        assertTrue(tower.ok());
    }

    @Test
    void shouldNotPushDuplicateLid() {
        tower.pushCup(1);
        tower.pushLid(1);
        tower.pushLid(1); // duplicado
        assertFalse(tower.ok());
    }

    @Test
    void shouldPopLid() {
        tower.pushCup(1);
        tower.pushLid(1);
        tower.popLid();
        assertEquals(1, tower.stackingItems().length);
        assertTrue(tower.ok());
    }

    @Test
    void shouldFailPopLidFromEmptyTower() {
        tower.popLid();
        assertFalse(tower.ok());
    }

    // --- height ---

    @Test
    void shouldReturnZeroHeightForEmptyTower() {
        assertEquals(0, tower.height());
    }

    @Test
    void shouldReturnPositiveHeightAfterPush() {
        tower.pushCup(1);
        assertTrue(tower.height() > 0);
    }

    @Test
    void shouldNotExceedMaxHeight() {
        Tower t = new Tower(3, 5);
        t.pushCup(3); // altura de cup 3 = 5
        t.pushCup(1); // no cabe
        assertFalse(t.ok());
    }

    // --- removeCup / removeLid ---

    @Test
    void shouldRemoveSpecificCup() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.removeCup(1);
        String[][] s = tower.stackingItems();
        assertEquals(1, s.length);
        assertEquals("2", s[0][1]);
        assertTrue(tower.ok());
    }

    @Test
    void shouldFailRemoveNonExistentCup() {
        tower.removeCup(5);
        assertFalse(tower.ok());
    }

    @Test
    void shouldRemoveSpecificLid() {
        tower.pushCup(1);
        tower.pushLid(1);
        tower.removeLid(1);
        assertEquals(1, tower.stackingItems().length);
        assertTrue(tower.ok());
    }

    // --- swap ---

    @Test
    void shouldSwapTwoCups() {
        tower.pushCup(1);
        tower.pushCup(2);
        String[][] before = tower.stackingItems();
        int idx1 = findIndex(before, "cup", "1");
        int idx2 = findIndex(before, "cup", "2");

        tower.swap("cup", 1, "cup", 2);

        String[][] after = tower.stackingItems();
        assertTrue(tower.ok());
        assertEquals("1", after[idx2][1]);
        assertEquals("2", after[idx1][1]);
    }

    @Test
    void shouldFailSwapWithNullType() {
        tower.pushCup(1);
        tower.swap(null, 1, "cup", 1);
        assertFalse(tower.ok());
    }

    @Test
    void shouldFailSwapNonExistentItems() {
        tower.pushCup(1);
        tower.swap("cup", 1, "cup", 99);
        assertFalse(tower.ok());
    }

    // --- reverseTower ---

    @Test
    void shouldReverseTower() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(3);
        tower.reverseTower();
        String[][] s = tower.stackingItems();
        assertEquals("3", s[0][1]);
        assertEquals("2", s[1][1]);
        assertEquals("1", s[2][1]);
    }

    // --- orderTower ---

    @Test
    void shouldOrderTowerDescending() {
        tower.pushCup(1);
        tower.pushCup(3);
        tower.pushCup(2);
        tower.orderTower();
        String[][] s = tower.stackingItems();
        int n0 = Integer.parseInt(s[0][1]);
        int n1 = Integer.parseInt(s[1][1]);
        int n2 = Integer.parseInt(s[2][1]);
        assertTrue(n0 >= n1);
        assertTrue(n1 >= n2);
    }

    // --- cover ---

    @Test
    void shouldCoverMatchingCups() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushLid(1);
        tower.cover();
        assertTrue(tower.ok());
    }

    // --- stackingItems ---

    @Test
    void shouldReturnEmptyArrayForEmptyTower() {
        assertEquals(0, tower.stackingItems().length);
    }

    @Test
    void shouldReturnCorrectTypeAndNumber() {
        tower.pushCup(3);
        tower.pushLid(3);
        String[][] s = tower.stackingItems();
        assertEquals(2, s.length);
        boolean hasCup3 = false, hasLid3 = false;
        for (String[] row : s) {
            if (row[0].contains("cup") && row[1].equals("3")) hasCup3 = true;
            if (row[0].equals("lid")  && row[1].equals("3")) hasLid3 = true;
        }
        assertTrue(hasCup3);
        assertTrue(hasLid3);
    }

    // --- lidedCups ---

    @Test
    void shouldDetectLidedCup() {
        tower.pushCup(1);
        tower.pushLid(1);
        int[] lided = tower.lidedCups();
        assertEquals(1, lided.length);
        assertEquals(1, lided[0]);
    }

    @Test
    void shouldReturnEmptyWhenNoLidedCups() {
        tower.pushCup(1);
        assertEquals(0, tower.lidedCups().length);
    }

    // --- swapToReduce ---

    @Test
    void shouldReturnNullWhenNoReducingSwapExists() {
        tower.pushCup(1); // única pieza, no hay swap posible
        assertNull(tower.swapToReduce());
        assertFalse(tower.ok());
    }

    // --- tazas especiales ---

    @Test
    void shouldPushOpenerCupAndRemoveLids() {
        tower.pushCup(1);
        tower.pushLid(1);
        tower.pushCup("opener", 2);
        assertTrue(tower.ok());
        // la opener elimina todas las tapas
        String[][] s = tower.stackingItems();
        for (String[] row : s) {
            assertNotEquals("lid", row[0]);
        }
    }

    @Test
    void shouldPushHierarchicalCupAndSink() {
        tower.pushCup(1);
        tower.pushCup("hierarchical", 3); // 3 > 1, se hunde
        assertTrue(tower.ok());
    }

    @Test
    void shouldNotPushFearfulLidIfCupIsBigger() {
        tower.pushCup(5);
        tower.pushLid("fearful", 1); // cup 5 > lid 1 → miedo
        assertFalse(tower.ok());
    }

    @Test
    void shouldPushFearfulLidIfCupIsSmaller() {
        tower.pushCup(1);
        tower.pushLid("fearful", 5); // cup 1 < lid 5 → entra
        assertTrue(tower.ok());
    }

    @Test
    void shouldPushCrazyLidWithoutError() {
        tower.pushCup(1);
        tower.pushLid("crazy", 1);
        assertTrue(tower.ok());
    }

    // --- utilidad ---

    private int findIndex(String[][] s, String type, String num) {
        for (int i = 0; i < s.length; i++) {
            if (s[i][0].equals(type) && s[i][1].equals(num)) return i;
        }
        return -1;
    }
}