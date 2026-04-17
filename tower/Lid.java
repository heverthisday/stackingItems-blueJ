package tower;

public class Lid extends StakingItem {
    public Lid(int number, String color) {
        super(number, color);
        this.height = 1;
    }

    @Override
    public String getType() { return "lid"; }
}