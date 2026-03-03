import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Collections;
import java.util.Comparator;

/**
 * Clase principal del simulador StackingItems.
 * Versión LIMPIA con una sola lista (items)
 * 
 * @author Hever
 * @version 4.0 - Reestructuración con 1 lista
 */
public class Tower {
    // CONSTANTES
    private static final String[] COLORS = {"red", "blue", "green", "yellow", "magenta"};
    private static final int PIXELS_PER_CM = 10;
    private static final int BASE_WIDTH = 20;
    private static final int TOWER_MARGIN = 50;
    
    // ATRIBUTOS
    private int width;
    private int maxHeight;
    private boolean isVisible;
    private boolean ok;
    
    // ÚNICA LISTA (fuente de verdad)
    private ArrayList<Item> items;
    
    // VISUALIZACIÓN
    private Canvas canvas;
    private Rectangle towerFrame;
    private ArrayList<Rectangle> heightMarks;
    
    /**
     * Constructor básico
     */
    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.isVisible = false;
        this.ok = true;
        
        this.items = new ArrayList<Item>();
        this.heightMarks = new ArrayList<Rectangle>();
        
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
    
    public boolean ok() {
        return ok;
    }
    
    public void exit() {
        System.exit(0);
    }

    /**
     * Crea una torre con n tazas: hace visible la simulación y añade
     * tazas numeradas desde 1 hasta n (se detiene si alguna no cabe).
     *
     * @param n número de tazas a crear
     */
    public void createTowerWithNCups(int n) {
        // Hacer visible la simulación antes de agregar elementos (según tu pedido)
        makeVisible();

        // Limpiar cualquier elemento existente para crear desde cero
        items.clear();

        // Intentar agregar tazas 1..n; pushCup validará espacio y existencia
        for (int i = 1; i <= n; i++) {
            pushCup(i);
            // Si ocurrió un error de espacio, paramos la creación
            if (!ok) {
                break;
            }
        }

        // Forzar un redibujo final (pushCup ya llama a redrawAll, pero
        // si quitamos elementos previos queremos asegurarnos de mostrar todo)
        redrawAll();
    }
    
