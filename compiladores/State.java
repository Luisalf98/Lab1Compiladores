
package compiladores;

import java.awt.Toolkit;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class State {
    
    public final static int START = 0; 
    public final static int NORMAL = 1; 
    public final static int END = 2; 
    public final static double DEFAULT_Y_POS = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2; 
    
    
    private final int id;
    private int stateType;
    private final ArrayList<Transition> transitions;
    private Circle stateCircle;
    private Text text;
    
    private int lr;
    private int ll;

    public State(int id, int stateType) {
        this.id = id;
        this.stateType = stateType;
        
        this.ll = -1;
        this.lr = -1;
        this.transitions = new ArrayList<>();
    }

    private void createDraw(double x, double y, double r){
        this.stateCircle = new Circle();
        this.text = new Text(x-5, y+5, String.valueOf(this.id));
        this.text.setTextAlignment(TextAlignment.CENTER);
        this.text.setFont(Font.font(Af.FONT_SIZE));
        
        this.stateCircle.setCenterX(x);
        this.stateCircle.setCenterY(y);
        this.stateCircle.setRadius(r);
        switch (this.stateType) {
            case START:
                this.stateCircle.setFill(Color.CYAN);
                this.text.setText(this.text.getText()+"\n(START)");
                this.text.setX(this.text.getX()-15);
                this.text.setY(this.text.getY()-5);
                break;
            case END:
                this.stateCircle.setFill(Color.GOLD);
                this.text.setText(this.text.getText()+"\n(END)");
                this.text.setX(this.text.getX()-10);
                this.text.setY(this.text.getY()-5);
                break;
            default:
                this.stateCircle.setFill(Color.CORAL);
                break;
        } 
        
    }

    public int getLr() {
        return lr;
    }

    public void setLr(int lr) {
        this.lr = lr;
    }

    public int getLl() {
        return ll;
    }

    public void setLl(int ll) {
        this.ll = ll;
    }
    
    

    public Text getText() {
        return text;
    }

    public Circle getStateCircle() {
        return stateCircle;
    }
    
    public int getId() {
        return id;
    }

    public int getStateType(){
        return this.stateType;
    }

    public void setStateType(int stateType) {
        this.stateType = stateType;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }
    
    public void addTransition(Transition t){
        this.transitions.add(t);
    }
    
    public void draw(Group gp, double x, double y, double r){
        this.createDraw(x, y, r);
        gp.getChildren().addAll(this.stateCircle, this.text);
    }
    
    @Override
    public String toString(){
        return this.id+"";
    }
    
}
