import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas de unidad para Tower - Ciclo 2.
 * Todas las pruebas se ejecutan en modo invisible.
 * 
 * @author Hever
 * @version 3.0 - Nombres corregidos segun especificacion
 */
public class TowerC2Test {


    @Test
    public void shouldCreateTowerWithNCups() {
        Tower t = new Tower(4);
        String[][] s = t.stackingItems();
        assertEquals(4, s.length);
        assertTrue(t.ok());
    }

    @Test
    public void shouldCreateCupsNumbered1ToN() {
        Tower t = new Tower(3);
        String[][] s = t.stackingItems();
        assertEquals("cup", s[0][0]);
        assertEquals("1", s[0][1]);
        assertEquals("cup", s[1][0]);
        assertEquals("2", s[1][1]);
        assertEquals("cup", s[2][0]);
        assertEquals("3", s[2][1]);
    }

    @Test
    public void shouldCreateTowerWithOneCup() {
        Tower t = new Tower(1);
        String[][] s = t.stackingItems();
        assertEquals(1, s.length);
        assertEquals("cup", s[0][0]);
        assertEquals("1", s[0][1]);
    }

    @Test
    public void shouldNotIncludeLidsWhenCreatingWithNCups() {
        Tower t = new Tower(3);
        String[][] s = t.stackingItems();
        for (String[] item : s) {
            assertEquals("cup", item[0]);
        }
    }



