package tower;

public class Cup extends StakingItem {
    private static final int BASE_THICKNESS = 1;
    private boolean isTapped;

    public Cup(int number, String color) {
        super(number, color);
        this.height = 2 * number - 1;
        this.isTapped = false;
    }

    public int getBaseThickness() { return BASE_THICKNESS; }
    public boolean isTapped() { return isTapped; }
    public void setTapped(boolean tapped) { this.isTapped = tapped; }

    @Override
    public String getType() { return "cup"; }
}