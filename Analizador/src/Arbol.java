
import java.util.ArrayList;

public class Arbol {

    private Arbol padre;
    private final ArrayList<Arbol> hijos;
    private final ArrayList<Variable> variables;
    private int nivel;
    
    public Arbol() {
        variables = new ArrayList();
        padre = null;
        hijos = new ArrayList();
        nivel = 0;
    }
    
    private void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    public int getNivel() {
        return nivel;
    }

    public void insertartHijo(Arbol hijo) {
        hijo.setPadre(this);
        int aux = nivel + 1;
        hijo.setNivel(aux);
        hijos.add(hijo);
    }
    
    private void setPadre(Arbol padre) {
        this.padre = padre;
    }

    public void insertarVariables(Variable var) {
        variables.add(var);
    }
    
    public ArrayList<Arbol> getHijos() {
        return hijos;
    }
    
    public Arbol getPadre() {
        return padre;
    }
    
    public ArrayList<Variable> getVariables() {
        return variables;
    }   
}
