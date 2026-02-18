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
    private static final String[] COLORS = {"red", "blue", "green", "yellow", "magenta"};
    
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
    /**
     * agrega una taza a la torre
     * @param i numero de la taza(determina altura y diametro)
     */
    public void pushCup(int i) {
        // validar que la taza existe
        for (Cup existingCup : cups) {
            if (existingCup.getNumber() == i) {
                ok = false; // La taza ya existe
                if (isVisible) {
                    //mostrar error (solo si esta visible)
                    System.out.println("Error: La taza " + i + " ya está en la torre.");
                }
                return;
            }
        }
        //determinar color usando modulos
        String color = COLORS[i % COLORS.length];

        //crear la nueva taza
        Cup cup = new Cup(i, color);
        
        //calcular la altura de la actual torre
        int currentHeight = calculateCurrentHeight();

        if(currentHeight + cup.getHeight() > maxHeight) {
            ok = false; // No hay espacio para la nueva taza
            if (isVisible) {
                //mostrar error (solo si esta visible)
                System.out.println("Error: No hay espacio para la taza " + i + " en la torre.");
            }
            return;
 
        }
        //calcular la posicion visual
        int cupWidth = BASE_WIDTH * i;
        int cupHeight = cup.getHeight() * PIXELS_PER_CM;

        // poscicion x: centrar marco en la torre
        int frameWidth = width * BASE_WIDTH;
        int xPos = TOWER_MARGIN + (frameWidth - cupWidth) / 2;

        // posicion y: calcular desde la base de la torre
        // Calcular posición base de la nueva taza
        int base;
        if (cups.isEmpty()) {
            base = 0;  // Primera taza, se apoya en el suelo
        } else {
            Cup lastCup = cups.get(cups.size() - 1);
            if (i < lastCup.getNumber()) {
                // Cabe DENTRO de la última
                int lastBase = calculateBasePosition(cups.size() - 1);
                base = lastBase + 1;
            } else {
                // Va ENCIMA
                int lastTop = calculateTopPosition(cups.size() - 1);
                base = lastTop;
            }
        }
        
        // Posición Y visual (desde arriba del canvas)
        int yPos = TOWER_MARGIN + (maxHeight - base - cup.getHeight()) * PIXELS_PER_CM;


        //agregar a la lista de tazas
        cups.add(cup);
        cup.setVisualProperties(cupHeight, cupWidth, xPos,yPos,  color);
        //si la torre es visible, mostrar la nueva taza
        if (isVisible) {
            cup.makeVisible();
        }
        ok = true; 

    }
    /**
 * Calcula la altura actual de todos los elementos apilados
 * @return altura en cm
 */
    /**
 * Calcula la altura actual de todos los elementos apilados
 * @return altura en cm
 */
private int calculateCurrentHeight() {
    if (cups.isEmpty() && lids.isEmpty()) {
        return 0;
    }
    
    int currentTop = 0;  // Tope actual de la torre
    
    // Calcular posiciones de las tazas
    for (int i = 0; i < cups.size(); i++) {
        Cup cup = cups.get(i);
        int base;
        
        if (i == 0) {
            // Primera taza - se apoya en el suelo
            base = 0;
        } else {
            Cup previousCup = cups.get(i - 1);
            
            if (cup.getNumber() < previousCup.getNumber()) {
                // Cabe DENTRO de la anterior
                // Se apoya en la base de la anterior + 1cm (grosor base)
                int previousBase = calculateBasePosition(i - 1);
                base = previousBase + 1;
            } else {
                // NO cabe - va ENCIMA
                // Se apoya en el tope de la anterior
                int previousTop = calculateTopPosition(i - 1);
                base = previousTop;
            }
        }
        
        // Calcular tope de esta taza
        int top = base + cup.getHeight();
        if (top > currentTop) {
            currentTop = top;
        }
    }
    
    // TODO: Agregar lógica para tapas después
    
    return currentTop;
}

