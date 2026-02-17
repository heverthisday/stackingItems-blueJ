/**
 * representa una taza cilindrica del simulador StackingItems.
 * Cada taza tiene 2i-1 cm y base 1cm
 * @author Hever
 */
public class Cup{
    //constantes
    private static final int BASE_THICKNESS = 1;
    // atributos
    private int number;
    private int height;
    private String color;
    private int xposition;
    private int yposition;
    
    //formas visuales
    private Rectangle body;
    /**
     * constructolr de cup
     * @param number numero de taza(determina altura y diametro)
     */
    public Cup(int number,String color){
        this.number = number;
        this.height = 2 * number -1;
        this.color = color;
        this.xposition = 0;
        this.yposition = 0;
        
        //crear cuerpo visual
        body = new Rectangle();
    }
    /**
     * hace visible la taza
     */
    public void makeVisible(){
        body.makeVisible();
    }
    /**
     * hace invisible la taza
     */
    public void makeInvisible(){
    body.makeInvisible();
    }
    /**
     * retorna la altura de la taza
     */
    public int getHeight(){
        return height;
    }
    /**
     * reotrna el numero de la taza
     */
    public int getNumber(){
        return number;
    }
    /**
     * rentorna el grosor de la base
     */
    public int getBaseThickness(){
        return BASE_THICKNESS;
    }
}
