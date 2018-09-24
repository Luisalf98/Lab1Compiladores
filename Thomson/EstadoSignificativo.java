/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thomson;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author andresvillazon
 */
public class EstadoSignificativo {
    
    public ArrayList<Integer> Significativos = new ArrayList<>();
    public ArrayList<U> SignificativosEstados = new ArrayList<>();
    public ArrayList<Transiciones> Subconjuntos = new ArrayList<>();
    
    public ArrayList<Transiciones> Metodo(ArrayList<Transiciones> Automata, ArrayList<U> AutomataSubconjunto, ArrayList<Transiciones> Sub){
        Subconjuntos = (ArrayList<Transiciones>) Sub.clone();
        EstadosSignifictaivos(Automata);
        FiltrarSignificativos(AutomataSubconjunto);
        
        return QuitarEstadosDuplicados();
    }
    
    public void EstadosSignifictaivos(ArrayList<Transiciones> Automata){
        for (Transiciones transiciones : Automata) {
            if(!"E".equals(transiciones.Transicion)){
                Significativos.add(transiciones.Inicio);
            }
        }
        Thompson Ultimo = new Thompson();
        Significativos.add(Ultimo.Ultimo(Automata));
        
        
    }
    
    public void FiltrarSignificativos(ArrayList<U> Subconjuntos){
        
        for (U Subconjunto : Subconjuntos) {
            ArrayList<Integer> NuevoSignificativo = new ArrayList<>();
            ArrayList<Integer> aux = Subconjunto.conjunto;
            for (Integer integer : aux) {
                if(Significativos.contains(integer)){
                    NuevoSignificativo.add(integer);
                }
            }
            Collections.sort(NuevoSignificativo);
            SignificativosEstados.add(new U(Subconjunto.Estado, (ArrayList<Integer>) NuevoSignificativo.clone()));
            NuevoSignificativo.clear();
        }
    }
    
    public ArrayList<Transiciones> QuitarEstadosDuplicados(){
        ArrayList<U> Buscar = new ArrayList<>();
        Buscar = (ArrayList<U>) SignificativosEstados.clone();
        int i=0;
        while(i<Buscar.size()-1){
            U aux1 = Buscar.get(i);
            int j = i+1;
            while (j <= Buscar.size()-1) {
                U aux2 = Buscar.get(j);
                if(aux1.conjunto.equals(aux2.conjunto)){
                    if(aux2.Sign==null){
                        ModificarSubConjunto(aux2.Estado, aux1.Estado);
                        Buscar.remove(aux2);
                    }else if(aux1.Sign==null){
                        ModificarSubConjunto(aux1.Estado, aux2.Estado);
                        Buscar.remove(aux1);
                        aux1 = Buscar.get(i);
                    }
                }else{
                   j++;
                }
            }
            i++;
        }
        return Subconjuntos;
    }
    
    public void ModificarSubConjunto(int EstadoBorrado, int Estado){
        int i = 0; 
        while (i <= Subconjuntos.size()-1) {
            Transiciones Subconjunto = Subconjuntos.get(i);
            if(Subconjunto.Fin==EstadoBorrado){
                Subconjunto.Fin=Estado;
            }
            if(Subconjunto.Inicio==EstadoBorrado){
                Subconjuntos.remove(Subconjunto);
                i--;
            }else{
               i++; 
            }
        }
        /*for (Transiciones Subconjunto : Subconjuntos) {
            if(Subconjunto.Fin==EstadoBorrado){
                Subconjunto.Fin=Estado;
            }
            if(Subconjunto.Inicio==EstadoBorrado){
                Subconjuntos.remove(Subconjunto);
            }
        }*/
    }
}
