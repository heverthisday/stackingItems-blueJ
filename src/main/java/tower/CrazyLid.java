package tower;
import java.util.ArrayList;
import java.util.Random;

public class CrazyLid extends Lid {
    public CrazyLid(int number, String color) {
        super(number, color);
    }

    @Override
    public void processEntry(ArrayList<Item> items) {
        String[] colors = {"red", "blue", "green", "yellow", "orange", "black", "pink"};
        Random rand = new Random();
        
        // Cambia el color de todos los que ya estaban
        for (Item item : items) {
            if (item != this) { // No se cambia a sí misma
                String randomColor = colors[rand.nextInt(colors.length)];
                item.changeColor(randomColor); 
            }
        }
    }

    @Override
    public String getType() { return "crazy"; }
}