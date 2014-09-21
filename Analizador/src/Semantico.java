
import java.util.ArrayList;

public class Semantico extends Arbol{
    
    private final ArrayList<Lexema> lexemas;
    private Arbol arb;

    public Semantico(ArrayList<Lexema> lexemas) {
        this.lexemas = lexemas;
        arb = new Arbol();
    }
    
    public String MensajeSemantico() {
        llenarArbol(arb, 0);
        return "";
    }
    
    private static boolean contiene(String id) {
        String[][] tipos = Frame.ExtraerTiposDeDatos();
        boolean existe = false;
        for (String[] t: tipos) {
            if (t[1].compareTo(id) == 0) {
                existe = true;
                break;
            }
        }
        return existe;
    }
    
    private void llenarArbol(Arbol arb, int indice) {
        for (int i = indice; i < lexemas.size(); i ++) {
            if (lexemas.get(i).getLexema().compareTo("¿") == 0) {
                Arbol arbHijo = new Arbol();
                arb.insertartHijo(arbHijo);
                llenarArbol(arbHijo, i + 1);
                return;
            } else if (lexemas.get(i).getLexema().compareTo("?") == 0) {
                arb = arb.getPadre();
            }
            if (lexemas.get(i).getToken().compareTo("Variable") == 0) {
                String nombre = lexemas.get(i).getLexema();
                String tipo = lexemas.get(i - 1).getId();
                String linea = lexemas.get(i).getLinea();
                if (contiene(lexemas.get(i - 1).getId())) {
                    arb.insertarVariables(new Variable(nombre, tipo, linea));
                } else {
                    arb.insertarVariables(new Variable(nombre, null, linea));
                }
            }
        }
    }
    
}
