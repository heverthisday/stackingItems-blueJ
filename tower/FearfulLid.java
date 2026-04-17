package tower;
import java.util.ArrayList;

public class FearfulLid extends Lid {
    public FearfulLid(int number, String color) {
        super(number, color);
    }

    @Override
    public boolean canBeAdded(ArrayList<Item> items) {
        if (items.isEmpty()) return true;
        
        // Miramos el último elemento que se puso (el que estaría abajo de esta tapa)
        Item lastItem = items.get(items.size() - 1);
        
        // Si el de abajo es una taza y es más grande que yo, me da miedo y no entro
        if (lastItem.getType().contains("cup") && lastItem.getNumber() > this.number) {
            return false;
        }
        return true;
    }

    @Override
    public String getType() { return "fearful"; }
}