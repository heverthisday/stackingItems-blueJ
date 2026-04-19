package test;

import org.junit.jupiter.api.Test;
import tower.Tower;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba de aceptación 2 — Swap de cups y lids.
 * Mejorada para ser mas robusta y aumentar cobertura.
 */
public class TowerAcceptance2 {

    // verifica que el swap entre dos cups intercambia posiciones correctamente
    @Test
    void acceptance2_swapCupsExchangesPositions() {
        Tower t = new Tower(5, 20);

        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        t.pushCup(4);

        String[][] before = t.stackingItems();
        assertEquals(4, before.length);

        int origIdx1 = findIndex(before, "cup", "1");
        int origIdx4 = findIndex(before, "cup", "4");

        assertTrue(origIdx1 >= 0);
        assertTrue(origIdx4 >= 0);

        t.swap("cup", 1, "cup", 4);

        // validar que la operacion no rompe la torre
        assertTrue(t.ok());

        String[][] after = t.stackingItems();

        int newIdx1 = findIndex(after, "cup", "1");
        int newIdx4 = findIndex(after, "cup", "4");

        // validacion principal de intercambio
        assertEquals(origIdx4, newIdx1);
        assertEquals(origIdx1, newIdx4);

        // validacion adicional de integridad
        assertEquals(4, after.length);
    }

    // verifica que el swap entre tapas intercambia posiciones correctamente
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

        assertTrue(origIdxL1 >= 0);
        assertTrue(origIdxL2 >= 0);

        t.swap("lid", 1, "lid", 2);

        assertTrue(t.ok());

        String[][] after = t.stackingItems();

        assertEquals(origIdxL2, findIndex(after, "lid", "1"));
        assertEquals(origIdxL1, findIndex(after, "lid", "2"));

        // validar que el tamaño no cambia
        assertEquals(before.length, after.length);
    }

    // verifica swap entre cup y lid sin perder elementos
    @Test
    void acceptance2_swapCupAndLid() {
        Tower t = new Tower(5, 20);

        t.pushCup(2);
        t.pushLid(2);

        int sizeBefore = t.stackingItems().length;

        t.swap("cup", 2, "lid", 2);

        assertTrue(t.ok());

        String[][] after = t.stackingItems();

        // no se deben perder elementos
        assertEquals(sizeBefore, after.length);

        // ambos elementos deben seguir existiendo
        assertTrue(findIndex(after, "cup", "2") >= 0);
        assertTrue(findIndex(after, "lid", "2") >= 0);
    }

    // caso adicional: swap con mismo elemento no debe alterar la torre
    @Test
    void acceptance2_swapSameElementDoesNothing() {
        Tower t = new Tower(5, 20);

        t.pushCup(1);

        String[][] before = t.stackingItems();

        t.swap("cup", 1, "cup", 1);

        String[][] after = t.stackingItems();

        assertEquals(before.length, after.length);
        assertEquals(findIndex(before, "cup", "1"), findIndex(after, "cup", "1"));
    }

    // caso adicional: swap invalido no debe romper estructura
    @Test
    void acceptance2_swapInvalidElementsDoesNotBreak() {
        Tower t = new Tower(5, 20);

        t.pushCup(1);

        t.swap("cup", 1, "cup", 99); // elemento inexistente

        // no asumimos ok true o false, solo que no rompe estructura
        assertNotNull(t.stackingItems());
    }

    // utilidad para buscar elementos en la torre
    private int findIndex(String[][] s, String type, String num) {
        for (int i = 0; i < s.length; i++) {
            if (s[i][0].equals(type) && s[i][1].equals(num)) return i;
        }
        return -1;
    }
}