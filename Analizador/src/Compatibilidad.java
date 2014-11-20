
import java.util.ArrayList;
import Modelo.Modelo;

public class Compatibilidad {
    
    private String errores;
    private final ArrayList<ArrayList<Lexema>> operaciones;
    private ArrayList<String> lineas;
    
    public Compatibilidad() {
        errores = "";
        operaciones = new ArrayList();
    }
    
    public void setLineasError(ArrayList<String> lineas) {
        this.lineas = lineas;
    }
    
    public String MensajeCompativilidad(ArrayList<Lexema> lexemas){
        toListOperaciones(lexemas);
        checarCompatibilidad(operaciones);
        return errores;
    }
    
    public void checarCompatibilidad(ArrayList<ArrayList<Lexema>> operaciones) {
        for (ArrayList<Lexema> operacion: operaciones) {
            ArrayList<String> posOrden = posOrden(operacion);
            if (!reduccion(posOrden, 0)) 
                errores += "Tipo de datos no compatibles. Linea: " + operacion.get(0).getLinea() + "\n";
        }
    }
    
    private boolean reduccion(ArrayList<String> posOrden, int indice) {
        if (posOrden.size() == 1) return true;
        switch (posOrden.get(indice)) {
            case "+":
            case "-":
            case "*":
                return remplaza("masMenosPor.xls", posOrden, indice);
            case "/":
                return remplaza("entre.xls", posOrden, indice);
            case "~":
                return remplaza("concatenar.xls", posOrden, indice);
            case ">":
            case "<":
            case "><":
            case ">=":
            case "<=":
            case "=":
                return remplaza("mayorMenorDiferente(etc).xls", posOrden, indice);
            case "|":
            case "&":
                return remplaza("orAnd.xls", posOrden, indice);
            case "<-":
                return remplaza("asignacion.xls", posOrden, indice);
            default:
                return reduccion(posOrden, ++indice);
        }
    }
    
    private boolean remplaza(String archivo, ArrayList<String> posOrden, int indice) {
        String producto = Modelo.producto(archivo, posOrden.get(indice - 2), posOrden.get(indice - 1));
        if (producto.compareTo("E") == 0) return false;
        posOrden.set(indice - 2, producto);
        posOrden.remove(indice - 1);
        posOrden.remove(indice - 1);
        return reduccion(posOrden, 0);
    }
    
    private void toListOperaciones(ArrayList<Lexema> codigo) {
        ArrayList<Lexema> operacion = new ArrayList();
        boolean entran = false;
        boolean esArray = false;
        boolean corchete = false;
        int i = 0;
        for (Lexema l: codigo) {
            if (!lineas.contains(l.getLinea())) switch (l.getLexemaTipo()) {
                case "[":
                    corchete = true;
                    int aux = codigo.indexOf(l);
                    String aux2 = codigo.get(aux + 1).getLexemaTipo();
                    if (aux2.compareTo("Numero") != 0 & aux2.compareTo("entero") != 0)
                        errores += "Tipo de tado no compatible. Linea: " + l.getLinea() + "\n";
                    break;
                case "arreglo":
                case "matriz":
                    esArray = true;
                    break;
                case "leer":
                    operacion = new ArrayList();
                    Lexema le = new Lexema();
                    le.setLexemaTipo("entero");le.setLinea(l.getLinea());
                    operacion.add(le);
                    le = new Lexema();
                    le.setLexemaTipo("<-");le.setLinea(l.getLinea());
                    operacion.add(le);
                    entran = true;
                    break;
                case "<-":
                    if (!esArray) {
                        operacion = new ArrayList();
                        operacion.add(codigo.get(codigo.indexOf(l) - 1));
                        operacion.add(l);
                        entran = true;
                    }
                    break;
                case "recorrido":
                    i = 0;
                case "si":
                case "mientras":
                case "imprimir":
                    operacion = new ArrayList();
                    entran = true;
                    break;
                case ",":
                    i ++;
                    if (entran) {
                        Lexema lex = new Lexema();
                        lex.setLexemaTipo(")");
                        operacion.add(lex);
                        operaciones.add(operacion);
                        operacion = new ArrayList();
                        lex = new Lexema();
                        lex.setLexemaTipo("(");
                        if (i != 2) operacion.add(lex);
                    }
                    if (i == 2) entran = false;
                    break;
                case "\\":
                    esArray = false;
                case "¿":
                    if (entran) {
                        operaciones.add(operacion);
                        entran = false;
                    }
                    
                    break;
                case "]":
                    corchete = false;
                    break;
                default:
                    if (entran & !corchete) operacion.add(l);
                    break;
            }
        }
    }
    
