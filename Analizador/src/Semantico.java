
import java.util.ArrayList;
import Modelo.Modelo;

public class Semantico extends Arbol{
    
    private final ArrayList<Lexema> lexemas;
    private final Arbol arb;
    private String errores;
    private final InterfazVariables intVar;
    
    public Semantico(ArrayList<Lexema> lexemas) {
        intVar = new InterfazVariables();
        this.lexemas = lexemas;
        arb = new Arbol();
        errores = "";
    }
    
    public String MensajeSemantico() {
        llenarArbol(arb, 0);
        checarUso(arb);
        checarDuplicados(arb);
        checarVariablesNoDefinidas(arb);
        intVar.setVisible(true);
        return errores;
    }
    
    private void checarVariablesNoDefinidas(Arbol arb) {
        ArrayList<Variable> vars = arb.getVariables();
        for (int i = 0; i < vars.size(); i++) {
            if (!vars.get(i).esDeclaracion()) {
                boolean seEncuentra = false;
                for (int j = 0; j < i; j++) {
                    seEncuentra = (
                            !vars.get(i).esDeclaracion() & vars.get(j).esDeclaracion() & 
                            vars.get(i).getNombreVariable().compareTo(vars.get(j).getNombreVariable()) == 0
                    );
                    if (seEncuentra) break;
                }
                if (!seEncuentra) checarNivelSuperior(vars.get(i), arb.getPadre());;
            }
        }
        for (Arbol a: arb.getHijos()) checarVariablesNoDefinidas(a);
    }
    
    private void checarNivelSuperior(Variable var, Arbol padre) {
        if (padre == null) {
            errores += "Variable \"" + var.getNombreVariable() + "\" nunca declarada , Linea: " + var.getLinea() + "\n";
            return;
        }
        for (Variable v: padre.getVariables()) {
            boolean aux = var.getNombreVariable().compareTo(v.getNombreVariable()) == 0 & v.esDeclaracion();
            if (aux) return;
        }
        checarNivelSuperior(var, padre.getPadre());
    }
    
    private void checarDuplicados(Arbol arb) {
        ArrayList<Variable> vars = arb.getVariables();
        for (int i = 0; i < vars.size(); i++) {
            for (int j = i + 1; j < vars.size(); j++) {
                boolean seEncuentra = (
                        vars.get(i).esDeclaracion() &
                        vars.get(j).esDeclaracion() &
                        vars.get(i).getNombreVariable().compareTo(vars.get(j).getNombreVariable()) == 0
                );
                if (seEncuentra) {
                    errores += "Variable \"" +
                            vars.get(i).getNombreVariable() +
                            "\" (Linea: " +
                            vars.get(i).getLinea() +
                            ") repetida en la linea: " +
                            vars.get(j).getLinea() + "\n";
                    break;
                }
            }
        }
        for (Arbol a: arb.getHijos()) checarDuplicados(a);
    }
    
    private boolean contiene(String id) {
        for (String[] t: Modelo.ExtraerTiposDeDatos()) if (t[1].compareTo(id) == 0) return true;
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
                if (contiene(tipo)) arb.insertarVariables(new Variable(nombre, true, tipo, linea, arb.getNivel()));
                else arb.insertarVariables(new Variable(nombre, false, null, linea, arb.getNivel()));
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
                            !vars.get(j).esDeclaracion() &
                            vars.get(i).getNombreVariable().compareTo(vars.get(j).getNombreVariable()) == 0
                    );
                    if (seEncuentra) break;
                }
                for (Arbol a: arb.getHijos()) {
                    seEncuentra = checarUsoSiguienteNivel(a, vars.get(i).getNombreVariable());
                    if (seEncuentra) break;
                }
                if (!seEncuentra) {
                    vars.get(i).setUsada(false);
                    errores += "Varable \"" +
                            vars.get(i).getNombreVariable() +
                            "\" no usada. Linea: " +
                            vars.get(i).getLinea() + "\n";
                }
                intVar.insertarRegistro(vars.get(i));
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
            if (!seEncuentra)
                for (Arbol a: arbol.getHijos()) return checarUsoSiguienteNivel(a, nombre);
            if (seEncuentra) return true;
        }
        return false;
    }
    
}
