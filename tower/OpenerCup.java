package tower;
import java.util.ArrayList;
import java.util.Iterator;

public class OpenerCup extends Cup {
    public OpenerCup(int number, String color) {
        super(number, color);
    }

    @Override
    public void processEntry(ArrayList<Item> items) {
        // Al entrar, recorre la torre y elimina TODAS las tapas (lids)
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            Item current = it.next();
            if (current.getType().equals("lid")) {
                current.makeInvisible();
                it.remove();
            }
        }
    }

    @Override
    public String getType() { return "opener"; }
}