    /**
     * Busca el índice de un item por su tipo ("cup" o "lid") y número.
     * Retorna -1 si no se encuentra.
     */
    private int findItemIndex(String type, int number) {
        if (type == null) return -1;
        String t = type.toLowerCase();
        if (!t.equals("cup") && !t.equals("lid")) return -1;
        for (int i = 0; i < items.size(); i++) {
            Item it = items.get(i);
            if (it.getType().equals(t) && it.getNumber() == number) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Intercambia dos items identificados explícitamente por tipo y número.
     * Acepta intercambios cup<->cup, lid<->lid y cup<->lid.
     * Después del intercambio valida la lista y redibuja la torre.
     *
     * @param typeA "cup" o "lid" para el primer item
     * @param numberA número del primer item
     * @param typeB "cup" o "lid" para el segundo item
     * @param numberB número del segundo item
     */
    public void swapItems(String typeA, int numberA, String typeB, int numberB) {
        // Validar tipos
        if (typeA == null || typeB == null) {
            ok = false;
            if (isVisible) JOptionPane.showMessageDialog(null, "Error: tipo nulo en swap");
            else System.out.println("Error: tipo nulo en swap");
            return;
        }
        String ta = typeA.toLowerCase();
        String tb = typeB.toLowerCase();
        if ((!ta.equals("cup") && !ta.equals("lid")) || (!tb.equals("cup") && !tb.equals("lid"))) {
            ok = false;
            if (isVisible) JOptionPane.showMessageDialog(null, "Error: tipos inválidos para swap (usar 'cup' o 'lid')");
            else System.out.println("Error: tipos inválidos para swap (usar 'cup' o 'lid')");
            return;
        }

        int idxA = findItemIndex(ta, numberA);
        int idxB = findItemIndex(tb, numberB);

        if (idxA == -1 || idxB == -1) {
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: no se encontraron los items especificados para swap");
            } else {
                System.out.println("Error: no se encontraron los items especificados para swap");
            }
            return;
        }

        // Realizar swap
        Collections.swap(items, idxA, idxB);

        // Validación básica: recalcular altura y marcar ok
        int h = calculateHeight();
        if (h > maxHeight) {
            ok = false;
            if (isVisible) JOptionPane.showMessageDialog(null, "Advertencia: el intercambio generó una torre que supera la altura máxima");
            else System.out.println("Advertencia: el intercambio generó una torre que supera la altura máxima");
        } else {
            ok = true;
        }

        // Redibujar según nueva lista
        redrawAll();
    }

    /**
     * Reorganiza la lista de items de forma que cada taza esté inmediatamente
     * seguida por su tapa correspondiente (si existe).
     * Por ejemplo: [c4, c2, c3, l2, l4, l3] → [c4, l4, c3, l3, c2, l2]
     * Las tazas que tienen su tapa se marcan como "tapadas" (color gris).
     */
    public void coverAvailableCups() {
        ArrayList<Item> reorganized = new ArrayList<>();
        ArrayList<Boolean> processed = new ArrayList<>();
        
        // Inicializar processed como false para todos los items
        for (int i = 0; i < items.size(); i++) {
            processed.add(false);
        }

        // Recorrer la lista y procesar tazas seguidas de sus tapas
        for (int i = 0; i < items.size(); i++) {
            if (!processed.get(i)) {
                Item current = items.get(i);
                
                // Si es una taza
                if (current instanceof Cup) {
                    Cup cup = (Cup) current;
                    reorganized.add(cup);
                    processed.set(i, true);
                    
                    // Buscar su tapa correspondiente
                    for (int j = 0; j < items.size(); j++) {
                        if (!processed.get(j) && items.get(j) instanceof Lid) {
                            Lid lid = (Lid) items.get(j);
                            if (lid.getNumber() == cup.getNumber()) {
                                reorganized.add(lid);
                                processed.set(j, true);
                                cup.setTapped(true);  // Marcar taza como tapada
                                break;
                            }
                        }
                    }
                }
                // Si es una tapa que no fue emparejada (procesar en siguiente iteración)
                else if (current instanceof Lid) {
                    reorganized.add(current);
                    processed.set(i, true);
                }
            }
        }

        // Actualizar la lista de items con la reorganizada
        items = reorganized;
        
        // Redibujar para aplicar los cambios visuales
        redrawAll();
        ok = true;
    }
    
    /**
 * Agrega una taza a la torre
 * @param i Número de la taza (determina altura = 2i-1 y diámetro)
 */
public void pushCup(int i) {
    // 1. Validar que no existe
    if (itemExists("cup", i)) {
        ok = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, "Error: La taza " + i + " ya está en la torre");
        } else {
            System.out.println("Error: La taza " + i + " ya está en la torre");
        }
        return;
    }
    
    // 2. Crear cup
    String color = COLORS[i % COLORS.length];
    Cup cup = new Cup(i, color);
    
    // 3. Validar altura
    int currentHeight = calculateHeight();
    if (currentHeight + cup.getHeight() > maxHeight) {
        ok = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, "Error: No hay espacio para la taza " + i);
        } else {
            System.out.println("Error: No hay espacio para la taza " + i);
        }
        return;
    }
    
    // 4. Agregar a items
    items.add(cup);
    
    // 5. FORMATEAR TODO
    redrawAll();
    
    ok = true;
}
/**
 * Verifica si existe un item de cierto tipo y número
 */
private boolean itemExists(String type, int number) {
    for (Item item : items) {
        if (item.getType().equals(type) && item.getNumber() == number) {
            return true;
        }
    }
    return false;
}

/**
 * Calcula altura total de la torre
 */
private int calculateHeight() {
    if (items.isEmpty()) {
        return 0;
    }
    
    int maxTop = 0;
    for (int i = 0; i < items.size(); i++) {
        int base = calculateItemBase(i);
        int top = base + items.get(i).getHeight();
        if (top > maxTop) {
            maxTop = top;
        }
    }
    
    return maxTop;
}

