package compiladores;

import Thomson.Transiciones;
import Thomson.U;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javafx.scene.Group;

public class Af {
    public static final int STATE_DISTANCE = 50; 
    public static final int STATE_RADIUS = 20; 
    public static final int FONT_SIZE = 10; 
    
    private final HashMap<Integer, State> statesAfn;
    private final HashMap<Integer, State> statesAfdno;
    private final HashMap<Integer, State> statesAfd;
    private final ArrayList<U> estadosD;
    private final HashMap<Integer, HashSet<Integer>> estSig;
    private final ArrayList<Transition> afn;
    private final ArrayList<Transition> afd;
    private final ArrayList<Transition> afdno;
    private final HashSet<Character> alfabeto;
    private int[] right;
    private int[] left;
    private int[] same;

    public Af(ArrayList<Transiciones> trans, ArrayList<Transiciones> subc, ArrayList<Transiciones> estsig,
            ArrayList<U> estD){
        statesAfn = new HashMap<>();
        afn = new ArrayList<>();
        statesAfdno = new HashMap<>();
        afdno = new ArrayList<>();
        statesAfd = new HashMap<>();
        afd = new ArrayList<>();
        
        alfabeto = new HashSet<>();
        estadosD = estD;
        
        estSig = new HashMap<>();
        
        
        trans.forEach(t -> {
            if(t.Transicion.charAt(0)!='E'){
                alfabeto.add(t.Transicion.charAt(0));
            }
            addTransition(this.afn, this.statesAfn, t.Inicio, t.Fin, t.Transicion.charAt(0));
        });
        subc.forEach(t -> {
            addTransition(this.afdno, this.statesAfdno, t.Inicio, t.Fin, t.Transicion.charAt(0));
        });
        estsig.forEach(t -> {
            addTransition(this.afd, this.statesAfd, t.Inicio, t.Fin, t.Transicion.charAt(0));
        });
        
        estadosSignificativos();

    }

    public HashMap<Integer, HashSet<Integer>> getEstSig() {
        return estSig;
    }

    public ArrayList<U> getEstadosD() {
        return estadosD;
    }
    
    public HashMap<Integer, State> getStatesAfd() {
        return statesAfd;
    }

    public ArrayList<Transition> getAfd() {
        return afd;
    }
    
    public HashMap<Integer, State> getStatesAfdno() {
        return statesAfdno;
    }

    public ArrayList<Transition> getAfdno() {
        return afdno;
    }
    
    public HashSet<Character> getAlfabeto(){
        return this.alfabeto;
    }

    public int[] getRight() {
        return right;
    }

    public int[] getLeft() {
        return left;
    }

    public int[] getSame() {
        return same;
    }
    
    public HashMap<Integer, State> getStatesAfn() {
        return statesAfn;
    }

    public ArrayList<Transition> getAfn() {
        return afn;
    }
    
    public void addState(HashMap<Integer, State> states, int id){
        states.putIfAbsent(id, new State(id,State.NORMAL));
    }
    
    public void addTransition(ArrayList<Transition> af, HashMap<Integer, State> states, int pst, int nst, char symbol){
        addState(states, pst);
        addState(states, nst);
        
        Transition t = new Transition(states.get(pst), states.get(nst), symbol);
        states.get(pst).addTransition(t);
        af.add(t);
    }
    
    public void estadosSignificativos(){
        this.estadosD.forEach(u -> {
            this.estSig.put(u.getEstado(), new HashSet<>());
            u.getConjunto().forEach(id ->{
                if(this.statesAfn.get(id).getTransitions().size()>0
                        && this.statesAfn.get(id).getTransitions().get(0).getSymbol()!='E'){
                    estSig.get(u.getEstado()).add(id);
                }
            });
        });
    }
    
    public void draw(Group gp, ArrayList<Transition> af, HashMap<Integer, State> states){
        
        int h = -1;
        Iterator<Integer> it = states.keySet().iterator();
        
        while(it.hasNext()){
            int q = it.next();
            if(q>h){
                h=q;
            }
        }
        
        states.get(0).setStateType(State.START);
        states.get(h).setStateType(State.END);
        
        states.forEach((id,st)->{
            st.draw(gp, STATE_DISTANCE*(2*id+1), 0, STATE_RADIUS);
        });
        
        this.right = new int[h+1];
        this.left = new int[h+1];
        this.same = new int[h+1];
        
        af.sort((t1,t2)->{
            if(Math.abs(t1.getPrevState().getId()-t1.getNextState().getId()) <
                    Math.abs(t2.getPrevState().getId()-t2.getNextState().getId())){
                return -1;
            } else if(Math.abs(t1.getPrevState().getId()-t1.getNextState().getId()) >
                    Math.abs(t2.getPrevState().getId()-t2.getNextState().getId())){
                return 1;
            }
            return 0;
        });
        
        af.forEach((t)->{
            int dif = t.getNextState().getId()-t.getPrevState().getId();
            int max = -1, pos=-1;
            int last = 0, current = Math.abs(dif);
            
            if(dif>0){
                for(int i=t.getPrevState().getId(); i<t.getNextState().getId(); i++){
                    if(this.right[i]>max){
                        max = this.right[i];
                        pos=i;
                    }
                }
                this.right[pos]++;
                max = this.right[pos];
                
                if(t.getPrevState().getLr()==-1){
                    last = current;
                }else{
                    last = t.getPrevState().getLr();
                }
                
                t.getPrevState().setLr(current);
                
            }else if(dif<0){
                for(int i=t.getPrevState().getId(); i>t.getNextState().getId(); i--){
                    if(this.left[i]>max){
                        max = this.left[i];
                        pos=i;
                    }
                }
                this.left[pos]++;
                max=this.left[pos];
                
                for(int i=t.getPrevState().getId()-1; i>t.getNextState().getId(); i--){
                    if(this.same[i]>max){
                        max = this.same[i];
                        pos=i;
                    }
                }
                this.same[pos]++;
                max=this.same[pos];
                
                if(t.getPrevState().getLl()==-1){
                    last = current;
                }else{
                    last = t.getPrevState().getLl();
                }
                
                t.getPrevState().setLl(current);
                
            }else{
                this.same[t.getPrevState().getId()]++;
                max = this.same[t.getPrevState().getId()];
            }
            
            t.draw(gp, max, last, current, dif==0);
        });
    }
    
}
