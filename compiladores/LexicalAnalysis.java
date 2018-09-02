package compiladores;

import java.awt.Toolkit;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class LexicalAnalysis extends Application{
    
    @Override
    public void start(Stage stage){
        
        State st0 = new State(0, State.START);
        State st1 = new State(1, State.END);
        State st2 = new State(2, State.NORMAL);
        
        st0.addTransition(st1, 'a');
        st0.addTransition(st1, 'b');
        st0.addTransition(st1, 'c');
        st0.addTransition(st1, 'd');
        st0.addTransition(st1, 'e');
        st0.addTransition(st2, 'f');
        st0.addTransition(st2, 'g');
        st0.addTransition(st2, 'h');
        st0.addTransition(st2, 'i');
        st0.addTransition(st2, 'j');
        
        st2.addTransition(st1, 'a');        
        st2.addTransition(st1, 'b');
        st2.addTransition(st1, 'c');
        st2.addTransition(st1, 'd');
        st2.addTransition(st1, 'e');
        st2.addTransition(st0, 'f');
        st2.addTransition(st0, 'g');
        st2.addTransition(st0, 'h');
        st2.addTransition(st0, 'i');
        st2.addTransition(st0, 'j');
        
        st0.createDraw(100, 100, 30);
        st1.createDraw(300, 100, 30);
        st2.createDraw(500, 100, 30);
        
        st0.createTransitionsDraw();
        st1.createTransitionsDraw();
        st2.createTransitionsDraw();
        
        Group gp = new Group();
        
        st0.draw(gp);
        st1.draw(gp);
        st2.draw(gp);
        
        ScrollPane sp = new ScrollPane(gp);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Scene scene = new Scene(sp, tk.getScreenSize().getWidth(), tk.getScreenSize().getHeight());
        
        stage.setTitle("Lexical Analysis");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
