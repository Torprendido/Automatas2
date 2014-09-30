
import java.util.ArrayList;

public class Compativilidad {
    
    String errores;
    
    public Compativilidad() {
        
    }
    
    public String MensajeCompativilidad(ArrayList<String> operacion){
        operacion = posOrden(operacion);
        for (String orden: operacion) {
            System.out.println(orden);
        }
        return "";
    }
    
    private ArrayList<String> posOrden(ArrayList<String> operacion) {
        String cabeza = "";
        ArrayList<String> operandos = new ArrayList();
        ArrayList<String> operadores = new ArrayList();
        for (String token: operacion) {
            if (!operadores.isEmpty()) cabeza = operadores.get(operadores.size() - 1);
            switch (token) {
                case "<-":
                    operandos.add(token);
                    break;
                case "+":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0) {
                        operandos.add(cabeza);
                        cabeza = operadores.get(operadores.size() -1);
                    }
                    operadores.add(token);
                    break;
                case "-":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("+") == 0) {
                        operandos.add(cabeza);
                        cabeza = operadores.get(operadores.size() -1);
                    }
                    operadores.add(token);
                    break;
                case "/":
                    if (cabeza.compareTo("*") == 0) operandos.add(cabeza);
                    operadores.add(token);
                    break;
                case "*":
                    if (cabeza.compareTo("-") == 0) operandos.add(cabeza);
                    operadores.add(token);
                    break;
                case ">":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0 |
                            cabeza.compareTo("+") == 0 | cabeza.compareTo("<") == 0) {
                        if (operadores.isEmpty()) break;
                        cabeza = operadores.remove(operadores.size() - 1);
                        operandos.add(cabeza);
                    }
                    operadores.add(token);
                    break;
                case "<":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0 |
                            cabeza.compareTo("+") == 0 | cabeza.compareTo(">") == 0) {
                        if (operadores.isEmpty()) break;
                        cabeza = operadores.remove(operadores.size() - 1);
                        operandos.add(cabeza);
                    }
                    operadores.add(token);
                    break;
                case "++":
                case "--":
                    break;
                case "(":
                    operadores.add(token);
                    break;
                case ")":
                    while (cabeza.compareTo("(") != 0) {
                        cabeza = operadores.remove(operadores.size() -1);
                        if (cabeza.compareTo("(") != 0) operandos.add(cabeza);
                    }
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
    
//    public static void main(String[] args) {
//        ArrayList<String> lista = new ArrayList();
//        lista.add("c");
//        lista.add("-");
//        lista.add("k");
//        lista.add(">");
//        lista.add("v");
////        lista.add("+");
////        lista.add("(");
////        lista.add("a");
////        lista.add("-");
////        lista.add("h");
////        lista.add("/");
////        lista.add("b");
////        lista.add(")");
//        new Compativilidad().MensajeCompativilidad(lista);
//    }
}
