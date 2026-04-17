/**
 * Clase de prueba de aceptación (Prueba común)
 */
public class TowerContestCTest {

    public static void main(String[] args) {
        System.out.println("=== Ejecutando Prueba de Aceptación Ciclo 3 ===");
        
        // Caso solicitado por el usuario
        System.out.println("Probando simulación: n=4, h=12");
        
        try {
            // El método simulate interno ya tiene el:
            // 1. new Tower(n)
            // 2. makeVisible()
            // 3. solve(n, h)
            // 4. pushCup(...)
            TowerContest.simulate(4, 12);
            
            System.out.println("Simulación finalizada para n=4, h=12.");
        } catch (Exception e) {
            System.out.println("Error durante la simulación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}