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
    
/**
 * Redibuja la tapa en una nueva posición absoluta
 */
public void redraw(int height, int width, int x, int y, String color) {
    body.makeInvisible();
    body = new Rectangle();
    body.changeSize(height, width);
    body.moveHorizontal(x);
    body.moveVertical(y);
    // Usar siempre el color original de la tapa (no cambiar apariencia al emparejar)
    body.changeColor(this.color);
}

    /**
     * Cambia el color de la tapa (útil para emparejar con la taza correspondiente)
     */
    // setColor se elimina para evitar que la tapa adopte colores externos
}