/**
 * Calcula la posición base de una taza en el ArrayList
 * @param index índice de la taza
 * @return posición base en cm
 */
private int calculateBasePosition(int index) {
    if (index == 0) {
        return 0;
    }
    
    Cup cup = cups.get(index);
    Cup previousCup = cups.get(index - 1);
    
    if (cup.getNumber() < previousCup.getNumber()) {
        // Cabe dentro
        return calculateBasePosition(index - 1) + 1;
    } else {
        // Va encima
        return calculateTopPosition(index - 1);
    }
}

/**
 * Calcula la posición del tope de una taza
 * @param index índice de la taza
 * @return posición del tope en cm
 */
private int calculateTopPosition(int index) {
    Cup cup = cups.get(index);
    return calculateBasePosition(index) + cup.getHeight();
}
    /**
 * Quita la taza que está arriba de la torre
 */
public void popCup() {
    // Validar que haya tazas
    if (cups.isEmpty()) {
        ok = false;
        if (isVisible) {
            System.out.println("Error: No hay tazas en la torre para quitar");
        }
        return;
    }
    
    // Obtener la última taza (la de arriba)
    Cup cup = cups.get(cups.size() - 1);
    
    // Hacerla invisible si la torre está visible
    if (isVisible) {
        cup.makeInvisible();
    }
    
    // Quitarla del ArrayList
    cups.remove(cups.size() - 1);
    
    ok = true;
}
/**
 * Quita una taza específica de la torre por su número
 * @param i Número de la taza a quitar
 */
/**
 * Quita una taza específica de la torre por su número
 * @param i Número de la taza a quitar
 */
public void removeCup(int i) {
    // Buscar la taza con ese número
    Cup cupToRemove = null;
    int indexToRemove = -1;
    
    for (int j = 0; j < cups.size(); j++) {
        if (cups.get(j).getNumber() == i) {
            cupToRemove = cups.get(j);
            indexToRemove = j;
            break;
        }
    }
    
    // Validar que existe
    if (cupToRemove == null) {
        ok = false;
        if (isVisible) {
            System.out.println("Error: La taza " + i + " no está en la torre");
        }
        return;
    }
    
    // Hacer invisible SOLO la taza que vamos a quitar
    if (isVisible) {
        cupToRemove.makeInvisible();
    }
    
    // Quitarla del ArrayList
    cups.remove(indexToRemove);
    
    // RECALCULAR Y REDIBUJAR las tazas que quedan
    if (isVisible) {
        // Primero hacer invisibles todas las que quedan
        for (Cup cup : cups) {
            cup.makeInvisible();
        }
        
        // Recalcular posiciones y mostrar
        for (int j = 0; j < cups.size(); j++) {
            Cup cup = cups.get(j);
            
            // Calcular nueva posición base
            int base = calculateBasePosition(j);
            
            // Calcular posición visual
            int cupNumber = cup.getNumber();
            int cupWidth = cupNumber * BASE_WIDTH;
            int cupHeight = cup.getHeight() * PIXELS_PER_CM;
            
            int frameWidth = width * BASE_WIDTH;
            int xPos = TOWER_MARGIN + (frameWidth - cupWidth) / 2;
            int yPos = TOWER_MARGIN + (maxHeight - base - cup.getHeight()) * PIXELS_PER_CM;
            
            // Reconfigurar y mostrar
            cup.setVisualProperties(cupHeight, cupWidth, xPos, yPos, cup.getColor());
            cup.makeVisible();
        }
    }
    
    ok = true;
}


    // MÉTODOS DE GESTIÓN DE TAPAS (Mini-Ciclo 3)
    public void pushLid(int i) { }
    public void popLid() { }
    public void removeLid(int i) { }
    
    // MÉTODOS DE REORGANIZACIÓN (Mini-Ciclo 4)
    public void orderTower() { }
    public void reverseTower() { }
    
    // MÉTODOS DE CONSULTA (Mini-Ciclo 4)
    public int height() { 
        return calculateCurrentHeight();
     }
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