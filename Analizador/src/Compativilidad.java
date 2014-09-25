
import java.util.ArrayList;

public class Compativilidad {
    
    String errores;
    
    public Compativilidad() {
        
    }
    
    public String MensajeCompativilidad(ArrayList<String> operacion){
        operacion = posOrden(operacion);
        
        return "";
    }
    
    private ArrayList<String> posOrden(ArrayList<String> operacion) {
        ArrayList<String> operandos = new ArrayList();
        ArrayList<String> operadores = new ArrayList();
        for (String token: operacion) {
            String cabeza = operadores.get(operadores.size() -1);
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
                case "|":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0 |
                            cabeza.compareTo("+") == 0 | cabeza.compareTo("&") == 0) {
                        operandos.add(cabeza);
                        cabeza = operadores.get(operadores.size() -1);
                    }
                    operadores.add(token);
                    break;
                case "&":
                    while (cabeza.compareTo("*") == 0 | cabeza.compareTo("/") == 0 | cabeza.compareTo("-") == 0 |
                            cabeza.compareTo("+") == 0 | cabeza.compareTo("|") == 0) {
                        operandos.add(cabeza);
                        cabeza = operadores.get(operadores.size() -1);
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
                        operandos.add(cabeza);
                        cabeza = operadores.get(operadores.size() -1);
                    }
                    break;
                default:
                    operandos.add(token);
                    break;
            }
        }
        while (!operadores.isEmpty()) operandos.add(operadores.get(operadores.size() - 1));
        return operandos;
    }
}