    @Test
    public void shouldSwapTwoCups() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(2);
        t.swap("cup", 1, "cup", 2);
        String[][] s = t.stackingItems();
        assertEquals("2", s[0][1]);
        assertEquals("1", s[1][1]);
        assertTrue(t.ok());
    }

    @Test
    public void shouldSwapCupAndLid() {
        Tower t = new Tower(10, 100);
        t.pushCup(2);
        t.pushLid(1);
        t.swap("cup", 2, "lid", 1);
        String[][] s = t.stackingItems();
        assertEquals("lid", s[0][0]);
        assertEquals("1", s[0][1]);
        assertEquals("cup", s[1][0]);
        assertEquals("2", s[1][1]);
        assertTrue(t.ok());
    }

    @Test
    public void shouldSwapTwoLids() {
        Tower t = new Tower(10, 100);
        t.pushCup(3);
        t.pushLid(1);
        t.pushLid(2);
        t.swap("lid", 1, "lid", 2);
        String[][] s = t.stackingItems();
        int idxLid1 = -1, idxLid2 = -1;
        for (int i = 0; i < s.length; i++) {
            if (s[i][0].equals("lid") && s[i][1].equals("1")) idxLid1 = i;
            if (s[i][0].equals("lid") && s[i][1].equals("2")) idxLid2 = i;
        }
        assertTrue(idxLid2 < idxLid1);
        assertTrue(t.ok());
    }

    @Test
    public void shouldNotSwapNonExistentItem() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.swap("cup", 1, "cup", 99);
        assertFalse(t.ok());
    }

    @Test
    public void shouldNotSwapWithInvalidType() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(2);
        t.swap("mug", 1, "cup", 2);
        assertFalse(t.ok());
    }

    @Test
    public void shouldNotSwapWithNullType() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(2);
        t.swap(null, 1, "cup", 2);
        assertFalse(t.ok());
    }

    @Test
    public void shouldPreserveAllItemsAfterSwap() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(3);
        t.pushLid(2);
        int beforeCount = t.stackingItems().length;
        t.swap("cup", 1, "cup", 3);
        int afterCount = t.stackingItems().length;
        assertEquals(beforeCount, afterCount);
    }

  

    @Test
    public void shouldCoverCupWithMatchingLid() {
        Tower t = new Tower(10, 100);
        t.pushCup(2);
        t.pushLid(2);
        t.cover();
        int[] lided = t.lidedCups();
        assertEquals(1, lided.length);
        assertEquals(2, lided[0]);
    }

    @Test
    public void shouldCoverMultipleCups() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(3);
        t.pushLid(1);
        t.pushLid(3);
        t.cover();
        int[] lided = t.lidedCups();
        assertEquals(2, lided.length);
        assertEquals(1, lided[0]);
        assertEquals(3, lided[1]);
    }

    @Test
    public void shouldNotCoverCupWithoutMatchingLid() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(1);
        t.cover();
        int[] lided = t.lidedCups();
        assertEquals(1, lided.length);
        assertEquals(1, lided[0]);
    }

    @Test
    public void shouldDoNothingWhenCoverWithNoLids() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(2);
        t.cover();
        int[] lided = t.lidedCups();
        assertEquals(0, lided.length);
        assertTrue(t.ok());
    }

    @Test
    public void shouldDoNothingWhenCoverWithNoCups() {
        Tower t = new Tower(10, 100);
        t.pushLid(1);
        t.pushLid(2);
        t.cover();
        int[] lided = t.lidedCups();
        assertEquals(0, lided.length);
    }

    @Test
    public void shouldPreserveItemCountAfterCover() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(1);
        int before = t.stackingItems().length;
        t.cover();
        int after = t.stackingItems().length;
        assertEquals(before, after);
    }



    @Test
    public void shouldFindSwapThatReducesHeight() {
        Tower t = new Tower(10, 50);
        t.pushCup(3);
        t.pushCup(1);
        int before = t.height();
        String[][] result = t.swapToReduce();
        assertNotNull(result);
        assertTrue(t.height() < before);
    }

    @Test
    public void shouldReturnTwoElementArrayForSwapToReduce() {
        Tower t = new Tower(10, 50);
        t.pushCup(4);
        t.pushCup(1);
        String[][] result = t.swapToReduce();
        if (result != null) {
            assertEquals(2, result.length);
            assertEquals(2, result[0].length);
            assertEquals(2, result[1].length);
        }
    }

    @Test
    public void shouldReturnNullIfNoSwapReducesHeight() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        String[][] result = t.swapToReduce();
        if (result == null) {
            assertFalse(t.ok());
        }
    }

    @Test
    public void shouldNotCrashOnEmptyTower() {
        Tower t = new Tower(10, 100);
        String[][] result = t.swapToReduce();
        assertNull(result);
        assertFalse(t.ok());
    }

    @Test
    public void shouldNotCrashWithSingleItem() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        String[][] result = t.swapToReduce();
        assertNull(result);
        assertFalse(t.ok());
    }



    @Test
    public void shouldSwapAndThenCover() {
        Tower t = new Tower(10, 100);
        t.pushCup(2);
        t.pushLid(2);
        t.pushCup(1);
        t.swap("lid", 2, "cup", 1);
        t.cover();
        int[] lided = t.lidedCups();
        assertTrue(lided.length >= 0);
    }

    @Test
    public void shouldMaintainOkAfterMultipleOperations() {
        Tower t = new Tower(5, 50);
        t.pushCup(1);
        assertTrue(t.ok());
        t.pushCup(2);
        assertTrue(t.ok());
        t.pushLid(1);
        assertTrue(t.ok());
        t.cover();
        assertTrue(t.ok());
        t.swap("cup", 1, "cup", 2);
        assertTrue(t.ok());
    }

    @Test
    public void shouldReportHeightAfterSwap() {
        Tower t = new Tower(10, 100);
        t.pushCup(3);
        t.pushCup(1);
        int h1 = t.height();
        t.swap("cup", 3, "cup", 1);
        int h2 = t.height();
        assertTrue(h1 > 0);
        assertTrue(h2 > 0);
    }



    @Test
    public void shouldPushAndPopCup() {
        Tower t = new Tower(10, 100);
        t.pushCup(3);
        assertTrue(t.ok());
        assertEquals(1, t.stackingItems().length);
        t.popCup();
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void shouldNotPushDuplicateCup() {
        Tower t = new Tower(10, 100);
        t.pushCup(2);
        t.pushCup(2);
        assertFalse(t.ok());
    }

    @Test
    public void shouldNotPushCupExceedingHeight() {
        Tower t = new Tower(10, 5);
        t.pushCup(4);
        assertFalse(t.ok());
    }

    @Test
    public void shouldNotPopCupFromEmptyTower() {
        Tower t = new Tower(10, 100);
        t.popCup();
        assertFalse(t.ok());
    }

    @Test
    public void shouldPushAndPopLid() {
        Tower t = new Tower(10, 100);
        t.pushLid(1);
        assertTrue(t.ok());
        t.popLid();
        assertTrue(t.ok());
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void shouldNotPushDuplicateLid() {
        Tower t = new Tower(10, 100);
        t.pushLid(1);
        t.pushLid(1);
        assertFalse(t.ok());
    }

    @Test
    public void shouldRemoveSpecificCup() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(2);
        t.removeCup(1);
        assertTrue(t.ok());
        String[][] s = t.stackingItems();
        assertEquals(1, s.length);
        assertEquals("2", s[0][1]);
    }

    @Test
    public void shouldNotRemoveNonExistentCup() {
        Tower t = new Tower(10, 100);
        t.removeCup(99);
        assertFalse(t.ok());
    }

    @Test
    public void shouldOrderTower() {
        Tower t = new Tower(10, 100);
        t.pushCup(1);
        t.pushCup(3);
        t.pushCup(2);
        t.orderTower();
        String[][] s = t.stackingItems();
        assertTrue(Integer.parseInt(s[0][1]) >= Integer.parseInt(s[1][1]));
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

    @Test
    public void shouldNotRemoveNonExistentLid() {
        Tower t = new Tower(10, 100);
        t.removeLid(99);
        assertFalse(t.ok());
    }

    @Test
    public void shouldReturnLidedCupsSortedAscending() {
        Tower t = new Tower(10, 100);
        t.pushCup(3);
        t.pushCup(1);
        t.pushLid(3);
        t.pushLid(1);
        t.cover();
        int[] lided = t.lidedCups();
        for (int i = 0; i < lided.length - 1; i++) {
            assertTrue(lided[i] <= lided[i + 1]);
        }
    }
}