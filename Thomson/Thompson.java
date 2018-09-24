/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thomson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author andresvillazon
 */
public class Thompson { 
    /**
     * @param args the command line arguments
     */
    
    private List<String> NoLetra = new ArrayList<>(Arrays.asList("+","*","?","(",")","[","]","|"));
    private List<String> Operadores = new ArrayList<>(Arrays.asList("+","*","?"));
    private HashMap<String,String> Agrupadores = new HashMap<>();
    private static Stack stack = new Stack();
    private static Stack stack2 = new Stack();
    private List<String> Alfabeto = new ArrayList<>();
    
    private static int countAux=0;

    public ArrayList<Transiciones> Thompson(String ExpresionRegular){
        Agrupadores.put("(", ")");
        Agrupadores.put("[", "]");
        return DividirExpresion(ExpresionRegular+"$");
    }
    
    public List<String> GetAlfabeto(){
        return Alfabeto;
    }

    // a+(a|(ba))+
    public ArrayList<Transiciones> DividirExpresion(String Cadena){
        int i=0;
        String Subcadena2;
        ArrayList<Transiciones> transicionesAux = new ArrayList<>();
        ArrayList<Transiciones> transicionesGeneral = new ArrayList<>();
        while(i < (Cadena.length()-1)){
            String Subcadena = Cadena.charAt(i)+"";
            Subcadena2=Subcadena;
            if(!NoLetra.contains(Subcadena)){
                if(!Alfabeto.contains(Subcadena)){
                  Alfabeto.add(Subcadena);   
                }
                Transiciones Edge = new Transiciones();
                Edge.Inicio = 0; Edge.Fin = 1; Edge.Transicion = Subcadena;
                transicionesAux.add(Edge);
                
            }else{
               if(Agrupadores.containsKey(Subcadena)){ 
                   stack.push(Agrupadores.get(Subcadena));  
                   int indiceInicio = i;
                   int indiceFin = indiceInicio+1;
                   while(indiceFin <= Cadena.length()-1 && !stack.empty()){
                       
                       String sub = Cadena.charAt(indiceFin)+"";
                       if(Agrupadores.containsKey(sub)){
                            stack.push(Agrupadores.get(sub));
                       }else{
                           if(Agrupadores.containsValue(sub)){
                              stack.pop();
                           }
                       }
                       indiceFin++;
                   }
                   String Sucabdena2 = Cadena.substring(indiceInicio+1, indiceFin-1);
                   transicionesAux = DividirExpresion(Sucabdena2+"$");
                   i=indiceFin-1;
                   Subcadena2 = Cadena.charAt(i)+"";
               }
               
               if("|".equals(Subcadena2)){
                    if(transicionesGeneral.isEmpty()){
                        transicionesGeneral = (ArrayList<Transiciones>) transicionesAux.clone();
                    }
                    int pos = Cadena.indexOf("|");
                    
                    int indiceInicio = pos;
                    indiceInicio++;
                    int indiceFin = indiceInicio+1;
                    String Cade = Cadena.charAt(indiceInicio)+"";
                    String Sucabdena2;
                    if(Agrupadores.containsKey(Cade)){ 
                       stack2.push(Agrupadores.get(Cade));  

                       while(indiceFin <= Cadena.length()-1 && !stack2.empty()){

                           String sub = Cadena.charAt(indiceFin)+"";
                           if(Agrupadores.containsKey(sub)){
                                stack2.push(Agrupadores.get(sub));
                           }else{
                               if(Agrupadores.containsValue(sub)){
                                  stack2.pop();
                               }
                           }
                           indiceFin++;
                       }
                        Sucabdena2 = Cadena.substring(indiceInicio+1, indiceFin-1);
                        i=indiceFin;
                    }else{
                        indiceInicio--;
                        indiceFin = Cadena.indexOf("$");
                        indiceFin=indiceFin+1;
                        Sucabdena2 = Cadena.substring(indiceInicio+1, indiceFin-1);
                        i=indiceFin-1;
                        
                    }
                   transicionesAux = DividirExpresion(Sucabdena2+"$");
                   
                   Subcadena2 = Cadena.charAt(i)+"";
                   
                   
                if(Operadores.contains(Subcadena2)){
                    ArrayList<Transiciones> LastTransicion;
                    int Tam = transicionesAux.size();
                    LastTransicion = (ArrayList<Transiciones>) transicionesAux.clone();
                    transicionesAux.clear();
                    switch(Subcadena2){
                        case "*":
                            Kleene(LastTransicion,Tam,transicionesAux);
                            break;
                        case "+":
                            Positiva(LastTransicion,Tam,transicionesAux);
                            break;
                        case "?":
                            OneOrZero(LastTransicion,Tam,transicionesAux);
                            break;
                    }

                }else{
                   i--;
                }
                   
                   
                   ArrayList<Transiciones> LastTransicion;
                   int Tam = transicionesAux.size();
                   LastTransicion = (ArrayList<Transiciones>) transicionesAux.clone();
                   transicionesAux.clear();
                   Or(transicionesGeneral,LastTransicion,LastTransicion.size(),transicionesAux);
                   transicionesGeneral.clear();
                   Subcadena2 = Cadena.charAt(i+1)+"";              
                }
                         
               if(Operadores.contains(Subcadena2)){
                   ArrayList<Transiciones> LastTransicion;
                   int Tam = transicionesAux.size();
                   LastTransicion = (ArrayList<Transiciones>) transicionesAux.clone();
                   transicionesAux.clear();
                   switch(Subcadena2){
                       case "*":
                           Kleene(LastTransicion,Tam,transicionesAux);
                           break;
                       case "+":
                           Positiva(LastTransicion,Tam,transicionesAux);
                           break;
                       case "?":
                           OneOrZero(LastTransicion,Tam,transicionesAux);
                           break;
                   }
                   
               }else{

               }
               
                
            
            }

            String Cad =Cadena.charAt(i+1)+"";
            if(!Operadores.contains(Cadena.charAt(i+1)+"") || "$".equals(Cadena.charAt(i+1)+"")){
                // Si luego de cualquier vaina sigue parentesis o una letra.
                // guardamos inmediatamente el contenido de Gaux en Ggeneral.
                // Pero primero debemos verificar si Ggeneral contenga nodos, en decir, necesitamos
                // unir el automata anterior con el que recien añadiremos.
                // CONCATENAR.
                int k=0;
                int Incremento=0;
                int size = transicionesGeneral.size();
                
                if(size!=0){
                    k=1;
                    //Transiciones Ultimo = transicionesGeneral.get(size-1);
                    Incremento = Ultimo(transicionesGeneral);
                    transicionesGeneral.add(new Transiciones(Incremento,(Incremento+1)+(transicionesAux.get(0).Inicio),transicionesAux.get(0).Transicion));
                }
               
                
                while(k<=(transicionesAux.size()-1)){
                    
                    int inicio = Incremento+(transicionesAux.get(k).Inicio);
                    int fin = Incremento+(transicionesAux.get(k).Fin);
                    String Trans = transicionesAux.get(k).Transicion;
                    transicionesGeneral.add(new Transiciones(inicio,fin,Trans));
                    k++;
                }     
                transicionesAux.clear();
            }
            
            i++;
        }
        return transicionesGeneral;
    }
    