    public ArrayList<String> posOrden(ArrayList<Lexema> operacionLexema) {
        ArrayList<String> operacion = new ArrayList();
        for (Lexema l: operacionLexema) operacion.add(l.getLexemaTipo());
        String cabeza = "";
        ArrayList<String> operandos = new ArrayList();
        ArrayList<String> operadores = new ArrayList();
        for (String token: operacion) {
            if (!operadores.isEmpty()) cabeza = operadores.get(operadores.size() - 1);
            switch (token) {
                case "<-":
                case "~":
                    operadores.add(token);
                    break;
                case "+":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                        cabeza = operadores.get(operadores.size() -1);
                    }
                    operadores.add(token);
                    break;
                case "-":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("+") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                        cabeza = operadores.get(operadores.size() - 1);
                    }
                    operadores.add(token);
                    break;
                case "/":
                    if (cabeza.compareTo("*") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                    }
                    operadores.add(token);
                    break;
                case "*":
                    if (cabeza.compareTo("/") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                    }
                    operadores.add(token);
                    break;
                case ">":
                case "<":
                case "><":
                case "<=":
                case ">=":
                case "=":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0 |
                            cabeza.compareTo("+") == 0 | cabeza.compareTo("<") == 0 |
                            cabeza.compareTo(">") == 0 | cabeza.compareTo("><") == 0 |
                            cabeza.compareTo("><") == 0 | cabeza.compareTo("<=") == 0 |
                            cabeza.compareTo(">=") == 0 | cabeza.compareTo("=") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                        cabeza = operadores.get(operadores.size() - 1);
                    }
                    operadores.add(token);
                    break;
                case "(":
                    operadores.add(token);
                    break;
                case ")":
                    if (cabeza.compareTo("(") == 0) operadores.remove(operadores.size() - 1);
                    while (cabeza.compareTo("(") != 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() -1);
                        cabeza = operadores.get(operadores.size() -1);
                        if (cabeza.compareTo("(") == 0) operadores.remove(operadores.size() - 1);
                    }
                    break;
                case "&":
                case "|":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0 |
                            cabeza.compareTo("&") == 0 | cabeza.compareTo("|") == 0 |
                            cabeza.compareTo("+") == 0 | cabeza.compareTo("<") == 0 |
                            cabeza.compareTo(">") == 0 | cabeza.compareTo("><") == 0 |
                            cabeza.compareTo("><") == 0 | cabeza.compareTo("<=") == 0 |
                            cabeza.compareTo(">=") == 0 | cabeza.compareTo("=") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                        cabeza = operadores.get(operadores.size() - 1);
                    }
                    operadores.add(token);
                    break;
                case "!":
                    break;
                default:
                    operandos.add(token);
                    break;
            }
        }
        while (!operadores.isEmpty()) {
            operandos.add(operadores.get(operadores.size() - 1));
            operadores.remove(operadores.size() - 1);
        }
        return operandos;
    }
    
    public static ArrayList<String> posFijo(ArrayList<String> operacion) {
        String cabeza = "";
        ArrayList<String> operandos = new ArrayList();
        ArrayList<String> operadores = new ArrayList();
        for (String token: operacion) {
            if (!operadores.isEmpty()) cabeza = operadores.get(operadores.size() - 1);
            switch (token) {
                case "<-":
                case "~":
                    operadores.add(token);
                    break;
                case "+":
                    if (cabeza.compareTo(token) == 0) {
                        operandos.add(operadores.remove(operadores.size() - 1));
                        operadores.add(token);
                        break;
                    }
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                        cabeza = operadores.get(operadores.size() -1);
                    }
                    operadores.add(token);
                    break;
                case "-":
                    if (cabeza.compareTo(token) == 0) {
                        operandos.add(operadores.remove(operadores.size() - 1));
                        operadores.add(token);
                        break;
                    }
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("+") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                        cabeza = operadores.get(operadores.size() - 1);
                    }
                    operadores.add(token);
                    break;
                case "/":
                    if (cabeza.compareTo(token) == 0) {
                        operandos.add(operadores.remove(operadores.size() - 1));
                        operadores.add(token);
                        break;
                    }
                    if (cabeza.compareTo("*") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                    }
                    operadores.add(token);
                    break;
                case "*":
                    if (cabeza.compareTo(token) == 0) {
                        operandos.add(operadores.remove(operadores.size() - 1));
                        operadores.add(token);
                        break;
                    }
                    if (cabeza.compareTo("/") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                    }
                    operadores.add(token);
                    break;
                case ">":
                case "<":
                case "><":
                case "<=":
                case ">=":
                case "=":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0 |
                            cabeza.compareTo("+") == 0 | cabeza.compareTo("<") == 0 |
                            cabeza.compareTo(">") == 0 | cabeza.compareTo("><") == 0 |
                            cabeza.compareTo("<=") == 0 | cabeza.compareTo(">=") == 0 |
                            cabeza.compareTo("=") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                        cabeza = operadores.get(operadores.size() - 1);
                    }
                    operadores.add(token);
                    break;
                case "(":
                    operadores.add(token);
                    break;
                case ")":
                    if ("(".contains(cabeza)) {
                        operadores.remove(operadores.size() -1);
                        break;
                    }
                    while (cabeza.compareTo("(") != 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() -1);
                        cabeza = operadores.get(operadores.size() -1);
                        if (cabeza.compareTo("(") == 0) operadores.remove(operadores.size() - 1);
                    }
                    break;
                case "&":
                case "|":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0 |
                            cabeza.compareTo("&") == 0 | cabeza.compareTo("|") == 0 |
                            cabeza.compareTo("+") == 0 | cabeza.compareTo("<") == 0 |
                            cabeza.compareTo(">") == 0 | cabeza.compareTo("><") == 0 |
                            cabeza.compareTo("<=") == 0 | cabeza.compareTo(">=") == 0 |
                            cabeza.compareTo("=") == 0) {
                        operandos.add(cabeza);
                        operadores.remove(operadores.size() - 1);
                        cabeza = operadores.get(operadores.size() - 1);
                    }
                    operadores.add(token);
                    break;
                case "!":
                    break;
                default:
                    operandos.add(token);
                    break;
            }
        }
        while (!operadores.isEmpty()) {
            operandos.add(operadores.get(operadores.size() - 1));
            operadores.remove(operadores.size() - 1);
        }
        return operandos;
    }
}
