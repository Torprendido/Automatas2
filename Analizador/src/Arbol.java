
import java.util.ArrayList;

public class Arbol {

    private Arbol padre;
    private final ArrayList<Arbol> hijos;
    private ArrayList<Variable> variables;
    
    public Arbol() {
        padre = null;
        hijos = new ArrayList();
    }

    public void setHijo(Arbol hijo) {
        hijo.setPadre(this);
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
}
