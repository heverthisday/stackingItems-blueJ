import java.util.ArrayList;

/**
 * Clase principal del simulador StackingItems.
 * Gestiona una torre donde se apilan tazas y tapas siguiendo reglas
 * de apilamiento DENTRO/ENCIMA según tamaños relativos.
 * 
 * @author Hever
 * @version 3.0 - Reestructuración Ciclo 2
 */
public class Tower {
    // CONSTANTES DE VISUALIZACIÓN
    private static final String[] COLORS = {"red", "blue", "green", "yellow", "magenta"};
    private static final int PIXELS_PER_CM = 10;
    private static final int BASE_WIDTH = 20;
    private static final int TOWER_MARGIN = 50;
    
    // ATRIBUTOS
    private int width;
    private int maxHeight;
    private boolean isVisible;
    private boolean ok;  
    private ArrayList<Cup> cups;
    private ArrayList<Lid> lids;
    private ArrayList<Item> items;
    
    // FORMAS VISUALES
    private Canvas canvas;
    private Rectangle towerFrame;
    private ArrayList<Rectangle> heightMarks;
    
    /**
     * Constructor de Tower
     */
    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.isVisible = false;
        this.ok = true;
        this.heightMarks = new ArrayList<Rectangle>();
        this.cups = new ArrayList<Cup>();
        this.lids = new ArrayList<Lid>();
        this.items = new ArrayList<Item>();
        