/**
 * Redibuja TODA la torre desde cero
 */
private void redrawAll() {
    if (!isVisible) return;
    
    // 1. BORRAR TODO
    canvas.eraseAll();
    
    // 2. RECREAR FRAME
    towerFrame = new Rectangle();
    int frameWidth = width * BASE_WIDTH;
    int frameHeight = maxHeight * PIXELS_PER_CM;
    towerFrame.changeSize(frameHeight, frameWidth);
    towerFrame.moveHorizontal(TOWER_MARGIN);
    towerFrame.moveVertical(TOWER_MARGIN);
    towerFrame.changeColor("black");
    towerFrame.makeVisible();
    
    // 3. RECREAR MARKS
    heightMarks.clear();
    for (int i = 0; i <= maxHeight; i++) {
        Rectangle mark = new Rectangle();
        mark.changeSize(2, 5);
        mark.moveHorizontal(TOWER_MARGIN - 10);
        mark.moveVertical(TOWER_MARGIN + (maxHeight - i) * PIXELS_PER_CM);
        mark.changeColor("black");
        mark.makeVisible();
        heightMarks.add(mark);
    }
    
    // 4. REDIBUJAR ITEMS
    for (int i = 0; i < items.size(); i++) {
        Item item = items.get(i);
        int base = calculateItemBase(i);
        
        int itemWidth = item.getNumber() * BASE_WIDTH;
        int itemHeight = item.getHeight() * PIXELS_PER_CM;
        int xPos = TOWER_MARGIN + (frameWidth - itemWidth) / 2;
        int yPos = TOWER_MARGIN + (maxHeight - base - item.getHeight()) * PIXELS_PER_CM;
        
        if (item instanceof Cup) {
            ((Cup) item).redraw(itemHeight, itemWidth, xPos, yPos, item.getColor());
        } else if (item instanceof Lid) {
            ((Lid) item).redraw(itemHeight, itemWidth, xPos, yPos, item.getColor());
        }
        
        item.makeVisible();
    }
}

    /**
 * Calcula la base de un item según las reglas de apilamiento
 * @param index Índice del item en items
 * @return Posición base en cm desde el suelo
 */
private int calculateItemBase(int index) {
    // CASO BASE: Primer elemento
    if (index == 0) {
        return 0;
    } 
    Item current = items.get(index);
    Item previous = items.get(index - 1);
    // REGLA 0: Si anterior es LID y actual también y número mayor → aplicar lógica especial
    if (previous.getType().equals("lid") && current.getType().equals("lid")) {
        if (current.getNumber() > previous.getNumber()) {
            return calculateLidBase(index, current.getNumber());
        }
    }
    // REGLA 1: Si anterior es LID → SIEMPRE ENCIMA
    if (previous.getType().equals("lid")) {
        int prevBase = calculateItemBase(index - 1);
        return prevBase + previous.getHeight();
    }
    // REGLA 2: Anterior es CUP, Actual es CUP → Comparar tamaños
    if (current.getType().equals("cup")) {
        if (current.getNumber() < previous.getNumber()) {
            return calculateItemBase(index - 1) + 1;  // DENTRO
        } else {
            int prevBase = calculateItemBase(index - 1);
            return prevBase + previous.getHeight();  // ENCIMA
        }
    }
    // REGLA 3: Anterior es CUP, Actual es LID → Lógica especial
    if (current.getType().equals("lid")) {
        return calculateLidBase(index, current.getNumber());
    }
    
    return 0;  // No debería llegar aquí
}

/**
 * Calcula la base de una lid usando lógica especial
 * Busca hacia atrás en las cups agregadas
 */
