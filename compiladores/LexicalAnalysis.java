package compiladores;

import Thomson.CompiladoresV20;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LexicalAnalysis extends Application{
    
    private  CompiladoresV20 c;
    private Af af;
    @Override
    public void start(Stage stage){
        
        
//        Af af = new Af(c.getThompsonAutomata(), c.getSubConjuntoAutomata(), c.getEstadoSignificativos(),
//                c.getTablaEstados());

//        System.out.println("ALFABETO");
//        System.out.println(af.getAlfabeto());
//        System.out.println("\nTHOMPSON");
//        System.out.println(af.getAfn());
//        System.out.println("\nAFD No Optimo - Subconjuntos");
//        System.out.println(af.getAfdno());
//        System.out.println("\nEstados AFD en terminos de estados del AFN");
//        System.out.println(af.getEstadosD());
//        System.out.println("\nEstados Significativos");
//        System.out.println(af.getEstSig());
//        System.out.println("\nAFD Optimo - Estados Significativos");
//        System.out.println(af.getAfd());
        
        GridPane grid = new GridPane();
        
//        Group gp1 = new Group();
//        ScrollPane sp1 = new ScrollPane(gp1);
//        af.draw(gp1, af.getAfn(), af.getStatesAfn());
        
        VBox vb = new VBox();
        Label l = new Label("Expresión Regular");
        TextField t = new TextField();
        Button b = new Button("Compilar");
        b.setOnMouseClicked(e->{
            c = new CompiladoresV20(t.getText());
            af = new Af(c.getThompsonAutomata(), c.getSubConjuntoAutomata(), c.getEstadoSignificativos(), 
                            c.getTablaEstados());
        });
        
        vb.setSpacing(5);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(l);
        vb.getChildren().add(t);
        vb.getChildren().add(b);
        
        grid.add(vb, 0, 0);
        
        ScrollPane main = new ScrollPane(grid);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Scene scene = new Scene(main, tk.getScreenSize().getWidth(), tk.getScreenSize().getHeight());
        
        stage.setTitle("Lexical Analysis");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}
