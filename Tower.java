import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Collections;
import java.util.Comparator;

/**
 * Clase principal del simulador StackingItems.
 * Version con una sola lista (items)
 * 
 * @author Hever
 * @version 5.0 - Nombres corregidos segun especificacion
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
    
    // UNICA LISTA (fuente de verdad)
    private ArrayList<Item> items;
    
    // VISUALIZACION
    private Canvas canvas;
    private Rectangle towerFrame;
    private ArrayList<Rectangle> heightMarks;
    
    /**
     * Constructor basico
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
    
    /**
     * Constructor del ciclo 2: crea una torre con n tazas (sin tapas).
     * Tazas numeradas de 1 a cups, con alturas 1, 3, 5, ..., 2n-1.
     * @param cups Numero de tazas a crear
     */
    public Tower(int cups) {
        this.width = cups;
        this.maxHeight = cups * cups;
        this.isVisible = false;
        this.ok = true;
        
        this.items = new ArrayList<Item>();
        this.heightMarks = new ArrayList<Rectangle>();
        
        canvas = Canvas.getCanvas();
        createTowerFrame();
        createHeightMarks();
        
        for (int i = 1; i <= cups; i++) {
            String color = COLORS[i % COLORS.length];
            Cup cup = new Cup(i, color);
            items.add(cup);
        }
        ok = true;
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
     * Busca el indice de un item por su tipo ("cup" o "lid") y numero.
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
     * Intercambia dos items identificados por tipo y numero.
     * Acepta intercambios cup<->cup, lid<->lid y cup<->lid.
     *
     * @param typeA "cup" o "lid" para el primer item
     * @param numberA numero del primer item
     * @param typeB "cup" o "lid" para el segundo item
     * @param numberB numero del segundo item
     */
    public void swap(String typeA, int numberA, String typeB, int numberB) {
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
            if (isVisible) JOptionPane.showMessageDialog(null, "Error: tipos invalidos para swap (usar 'cup' o 'lid')");
            else System.out.println("Error: tipos invalidos para swap (usar 'cup' o 'lid')");
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

        Collections.swap(items, idxA, idxB);

        int h = calculateHeight();
        if (h > maxHeight) {
            ok = false;
            if (isVisible) JOptionPane.showMessageDialog(null, "Advertencia: el intercambio genero una torre que supera la altura maxima");
            else System.out.println("Advertencia: el intercambio genero una torre que supera la altura maxima");
        } else {
            ok = true;
        }

        redrawAll();
    }

    /**
     * Tapa las tazas que tienen sus tapas correspondientes en la torre.
     * Reorganiza la lista para que cada taza quede seguida de su tapa.
     */
    public void cover() {
        ArrayList<Item> reorganized = new ArrayList<>();
        ArrayList<Boolean> processed = new ArrayList<>();
        
        for (int i = 0; i < items.size(); i++) {
            processed.add(false);
        }

        for (int i = 0; i < items.size(); i++) {
            if (!processed.get(i)) {
                Item current = items.get(i);
                
                if (current instanceof Cup) {
                    Cup cup = (Cup) current;
                    reorganized.add(cup);
                    processed.set(i, true);
                    
                    for (int j = 0; j < items.size(); j++) {
                        if (!processed.get(j) && items.get(j) instanceof Lid) {
                            Lid lid = (Lid) items.get(j);
                            if (lid.getNumber() == cup.getNumber()) {
                                reorganized.add(lid);
                                processed.set(j, true);
                                cup.setTapped(true);
                                break;
                            }
                        }
                    }
                } else if (current instanceof Lid) {
                    reorganized.add(current);
                    processed.set(i, true);
                }
            }
        }

        items = reorganized;
        redrawAll();
        ok = true;
    }
    
    /**
     * Agrega una taza a la torre
     * @param i Numero de la taza (determina altura = 2i-1 y diametro)
     */
    public void pushCup(int i) {
        if (itemExists("cup", i)) {
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: La taza " + i + " ya esta en la torre");
            } else {
                System.out.println("Error: La taza " + i + " ya esta en la torre");
            }
            return;
        }
        
        String color = COLORS[i % COLORS.length];
        Cup cup = new Cup(i, color);
        
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
        
        items.add(cup);
        redrawAll();
        ok = true;
    }

    /**
     * Verifica si existe un item de cierto tipo y numero
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
     * Redibuja toda la torre desde cero
     */
    private void redrawAll() {
        if (!isVisible) return;
        
        canvas.eraseAll();
        
        towerFrame = new Rectangle();
        int frameWidth = width * BASE_WIDTH;
        int frameHeight = maxHeight * PIXELS_PER_CM;
        towerFrame.changeSize(frameHeight, frameWidth);
        towerFrame.moveHorizontal(TOWER_MARGIN);
        towerFrame.moveVertical(TOWER_MARGIN);
        towerFrame.changeColor("black");
        towerFrame.makeVisible();
        
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
     * Calcula la base de un item segun las reglas de apilamiento
     * @param index Indice del item en items
     * @return Posicion base en cm desde el suelo
     */
    private int calculateItemBase(int index) {
        if (index == 0) {
            return 0;
        } 
        Item current = items.get(index);
        Item previous = items.get(index - 1);
        
        if (previous.getType().equals("lid") && current.getType().equals("lid")) {
            if (current.getNumber() > previous.getNumber()) {
                return calculateLidBase(index, current.getNumber());
            }
        }
        
        if (previous.getType().equals("lid")) {
            int prevBase = calculateItemBase(index - 1);
            return prevBase + previous.getHeight();
        }
        
        if (current.getType().equals("cup")) {
            if (current.getNumber() < previous.getNumber()) {
                return calculateItemBase(index - 1) + 1;
            } else {
                int prevBase = calculateItemBase(index - 1);
                return prevBase + previous.getHeight();
            }
        }
        
        if (current.getType().equals("lid")) {
            return calculateLidBase(index, current.getNumber());
        }
        
        return 0;
    }

    /**
     * Calcula la base de una lid usando logica especial
     * Busca hacia atras en las cups agregadas
     */
    private int calculateLidBase(int lidIndex, int lidNumber) {
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
        Cup cup1 = numCups >= 1 ? cups.get(numCups - 1) : null;
        Cup cup2 = numCups >= 2 ? cups.get(numCups - 2) : null;
        Cup cup3 = numCups >= 3 ? cups.get(numCups - 3) : null;
        
        if (cup1 != null && lidNumber < cup1.getNumber()) {
            int cup1Index = items.indexOf(cup1);
            int cup1Base = calculateItemBase(cup1Index);
            return cup1Base + 1;
        }
        
        if (cup1 != null && lidNumber == cup1.getNumber()) {
            int cup1Index = items.indexOf(cup1);
            int cup1Base = calculateItemBase(cup1Index);
            return cup1Base + cup1.getHeight();
        }
        
        if (cup2 != null && lidNumber < cup2.getNumber()) {
            int cup1Index = items.indexOf(cup1);
            int cup1Base = calculateItemBase(cup1Index);
            return cup1Base + cup1.getHeight();
        }
        
        if (cup2 != null && lidNumber == cup2.getNumber()) {
            int cup2Index = items.indexOf(cup2);
            int cup2Base = calculateItemBase(cup2Index);
            return cup2Base + cup2.getHeight();
        }
        
        if (cup3 != null) {
            int cup3Index = items.indexOf(cup3);
            int cup3Base = calculateItemBase(cup3Index);
            int cup3Top = cup3Base + cup3.getHeight();
            
            int cup1Index = items.indexOf(cup1);
            int cup1Base = calculateItemBase(cup1Index);
            int cup1Top = cup1Base + cup1.getHeight();
            
            if (cup1Top > cup3Top) {
                return cup1Top;
            } else {
                return cup3Top;
            }
        }
        
        int cup1Index = items.indexOf(cup1);
        int cup1Base = calculateItemBase(cup1Index);
        return cup1Base + cup1.getHeight();
    }

    /**
     * Agrega una tapa a la torre
     * @param i Numero de la tapa
     */
    public void pushLid(int i) {
        if (itemExists("lid", i)) {
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Error: La tapa " + i + " ya esta en la torre");
            } else {
                System.out.println("Error: La tapa " + i + " ya esta en la torre");
            }
            return;
        }
        
        String color = COLORS[i % COLORS.length];
        Lid lid = new Lid(i, color);
        
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
        
        items.add(lid);
        redrawAll();
        ok = true;
    }

    /**
     * Elimina la ultima taza agregada a la torre.
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
     * Elimina la ultima tapa agregada a la torre.
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
     * Remueve una taza especifica (por numero) de la torre.
     * Si el item no existe muestra mensaje.
     */
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
                JOptionPane.showMessageDialog(null, "Error: la taza " + number + " no esta en la torre");
            } else {
                System.out.println("Error: la taza " + number + " no esta en la torre");
            }
            return;
        }
        items.remove(idx);
        redrawAll();
        ok = true;
    }

    /**
     * Remueve una tapa especifica (por numero) de la torre.
     * Si el item no existe muestra mensaje.
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
                JOptionPane.showMessageDialog(null, "Error: la tapa " + number + " no esta en la torre");
            } else {
                System.out.println("Error: la tapa " + number + " no esta en la torre");
            }
            return;
        }
        items.remove(idx);
        redrawAll();
        ok = true;
    }

    /**
     * Retorna informacion de todos los elementos apilados
     * @return Matriz donde cada fila es [tipo, numero]
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
     * Retorna los numeros de las tazas tapadas por su tapa correspondiente,
     * ordenados de menor a mayor.
     * @return arreglo con los numeros de las tazas tapadas
     */
    public int[] lidedCups() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof Cup) {
                Cup cup = (Cup) items.get(i);
                int cupBase = calculateItemBase(i);
                int cupTop = cupBase + cup.getHeight();
                for (int j = 0; j < items.size(); j++) {
                    if (items.get(j) instanceof Lid) {
                        Lid lid = (Lid) items.get(j);
                        if (lid.getNumber() == cup.getNumber()) {
                            int lidBase = calculateItemBase(j);
                            if (lidBase == cupTop) {
                                list.add(cup.getNumber());
                                break;
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(list);
        int[] result = new int[list.size()];
        for (int k = 0; k < list.size(); k++) {
            result[k] = list.get(k);
        }
        if (isVisible) {
            StringBuilder sb = new StringBuilder("Tazas tapadas: ");
            for (int n : result) {
                sb.append(n).append(" ");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "Tazas tapadas", JOptionPane.INFORMATION_MESSAGE);
        }
        return result;
    }

    /**
     * Retorna la altura total de la torre
     * @return Altura en centimetros
     */
    public int height() {
        int h = calculateHeight();
        if (isVisible) {
            JOptionPane.showMessageDialog(null, "Altura total de la torre: " + h + " cm", "Altura", JOptionPane.INFORMATION_MESSAGE);
        }
        return h;
    }

    /**
     * Reordena los elementos de la torre por numero descendente.
     * Solo se mantienen aquellos que no excedan la altura maxima.
     */
    public void orderTower() {
        ArrayList<Item> sorted = new ArrayList<>(items);
        sorted.sort(Comparator.comparingInt(Item::getNumber).reversed());
        
        items = sorted;
        int h = calculateHeight();
        
        if (h > maxHeight) {
            while (h > maxHeight && !items.isEmpty()) {
                items.remove(items.size() - 1);
                h = calculateHeight();
            }
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Advertencia: se removieron elementos al ordenar para cumplir altura maxima");
            } else {
                System.out.println("Advertencia: se removieron elementos al ordenar para cumplir altura maxima");
            }
        } else {
            ok = true;
        }
        
        redrawAll();
    }

    /**
     * Invierte el orden actual de la torre.
     * Solo se mantienen los que quepan bajo el limite de altura.
     */
    public void reverseTower() {
        ArrayList<Item> rev = new ArrayList<>(items);
        Collections.reverse(rev);
        
        items = rev;
        int h = calculateHeight();
        
        if (h > maxHeight) {
            while (h > maxHeight && !items.isEmpty()) {
                items.remove(items.size() - 1);
                h = calculateHeight();
            }
            ok = false;
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "Advertencia: se removieron elementos al invertir para cumplir altura maxima");
            } else {
                System.out.println("Advertencia: se removieron elementos al invertir para cumplir altura maxima");
            }
        } else {
            ok = true;
        }
        
        redrawAll();
    }

    /**
     * Busca un intercambio de dos items que reduzca la altura total de la torre.
     * Retorna la informacion de los items a intercambiar o null si no hay mejora.
     * @return String[][] con {{typeA, numA}, {typeB, numB}} o null
     */
    public String[][] swapToReduce() {
        int currentHeight = calculateHeight();
        String[][] bestSwap = null;
        int bestHeight = currentHeight;
        
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                Item itemI = items.get(i);
                Item itemJ = items.get(j);
                
                Collections.swap(items, i, j);
                int newHeight = calculateHeight();
                
                if (newHeight < bestHeight) {
                    bestHeight = newHeight;
                    bestSwap = new String[][]{
                        {itemI.getType(), String.valueOf(itemI.getNumber())},
                        {itemJ.getType(), String.valueOf(itemJ.getNumber())}
                    };
                }
                
                Collections.swap(items, i, j);
            }
        }
        
        if (bestSwap != null) {
            String typeA = bestSwap[0][0];
            int numA = Integer.parseInt(bestSwap[0][1]);
            String typeB = bestSwap[1][0];
            int numB = Integer.parseInt(bestSwap[1][1]);
            
            swap(typeA, numA, typeB, numB);
            
            if (isVisible) {
                JOptionPane.showMessageDialog(null, 
                    "Se ejecuto intercambio que reduce altura de " + currentHeight + " cm a " + calculateHeight() + " cm");
            } else {
                System.out.println("Intercambio que reduce altura: " + currentHeight + " -> " + calculateHeight() + " cm");
            }
            return bestSwap;
        }
        
        ok = false;
        if (isVisible) {
            JOptionPane.showMessageDialog(null, "No se encontro intercambio que reduzca la altura");
        } else {
            System.out.println("No se encontro intercambio que reduzca la altura");
        }
        return null;
    }
}