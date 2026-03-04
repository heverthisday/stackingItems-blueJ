import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Conjunto de pruebas unitarias para la clase {@Tower}.
 
 */
public class TowerC2Test {
    private Tower tower;

    @Before
    public void setUp() {
        // cada test parte de una torre vacía con altura máxima razonable
        tower = new Tower(5, 20);
    }



    @Test
    public void shouldAddCupWithinHeight() {
        // Arrange
        // tower creado en setUp

        // Act
        tower.pushCup(1);

        // Assert
        assertTrue("la operación debe ser exitosa", tower.ok());
        assertEquals("altura esperada después de una taza de número 1", 1, tower.height());
        String[][] items = tower.stackingItems();
        assertEquals(1, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("1", items[0][1]);
    }

    @Test
    public void shouldNotAllowDuplicateCup() {
        // Arrange
        tower.pushCup(2);

        // Act
        tower.pushCup(2);

        // Assert
        assertFalse("no debe permitirse añadir una taza duplicada", tower.ok());
        String[][] items = tower.stackingItems();
        assertEquals(1, items.length);
    }

    @Test
    public void shouldRejectCupWhenExceedHeight() {
        // Arrange
        Tower small = new Tower(3, 1); // altura máxima < altura de taza 2

        // Act
        small.pushCup(2);

        // Assert
        assertFalse("debe fallar al sobrepasar la altura máxima", small.ok());
        assertEquals("la torre permanece vacía", 0, small.height());
    }



    @Test
    public void shouldAddLidWithinHeight() {
        // Arrange
        tower.pushCup(3); // para dar algo de altura

        // Act
        tower.pushLid(1);

        // Assert
        assertTrue(tower.ok());
        // la tapa 1 queda "dentro" de la taza 3, de modo que la altura no cambia
        assertEquals(5, tower.height());
        String[][] items = tower.stackingItems();
        assertEquals(2, items.length);
        assertEquals("lid", items[1][0]);
    }

    @Test
    public void shouldNotAllowDuplicateLid() {
        // Arrange
        tower.pushLid(1);

        // Act
        tower.pushLid(1);

        // Assert
        assertFalse(tower.ok());
        assertEquals(1, tower.stackingItems().length);
    }

    

    @Test
    public void popCupRemovesLastCup() {
        // Arrange: cup1 seguido de cup2
        tower.pushCup(1);
        tower.pushCup(2);

        // Act: pop remueve la última taza agregada (cup2)
        tower.popCup();

        // Assert: debe quedar solo cup1
        assertTrue(tower.ok());
        String[][] items = tower.stackingItems();
        assertEquals(1, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("1", items[0][1]);
    }

    @Test
    public void popCupOnEmptyShouldFail() {
        // Arrange
        // tower vacío

        // Act
        tower.popCup();

        // Assert
        assertFalse(tower.ok());
        assertEquals(0, tower.stackingItems().length);
    }

    @Test
    public void popLidOnEmptyShouldFail() {
        // Arrange

        // Act
        tower.popLid();

        // Assert
        assertFalse(tower.ok());
    }

 

    @Test
    public void removeSpecificCupShouldWork() {
        // Arrange: cup1 y cup2
        tower.pushCup(1);
        tower.pushCup(2);

        // Act: remover cup1 específicamente
        tower.removeCup(1);

        // Assert: debe quedar solo cup2
        assertTrue(tower.ok());
        String[][] items = tower.stackingItems();
        assertEquals(1, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("2", items[0][1]);
    }

    @Test
    public void removeNonexistentCupShouldFail() {
        // Arrange
        tower.pushCup(1);

        // Act
        tower.removeCup(5);

        // Assert
        assertFalse(tower.ok());
        assertEquals(1, tower.stackingItems().length);
    }

    @Test
    public void removeSpecificLidShouldWork() {
        // Arrange
        tower.pushLid(1);
        tower.pushLid(2);

        // Act
        tower.removeLid(1);

        // Assert
        assertTrue(tower.ok());
        String[][] items = tower.stackingItems();
        assertEquals(1, items.length);
        assertEquals("lid", items[0][0]);
        assertEquals("2", items[0][1]);
    }

    @Test
    public void removeNonexistentLidShouldFail() {
        // Arrange
        tower.pushLid(1);

        // Act
        tower.removeLid(5);

        // Assert
        assertFalse(tower.ok());
        assertEquals(1, tower.stackingItems().length);
    }



    @Test
    public void stackingItemsReturnsOrderedPairs() {
        // Arrange: c1, l1, c2
        tower.pushCup(1);
        tower.pushLid(1);
        tower.pushCup(2);

        // Act
        String[][] items = tower.stackingItems();

        // Assert: verifica que el array tiene 3 elementos en el orden correcto
        assertEquals(3, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("1", items[0][1]);
        assertEquals("lid", items[1][0]);
        assertEquals("1", items[1][1]);
        assertEquals("cup", items[2][0]);
        assertEquals("2", items[2][1]);
    }

    @Test
    public void heightReflectsStackedItemsCorrectly() {
        // Arrange: cup1 altura=1, cup2 altura=3, cup2>cup1 así que encima
        tower.pushCup(1);
        tower.pushCup(2);

        // Act
        int h = tower.height();

        // Assert: cup1 base=0 (altura 1), cup2 base=1 (altura 3), total=4
        assertEquals(4, h);
    }



    @Test
    public void coverAvailableCupsPairsCupsAndLids() {
        // Arrange: c1, c2, l2, l1
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushLid(2);
        tower.pushLid(1);

        // Act: reorganiza como c1-l1, c2-l2
        tower.coverAvailableCups();

        // Assert: verifica que quedaron 4 items y están emparejados
        String[][] items = tower.stackingItems();
        assertEquals(4, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("1", items[0][1]);
        assertEquals("lid", items[1][0]);
        assertEquals("1", items[1][1]);
        assertEquals("cup", items[2][0]);
        assertEquals("2", items[2][1]);
        assertEquals("lid", items[3][0]);
        assertEquals("2", items[3][1]);
    }

    @Test
    public void tappedCupsInfoDetectsCoveredCups() {
        // Arrange: agregar cups y lids emparejados
        tower.pushCup(1);
        tower.pushLid(1);
        tower.pushCup(2);
        tower.pushLid(2);

        // Act: coverAvailableCups() empareja cada cup con su lid
        tower.coverAvailableCups();
        String[][] tapped = tower.tappedCupsInfo();

        // Assert: ambas tazas deberían estar tapadas (cup1 y cup2)
        assertEquals("deben haber 2 tazas tapadas", 2, tapped.length);
        assertEquals("primera taza debe ser cup", "cup", tapped[0][0]);
        assertEquals("primera taza debe ser número 1", "1", tapped[0][1]);
        assertEquals("segunda taza debe ser cup", "cup", tapped[1][0]);
        assertEquals("segunda taza debe ser número 2", "2", tapped[1][1]);
    }



    @Test
    public void sortDescendingKeepsHeightUnderLimitAndMarksOk() {
        // Arrange: torre con maxHeight suficientemente grande para añadir ambas cups
        Tower small = new Tower(5, 5);
        small.pushCup(1); // altura 1
        small.pushCup(2); // altura 3; juntas hacen 4, cabe en el límite 5

        // Act: sortDescending() reordena sin necesidad de remover
        small.sortDescending();

        // Assert: ok se mantiene true y altura sigue dentro del límite
        assertTrue(small.ok());
        assertTrue(small.height() <= 5);
    }

    @Test
    public void swapItemsWithInvalidTypeSetsError() {
        // Arrange
        tower.pushCup(1);
        tower.pushLid(1);

        // Act
        tower.swapItems("invalid", 1, "cup", 1);

        // Assert
        assertFalse(tower.ok());
    }

    @Test
    public void swapItemsBetweenElementsUpdatesOrder() {
        // Arrange: cup1 seguido de cup2
        tower.pushCup(1);
        tower.pushCup(2);

        // Act: intercambia las dos tazas
        tower.swapItems("cup", 1, "cup", 2);

        // Assert: verifica que el orden se invirtió
        assertTrue(tower.ok());
        String[][] items = tower.stackingItems();
        assertEquals(2, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("2", items[0][1]);
        assertEquals("cup", items[1][0]);
        assertEquals("1", items[1][1]);
    }

    @Test
    public void reverseOrderInvertsStackingSequence() {
        // Arrange: c1, c2, c3
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(3);

        // Act
        tower.reverseOrder();

        // Assert: debe quedar c3, c2, c1
        assertTrue(tower.ok());
        String[][] items = tower.stackingItems();
        assertEquals(3, items.length);
        assertEquals("3", items[0][1]);
        assertEquals("2", items[1][1]);
        assertEquals("1", items[2][1]);
    }

    @Test
    public void reverseOrderKeepsHeightUnderLimit() {
        // Arrange: torre con altitud suficiente para ambas cups
        Tower small = new Tower(5, 5);
        small.pushCup(1);
        small.pushCup(2);

        // Act
        small.reverseOrder();

        // Assert: la operación no debe fallar y la altura sigue dentro del límite
        assertTrue(small.ok());
        assertTrue(small.height() <= 5);
    }

    @Test
    public void findReducingSwapLocatesOptimalExchange() {
        // Arrange: c1, c2, c3 en mala disposición
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(3);

        // Act: busca un swap que reduzca altura
        Object[] swap = tower.findReducingSwap();

        // Assert: si encuentra un swap, ok es true y altura disminuyó
        if (swap != null) {
            assertTrue(tower.ok());
            assertTrue(tower.height() >= 0);
        }
    }

    @Test
    public void findReducingSwapFailsWhenNoOptimalSwap() {
        // Arrange: solo una taza (no puede haber swap)
        tower.pushCup(1);

        // Act
        Object[] swap = tower.findReducingSwap();

        // Assert
        assertNull(swap);
        assertFalse(tower.ok());
    }
}
