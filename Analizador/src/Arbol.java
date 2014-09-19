
import java.util.ArrayList;

public class Arbol {

    private Arbol padre;
    private ArrayList<Arbol> hijos;
    private ArrayList<Variable> variables;
    
    public Arbol() {
        padre = new Arbol();
        hijos = new ArrayList();
    }
    
    
}
