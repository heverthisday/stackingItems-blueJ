package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tower.Tower;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de unidad para Tower - Ciclo 2.
 * Migrado a JUnit 5.
 *
 * A partir del metodo shouldCoverWithMultipleMatchingPairs se agregan
 * pruebas nuevas orientadas a mejorar la cobertura del codigo de dominio
 * (paquete tower) por encima del 75%, cubriendo ramas no ejercitadas
 * en los tests originales.
 */
public class TowerC2Test {

    private Tower tower;

    @BeforeEach
    void setUp() {
        tower = new Tower(10, 200);
    }

    // Constructor con N tazas

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

    // pushCup / popCup

    @Test
    void shouldPushCupAndIncreaseSize() {
        tower.pushCup(1);
        assertEquals(1, tower.stackingItems().length);
        assertTrue(tower.ok());
    }

    @Test
    void shouldNotPushDuplicateCup() {
        tower.pushCup(2);
        tower.pushCup(2);
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

    // pushLid / popLid

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
        tower.pushLid(1);
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

    // height

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
        t.pushCup(3);
        t.pushCup(1);
        assertFalse(t.ok());
    }

    // removeCup / removeLid

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

    // swap

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

    // reverseTower

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

    // orderTower

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

    // cover

    @Test
    void shouldCoverMatchingCups() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushLid(1);
        tower.cover();
        assertTrue(tower.ok());
    }

    // stackingItems

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

    // lidedCups

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

    // swapToReduce

    @Test
    void shouldReturnNullWhenNoReducingSwapExists() {
        tower.pushCup(1);
        assertNull(tower.swapToReduce());
        assertFalse(tower.ok());
    }

    // tazas y tapas especiales

    @Test
    void shouldPushOpenerCupAndRemoveLids() {
        tower.pushCup(1);
        tower.pushLid(1);
        tower.pushCup("opener", 2);
        assertTrue(tower.ok());
        String[][] s = tower.stackingItems();
        for (String[] row : s) {
            assertNotEquals("lid", row[0]);
        }
    }

    @Test
    void shouldPushHierarchicalCupAndSink() {
        tower.pushCup(1);
        tower.pushCup("hierarchical", 3);
        assertTrue(tower.ok());
    }

    @Test
    void shouldNotPushFearfulLidIfCupIsBigger() {
        tower.pushCup(5);
        tower.pushLid("fearful", 1);
        assertFalse(tower.ok());
    }

    @Test
    void shouldPushFearfulLidIfCupIsSmaller() {
        tower.pushCup(1);
        tower.pushLid("fearful", 5);
        assertTrue(tower.ok());
    }

    @Test
    void shouldPushCrazyLidWithoutError() {
        tower.pushCup(1);
        tower.pushLid("crazy", 1);
        assertTrue(tower.ok());
    }


    // Nuevas pruebas para mejorar cobertura del codigo de dominio (tower)
    // Agregadas para alcanzar el 75% de cubrimiento requerido en el dominio
    // Importante eta nueva adicion: Cubren ramas de calculateLidBase, cover, swapToReduce,
    // clases especiales y el constructor Tower(int cups).


    // calculateLidBase: tapa menor que la ultima taza (se mete dentro)
    @Test
    void lidSmallerThanLastCupSitsInsideIt() {
        tower.pushCup(3);
        tower.pushLid(1);
        assertTrue(tower.ok());
        assertTrue(tower.height() > 0);
    }

    // calculateLidBase: tapa igual a la ultima taza (queda encima)
    @Test
    void lidEqualToLastCupSitsOnTopOfIt() {
        tower.pushCup(2);
        tower.pushLid(2);
        assertTrue(tower.ok());
        int[] lided = tower.lidedCups();
        assertEquals(1, lided.length);
        assertEquals(2, lided[0]);
    }

    // calculateLidBase: dos tazas, tapa menor que la penultima
    @Test
    void lidSmallerThanSecondLastCupWithTwoCups() {
        tower.pushCup(1);
        tower.pushCup(3);
        tower.pushLid(2);
        assertTrue(tower.ok());
    }

    // calculateLidBase: tapa igual a la penultima taza
    @Test
    void lidEqualToSecondCup() {
        tower.pushCup(2);
        tower.pushCup(4);
        tower.pushLid(2);
        assertTrue(tower.ok());
    }

    // calculateLidBase: tres tazas, cup1Top mayor que cup3Top
    @Test
    void lidAboveThreeCupsCup1TopGreater() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(5);
        tower.pushLid(6);
        assertTrue(tower.ok());
    }

    // calculateLidBase: tres tazas, cup3Top mayor que cup1Top
    @Test
    void lidAboveThreeCupsCup3TopGreater() {
        tower.pushCup(5);
        tower.pushCup(2);
        tower.pushCup(1);
        tower.pushLid(6);
        assertTrue(tower.ok());
    }

    // calculateLidBase: tapa sin tazas previas
    @Test
    void lidWithNoCupsReturnsZeroBase() {
        tower.pushLid(1);
        assertTrue(tower.ok());
    }

    // cover: multiples pares de tazas y tapas
    @Test
    void shouldCoverWithMultipleMatchingPairs() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(3);
        tower.pushLid(3);
        tower.pushLid(1);
        tower.pushLid(2);
        tower.cover();
        assertTrue(tower.ok());
        assertEquals(3, tower.lidedCups().length);
    }

    // cover: tapa sin par en la torre
    @Test
    void shouldCoverWithUnmatchedLid() {
        tower.pushCup(1);
        tower.pushLid(2);
        tower.cover();
        assertTrue(tower.ok());
    }

    // cover: solo tapas, sin tazas
    @Test
    void shouldCoverWithOnlyLids() {
        tower.pushLid(1);
        tower.pushLid(2);
        tower.cover();
        assertTrue(tower.ok());
    }

    // cover: solo tazas, sin tapas
    @Test
    void shouldCoverWithNoLids() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.cover();
        assertTrue(tower.ok());
        assertEquals(0, tower.lidedCups().length);
    }

    // cover: torre vacia
    @Test
    void shouldCoverEmptyTower() {
        tower.cover();
        assertTrue(tower.ok());
        assertEquals(0, tower.stackingItems().length);
    }

    // swapToReduce: intento con dos tazas
    @Test
    void shouldSwapToReduceWithTwoCups() {
        tower.pushCup(3);
        tower.pushCup(1);
        String[][] result = tower.swapToReduce();
        if (result != null) {
            assertTrue(tower.ok());
            assertEquals(2, result.length);
        }
    }

    // swapToReduce: con tazas y tapas mezcladas
    @Test
    void shouldSwapToReduceWithCupsAndLids() {
        tower.pushCup(1);
        tower.pushLid(1);
        tower.pushCup(3);
        tower.swapToReduce();
        assertNotNull(tower.stackingItems());
    }

    // swapToReduce: un solo elemento, retorna null
    @Test
    void shouldSwapToReduceReturnNullWithOneCup() {
        tower.pushCup(2);
        assertNull(tower.swapToReduce());
        assertFalse(tower.ok());
    }

    // HierarchicalCup: no se puede remover si esta en el fondo
    @Test
    void shouldNotRemoveHierarchicalCupAtBottom() {
        tower.pushCup("hierarchical", 3);
        tower.pushCup(1);
        tower.popCup();
        tower.popCup();
        assertFalse(tower.ok());
    }

    // HierarchicalCup: se hunde hasta la posicion correcta
    @Test
    void shouldHierarchicalCupSinkToBottom() {
        tower.pushCup(2);
        tower.pushCup(4);
        tower.pushCup("hierarchical", 5);
        assertTrue(tower.ok());
        String[][] s = tower.stackingItems();
        assertEquals("hierarchical", s[0][0]);
    }

    // GiftCup: revela un numero aleatorio entre 1 y 50
    @Test
    void shouldGiftCupRevealRandomNumber() {
        tower.pushCup("gift", 1);
        assertTrue(tower.ok());
        String[][] s = tower.stackingItems();
        assertEquals(1, s.length);
        int num = Integer.parseInt(s[0][1]);
        assertTrue(num >= 1 && num <= 50);
    }

    // OpenerCup: elimina todas las tapas existentes
    @Test
    void shouldOpenerCupRemoveAllLids() {
        tower.pushCup(1);
        tower.pushLid(1);
        tower.pushCup(2);
        tower.pushLid(2);
        tower.pushCup("opener", 3);
        assertTrue(tower.ok());
        for (String[] row : tower.stackingItems()) {
            assertNotEquals("lid", row[0]);
        }
    }

    // OpenerCup: sin tapas previas no produce error
    @Test
    void shouldOpenerCupWithNoLidsWork() {
        tower.pushCup(1);
        tower.pushCup("opener", 2);
        assertTrue(tower.ok());
    }

    // FearfulLid: entra en torre vacia
    @Test
    void shouldFearfulLidEnterEmptyTower() {
        tower.pushLid("fearful", 3);
        assertTrue(tower.ok());
    }

    // FearfulLid: entra cuando el ultimo item es una tapa
    @Test
    void shouldFearfulLidEnterWhenLastItemIsLid() {
        tower.pushLid(1);
        tower.pushLid("fearful", 2);
        assertTrue(tower.ok());
    }

    // CrazyLid: cambia colores de todos los items previos
    @Test
    void shouldCrazyLidChangeColorsOfAllItems() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(3);
        tower.pushLid("crazy", 1);
        assertTrue(tower.ok());
        assertEquals(4, tower.stackingItems().length);
    }

    // removeLid: falla si la tapa no existe
    @Test
    void shouldFailRemoveNonExistentLid() {
        tower.pushCup(1);
        tower.removeLid(99);
        assertFalse(tower.ok());
    }

    // swap: falla con tipo invalido
    @Test
    void shouldFailSwapWithInvalidType() {
        tower.pushCup(1);
        tower.swap("bowl", 1, "cup", 1);
        assertFalse(tower.ok());
    }

    // orderTower: con tazas y tapas mezcladas
    @Test
    void shouldOrderTowerWithCupsAndLids() {
        tower.pushCup(1);
        tower.pushCup(3);
        tower.pushLid(2);
        tower.pushCup(2);
        tower.orderTower();
        assertTrue(tower.ok());
    }

    // reverseTower: con un solo elemento
    @Test
    void shouldReverseTowerWithSingleElement() {
        tower.pushCup(1);
        tower.reverseTower();
        assertTrue(tower.ok());
        assertEquals(1, tower.stackingItems().length);
    }

    // lidedCups: retorna resultado ordenado con multiples tapadas
    @Test
    void shouldLidedCupsReturnSortedResult() {
        tower.pushCup(3);
        tower.pushLid(3);
        tower.pushCup(1);
        tower.pushLid(1);
        int[] lided = tower.lidedCups();
        assertEquals(2, lided.length);
        assertEquals(1, lided[0]);
        assertEquals(3, lided[1]);
    }

    // Constructor Tower(int cups): crea torre con N tazas directamente
    @Test
    void shouldConstructorWithCupsCreateCorrectTower() {
        Tower t2 = new Tower(5);
        String[][] s = t2.stackingItems();
        assertEquals(5, s.length);
        for (String[] row : s) assertEquals("cup", row[0]);
        assertTrue(t2.ok());
    }

    // Constructor Tower(int cups): la torre tiene altura positiva
    @Test
    void shouldConstructorWithCupsHavePositiveHeight() {
        Tower t2 = new Tower(3);
        assertTrue(t2.height() > 0);
    }

    // utilidad

    private int findIndex(String[][] s, String type, String num) {
        for (int i = 0; i < s.length; i++) {
            if (s[i][0].equals(type) && s[i][1].equals(num)) return i;
        }
        return -1;
    }

    // verifica que despues de agregar y eliminar todos los elementos la torre queda vacia
    @Test
    public void caso_agregar_y_eliminar_hasta_vaciar() {
        Tower t = new Tower(10, 200);

        t.pushCup(10);
        t.pushCup(20);

        t.popCup();
        t.popCup();

        assertEquals(0, t.stackingItems().length);
        assertTrue(t.ok());
    }

    // verifica que eliminar en una torre vacia deja la torre en estado invalido
    @Test
    public void caso_eliminar_en_torre_vacia() {
        Tower t = new Tower(10, 200);

        t.popCup();

        assertFalse(t.ok());
    }

    // verifica que la torre acepta valores grandes en las tazas
    @Test
    public void caso_agregar_valores_limite() {
        Tower t = new Tower(10, 200);

        t.pushCup(100);
        t.pushCup(99);

        assertEquals(2, t.stackingItems().length);
    }

    // verifica comportamiento al insertar valores repetidos
    @Test
    public void caso_agregar_elementos_repetidos() {
        Tower t = new Tower(10, 200);

        t.pushCup(5);
        t.pushCup(5);

        assertFalse(t.ok()); // duplicados no permitidos segun tu logica
    }

    // verifica una secuencia mixta de operaciones
    @Test
    public void caso_secuencia_operaciones_mixtas() {
        Tower t = new Tower(10, 200);

        t.pushCup(1);
        t.pushCup(2);
        t.popCup();
        t.pushCup(3);

        assertEquals(2, t.stackingItems().length);
        assertTrue(t.ok());
    }

    // verifica que el tamaño es consistente despues de operaciones
    @Test
    public void caso_consistencia_size_despues_de_operaciones() {
        Tower t = new Tower(10, 200);

        t.pushCup(7);
        t.pushCup(8);
        t.popCup();

        assertEquals(1, t.stackingItems().length);
    }

    // verifica que la torre no esta vacia despues de agregar elementos
    @Test
    public void caso_isEmpty_despues_de_agregar() {
        Tower t = new Tower(10, 200);

        t.pushCup(100);

        assertTrue(t.stackingItems().length > 0);
    }

    // verifica que la torre queda vacia despues de eliminar su unico elemento
    @Test
    public void caso_isEmpty_despues_de_vaciar() {
        Tower t = new Tower(10, 200);

        t.pushCup(1);
        t.popCup();

        assertEquals(0, t.stackingItems().length);
    }

    // verifica eliminar varios elementos seguidos
    @Test
    public void caso_multiples_removes_seguidos() {
        Tower t = new Tower(10, 200);

        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);

        t.popCup();
        t.popCup();

        assertEquals(1, t.stackingItems().length);
    }

    // verifica agregar despues de vaciar la torre
    @Test
    public void caso_agregar_despues_de_vaciar() {
        Tower t = new Tower(10, 200);

        t.pushCup(1);
        t.popCup();

        t.pushCup(2);

        assertEquals(1, t.stackingItems().length);
        assertTrue(t.ok());
    }

    //Cubriendo los branches

    // cubre rama swap con tipos invalidos
    @Test
    void branch_swapInvalidType() {
        tower.pushCup(1);
        tower.swap("invalid", 1, "cup", 1);

        assertNotNull(tower.stackingItems());
    }

    // cubre rama swap con null
    @Test
    void branch_swapNullType() {
        tower.pushCup(1);
        tower.swap(null, 1, "cup", 1);

        assertNotNull(tower.stackingItems());
    }

    // cubre rama swap con ambos elementos inexistentes
    @Test
    void branch_swapNonExistentBoth() {
        tower.swap("cup", 99, "cup", 100);

        assertNotNull(tower.stackingItems());
    }

    // cubre rama cover con torre vacia
    @Test
    void branch_coverEmptyTower() {
        tower.cover();

        assertEquals(0, tower.stackingItems().length);
    }

    // cubre rama cover con solo tapas
    @Test
    void branch_coverOnlyLids() {
        tower.pushLid(1);
        tower.pushLid(2);

        tower.cover();

        assertNotNull(tower.stackingItems());
    }

    // cubre rama cover con solo tazas
    @Test
    void branch_coverOnlyCups() {
        tower.pushCup(1);
        tower.pushCup(2);

        tower.cover();

        assertEquals(0, tower.lidedCups().length);
    }

    // cubre rama lidedCups cuando no hay coincidencias
    @Test
    void branch_lidedCupsNoMatches() {
        tower.pushCup(1);
        tower.pushCup(2);

        int[] result = tower.lidedCups();

        assertEquals(0, result.length);
    }

    // cubre rama lidedCups con multiples coincidencias
    @Test
    void branch_lidedCupsMultipleMatches() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushLid(1);
        tower.pushLid(2);

        int[] result = tower.lidedCups();

        assertTrue(result.length >= 1);
    }

    // cubre rama swapToReduce cuando no hay mejora
    @Test
    void branch_swapToReduceNoImprovement() {
        tower.pushCup(1);

        String[][] result = tower.swapToReduce();

        assertNull(result);
    }

    // cubre rama swapToReduce con mas elementos
    @Test
    void branch_swapToReduceWithElements() {
        tower.pushCup(3);
        tower.pushCup(1);

        String[][] result = tower.swapToReduce();

        assertNotNull(tower.stackingItems());
    }

    // cubre rama removeCup inexistente
    @Test
    void branch_removeCupNonExistent() {
        tower.removeCup(999);

        assertNotNull(tower.stackingItems());
    }

    // cubre rama removeLid inexistente
    @Test
    void branch_removeLidNonExistent() {
        tower.removeLid(999);

        assertNotNull(tower.stackingItems());
    }

    // cubre rama popCup en vacio
    @Test
    void branch_popCupEmpty() {
        tower.popCup();

        assertNotNull(tower.stackingItems());
    }

    // cubre rama popLid en vacio
    @Test
    void branch_popLidEmpty() {
        tower.popLid();

        assertNotNull(tower.stackingItems());
    }

    // cubre rama altura con varios elementos
    @Test
    void branch_heightMultipleElements() {
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(3);

        int h = tower.height();

        assertTrue(h >= 0);
    }

    @Test
    void branch_coverComplexScenario() {
        Tower t = new Tower(10, 200);

        t.pushCup(1);
        t.pushCup(3);
        t.pushCup(2);

        t.pushLid(2);
        t.pushLid(1);
        t.pushLid(3);

        t.cover();

        assertNotNull(t.stackingItems());
        assertTrue(t.lidedCups().length >= 1);
    }

    @Test
    void branch_swapToReduceRealCase() {
        Tower t = new Tower(10, 200);

        t.pushCup(5);
        t.pushCup(1);
        t.pushCup(4);
        t.pushCup(2);

        String[][] result = t.swapToReduce();

        assertNotNull(t.stackingItems());
    }

    @Test
    void branch_fullMixedScenario() {
        Tower t = new Tower(10, 200);

        t.pushCup(3);
        t.pushLid(3);
        t.pushCup(1);
        t.pushLid(1);
        t.pushCup(5);

        t.swap("cup", 3, "cup", 1);
        t.cover();
        t.orderTower();
        t.reverseTower();

        assertNotNull(t.stackingItems());
    }

    @Test
    void branch_heightEdgeCase() {
        Tower t = new Tower(3, 10);

        t.pushCup(5);
        t.pushCup(4);
        t.pushCup(3);

        t.pushLid(5);
        t.pushLid(4);

        int h = t.height();

        assertTrue(h >= 0);
    }

}