private int calculateLidBase(int lidIndex, int lidNumber) {
    // Obtener cups en orden de agregación
    ArrayList<Cup> cups = new ArrayList<>();
    for (int i = 0; i < lidIndex; i++) {
        if (items.get(i) instanceof Cup) {
            cups.add((Cup) items.get(i));
        }
    }
    if (cups.isEmpty()) {
        return 0;
    }
    int numCups = cups.size();
    Cup cup1 = numCups >= 1 ? cups.get(numCups - 1) : null;  // Última
    Cup cup2 = numCups >= 2 ? cups.get(numCups - 2) : null;  // Penúltima
    Cup cup3 = numCups >= 3 ? cups.get(numCups - 3) : null;  // Antepenúltima
     // CASO 1: lid < cup-1 → DENTRO
    if (cup1 != null && lidNumber < cup1.getNumber()) {
        int cup1Index = items.indexOf(cup1);
        int cup1Base = calculateItemBase(cup1Index);
        return cup1Base + 1;
    }
    // CASO 2: lid == cup-1 → TAPA cup-1
    if (cup1 != null && lidNumber == cup1.getNumber()) {
        int cup1Index = items.indexOf(cup1);
        int cup1Base = calculateItemBase(cup1Index);
        return cup1Base + cup1.getHeight();
    }
    // CASO 3: lid > cup-1 Y lid < cup-2 → ENCIMA de cup-1
    if (cup2 != null && lidNumber < cup2.getNumber()) {
        int cup1Index = items.indexOf(cup1);
        int cup1Base = calculateItemBase(cup1Index);
        return cup1Base + cup1.getHeight();
    }
    // CASO 4: lid > cup-1 Y lid == cup-2 → TAPA cup-2
    if (cup2 != null && lidNumber == cup2.getNumber()) {
        int cup2Index = items.indexOf(cup2);
        int cup2Base = calculateItemBase(cup2Index);
        return cup2Base + cup2.getHeight();
    }
    // CASO 5: lid > cup-1 Y lid > cup-2 → buscar cup-3
    if (cup3 != null) {
        int cup3Index = items.indexOf(cup3);
        int cup3Base = calculateItemBase(cup3Index);
        int cup3Top = cup3Base + cup3.getHeight();
        
        int cup1Index = items.indexOf(cup1);
        int cup1Base = calculateItemBase(cup1Index);
        int cup1Top = cup1Base + cup1.getHeight();
        
        if (cup1Top > cup3Top) {
            return cup1Top;  // TOPE
        } else {
            return cup3Top;  // TAPA cup-3
        }
    }
    // Por defecto: encima de cup-1
    int cup1Index = items.indexOf(cup1);
    int cup1Base = calculateItemBase(cup1Index);
    return cup1Base + cup1.getHeight();
}
/**
 * Agrega una tapa a la torre
 * @param i Número de la tapa
 */