        canvas = Canvas.getCanvas();
        createTowerFrame();
        createHeightMarks();
    }
    
    private void createTowerFrame() {
        int frameWidth = width * BASE_WIDTH;
        int frameHeight = maxHeight * PIXELS_PER_CM;
        
        towerFrame = new Rectangle();
        towerFrame.changeSize(frameHeight, frameWidth);
        towerFrame.moveHorizontal(TOWER_MARGIN);
        towerFrame.moveVertical(TOWER_MARGIN);
        towerFrame.changeColor("black");
    }
    
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
    
    
    
    /**
     * Agrega una taza a la torre
     */
    public void pushCup(int i) {
        for (Cup cup : cups) {
            if (cup.getNumber() == i) {
                ok = false;
                if (isVisible) {
                    System.out.println("Error: La taza " + i + " ya está en la torre");
                }
                return;
            }
        }   
        
        String color = COLORS[i % COLORS.length];
        Cup cup = new Cup(i, color);
        
        int currentHeight = calculateHeight();
        if (currentHeight + cup.getHeight() > maxHeight) {
            ok = false;
            if (isVisible) {
                System.out.println("Error: No hay espacio para la taza " + i);
            }
            return;
        } 
        
        cups.add(cup);
        items.add(cup); 
        
        if (isVisible) {
            int index = items.size() - 1;
            int base = calculateItemBase(index);
            updateItemVisualPosition(cup, base);
            cup.makeVisible();
        }
        
        ok = true;
    }
    
    /**
     * Agrega una tapa a la torre
     */
    public void pushLid(int i) {
        for (Lid lid : lids) {
            if (lid.getNumber() == i) {
                ok = false;
                if (isVisible) {
                    System.out.println("Error: La tapa " + i + " ya está en la torre");
                }
                return;
            }
        }
        
        String color = COLORS[i % COLORS.length];
        Lid lid = new Lid(i, color);
        
        int currentHeight = calculateHeight();
        if (currentHeight + lid.getHeight() > maxHeight) {
            ok = false;
            if (isVisible) {
                System.out.println("Error: No hay espacio para la tapa " + i);
            }
            return;
        }
        
        lids.add(lid);
        
        int base = calculateLidBaseUsingCups(i);
        
        items.add(lid);
        
        if (isVisible) {
            updateItemVisualPosition(lid, base);
            lid.makeVisible();
        }
        
        ok = true;
    }
    
    /**
     * Retorna la altura total actual de la torre
     */
    public int height() {
        return calculateHeight();
    }
    
    /**
     * Retorna información de todos los elementos apilados
     */
    public String[][] stackingItems() {
        int totalItems = items.size();
        String[][] result = new String[totalItems][2];
        
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            result[i][0] = item.getType();
            result[i][1] = String.valueOf(item.getNumber());
        }
        
        return result;
    }
    
    public void makeVisible() {
        if (!isVisible) {
            canvas.setVisible(true);
            towerFrame.makeVisible();
            
            for (Rectangle mark : heightMarks) {
                mark.makeVisible();
            }
            
            for (Item item : items) {
                item.makeVisible();
            }
            
            isVisible = true;
        }
    }
    
    public void makeInvisible() {
        if (isVisible) {
            towerFrame.makeInvisible();
            
            for (Rectangle mark : heightMarks) {
                mark.makeInvisible();
            }
            
            for (Item item : items) {
                item.makeInvisible();
            }
            
            isVisible = false;
        }
    }
    
    public void exit() {
        System.exit(0);
    }
    
    public boolean ok() {
        return ok;
    }
    
    // ========== MÉTODOS AUXILIARES ==========
    
    private void updateItemVisualPosition(Item item, int base) {
        int itemWidth = item.getNumber() * BASE_WIDTH;
        int itemHeight = item.getHeight() * PIXELS_PER_CM;
        
        int frameWidth = width * BASE_WIDTH;
        int xPos = TOWER_MARGIN + (frameWidth - itemWidth) / 2;
        int yPos = TOWER_MARGIN + (maxHeight - base - item.getHeight()) * PIXELS_PER_CM;
        
        if (item instanceof Cup) {
            ((Cup) item).setVisualProperties(itemHeight, itemWidth, xPos, yPos, item.getColor());
        } else if (item instanceof Lid) {
            ((Lid) item).setVisualProperties(itemHeight, itemWidth, xPos, yPos, item.getColor());
        }
    }
    
    private int calculateItemTop(int index) {
        Item item = items.get(index);
        int base = calculateItemBase(index);
        return base + item.getHeight();
    }
    
    private int calculateLidBaseUsingCups(int lidNumber) {
        if (cups.isEmpty()) {
            return 0;
        }
        
        int numCups = cups.size();
        Cup cup1 = numCups >= 1 ? cups.get(numCups - 1) : null;
        Cup cup2 = numCups >= 2 ? cups.get(numCups - 2) : null;
        Cup cup3 = numCups >= 3 ? cups.get(numCups - 3) : null;
        
        if (cup1 != null && lidNumber < cup1.getNumber()) {
            int cup1Base = findCupBase(cup1);
            return cup1Base + 1;
        }
        
        if (cup1 != null && lidNumber == cup1.getNumber()) {
            int cup1Top = findCupTop(cup1);
            return cup1Top;
        }
        
        if (cup2 != null && lidNumber < cup2.getNumber()) {
            int cup1Top = findCupTop(cup1);
            return cup1Top;
        }
        
        if (cup2 != null && lidNumber == cup2.getNumber()) {
            int cup2Top = findCupTop(cup2);
            return cup2Top;
        }
        
        if (cup3 != null) {
            int cup3Top = findCupTop(cup3);
            int cup1Top = findCupTop(cup1);
            
            if (cup1Top > cup3Top) {
                return cup1Top;
            } else {
                return cup3Top;
            }
        }
        
        return findCupTop(cup1);
    }
    
    private int calculateItemBase(int index) {
        Item item = items.get(index);
        
        if (item.getType().equals("lid")) {
            return calculateLidBaseUsingCups(item.getNumber());
        }
        
        if (index == 0) {
            return 0;
        }
        
        Item previousItem = items.get(index - 1);
        
        boolean previousIsClosed = false;
        if (previousItem.getType().equals("cup")) {
            for (int j = 0; j < index; j++) {
                Item checkItem = items.get(j);
                if (checkItem.getType().equals("lid") && 
                    checkItem.getNumber() == previousItem.getNumber()) {
                    previousIsClosed = true;
                    break;
                }
            }
        }
        
        if (previousIsClosed) {
            return calculateItemTop(index - 1);
        } else if (item.getNumber() < previousItem.getNumber()) {
            return calculateItemBase(index - 1) + 1;
        } else {
            return calculateItemTop(index - 1);
        }
    }
    
    private int calculateHeight() {
        if (items.isEmpty()) {
            return 0;
        }
        
        int maxTop = 0;
        
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int base = calculateItemBase(i);
            int top = base + item.getHeight();
            
            if (top > maxTop) {
                maxTop = top;
            }
        }
        
        return maxTop;
    }
    
    private int findCupBase(Cup cup) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == cup) {
                if (i == 0) {
                    return 0;
                }
                
                Item previous = items.get(i - 1);
                if (cup.getNumber() < previous.getNumber()) {
                    return findItemBase(previous) + 1;
                } else {
                    return findItemTop(previous);
                }
            }
        }
        return 0;
    }
    
    private int findCupTop(Cup cup) {
        return findCupBase(cup) + cup.getHeight();
    }
    
    private int findItemBase(Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == item) {
                if (i == 0) return 0;
                Item prev = items.get(i - 1);
                if (item.getNumber() < prev.getNumber()) {
                    return findItemBase(prev) + 1;
                } else {
                    return findItemTop(prev);
                }
            }
        }
        return 0;
    }
    
    private int findItemTop(Item item) {
        return findItemBase(item) + item.getHeight();
    }
    /**
 * Quita la última taza agregada de la torre
 */
    public void popCup() {
        // Validar que hay tazas
        if (cups.isEmpty()) {
            ok = false;
            if (isVisible) {
                System.out.println("Error: No hay tazas en la torre para quitar");
            }
            return;
        }
        
        // Obtener última cup
        Cup cup = cups.get(cups.size() - 1);
        
        // Hacer invisible
        if (isVisible) {
            cup.makeInvisible();
        }
        
        // Quitar de ambas listas
        cups.remove(cups.size() - 1);
        items.remove(cup);
        
        // Reorganizar TODO visualmente
        reorganizeVisualPositions();
        
        ok = true;
    }
    public void popLid() {
    if (lids.isEmpty()) {
        ok = false;
        if (isVisible) {
            System.out.println("Error: No hay tapas en la torre");
        }
        return;
    }
    
    Lid lid = lids.get(lids.size() - 1);
    if (isVisible) lid.makeInvisible();
    
    lids.remove(lids.size() - 1);
    items.remove(lid);
    
    reorganizeVisualPositions();
    ok = true;
}
/**
 * Quita una taza específica de la torre por su número
 * @param i Número de la taza a quitar
 */
