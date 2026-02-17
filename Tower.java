import java.util.ArrayList;

/**
 * Clase principal del simulador StackingItems.
 * Gestiona una torre donde se apilan tazas y tapas.
 * 
 * @author Hever
 * @version 1.0
 */
public class Tower {
    // CONSTANTES DE VISUALIZACIÓN
    private static final int PIXELS_PER_CM = 10;      // 10 píxeles = 1 cm
    private static final int BASE_WIDTH = 20;         // Ancho base para calcular diámetros
    private static final int TOWER_MARGIN = 50;       // Margen alrededor de la torre
    
    // ATRIBUTOS
    private int width;                    // Ancho de la torre
    private int maxHeight;                // Altura máxima de la torre
    private ArrayList<Cup> cups;          // Tazas apiladas
    private ArrayList<Lid> lids;          // Tapas apiladas
    private boolean isVisible;            // Estado de visibilidad
    private boolean ok;                   // Estado de última operación
    
    // FORMAS VISUALES
    private Canvas canvas;                // Lienzo para dibujar
    private Rectangle towerFrame;         // Marco de la torre
    private ArrayList<Rectangle> heightMarks;  // Marcas de centímetros
    
    /**
     * Constructor de Tower
     * @param width Ancho de la torre
     * @param maxHeight Altura máxima de la torre en cm
     */
    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.cups = new ArrayList<Cup>();
        this.lids = new ArrayList<Lid>();
        this.isVisible = false;
        this.ok = true;
        this.heightMarks = new ArrayList<Rectangle>();
        
        // Inicializar canvas
        canvas = Canvas.getCanvas();
        
        // Crear marco de la torre
        createTowerFrame();
        
        // Crear marcas de altura
        createHeightMarks();
    }
    
    /**
     * Crea el marco visual de la torre
     */
    private void createTowerFrame() {
        int frameWidth = width * BASE_WIDTH;
        int frameHeight = maxHeight * PIXELS_PER_CM;
        
        towerFrame = new Rectangle();
        towerFrame.changeSize(frameHeight, frameWidth);
        towerFrame.moveHorizontal(TOWER_MARGIN);
        towerFrame.moveVertical(TOWER_MARGIN);
        towerFrame.changeColor("black");
    }
    
    /**
     * Crea las marcas de centímetros en la torre
     */
    private void createHeightMarks() {
        for (int i = 0; i <= maxHeight; i++) {
            Rectangle mark = new Rectangle();
            mark.changeSize(2, 5);
            mark.moveHorizontal(TOWER_MARGIN - 10);
            mark.moveVertical(TOWER_MARGIN + (maxHeight - i) * PIXELS_PER_CM);
            mark.changeColor("black");
            heightMarks.add(mark);
        }
    }
    
    // MÉTODOS DE GESTIÓN DE TAZAS (Mini-Ciclo 2)
    public void pushCup(int i) { }
    public void popCup() { }
    public void removeCup(int i) { }
    
    // MÉTODOS DE GESTIÓN DE TAPAS (Mini-Ciclo 3)
    public void pushLid(int i) { }
    public void popLid() { }
    public void removeLid(int i) { }
    
    // MÉTODOS DE REORGANIZACIÓN (Mini-Ciclo 4)
    public void orderTower() { }
    public void reverseTower() { }
    
    // MÉTODOS DE CONSULTA (Mini-Ciclo 4)
    public int height() { return 0; }
    public int[] lidedCups() { return null; }
    public String[][] stackingItems() { return null; }
    
    // MÉTODOS DE VISIBILIDAD (Mini-Ciclo 1)
    public void makeVisible() {
        if (!isVisible) {
            canvas.setVisible(true);
            towerFrame.makeVisible();
            
            // Mostrar marcas de altura
            for (Rectangle mark : heightMarks) {
                mark.makeVisible();
            }
            
            // Mostrar todas las tazas
            for (Cup cup : cups) {
                cup.makeVisible();
            }
            
            // Mostrar todas las tapas
            for (Lid lid : lids) {
                lid.makeVisible();
            }
            
            isVisible = true;
        }
    }
    
    public void makeInvisible() {
        if (isVisible) {
            towerFrame.makeInvisible();
            
            // Ocultar marcas de altura
            for (Rectangle mark : heightMarks) {
                mark.makeInvisible();
            }
            
            // Ocultar todas las tazas
            for (Cup cup : cups) {
                cup.makeInvisible();
            }
            
            // Ocultar todas las tapas
            for (Lid lid : lids) {
                lid.makeInvisible();
            }
            
            isVisible = false;
        }
    }
    
    public void exit() {
        System.exit(0);
    }
    
    // MÉTODO DE ESTADO
    public boolean ok() {
        return ok;
    }
}