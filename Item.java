public interface Item {
    int getHeight();
    int getNumber();
    String getColor();
    String getType();
    void makeVisible();
    void makeInvisible();
    void redraw(int height, int width, int x, int y, String color);  // ← ¿lo tienes?
}