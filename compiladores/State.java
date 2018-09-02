
package compiladores;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class State {
    
    public final static int START = 0; 
    public final static int NORMAL = 1; 
    public final static int END = 2; 
    public final static double DEFAULT_Y_POS = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2; 
    
    
    private final int id;
    private final int stateType;
    private final ArrayList<Transition> transitions;
    private Circle stateCircle;
    private Text text;
    private HashMap<Integer,Integer> incidents;

    public State(int id, int stateType) {
        this.id = id;
        this.stateType = stateType;
        
        this.incidents = new HashMap<>();
        this.transitions = new ArrayList<>();
    }

    public void createDraw(double x, double y, double r){
        this.stateCircle = new Circle();
        this.text = new Text(x-5, y+5, String.valueOf(this.id));
        this.text.setTextAlignment(TextAlignment.CENTER);
        
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
    
    public void createTransitionsDraw(){
        this.transitions.forEach((t) -> {
            t.createDraw();
        });
    }

    public Text getText() {
        return text;
    }

    public HashMap<Integer,Integer> getIncidents() {
        return this.incidents;
    }
    
    public void addOneToIncidents(int key){
        this.incidents.replace(key, this.incidents.get(key)+1);
    }
    
    public int getIncidentCounter(int key){
        return this.incidents.get(key);
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

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }
    
    public void addTransition(State st, char c){
        this.transitions.add(new Transition(this, st, c));
        this.incidents.putIfAbsent(st.getId(), 0);
    }
    
    public void draw(Group gp){
        gp.getChildren().addAll(this.stateCircle, this.text);
        this.transitions.forEach((t)->{
            t.draw(gp);
        });
    }
    
    
}