public void pushLid(int i) {
    // 1. Validar que no existe
    if (itemExists("lid", i)) {
        ok = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, "Error: La tapa " + i + " ya está en la torre");
        } else {
            System.out.println("Error: La tapa " + i + " ya está en la torre");
        }
        return;
    }
    
    // 2. Crear lid
        String color = COLORS[i % COLORS.length];
        Lid lid = new Lid(i, color);
        // Si ya existe la taza correspondiente, no cambiar el color de la tapa.
        // (Las tapas mantienen su color original ahora.)
        int cupIdx = findItemIndex("cup", i);
        if (cupIdx != -1 && items.get(cupIdx) instanceof Cup) {
            // No se cambia el color de la tapa para preservar apariencia original
        }
    
    // 3. Validar altura
    int currentHeight = calculateHeight();
    if (currentHeight + lid.getHeight() > maxHeight) {
        ok = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, "Error: No hay espacio para la tapa " + i);
        } else {
            System.out.println("Error: No hay espacio para la tapa " + i);
        }
        return;
    }
    
    // 4. Agregar a items
    items.add(lid);
    
    // 5. FORMATEAR TODO
    redrawAll();
    
    ok = true;
}

    /**
     * Elimina la última taza agregada a la torre.
     * Si no hay tazas, marca error.
     */
    public void popCup() {
        int idx = -1;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).getType().equals("cup")) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: no hay tazas para eliminar");
            } else {
                System.out.println("Error: no hay tazas para eliminar");
            }
            return;
        }
        items.remove(idx);
        redrawAll();
        ok = true;
    }

    /**
     * Elimina la última tapa agregada a la torre.
     * Si no hay tapas, marca error.
     */
    public void popLid() {
        int idx = -1;
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i).getType().equals("lid")) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: no hay tapas para eliminar");
            } else {
                System.out.println("Error: no hay tapas para eliminar");
            }
            return;
        }
        items.remove(idx);
        redrawAll();
        ok = true;
    }

    /**
     * Remueve una taza específica (por número) de la torre.
     * Si el ítem no existe muestra mensaje.
                                // Asegurar que la tapa tenga el color de la taza
                                lid.setColor(cup.getColor());
                                reorganized.add(lid);
    public void removeCup(int number) {
        int idx = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getType().equals("cup") && items.get(i).getNumber() == number) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: la taza " + number + " no está en la torre");
            } else {
                System.out.println("Error: la taza " + number + " no está en la torre");
            }
            return;
        }
        items.remove(idx);
        redrawAll();
        ok = true;
    }

    /**
     * Remueve una tapa específica (por número) de la torre.
     * Si el ítem no existe muestra mensaje.
     */
    public void removeLid(int number) {
        int idx = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getType().equals("lid") && items.get(i).getNumber() == number) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: la tapa " + number + " no está en la torre");
            } else {
                System.out.println("Error: la tapa " + number + " no está en la torre");
            }
            return;
        }
        items.remove(idx);
        redrawAll();
        ok = true;
    }

    /**
     * Retorna información de todos los elementos apilados
     * @return Matriz donde cada fila es [tipo, número]
     */
public String[][] stackingItems() {
    String[][] result = new String[items.size()][2];
    
    for (int i = 0; i < items.size(); i++) {
        Item item = items.get(i);
        result[i][0] = item.getType();
        result[i][1] = String.valueOf(item.getNumber());
    }
    
    if (isVisible) {
        StringBuilder sb = new StringBuilder();
        for (String[] row : result) {
            sb.append(row[0]).append(" ").append(row[1]).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Elementos apilados", JOptionPane.INFORMATION_MESSAGE);
    }

    return result;
}

    /**
     * Retorna información de las tazas que actualmente están tapadas por su
     * correspondiente tapa. Se considera tapa "respectiva" si tiene el mismo número
     * y su base coincide con la parte superior de la taza (esto cubre casos de tapa
     * inmediata y el caso especial 4).
     *
     * @return matriz donde cada fila es ["cup", número]
     */
    public String[][] tappedCupsInfo() {
        ArrayList<String[]> list = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof Cup) {
                Cup cup = (Cup) items.get(i);
                int cupBase = calculateItemBase(i);
                int cupTop = cupBase + cup.getHeight();
                // buscar tapa correspondiente
                for (int j = 0; j < items.size(); j++) {
                    if (items.get(j) instanceof Lid) {
                        Lid lid = (Lid) items.get(j);
                        if (lid.getNumber() == cup.getNumber()) {
                            int lidBase = calculateItemBase(j);
                            if (lidBase == cupTop) {
                                list.add(new String[]{"cup", String.valueOf(cup.getNumber())});
                                break;
                            }
                        }
                    }
                }
            }
        }
        String[][] result = new String[list.size()][2];
        for (int k = 0; k < list.size(); k++) {
            result[k] = list.get(k);
        }
        if (isVisible) {
            StringBuilder sb = new StringBuilder();
            for (String[] row : result) {
                sb.append(row[0]).append(" ").append(row[1]).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "Tazas tapadas", JOptionPane.INFORMATION_MESSAGE);
        }
        return result;
    }
/**
 * Retorna la altura total de la torre
 * @return Altura en centímetros
 */
