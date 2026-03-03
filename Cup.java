/**
 * representa una taza cilindrica del simulador StackingItems.
 * Cada taza tiene 2i-1 cm y base 1cm
 * @author Hever
 */
public class Cup implements Item {
    private static final int BASE_THICKNESS = 1;
    private static final String[] TAPPED_COLORS = {"lightgray", "lightblue", "brown", "purple"};
    
    private int number;
    private int height;
    private String color;
    private boolean isTapped;
    private String tappedColor;  // Color distinto cuando est� tapada
    
    private Rectangle body;
    
    public Cup(int number, String color){
        this.number = number;
        this.height = 2 * number - 1;
        this.color = color;
        this.isTapped = false;
        this.tappedColor = TAPPED_COLORS[number % TAPPED_COLORS.length];
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
    
    /**
     * Marca la taza como tapada o destapada.
     */
    public void setTapped(boolean tapped) {
        this.isTapped = tapped;
    }
    
    /**
     * Retorna true si la taza est� tapada.
     */
    public boolean isTapped() {
        return isTapped;
    }
    

    /**
     * Redibuja la taza en una nueva posici�n absoluta
     * Crea un nuevo Rectangle desde cero
     * @param height Altura en p�xeles
     * @param width Ancho en p�xeles
     * @param x Posici�n X absoluta
     * @param y Posici�n Y absoluta
     * @param color Color de la taza
     */
    public void redraw(int height, int width, int x, int y, String color) {
        // Borrar el Rectangle anterior
        body.makeInvisible();
        
        // Crear nuevo Rectangle desde cero (posici�n 0,0)
        body = new Rectangle();
        body.changeSize(height, width);
        body.moveHorizontal(x);
        body.moveVertical(y);
        
        // Mostrar siempre el color original (no cambiar apariencia cuando est� tapada)
        body.changeColor(color);
    }
}
