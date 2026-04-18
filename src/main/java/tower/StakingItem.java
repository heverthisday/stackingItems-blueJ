package tower;
import java.util.ArrayList;
import shapes.Rectangle;

public abstract class StakingItem implements Item {
    protected int number;
    protected int height;
    protected String color;
    protected Rectangle body;

    public StakingItem(int number, String color) {
        this.number = number;
        this.color = color;
        this.body = new Rectangle();
    }

    public int getHeight() { return height; }
    public int getNumber() { return number; }
    public String getColor() { return color; }

    public void makeVisible() { body.makeVisible(); }
    public void makeInvisible() { body.makeInvisible(); }

    public void redraw(int height, int width, int x, int y, String color) {
        body.makeInvisible();
        body = new Rectangle();
        body.changeSize(height, width);
        body.moveHorizontal(x);
        body.moveVertical(y);
        body.changeColor(color);
        body.makeVisible();
    }
    /**
     * Define qué sucede cuando este ítem entra a la torre.
     * @param items La lista actual de la torre para que el ítem interactúe con ella.
     */
    public void processEntry(ArrayList<Item> items) {
        // Por defecto no hace nada extra
    }

    /**
     * Indica si el ítem permite ser removido de la torre.
     */
    public boolean canBeRemoved(ArrayList<Item> items) {
        return true; // Por defecto todos se pueden quitar
    }
    /**
     * Indica si el ítem permite ser añadido a la torre dada su situación actual.
     * @param items La lista actual de la torre.
     * @return true si se puede añadir, false de lo contrario.
     */
    public boolean canBeAdded(ArrayList<Item> items) {
        return true; // Por defecto, todos los ítems permiten ser añadidos
    }
    
    // También asegúrate de tener este para la CrazyLid
    public void changeColor(String newColor) {
        this.color = newColor;
        body.changeColor(newColor);
    }
}