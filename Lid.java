/**
 * representa una tapa del simulador StackingItems.
 * cada tapa tiene una altura de 1 cm y corresponde a una taza especifica
 * @author hever
 */
public class Lid{
    private static final int  LID_HEIGHT = 1;
    //atributos
    private int number;
    private String color;
    private int xPosition;
    private int yPosition;
    
    private Rectangle body;
    /**
     * contsructor de lid 
     * @param number numero de tapa(debe corresponder a una taza)
     * @param color color de la tapa(igual al de la taza)
     */
    public Lid(int number,String color){
    this.number = number;
    this.color = color;
    this.xPosition = 0;
    this.yPosition = 0;
    // crear lo visual
    body = new Rectangle();
    
    }
    public void makeVisible(){
        body.makeVisible();
    }
    
    public void makeInvisible(){
        body.makeInvisible();
    }
    /**
     * reotrna la altura de la tapa que siempre es 1
     */
    public int getHeight(){
        return LID_HEIGHT;
    }
    /**
     * RETORNA EL NUMERO DE LA TAPA
     */
    public int getNumber(){
        return number;
    }
}