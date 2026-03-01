/**
 * Interfaz que representa un elemento apilable en la torre
 * Puede ser una taza (Cup) o una tapa (Lid)
 * 
 * @author Hever
 * @version 3.0
 */
public interface Item {
    /**
     * Retorna el número del item
     * @return número del item
     */
    int getNumber();
    
    /**
     * Retorna la altura del item en centímetros
     * @return altura en cm
     */
    int getHeight();
    
    /**
     * Retorna el tipo de item
     * @return "cup" para tazas, "lid" para tapas
     */
    String getType();
    
    /**
     * Retorna el color del item
     * @return color del item
     */
    String getColor();
    
    /**
     * Hace visible el item
     */
    void makeVisible();
    
    /**
     * Hace invisible el item
     */
    void makeInvisible();
}