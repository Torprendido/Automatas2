
import java.util.ArrayList;

public class Semantico extends Arbol{
    
    private final ArrayList<Lexema> lexemas;
    private final Arbol arb;
    private String errores;

    public Semantico(ArrayList<Lexema> lexemas) {
        this.lexemas = lexemas;
        arb = new Arbol();
        errores = "";
    }
    
    public String MensajeSemantico() {
        llenarArbol(arb, 0);
        checarUso(arb);
        return errores;
    }
    
    private static boolean contiene(String id) {
        for (String[] t: Frame.ExtraerTiposDeDatos()) if (t[1].compareTo(id) == 0) return true;
        return false;
    }
    
    private void llenarArbol(Arbol arb, int indice) {
        for (int i = indice; i < lexemas.size(); i ++) {
            if (lexemas.get(i).getLexema().compareTo("¿") == 0) {
                Arbol arbHijo = new Arbol();
                arb.insertartHijo(arbHijo);
                llenarArbol(arbHijo, i + 1);
                return;
            } else if (lexemas.get(i).getLexema().compareTo("?") == 0) {
                llenarArbol(arb.getPadre(), i + 1);
                return;
            }
            if (lexemas.get(i).getToken().compareTo("Variable") == 0) {
                String nombre = lexemas.get(i).getLexema();
                String tipo = lexemas.get(i - 1).getId();
                String linea = lexemas.get(i).getLinea();
                if (contiene(tipo)) {
                    arb.insertarVariables(new Variable(nombre, true, tipo, linea));
                } else {
                    arb.insertarVariables(new Variable(nombre, false, null, linea));
                }
            }
        }
    }
    
    private void checarUso(Arbol arb) {
        ArrayList<Variable> vars = arb.getVariables();
        for (int i = 0; i < vars.size(); i++) {
            if (vars.get(i).esDeclaracion()) {
                boolean seEncuentra = false;
                for (int j = i + 1; j < vars.size(); j++) {
                    seEncuentra = (
                            !vars.get(j).esDeclaracion() & vars.get(i).getNombreVariable().compareTo(vars.get(j).getNombreVariable()) == 0
                    );
                    if (seEncuentra) break;
                }
                if (!seEncuentra) {
                    for (Arbol a: arb.getHijos()) {
                        seEncuentra = checarUsoSiguienteNivel(a, vars.get(i).getNombreVariable());
                        if (seEncuentra) break;
                    }
                }
                errores += !seEncuentra ? "Varable \"" + vars.get(i).getNombreVariable() + "\" no usada. Linea: " + vars.get(i).getLinea() + "\n": "";
            }
        }
        for (Arbol a: arb.getHijos()) checarUso(a);
    }

    private boolean checarUsoSiguienteNivel(Arbol arbol, String nombre) {
        for (Variable v: arbol.getVariables()) {
            boolean nombresIguales = nombre.compareTo(v.getNombreVariable()) == 0;
            boolean seEncuentra = nombresIguales & !v.esDeclaracion();
            boolean esGlobalDeNivel = nombresIguales & v.esDeclaracion();
            if (esGlobalDeNivel) return false;
            if (!seEncuentra) {
                for (Arbol a: arbol.getHijos()) return checarUsoSiguienteNivel(a, nombre);
            }
            if (seEncuentra) return true; 
        }
        return false;
    }
    
}
