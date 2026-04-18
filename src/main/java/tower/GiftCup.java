package tower;
import java.util.ArrayList;
import java.util.Random;

public class GiftCup extends Cup {
    private boolean opened;

    public GiftCup(int number, String color) {
        // Ignoramos el número que pasan por parámetro y ponemos uno temporal (0)
        super(0, "white"); 
        this.opened = false;
    }

    @Override
    public void processEntry(ArrayList<Item> items) {
        if (!opened) {
            Random rand = new Random();
            // Se desenvuelve y obtiene un número real entre 1 y 50
            this.number = rand.nextInt(50) + 1;
            
            // Cambia a un color sorpresa según su nuevo número
            String[] surpriseColors = {"magenta", "cyan", "gold", "silver"};
            this.changeColor(surpriseColors[this.number % surpriseColors.length]);
            
            this.opened = true;
            System.out.println("¡Sorpresa! La GiftCup reveló el número: " + this.number);
        }
    }

    @Override
    public String getType() {
        return opened ? "cup" : "gift";
    }
}