public int height() {
        int h = calculateHeight();
        if (isVisible) {
            JOptionPane.showMessageDialog(null, "Altura total de la torre: " + h + " cm", "Altura", JOptionPane.INFORMATION_MESSAGE);
        }
        return h;
    }

    /**
     * Reordena los elementos de la torre por número descendente.
     * Solo se mantienen aquellos que, en la nueva pila, no excedan la altura máxima.
     */
    public void sortDescending() {
        ArrayList<Item> sorted = new ArrayList<>(items);
        sorted.sort(Comparator.comparingInt(Item::getNumber).reversed());
        
        // Asignar temporalmente para validar altura real
        items = sorted;
        int h = calculateHeight();
        
        if (h > maxHeight) {
            // Filtrar desde el final hasta que quepa
            while (h > maxHeight && !items.isEmpty()) {
                items.remove(items.size() - 1);
                h = calculateHeight();
            }
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Advertencia: se removieron elementos al ordenar para cumplir altura máxima");
            } else {
                System.out.println("Advertencia: se removieron elementos al ordenar para cumplir altura máxima");
            }
        } else {
            ok = true;
        }
        
        redrawAll();
    }

    /**
     * Invierte el orden actual de la torre.
     * Solo se mantienen los que quepan bajo el límite de altura.
     */
    public void reverseOrder() {
        ArrayList<Item> rev = new ArrayList<>(items);
        Collections.reverse(rev);
        
        // Asignar temporalmente para validar altura real
        items = rev;
        int h = calculateHeight();
        
        if (h > maxHeight) {
            // Filtrar desde el final hasta que quepa
            while (h > maxHeight && !items.isEmpty()) {
                items.remove(items.size() - 1);
                h = calculateHeight();
            }
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Advertencia: se removieron elementos al invertir para cumplir altura máxima");
            } else {
                System.out.println("Advertencia: se removieron elementos al invertir para cumplir altura máxima");
            }
        } else {
            ok = true;
        }
        
        redrawAll();
    }

    /**
     * Busca un intercambio de dos items que reduzca la altura total de la torre.
     * Retorna un array con [typeA, numberA, typeB, numberB] si encuentra uno,
     * o null si no hay intercambio que reduzca la altura.
     * 
     * @return array [String typeA, int numberA, String typeB, int numberB] o null
     */
    public Object[] findReducingSwap() {
        int currentHeight = calculateHeight();
        Object[] bestSwap = null;
        int bestHeight = currentHeight;
        
        // Probar todos los pares de items
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                Item itemI = items.get(i);
                Item itemJ = items.get(j);
                
                // Intercambiar temporalmente
                Collections.swap(items, i, j);
                int newHeight = calculateHeight();
                
                // Si la nueva altura es menor y el mejor encontrado, guardar
                if (newHeight < bestHeight) {
                    bestHeight = newHeight;
                    bestSwap = new Object[]{
                        itemI.getType(), 
                        itemI.getNumber(), 
                        itemJ.getType(), 
                        itemJ.getNumber()
                    };
                }
                
                // Revertir intercambio
                Collections.swap(items, i, j);
            }
        }
        
        // Si encontr� un intercambio que reduce altura, ejecutarlo y retornar
        if (bestSwap != null) {
            String typeA = (String) bestSwap[0];
            int numA = (int) bestSwap[1];
            String typeB = (String) bestSwap[2];
            int numB = (int) bestSwap[3];
            
            swapItems(typeA, numA, typeB, numB);
            
            if (isVisible) {
                JOptionPane.showMessageDialog(null, 
                    "Se ejecut� intercambio que reduce altura de " + currentHeight + " cm a " + calculateHeight() + " cm");
            } else {
                System.out.println("Intercambio que reduce altura: " + currentHeight + "  " + calculateHeight() + " cm");
            }
            return bestSwap;
        }
        
        ok = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, "No se encontr� intercambio que reduzca la altura");
        } else {
            System.out.println("No se encontr� intercambio que reduzca la altura");
        }
        return null;
    }
}
