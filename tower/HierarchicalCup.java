package tower;

import java.util.ArrayList;
import java.util.Collections;

public class HierarchicalCup extends Cup {

    public HierarchicalCup(int number, String color) {
        super(number, color);
    }

    @Override
    public void processEntry(ArrayList<Item> items) {
        // 1. Buscamos dónde estamos parados ahora (al final de la lista)
        int miPosicion = items.indexOf(this);
        
        // 2. Mientras no estemos en el suelo...
        while (miPosicion > 0) {
            Item elDeAbajo = items.get(miPosicion - 1);
            
            // 3. Si soy más grande que el de abajo, me hundo
            if (this.getNumber() > elDeAbajo.getNumber()) {
                Collections.swap(items, miPosicion, miPosicion - 1);
                miPosicion--; // Mi nuevo índice es uno menos
            } else {
                // Si el de abajo es igual o más grande, paramos
                break;
            }
        }
    }

    @Override
    public boolean canBeRemoved(ArrayList<Item> items) {
        // Si mi índice es 0, devuelvo FALSE (no me pueden quitar)
        // Si mi índice es cualquier otro, devuelvo TRUE
        return items.indexOf(this) != 0;
    }

    @Override
    public String getType() {
        return "hierarchical";
    }
}