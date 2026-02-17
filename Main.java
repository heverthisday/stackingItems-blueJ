/**
 * Clase principal para probar el simulador StackingItems
 * 
 * @author Hever
 * @version 1.0
 */
public class Main {
    
    /**
     * Método principal para ejecutar el simulador
     */
    public static void main(String[] args) {
        // Crear una torre de ancho 5 y altura máxima 20 cm
        Tower tower = new Tower(5, 20);
        
        // Hacerla visible
        tower.makeVisible();
        
        System.out.println("Torre creada y visible. Estado ok: " + tower.ok());
    }
}