public void removeCup(int i) {
    // Buscar la cup
    Cup cupToRemove = null;
    for (Cup cup : cups) {
        if (cup.getNumber() == i) {
            cupToRemove = cup;
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
    
    // Hacer invisible
    if (isVisible) {
        cupToRemove.makeInvisible();
    }
    
    // Quitar de ambas listas
    cups.remove(cupToRemove);
    items.remove(cupToRemove);
    
    // Reorganizar
    reorganizeVisualPositions();
    
    ok = true;
}
/**
 * Quita una tapa específica de la torre por su número
 * @param i Número de la tapa a quitar
 */
public void removeLid(int i) {
    // Buscar la lid
    Lid lidToRemove = null;
    for (Lid lid : lids) {
        if (lid.getNumber() == i) {
            lidToRemove = lid;
            break;
        }
    }
    
    // Validar que existe
    if (lidToRemove == null) {
        ok = false;
        if (isVisible) {
            System.out.println("Error: La tapa " + i + " no está en la torre");
        }
        return;
    }
    
    // Hacer invisible
    if (isVisible) {
        lidToRemove.makeInvisible();
    }
    
    // Quitar de ambas listas
    lids.remove(lidToRemove);
    items.remove(lidToRemove);
    
    // Reorganizar
    reorganizeVisualPositions();
    
    ok = true;
}
/**
 * Ordena todos los elementos de la torre de mayor a menor número
 * Mantiene separación entre cups (abajo) y lids (arriba)
 */
public void orderTower() {
    // Guardar números
    ArrayList<Integer> cupNumbers = new ArrayList<>();
    for (Cup cup : cups) {
        cupNumbers.add(cup.getNumber());
    }
    
    ArrayList<Integer> lidNumbers = new ArrayList<>();
    for (Lid lid : lids) {
        lidNumbers.add(lid.getNumber());
    }
    
    // Ordenar descendente
    cupNumbers.sort((a, b) -> b - a);
    lidNumbers.sort((a, b) -> b - a);
    
    // Limpiar todo
    for (Item item : items) {
        if (isVisible) item.makeInvisible();
    }
    cups.clear();
    lids.clear();
    items.clear();
    
    // Reconstruir ordenado
    for (int num : cupNumbers) {
        pushCup(num);
    }
    for (int num : lidNumbers) {
        pushLid(num);
    }
    
    ok = true;
}
/**
 * Invierte el orden de todos los elementos en la torre
 * Mantiene separación entre cups (abajo) y lids (arriba)
 */
public void reverseTower() {
    // Guardar números
    ArrayList<Integer> cupNumbers = new ArrayList<>();
    for (Cup cup : cups) {
        cupNumbers.add(cup.getNumber());
    }
    
    ArrayList<Integer> lidNumbers = new ArrayList<>();
    for (Lid lid : lids) {
        lidNumbers.add(lid.getNumber());
    }
    
    // Invertir
    java.util.Collections.reverse(cupNumbers);
    java.util.Collections.reverse(lidNumbers);
    
    // Limpiar todo
    for (Item item : items) {
        if (isVisible) item.makeInvisible();
    }
    cups.clear();
    lids.clear();
    items.clear();
    
    // Reconstruir invertido
    for (int num : cupNumbers) {
        pushCup(num);
    }
    for (int num : lidNumbers) {
        pushLid(num);
    }
    
    ok = true;
}
/**
 * Retorna los números de las tazas que están REALMENTE tapadas
 * Una taza está tapada si su tapa está visualmente sobre ella
 * @return Array con números de tazas tapadas
 */
public int[] lidedCups() {
    ArrayList<Integer> tappedCups = new ArrayList<>();
    
    for (Cup cup : cups) {
        int cupNumber = cup.getNumber();
        
        Lid matchingLid = null;
        for (Lid lid : lids) {
            if (lid.getNumber() == cupNumber) {
                matchingLid = lid;
                break;
            }
        }
        
        if (matchingLid != null) {
            int cupIndex = items.indexOf(cup);
            int lidIndex = items.indexOf(matchingLid);
            
            int cupTop = calculateItemBase(cupIndex) + cup.getHeight();
            int lidBase = calculateItemBase(lidIndex);
            
            // DEBUG
            System.out.println("DEBUG Cup" + cupNumber + ":");
            System.out.println("  cupIndex=" + cupIndex + ", lidIndex=" + lidIndex);
            System.out.println("  cupTop=" + cupTop + ", lidBase=" + lidBase);
            System.out.println("  ¿Tapada? " + (cupTop == lidBase));
            
            if (cupTop == lidBase) {
                tappedCups.add(cupNumber);
            }
        }
    }
    
    int[] result = new int[tappedCups.size()];
    for (int i = 0; i < tappedCups.size(); i++) {
        result[i] = tappedCups.get(i);
    }
    
    return result;
}

    private void reorganizeVisualPositions() {
        // Solo reorganizar si está visible
        if (!isVisible) {
            return;
        }
        
        // Recorrer items y redibujar cada uno
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            
            // Calcular posición base del item
            int base = calculateItemBase(i);
            
            // Calcular dimensiones y posición visual
            int itemWidth = item.getNumber() * BASE_WIDTH;
            int itemHeight = item.getHeight() * PIXELS_PER_CM;
            
            int frameWidth = width * BASE_WIDTH;
            int xPos = TOWER_MARGIN + (frameWidth - itemWidth) / 2;
            int yPos = TOWER_MARGIN + (maxHeight - base - item.getHeight()) * PIXELS_PER_CM;
            
            // Redibujar usando el nuevo método redraw()
            if (item instanceof Cup) {
                ((Cup) item).redraw(itemHeight, itemWidth, xPos, yPos, item.getColor());
            } else if (item instanceof Lid) {
                ((Lid) item).redraw(itemHeight, itemWidth, xPos, yPos, item.getColor());
            }
            
            // Hacer visible
            item.makeVisible();
        }
            
    } 
}