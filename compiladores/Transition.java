
package compiladores;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Transition {
    
    public static class TransitionLine{
       
        public static final double ANGLE = Math.toRadians(40);
        public static final double SAME_START_ANGLE = Math.toRadians(120);
        public static final double SAME_END_ANGLE = Math.toRadians(60);
        private final CubicCurve line;
        private final Polygon arrow;
        private final Text symbol;
        
        public TransitionLine(double x1, double y1, double x2, double y2, double incidents, 
                double last, double current, String symbol, boolean same){
            
            this.line = new CubicCurve();
            this.line.setFill(null);
            this.line.setStrokeWidth(2);
            this.line.setStroke(Color.GREEN);
            
            double dm = Math.sqrt(Math.pow(y2-y1, 2)+Math.pow(x2-x1, 2))/2;
            double h = dm*Math.tan(ANGLE)+80*(incidents-1);
            double newAngle = Math.atan2(h, dm);
            
            double d = Math.sqrt(Math.pow(dm*Math.tan(newAngle), 2)+Math.pow(dm, 2));
            
            double ang = Math.atan2((y2-y1),(x2-x1));
            double ang2 = ang-newAngle;
            
            this.line.setStartX(x1);
            this.line.setStartY(y1);
            this.line.setEndX(x2);
            this.line.setEndY(y2);
            
            if(same){
                double l = dm*3*incidents;
                this.line.setControlX1(x1+l*Math.cos(SAME_START_ANGLE));
                this.line.setControlY1(y1+l*Math.sin(SAME_START_ANGLE));
                this.line.setControlX2(x2+l*Math.cos(SAME_END_ANGLE));
                this.line.setControlY2(y2+l*Math.sin(SAME_END_ANGLE));
            }else{
                this.line.setControlX1(x1+Math.cos(ang2)*d/2);
                this.line.setControlY1(y1+Math.sin(ang2)*d/2);
                this.line.setControlX2(x2+Math.cos(ang2)*-d/2);
                this.line.setControlY2(y2+Math.sin(ang2)*d/2);
            }
            
            Point2D[] sp = this.getSlopePoints(0.5f);
            Point2D lp = this.getLinePoint(0.5f);
            double endAngle = Math.atan2(sp[1].getY()-sp[0].getY(),sp[1].getX()-sp[0].getX());
            
            this.arrow = new Polygon(lp.getX()+5*Math.cos(endAngle), 
            lp.getY()+5*Math.sin(endAngle), 
            lp.getX()+10*Math.cos(endAngle+Math.toRadians(150)), 
            lp.getY()+10*Math.sin(endAngle+Math.toRadians(150)),
            lp.getX()+10*Math.cos(endAngle+Math.toRadians(-150)), 
            lp.getY()+10*Math.sin(endAngle+Math.toRadians(-150)));
            this.arrow.setFill(Color.RED);
            
            
            this.symbol = new Text(lp.getX()+4, lp.getY()-6, symbol);
            this.symbol.setFont(new Font(Af.FONT_SIZE));
            
        }

        public CubicCurve getLine() {
            return line;
        }

        public Polygon getArrow() {
            return arrow;
        }
        
        public void draw(Group gp){
            gp.getChildren().addAll(this.line, this.arrow, this.symbol);
        }
        
        public Point2D getLinePoint(double t){
            return new Point2D(Math.pow(t, 3)*this.line.getEndX()+3*t*t*(1-t)*this.line.getControlX2()
                    +3*t*Math.pow(1-t, 2)*this.line.getControlX1()+Math.pow(1-t, 3)*this.line.getStartX(),
                    Math.pow(t, 3)*this.line.getEndY()+3*t*t*(1-t)*this.line.getControlY2()
                    +3*t*Math.pow(1-t, 2)*this.line.getControlY1()+Math.pow(1-t, 3)*this.line.getStartY());
        }
        
        public Point2D[] getSlopePoints(double t){
            return new Point2D[]{new Point2D(t*t*this.line.getControlX2()+2*t*(1-t)*this.line.getControlX1()+
                    Math.pow(1-t,2)*this.line.getStartX(),
                    t*t*this.line.getControlY2()+2*t*(1-t)*this.line.getControlY1()+
                    Math.pow(1-t,2)*this.line.getStartY()),
                new Point2D(t*t*this.line.getEndX()+2*t*(1-t)*this.line.getControlX2()+
                    Math.pow(1-t,2)*this.line.getControlX1(),
                    t*t*this.line.getEndY()+2*t*(1-t)*this.line.getControlY2()+
                    Math.pow(1-t,2)*this.line.getControlY1())};
        }
        
    }
    
    private final State nextState;
    private final State prevState;
    private final char symbol;
    private TransitionLine tl;
    
    public Transition(State prevSt, State nextSt, char symbol){
        this.nextState = nextSt;
        this.symbol = symbol;
        this.prevState = prevSt;
    }

    public State getPrevState() {
        return prevState;
    }

    public State getNextState() {
        return nextState;
    }

    public char getSymbol() {
        return symbol;
    }

    public TransitionLine getTl() {
        return tl;
    }
    
    private void createDraw(int inc, int last, int current, boolean same){
        
        double x1,x2,y1,y2;
        
        if(same){
            
            x1 = this.prevState.getStateCircle().getCenterX()+
                    this.prevState.getStateCircle().getRadius()*Math.cos(TransitionLine.SAME_START_ANGLE);
            y1 = this.prevState.getStateCircle().getCenterY()+
                    this.prevState.getStateCircle().getRadius()*Math.sin(TransitionLine.SAME_START_ANGLE);
            x2 = this.nextState.getStateCircle().getCenterX()+
                    this.nextState.getStateCircle().getRadius()*Math.cos(TransitionLine.SAME_END_ANGLE);
            y2 = this.nextState.getStateCircle().getCenterY()+
                    this.nextState.getStateCircle().getRadius()*Math.sin(TransitionLine.SAME_END_ANGLE);
            
        }else{
            double ang = Math.atan2(this.nextState.getStateCircle().getCenterY()-
                    this.prevState.getStateCircle().getCenterY(),
                this.nextState.getStateCircle().getCenterX()-
                        this.prevState.getStateCircle().getCenterX());
            
            x1 = this.prevState.getStateCircle().getCenterX()+
                    this.prevState.getStateCircle().getRadius()*Math.cos(ang-TransitionLine.ANGLE);
            y1 = this.prevState.getStateCircle().getCenterY()+
                    this.prevState.getStateCircle().getRadius()*Math.sin(ang-TransitionLine.ANGLE);
            x2 = this.nextState.getStateCircle().getCenterX()+
                    this.nextState.getStateCircle().getRadius()*-Math.cos(ang+TransitionLine.ANGLE);
            y2 = this.nextState.getStateCircle().getCenterY()+
                    this.nextState.getStateCircle().getRadius()*-Math.sin(ang+TransitionLine.ANGLE);
            
        }
        
        this.tl = new TransitionLine(x1, y1, x2, y2, inc, last, current, String.valueOf(this.symbol), same);
    }
    
    public void draw (Group gp, int inc, int last, int current, boolean same){
        this.createDraw(inc, last, current, same);
        this.tl.draw(gp);
    }
    
    @Override
    public String toString(){
        return this.prevState+"->"+this.nextState+":"+this.symbol;
    }
}
