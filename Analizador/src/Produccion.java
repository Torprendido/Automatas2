
import java.util.ArrayList;

public class Produccion { 
    
    private String gramatica = "";
    private final ArrayList<String> listas = new ArrayList();
    
    public Produccion(String gramatica) {
        this.gramatica = gramatica;
        CrearLista();
    }

    public ArrayList<String> getListas() {
        return listas;
    }
    
    private void CrearLista() {
        String s = "";
        for (int i = 0; i < gramatica.length(); i++) {
            if (gramatica.charAt(i) == '>' | gramatica.charAt(i) == ']') {
                i++;
                listas.add(s);
                s = "";
            }
            if (i == gramatica.length()) break;
            if (gramatica.charAt(i) == '<' | gramatica.charAt(i) == '[') i++;
            s = s + gramatica.charAt(i);
        }
    }
}
