/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Thomson;

/**
 *
 * @author andresvillazon
 */
public class Transiciones{
    public int Inicio;
    public int Fin;
    public String Transicion;

    public Transiciones(int Inicio, int Fin, String Transicion) {
        this.Inicio = Inicio;
        this.Fin = Fin;
        this.Transicion = Transicion;
    }

    public Transiciones() {
    }
    
    /*public Transiciones clone(){
        Transiciones trans = new Transiciones(this.Inicio,this.Fin,this.Transicion);
        return trans;
    }*/
    
    @Override
    public String toString(){
        return this.Inicio+"->"+this.Fin+":"+this.Transicion;
    }
    
}
