/**
 * representa una taza cilindrica del simulador StackingItems.
 * Cada taza tiene 2i-1 cm y base 1cm
 * @author Hever
 */
public class Cup implements Item {
    private static final int BASE_THICKNESS = 1;
    
    private int number;
    private int height;
    private String color;
    private int xPosition;
    private int yPosition;
    
    private Rectangle body;
    
    public Cup(int number, String color){
        this.number = number;
        this.height = 2 * number - 1;
        this.color = color;
        this.xPosition = 0;
        this.yPosition = 0;
        
        body = new Rectangle();
    }
    
    public void makeVisible(){
        body.makeVisible();
    }
    
    public void makeInvisible(){
        body.makeInvisible();
    }
    
    public int getHeight(){
        return height;
    }
    
    public int getNumber(){
        return number;
    }
    
    public int getBaseThickness(){
        return BASE_THICKNESS;
    }
    
    public String getColor() {
        return color;
    }
    
    public String getType() {
        return "cup";
    }
    
    public void setVisualProperties(int height, int width, int x, int y, String color) {
        body.changeSize(height, width);
        body.moveHorizontal(x);
        body.moveVertical(y);
        body.changeColor(color);
    }
    /**
     * Redibuja la taza en una nueva posición absoluta
     * Crea un nuevo Rectangle desde cero
     * @param height Altura en píxeles
     * @param width Ancho en píxeles
     * @param x Posición X absoluta
     * @param y Posición Y absoluta
     * @param color Color de la taza
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