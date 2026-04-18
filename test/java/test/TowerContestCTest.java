package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tower.TowerContest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba de aceptación Ciclo 3 — TowerContest.solve().
 * Convertida de main() a JUnit 5.
 */
public class TowerContestCTest {

    // --- Casos válidos ---

    @Test
    void shouldSolveMinHeightFor4Cups() {
        // altura mínima para n=4 es 2*4-1 = 7
        String result = TowerContest.solve(4, 7);
        assertNotNull(result);
        assertNotEquals("impossible", result);
    }

    @Test
    void shouldSolveMaxHeightFor4Cups() {
        // altura máxima para n=4 es 4*4 = 16
        String result = TowerContest.solve(4, 16);
        assertNotNull(result);
        assertNotEquals("impossible", result);
    }

    @Test
    void shouldSolveFor4CupsHeight12() {
        String result = TowerContest.solve(4, 12);
        assertNotNull(result);
        assertNotEquals("impossible", result);
    }

    @Test
    void shouldSolveFor3CupsMinHeight() {
        // mínimo para n=3 es 5
        String result = TowerContest.solve(3, 5);
        assertNotNull(result);
        assertNotEquals("impossible", result);
    }

    @ParameterizedTest(name = "n={0}, h={1} debe ser posible")
    @CsvSource({
            "1, 1",
            "2, 3",
            "2, 4",
            "3, 5",
            "3, 9",
            "4, 7",
            "4, 12",
            "4, 16",
            "5, 9",
            "5, 25"
    })
    void shouldSolveValidCases(int n, int h) {
        String result = TowerContest.solve(n, h);
        assertNotNull(result);
        assertNotEquals("impossible", result,
                "Se esperaba solución para n=" + n + ", h=" + h);
    }

    // --- Casos imposibles ---

    @Test
    void shouldReturnImpossibleWhenHeightTooLow() {
        // Para n=4, h=6 < mínimo (7)
        String result = TowerContest.solve(4, 6);
        assertEquals("impossible", result);
    }

    @Test
    void shouldReturnImpossibleWhenHeightTooHigh() {
        // Para n=4, h=17 > máximo (16)
        String result = TowerContest.solve(4, 17);
        assertEquals("impossible", result);
    }

    @ParameterizedTest(name = "n={0}, h={1} debe ser imposible")
    @CsvSource({
            "3, 4",   // menor al mínimo
            "3, 10",  // mayor al máximo
            "4, 6",
            "4, 17",
            "5, 8"    // menor al mínimo de 5 tazas (9)
    })
    void shouldReturnImpossibleForInvalidCases(int n, int h) {
        String result = TowerContest.solve(n, h);
        assertEquals("impossible", result,
                "Se esperaba 'impossible' para n=" + n + ", h=" + h);
    }

    // --- Verificación de formato del resultado ---

    @Test
    void resultShouldContainNHeights() {
        int n = 4;
        String result = TowerContest.solve(n, 12);
        assertNotEquals("impossible", result);
        String[] parts = result.split(" ");
        assertEquals(n, parts.length,
                "El resultado debe tener exactamente " + n + " alturas");
    }

    @Test
    void resultHeightsShouldBeOddNumbers() {
        String result = TowerContest.solve(3, 5);
        assertNotEquals("impossible", result);
        for (String part : result.split(" ")) {
            int h = Integer.parseInt(part);
            assertEquals(1, h % 2, "Cada altura debe ser impar, pero encontré: " + h);
        }
    }

    @Test
    void resultHeightsSumShouldMatchTarget() {
        int n = 4, target = 12;
        String result = TowerContest.solve(n, target);
        assertNotEquals("impossible", result);
        int sum = 0;
        for (String part : result.split(" ")) sum += Integer.parseInt(part);
        assertEquals(target, sum,
                "La suma de alturas debe ser " + target + " pero fue " + sum);
    }
}