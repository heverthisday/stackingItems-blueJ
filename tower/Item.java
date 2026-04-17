package tower;

/**
 * Interfaz que define el contrato para cualquier objeto que pueda ser 
 * apilado en la torre (Tazas o Tapas).
 */
public interface Item {
    int getHeight();
    int getNumber();
    String getColor();
    String getType();
    void makeVisible();
    void makeInvisible();
    /**
     * Redibuja el ítem en una posición específica del lienzo.
     */
    void redraw(int height, int width, int x, int y, String color);
    void changeColor(String newColor); // Añade esta línea

}