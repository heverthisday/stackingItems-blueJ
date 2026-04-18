package test;

import org.junit.jupiter.api.Test;
import tower.TowerContest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas para TowerContest.
 *
 * Objetivo:
 * Validar el comportamiento del metodo solve con diferentes tipos de entradas,
 * incluyendo casos validos, casos invalidos y condiciones limite del problema.
 *
 * Enfoque:
 * Se prueban rangos de altura, casos especiales del problema, estructura del resultado
 * y propiedades matematicas basicas de la solucion generada.
 */
public class TowerContestTest {

    // Caso: verifica que una altura menor al minimo permitido sea rechazada correctamente
    @Test
    void casoAlturaMenorMinimo() {
        String r = TowerContest.solve(3, 4);
        assertEquals("impossible", r);
    }

    // Caso: verifica que una altura mayor al maximo permitido sea rechazada correctamente
    @Test
    void casoAlturaMayorMaximo() {
        String r = TowerContest.solve(3, 10);
        assertEquals("impossible", r);
    }

    // Caso: verifica que la altura especial prohibida (n^2 - 2) sea detectada
    @Test
    void casoAlturaProhibida() {
        String r = TowerContest.solve(4, 14);
        assertEquals("impossible", r);
    }

    // Caso: verifica la construccion correcta cuando la altura es exactamente la minima posible
    @Test
    void casoAlturaMinima() {
        String r = TowerContest.solve(3, 5);
        assertEquals("5 3 1", r);
    }

    // Caso: verifica que el algoritmo construya una solucion cuando la altura es maxima
    @Test
    void casoAlturaMaxima() {
        String r = TowerContest.solve(3, 9);
        assertNotEquals("impossible", r);
    }

    // Caso: valida un escenario general donde la altura es valida y no es un extremo
    @Test
    void casoGeneral() {
        String r = TowerContest.solve(4, 10);
        assertNotEquals("impossible", r);
    }

    // Caso: valida el comportamiento con el numero minimo de tazas posible
    @Test
    void casoN1() {
        String r = TowerContest.solve(1, 1);
        assertEquals("1", r);
    }

    // Caso: valida el comportamiento con dos tazas y altura valida
    @Test
    void casoN2() {
        String r = TowerContest.solve(2, 3);
        assertEquals("3 1", r);
    }

    // Caso: verifica que todas las alturas generadas sean numeros impares validos
    @Test
    void casoFormatoImpar() {
        String r = TowerContest.solve(3, 7);
        if (!r.equals("impossible")) {
            for (String p : r.split(" ")) {
                assertEquals(1, Integer.parseInt(p) % 2,
                        "Se esperaba numero impar pero fue: " + p);
            }
        }
    }

    // Caso: valida que la cantidad de elementos en la solucion sea igual al numero de tazas
    @Test
    void casoCantidad() {
        int n = 5;
        String r = TowerContest.solve(n, 15);
        if (!r.equals("impossible")) {
            assertEquals(n, r.split(" ").length);
        }
    }

    // Caso: verifica rechazo de altura justo por debajo del minimo en un escenario grande
    @Test
    void casoDebajoMinimoGrande() {
        String r = TowerContest.solve(10, 18);
        assertEquals("impossible", r);
    }

    // Caso: verifica rechazo de altura justo por encima del maximo permitido
    @Test
    void casoEncimaMaximo() {
        String r = TowerContest.solve(5, 26);
        assertEquals("impossible", r);
    }

    // Caso: valida que una altura intermedia genere una solucion correcta
    @Test
    void casoIntermedio() {
        String r = TowerContest.solve(5, 20);
        assertNotEquals("impossible", r);
    }

    // Caso: evalua comportamiento del algoritmo con valores grandes en el limite superior
    @Test
    void casoMaximoGrande() {
        String r = TowerContest.solve(10, 100);
        assertNotEquals("impossible", r);
    }

    // Caso: valida un escenario intermedio con tres tazas
    @Test
    void casoN3Medio() {
        String r = TowerContest.solve(3, 7);
        assertNotEquals("impossible", r);
    }

    // Caso: verifica que no existan valores duplicados en la solucion generada
    @Test
    void casoSinDuplicados() {
        String r = TowerContest.solve(5, 15);
        if (!r.equals("impossible")) {
            Set<String> s = new HashSet<>();
            for (String p : r.split(" ")) {
                s.add(p);
            }
            assertEquals(r.split(" ").length, s.size(),
                    "Se encontraron valores duplicados en: " + r);
        }
    }

    // Caso: valida que todos los valores esten dentro del rango permitido de alturas
    @Test
    void casoRangoValores() {
        int n = 6;
        String r = TowerContest.solve(n, 20);
        if (!r.equals("impossible")) {
            for (String p : r.split(" ")) {
                int v = Integer.parseInt(p);
                assertTrue(v >= 1 && v <= (2 * n - 1),
                        "Valor fuera de rango: " + v);
            }
        }
    }

    // Caso: valida que el resultado generado no sea una cadena vacia
    @Test
    void casoNoVacio() {
        String r = TowerContest.solve(4, 8);
        assertTrue(r.length() > 0);
    }

    // Caso: valida que el formato de la cadena no tenga espacios adicionales al inicio o al final
    @Test
    void casoFormatoEspacios() {
        String r = TowerContest.solve(4, 9);
        if (!r.equals("impossible")) {
            assertFalse(r.startsWith(" "), "El resultado no debe comenzar con espacio");
            assertFalse(r.endsWith(" "),   "El resultado no debe terminar con espacio");
        }
    }

    // Caso: valida que la conversion de altura a numero de taza sea consistente
    @Test
    void casoConversion() {
        String r = TowerContest.solve(5, 18);
        if (!r.equals("impossible")) {
            for (String p : r.split(" ")) {
                int h = Integer.parseInt(p);
                int t = (h + 1) / 2;
                assertTrue(t >= 1 && t <= 5,
                        "Numero de taza fuera de rango: " + t);
            }
        }
    }
}