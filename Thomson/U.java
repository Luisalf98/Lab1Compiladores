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
public class U {
    int Estado;
    String Sign = null;
    ArrayList<Integer> conjunto;
        
    public U(){

    }

    public U(int Estado, ArrayList<Integer> conjunto){
        this.Estado = Estado;
        this.conjunto = conjunto;
    }

    public int getEstado() {
        return Estado;
    }

    public String getSign() {
        return Sign;
    }

    public ArrayList<Integer> getConjunto() {
        return conjunto;
    }
    
    
    
    @Override
    public String toString(){
        return "Estado: "+Estado+" => "+conjunto;
    }
        
}
