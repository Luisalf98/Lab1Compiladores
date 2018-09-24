/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thomson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author andresvillazon
 */
public class Subconjunto {
   
    public ArrayList<U> subconjunto = new ArrayList<>();
    public ArrayList<Transiciones> Tran = new ArrayList<>();
    Stack<U> us = new Stack<>();  
    int counterEstados=0;
    
    public ArrayList<Transiciones> Metodo(ArrayList<String> Alfabeto, ArrayList<Transiciones> Thompson){
        
        ArrayList<Integer> S0 = new ArrayList<>(Arrays.asList(0));
        
        //Calculo de Cerradura-E para S0.
        U A = new U();
        A.Estado = counterEstados;
        A.conjunto = Cerradura(S0, Thompson);
        counterEstados++;
        subconjunto.add(A);
        us.push(A);
        
        while(!us.empty()){
            U T = new U();
            T = us.pop();
            for (String alfa : Alfabeto) {
                ArrayList<Integer> a = Cerradura(Mueve(T.conjunto, Thompson, alfa), Thompson);
                if(!a.isEmpty()){
                    U NotSame = VerifificarU(a);   
                    if(NotSame==null){
                       U aux = new U();
                       aux.Estado = counterEstados;
                       counterEstados++;
                       subconjunto.add(aux);
                       aux.conjunto = a;
                       Tran.add(new Transiciones(T.Estado, aux.Estado, alfa));
                       us.add(aux);
                    }else{
                       Tran.add(new Transiciones(T.Estado,NotSame.Estado,alfa));
                    }
                }
                
            }
        }
        return Tran;
    }

    public ArrayList<Integer> Cerradura(ArrayList<Integer> Conjunto, ArrayList<Transiciones> Thompson){
        Stack stack = new Stack();
        ArrayList<Integer> CerraduraE = new ArrayList<>(); 
        for (Integer string : Conjunto) {
            stack.push(string);
            CerraduraE.add(string);
        }
        
        while(!stack.empty()){
            Integer Elem = (Integer) stack.pop();
            for (Transiciones Transicion : Thompson) {
                if(Transicion.Inicio == Elem && "E".equals(Transicion.Transicion) && !CerraduraE.contains(Transicion.Fin)){
                    stack.push(Transicion.Fin);
                    CerraduraE.add(Transicion.Fin);
                }
            }
        }
        return CerraduraE;
    }
    
    public ArrayList<Integer> Mueve(ArrayList<Integer> Conjunto, ArrayList<Transiciones> Thompson, String Simbolo){
        ArrayList<Integer> Mueve = new ArrayList<>(); 
        for (Integer estado : Conjunto) {
            for (Transiciones transiciones : Thompson) {
                if(transiciones.Inicio == estado && Simbolo.equals(transiciones.Transicion) && !Mueve.contains(transiciones.Fin)){
                    Mueve.add(transiciones.Fin);
                }
            }
        }
        return Mueve;
    }

    public U VerifificarU(ArrayList<Integer> a) {
        boolean equalT = false;
        for (U u : subconjunto) {
            ArrayList<Integer> auxConjunto = u.conjunto;
            int size1 = auxConjunto.size();
            int size2 = a.size();
            if(size1==size2 && !equalT){
                int i=0;
                boolean equal = true;
                while(i<=a.size()-1 && equal){
                    if(!u.conjunto.contains(a.get(i))){
                        equal = false;
                    } 
                    i++;
                }
                
                if(equal){
                    equalT = true;
                    return u;
                }
            }
        }
        return null;
    }
    
    public ArrayList<U> BuscarIniFin(int Fin){
        int i=0;
        while(i<=subconjunto.size()-1){
            for (U u : subconjunto) {
                if(u.conjunto.contains(0)){
                    u.Sign="-";
                }else if(u.conjunto.contains(Fin)){
                    u.Sign = "*";
                }
            }
            i++;
        }
        return subconjunto;
    }  
}

