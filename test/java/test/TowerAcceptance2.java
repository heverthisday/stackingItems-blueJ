package test;

import org.junit.jupiter.api.Test;
import tower.Tower;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba de aceptación 2 — Swap de cups.
 * Convertida de main() a JUnit 5.
 */
public class TowerAcceptance2 {

    @Test
    void acceptance2_swapCupsExchangesPositions() {
        Tower t = new Tower(5, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        t.pushCup(4);

        String[][] before = t.stackingItems();
        assertEquals(4, before.length, "Se esperaban 4 elementos antes del swap");

        int origIdx1 = findIndex(before, "cup", "1");
        int origIdx4 = findIndex(before, "cup", "4");
        assertNotEquals(-1, origIdx1, "Cup 1 no encontrada antes del swap");
        assertNotEquals(-1, origIdx4, "Cup 4 no encontrada antes del swap");

        t.swap("cup", 1, "cup", 4);
        assertTrue(t.ok(), "El swap reportó error");

        String[][] after = t.stackingItems();
        int newIdx1 = findIndex(after, "cup", "1");
        int newIdx4 = findIndex(after, "cup", "4");

        assertEquals(origIdx4, newIdx1, "Cup 1 debería estar en la posición de Cup 4");
        assertEquals(origIdx1, newIdx4, "Cup 4 debería estar en la posición de Cup 1");
    }

    @Test
    void acceptance2_swapLidsExchangesPositions() {
        Tower t = new Tower(5, 20);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid(1);
        t.pushLid(2);

        String[][] before = t.stackingItems();
        int origIdxL1 = findIndex(before, "lid", "1");
        int origIdxL2 = findIndex(before, "lid", "2");
        assertNotEquals(-1, origIdxL1);
        assertNotEquals(-1, origIdxL2);

        t.swap("lid", 1, "lid", 2);
        assertTrue(t.ok());

        String[][] after = t.stackingItems();
        assertEquals(origIdxL2, findIndex(after, "lid", "1"));
        assertEquals(origIdxL1, findIndex(after, "lid", "2"));
    }

    @Test
    void acceptance2_swapCupAndLid() {
        Tower t = new Tower(5, 20);
        t.pushCup(2);
        t.pushLid(2);

        t.swap("cup", 2, "lid", 2);
        assertTrue(t.ok());
        assertEquals(2, t.stackingItems().length);
    }

    private int findIndex(String[][] s, String type, String num) {
        for (int i = 0; i < s.length; i++) {
            if (s[i][0].equals(type) && s[i][1].equals(num)) return i;
        }
        return -1;
    }
}