    //*
    public void Kleene(ArrayList<Transiciones> Automata, int size, ArrayList<Transiciones> transicionesAux){
        countAux=0;
        int i=0;
        transicionesAux.add(new Transiciones(countAux,Counter(countAux),"E"));
        
        while(i<=(size-1)){
            int ini = Automata.get(i).Inicio;
            int fin = Automata.get(i).Fin;
            String transicion = Automata.get(i).Transicion;
            transicionesAux.add(new Transiciones(inc(ini),inc(fin),transicion));         
            i++;
        }
        int Ultimo = Ultimo(Automata);
        // Transicion de [Ultimo(c)-Ultimo+1]
        transicionesAux.add(new Transiciones(inc(Ultimo),inc(Ultimo)+1,"E"));
        // Transicion de [0-ultimo]
        transicionesAux.add(new Transiciones(0,inc(Ultimo)+1,"E"));
        if(size<=0){
            transicionesAux.add(new Transiciones(inc(Automata.get(0).Fin),inc(Automata.get(0).Inicio),"E"));
        }else{
            int Ul = Ultimo(Automata);
            int Pr = Primero(Automata);
            transicionesAux.add(new Transiciones(inc(Ul),inc(Pr),"E"));
        }
        
    }
    
    //+
    public void Positiva(ArrayList<Transiciones> Automata, int size, ArrayList<Transiciones> transicionesAux){
        countAux=0;
        int i=0;
        transicionesAux.add(new Transiciones(countAux,Counter(countAux),"E"));
        
        while(i<=(size-1)){
            int ini = Automata.get(i).Inicio;
            int fin = Automata.get(i).Fin;
            String transicion = Automata.get(i).Transicion;
            transicionesAux.add(new Transiciones(inc(ini),inc(fin),transicion));         
            i++;
        } 
        int Ultimo = Ultimo(Automata);
        transicionesAux.add(new Transiciones(inc(Ultimo),inc(Ultimo)+1,"E"));
        
        if(size<=0){
            transicionesAux.add(new Transiciones(inc(Automata.get(0).Fin),inc(Automata.get(0).Inicio),"E"));
        }else{
            int Ul = Ultimo(Automata);
            int Pr = Primero(Automata);
            transicionesAux.add(new Transiciones(inc(Ul),inc(Pr),"E"));
        }
    }
    
