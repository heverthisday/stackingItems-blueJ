/**
 * Representa una tapa del simulador StackingItems.
 * Cada tapa tiene 1 cm de altura
 * @author Hever
 */
public class Lid implements Item {
    private int number;
    private int height;
    private String color;
    
    private Rectangle body;
    
    public Lid(int number, String color) {
        this.number = number;
        this.height = 1;  // Todas las tapas tienen 1cm de altura
        this.color = color;
        
        body = new Rectangle();
    }
    
    public void makeVisible() {
        body.makeVisible();
    }
    
    public void makeInvisible() {
        body.makeInvisible();
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getNumber() {
        return number;
    }
    
    public String getColor() {
        return color;
    }
    
    public String getType() {
        return "lid";
    }
    
    public void setVisualProperties(int height, int width, int x, int y, String color) {
        body.changeSize(height, width);
        body.moveHorizontal(x);
        body.moveVertical(y);
        body.changeColor(color);
    }
        /**
     * Redibuja la tapa en una nueva posición absoluta
     * Crea un nuevo Rectangle desde cero
     * @param height Altura en píxeles
     * @param width Ancho en píxeles
     * @param x Posición X absoluta
     * @param y Posición Y absoluta
     * @param color Color de la tapa
     */
    public void redraw(int height, int width, int x, int y, String color) {
        // Borrar el Rectangle anterior
        body.makeInvisible();
        
        // Crear nuevo Rectangle desde cero (posición 0,0)
        body = new Rectangle();
        body.changeSize(height, width);
        body.moveHorizontal(x);
        body.moveVertical(y);
        body.changeColor(color);
    }
}