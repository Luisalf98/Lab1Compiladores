/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thomson;

import java.util.ArrayList;

/**
 *
 * @author andresvillazon
 */
public class CompiladoresV20 {
    private final ArrayList<Transiciones> ThompsonAutomata;
    private final ArrayList<Transiciones> SubConjuntoAutomata;
    private final ArrayList<Transiciones> EstadoSignificativos;
    private final ArrayList<String> Alfabeto;
    private final ArrayList<U> TablaEstados;

    public ArrayList<U> getTablaEstados() {
        return TablaEstados;
    }
    
    public ArrayList<Transiciones> getThompsonAutomata() {
        return ThompsonAutomata;
    }

    public ArrayList<Transiciones> getSubConjuntoAutomata() {
        return SubConjuntoAutomata;
    }

    public ArrayList<Transiciones> getEstadoSignificativos() {
        return EstadoSignificativos;
    }

    public ArrayList<String> getAlfabeto() {
        return Alfabeto;
    }
    
    public CompiladoresV20(String ExpresionR) {
        
        Thompson Thom = new Thompson();
        /// Transiciones por Thopson
        ThompsonAutomata = Thom.Thompson(ExpresionR);
        ////////////////////////////////////////////
        
        int size = ThompsonAutomata.size();
        int i=0;
        
        Alfabeto = (ArrayList<String>) Thom.GetAlfabeto();
        
        //// Transiciones por SubConjunto
        Subconjunto Sub = new Subconjunto();
        SubConjuntoAutomata = Sub.Metodo(Alfabeto, ThompsonAutomata);
        ///////////////////////////////////
        // Fijar el estado Inicial y Final.
        
        
        //// En la Tabla Puedo Checkar Los Conjuntos de Cada estado y si son S0 o F
        TablaEstados = Sub.BuscarIniFin(Thom.Ultimo(ThompsonAutomata));
        
        
        EstadoSignificativo ESignificativo = new EstadoSignificativo();
        
        /// Array de Transiciones Aplicado Metodo de Estados Significativos (Muestra los estados con sus numeros reales)
        // Es decir, puede que se salte un numero, debido a la eliminacion de conjuntos iguales.
        EstadoSignificativos = ESignificativo.Metodo(ThompsonAutomata, TablaEstados, SubConjuntoAutomata);
        
        
    }

}
