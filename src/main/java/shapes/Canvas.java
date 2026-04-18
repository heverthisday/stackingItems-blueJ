package shapes;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * Supports headless mode for JUnit testing environments.
 *
 * @author: Bruce Quig, Michael Kolling (mik)
 * @version: 1.7 (headless support)
 */
public class Canvas {
    private static Canvas canvasSingleton;

    /**
     * Factory method. Returns a no-op canvas in headless mode (JUnit).
     */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            if (GraphicsEnvironment.isHeadless()) {
                canvasSingleton = new Canvas(true);
            } else {
                canvasSingleton = new Canvas("BlueJ Shapes Demo", 300, 300, Color.white);
                canvasSingleton.setVisible(true);
            }
        }
        return canvasSingleton;
    }

    private final boolean headless;
    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;

    private Canvas(String title, int width, int height, Color bgColor) {
        headless = false;
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColor;
        frame.pack();
        objects = new ArrayList<>();
        shapes = new HashMap<>();
    }

    private Canvas(boolean headless) {
        this.headless = headless;
        objects = new ArrayList<>();
        shapes = new HashMap<>();
    }

    public void setVisible(boolean visible) {
        if (headless) return;
        if (graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    public void draw(Object referenceObject, String color, java.awt.Shape shape) {
        if (headless) return;
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }

    public void erase(Object referenceObject) {
        if (headless) return;
        objects.remove(referenceObject);
        shapes.remove(referenceObject);
        redraw();
    }

    public void eraseAll() {
        if (headless) return;
        erase();
        canvas.repaint();
    }

    public void setForegroundColor(String colorString) {
        if (headless || graphic == null) return;
        if (colorString.equals("red"))          graphic.setColor(Color.red);
        else if (colorString.equals("black"))   graphic.setColor(Color.black);
        else if (colorString.equals("blue"))    graphic.setColor(Color.blue);
        else if (colorString.equals("yellow"))  graphic.setColor(Color.yellow);
        else if (colorString.equals("green"))   graphic.setColor(Color.green);
        else if (colorString.equals("magenta")) graphic.setColor(Color.magenta);
        else if (colorString.equals("white"))   graphic.setColor(Color.white);
        else                                    graphic.setColor(Color.black);
    }

    public void wait(int milliseconds) {
        if (headless) return;
        try { Thread.sleep(milliseconds); } catch (Exception e) { /* ignorar */ }
    }

    private void redraw() {
        if (headless) return;
        erase();
        for (Object shape : objects) shapes.get(shape).draw(graphic);
        canvas.repaint();
    }

    private void erase() {
        if (headless || graphic == null) return;
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    private class CanvasPane extends JPanel {
        public void paint(Graphics g) { g.drawImage(canvasImage, 0, 0, null); }
    }

    private class ShapeDescription {
        private java.awt.Shape shape;
        private String colorString;
        public ShapeDescription(java.awt.Shape shape, String color) {
            this.shape = shape; this.colorString = color;
        }
        public void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.fill(shape);
        }
    }
}