    //?
    public void OneOrZero(ArrayList<Transiciones> Automata, int size, ArrayList<Transiciones> transicionesAux){
        countAux=0;
        int i=0;
        transicionesAux.add(new Transiciones(countAux,Counter(countAux),"E"));
        
        while(i<=(size-1)){
            int ini = Automata.get(i).Inicio;
            int fin = Automata.get(i).Fin;
            String transicion = Automata.get(i).Transicion;
            transicionesAux.add(new Transiciones(inc(ini),inc(fin),transicion));         
            i++;
        }
        int Ultimo = Ultimo(Automata);
        transicionesAux.add(new Transiciones(inc(Ultimo),inc(Ultimo)+1,"E"));
        transicionesAux.add(new Transiciones(0,inc(Ultimo)+1,"E"));
    }
    
    public void Or(ArrayList<Transiciones> General, ArrayList<Transiciones> Automata, int size, ArrayList<Transiciones> transicionesAux){
        countAux=0;
        int i=0;
        int j=0;
        int NodoI =0;
        int NodoF =0;
        transicionesAux.add(new Transiciones(0,Counter(countAux),"E"));
        //transicionesAux.add(new Transiciones(0,Counter(countAux),"E"));
        
        while(i<=(General.size()-1)){
            int ini = General.get(i).Inicio;
            int fin = General.get(i).Fin;
            String transicion = General.get(i).Transicion;
            transicionesAux.add(new Transiciones(inc(ini),inc(fin),transicion));         
            i++;
        }
        int Ultimo = Ultimo(General);
        //NodoF = Ultimo+1;
        transicionesAux.add(new Transiciones(0,inc(Ultimo)+1,"E"));
        //Ultimo = Ultimo(transicionesAux);
        int Tam = Automata.size();
        while(j<=(Tam-1)){
            int ini = Automata.get(j).Inicio;
            int fin = Automata.get(j).Fin;
            String transicion = Automata.get(j).Transicion;
            transicionesAux.add(new Transiciones(Ultimo+inc(ini)+1,Ultimo+inc(fin)+1,transicion));         
            j++;
        }
        
        int Ultimo2 = Ultimo(transicionesAux);
        //NodoF = Ultimo+1;
        transicionesAux.add(new Transiciones(inc(Ultimo),inc(Ultimo2),"E"));
        transicionesAux.add(new Transiciones((Ultimo2),inc(Ultimo2),"E"));    
    }
    
    public int Counter(int c){
        countAux=countAux+1;
        return countAux;
    }
    
    public int inc(int c){
        return c+1;
    }
    
    public int Ultimo(ArrayList<Transiciones> Auxiliar){
        int ultimo=0;
        for (int i = 0; i <= Auxiliar.size()-1; i++) {           
            if(Auxiliar.get(i).Fin>ultimo){
                ultimo = Auxiliar.get(i).Fin;
            }
        }
        return ultimo;
    }

    private int Primero(ArrayList<Transiciones> Auxiliar) {
         int ultimo=9999;
        for (int i = 0; i <= Auxiliar.size()-1; i++) {
            if(Auxiliar.get(i).Inicio<ultimo){
                ultimo = Auxiliar.get(i).Inicio;
            }
        }
        return ultimo;
    }
    
}
