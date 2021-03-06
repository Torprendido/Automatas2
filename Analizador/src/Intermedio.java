
import java.util.ArrayList;
import java.util.Stack;

public class Intermedio {
    
    public static ArrayList<String> codigoIntermedio;
    private Etiketa etiketaSuper = new Etiketa(true);
    private Etiketa etiketaUltima;
    public int indiceActual;
    private int indiceFinal;
    private ArrayList<Comp> comps;
    private final Stack<Etiketa> pila;
    public final static Stack<String> cadenas = new Stack();
    
    public Intermedio() {
        pila = new Stack();
        indiceActual = 0; indiceFinal = 0;
        codigoIntermedio = new ArrayList();
    }

    private void acomodarEtiketas(ArrayList<String> asignaciones) {
        comps = new ArrayList();
        int j = 0;
        String asignacion = "";
        for (int i = asignaciones.size() - 1; i >= 0; i --) {
            if (asignaciones.get(i).contains("|")) {
                etiketaUltima = new Etiketa("|", etiketaSuper.verdadera1, etiketaSuper.falsa1);
                asignacion = asignaciones.get(i);
                j = i; break;
            }
            else if (asignaciones.get(i).contains("&")) {
                etiketaUltima = new Etiketa("&", etiketaSuper.verdadera1, etiketaSuper.falsa1);
                asignacion = asignaciones.get(i);
                j = i; break;
            } 
        }
        recurcivoAcomodar(etiketaUltima, asignacion, asignaciones, j);
        Etiketa e = etiketaSuper;
        for (String a: asignaciones) {
            String aux = a.substring(3, a.length() - 1);
            if (
                    a.contains(">") | a.contains(">=")| a.contains("<")|
                    a.contains("<=") | a.contains("=") | a.contains("><")
            )  {
                for (Comp c: comps) {
                    if (a.contains(c.asignacion))
                        if (!c.lado) {
                            codigoIntermedio.add(
                                    indiceActual ++,
                                    "compara " + operadores(aux) + "\n" +
                                    salto +" E" + c.verdadera +"\n" +
                                    "JMP E" + c.falsa + "\n" +
                                    (c.opR.contains("&")?"E" + c.verdadera + ":\n": "E" + c.falsa + ":\n")
                            );
                            break;
                        } else {
                            codigoIntermedio.add(
                                    indiceActual ++,
                                    "compara " + operadores(aux) + "\n" +
                                    salto +" E" + c.verdadera +"\n" +
                                    "JMP E" + c.falsa + "\n" +
                                    "E" + aDonde(c) + ":\n"
                            );
                            break;
                        }
                }
                if (comps.isEmpty()) {
                    codigoIntermedio.add(
                            indiceActual ++,
                            "compara " + operadores(aux) + "\n" +
                            salto + " E" + e.verdadera1 +"\n" +
                            "JMP E" + e.falsa1 + "\n" +
                            "E" + e.verdadera1+ ":\n"
                    );
                }
            }
        }
    }
    
    private String salto = "";
    private String operadores(String exp) {
        if (exp.contains("<=")) {
            salto = "JLE";
            return exp.replaceAll("<=", ", ");
        } else if (exp.contains("><")) {
            salto = "JNE";
            return exp.replaceAll("><", ", ");
        }
        else if (exp.contains(">=")) {
            salto = "JGE";
            return exp.replaceAll(">=", ", ");
        }
        else if (exp.contains("<")) {
            salto = "JL";
            return exp.replaceAll("<", ", ");
        }
        else if (exp.contains(">")) {
            salto = "JG";
            return exp.replaceAll(">", ", ");
        }
        else {
            salto = "JE";
            return exp.replaceAll("=", ", ");
        }
    }
    
    private int aDonde(Comp c) {
        if (etiketaSuper.verdadera1 == c.verdadera) {
            return (etiketaSuper.falsa1 == c.falsa)?c.verdadera:c.falsa;
        } else return c.verdadera;
    }
    
    private void recurcivoAcomodar(Etiketa e, String asignacion, ArrayList<String> asignaciones, int k) {
        if (e == null) return;
        Etiketa e2 = null;
        Etiketa e3 = null;
        for (int i = k - 1; i >= 0; i --) {
            if (asignacion.contains(asignaciones.get(i).substring(0, 2))) {
                if (asignaciones.get(i).contains("|")) {
                    e2 = new Etiketa("|", e.verdadera2, e.falsa2);
                    k = i; break;
                } else if (asignaciones.get(i).contains("&")) {
                    e2 = new Etiketa("&", e.verdadera2, e.falsa2);
                    k = i; break;
                } else {
                    comps.add(new Comp(asignaciones.get(i), e.verdadera2, e.falsa2, true, e.opR));
                    k = i; break;
                }
            }    
        }
        int j = k;
        for (int i = j - 1; i >= 0; i --) {
            if (asignacion.contains(asignaciones.get(i).substring(0, 2))) {
                if (asignaciones.get(i).contains("|")) {
                    e3 = new Etiketa("|", e.verdadera1, e.falsa1);
                    j = i; break;
                } else if (asignaciones.get(i).contains("&")) {
                    e3 = new Etiketa("&", e.verdadera1, e.falsa1);
                    j = i; break;
                } else {
                    comps.add(new Comp(asignaciones.get(i), e.verdadera1, e.falsa1, false, e.opR));
                    j = i; break; 
                }
            }
        }
        if (k > 0 | j > 0) {
            recurcivoAcomodar(e2, asignaciones.get(k), asignaciones, k);
            recurcivoAcomodar(e3, asignaciones.get(j), asignaciones, j);
        } 
    }
    
    private String instrucEnsamble(String op1, String simbolo, String op2) {
        op1 = op1.contains("T") ? "@temp" : op1;
        op2 = op2.contains("T") ? "@temp" : op2;
        switch (simbolo) {
            case "+":
                return op1.compareTo(op2) == 0 ? "pop @temp0\npop @temp\nsuma @temp, @temp0\n" : "suma " + op1 + ", " + op2 + "\n";
            case "-":
                return op1.compareTo(op2) == 0 ? "pop @temp0\npop @temp\nresta @temp, @temp0\n" : "resta " + op1 + ", " + op2 + "\n";
            case "*":
                return op1.compareTo(op2) == 0 ? "pop @temp0\npop @temp\nmulti @temp, @temp0\n" : "multi " + op1 + ", " + op2 + "\n";
            case "/":
                return op1.compareTo(op2) == 0? "pop @temp0\npop @temp\ndivi @temp, @temp0\n" : "divi " + op1 + ", " + op2 + "\n";
            default:
                return "mov dx, @temp\nmov " + op1 + ", dx\n";
        }
    }
    
    private ArrayList<String> temporales(ArrayList<String> posfijo) {
        ArrayList<String> asignaciones = new ArrayList();
        int k = 0; int i = 0; String T; String aux;
        while (posfijo.size() > 1) {
            switch (posfijo.get(i)) {
                case "+": case "-": case "*": case "/": case "~":
                    T = "T" + k++;
                    aux = T + "@" +  posfijo.get(i-2) + posfijo.get(i) + posfijo.get(i-1) + "\n";
                    asignaciones.add(aux);
                    aux = instrucEnsamble(posfijo.get(i-2), posfijo.get(i), posfijo.get(i-1));
                    codigoIntermedio.add(indiceActual ++, aux);
                    posfijo.add(i + 1, T);
                    posfijo.remove(i - 2);
                    posfijo.remove(i - 2);
                    posfijo.remove(i - 2);
                    i = 0;
                    break;
                case "|": case "&": case ">": case "<": case "><": case ">=": case "<=": case "=":
                    T = "T" + k++;
                    aux = T + "=" +  posfijo.get(i-2) + posfijo.get(i) + posfijo.get(i-1) + "\n";
                    asignaciones.add(aux);
                    posfijo.add(i + 1, T);
                    posfijo.remove(i - 2);
                    posfijo.remove(i - 2);
                    posfijo.remove(i - 2);
                    i = 0;
                    break;
                case "<-":
                    aux = instrucEnsamble(posfijo.get(i-2), posfijo.get(i), posfijo.get(i-1));
                    codigoIntermedio.add(indiceActual ++, aux);
                    posfijo.add(i + 1, "");
                    posfijo.remove(i - 2);
                    posfijo.remove(i - 2);
                    posfijo.remove(i - 2);
                    break;
                default:
                    i ++;
                    break;

            }
        }
        if (asignaciones.isEmpty()) asignaciones.addAll(posfijo);
        return asignaciones;
    }
    
    public void setNumeroProduccion(int numeroProduccion, ArrayList<String> expresion) {
        switch (numeroProduccion) {
            case 1: Prog_PS(); break;
            case 7: Sentencia_7(); break;
            case 8: Sentencia_8(); break;
            case 120: Sentencia_12(expresion.get(2)); break;
            case 730: casos(expresion.get(1)); break;
            case 74: casos2(); break;
            case 750: asignacion(expresion); break;
            case 15: Sentencia_15(); break;
            case 410: d_imprime(expresion); break;
            case 470: d_recorrido(expresion); break;
            case 62: sino_62(); break;
            case 64: no_64(); break;
            case 65: no_65(); break;
            case 460: leer(expresion); break;
            case 0: comp2_(expresion); break;
            default:
                break;
        }
    }
    
    private void asignacion(ArrayList<String> expresion) {
        expresion.remove(expresion.size() - 1);
        temporales(Compatibilidad.posFijo(expresion));
    }
    
    private void leer(ArrayList<String> expresion) {
        codigoIntermedio.add(
                indiceActual ++,
                expresion.get(0) + " " + expresion.get(2) +"\n"
        );
    }
    
    private void Prog_PS() {
        codigoIntermedio.add(
                indiceActual ++,
                "compara macro @op1, @op2\n" +
                "mov ax, @op1\n" +
                "cmp ax, @op2\n" +
                "endm\n\n" +
                "suma macro @op1, @op2\n" +
                "mov dx, @op1\n" +
                "add dx, @op2\n" +
                "mov @temp, dx\n" +
                "push dx\n" +
                "endm\n\n" +
                "resta macro @op1, @op2\n" +
                "mov dx, @op1\n" +
                "sub dx, @op2\n" +
                "mov @temp, dx\n" +
                "push dx\n" +
                "endm\n\n" +
                "multi  macro @op1, @op2\n" +
                "mov ax, @op1\n" +
                "mov ah, 0\n" +
                "mov bx, @op2\n" +
                "mul bx\n" +
                "mov @temp, ax\n" +
                "push ax\n" +
                "endm\n\n" +
                "divi  macro @op1, @op2\n" +
                "mov ax, @op1\n" +
                "mov ah, 0\n" +
                "mov bx, @op2\n" +
                "div bl\n" +
                "mov ah, 0\n" +
                "mov @temp, ax\n" +
                "push ax\n" +
                "endm\n\n" +
                "leer macro var\n" +
                "mov var, 10\n" +
                "mov ah, 1\n" +
                "int 21h\n" +
                "mov ah, 0\n" +
                "sub al, 48\n" +
                "mul var\n" +  
                "mov var, ax\n" +
                "mov ah, 1\n" +
                "int 21h\n" +
                "mov ah, 0\n" +
                "sub al, 48\n" +
                "add var, ax\n" +
                "mov ah, 2\n" +
                "mov dl, 10\n" +
                "int 21h\n" +
                "endm\n\n" +
                "imprimir macro MENS\n" +
                "local A1\n" +
                "local A2\n" +
                "mov cl, lengthof MENS\n" +
                "cmp cl, 1\n" +
                "je A1\n" +
                "mov ah, 09h\n" +
                "mov dx, offset MENS\n" +
                "int 21h\n" +
                "mov ah, 2\n" +
                "mov dl, 10\n" +
                "int 21\n" +
                "jmp A2\n" +
                "A1: mov ax, word ptr MENS\n" +
                "mov ah, 0\n" +
                "mov bl, 10\n" +
                "div bl\n" +
                "add al, 48\n" +
                "add ah, 48\n" +
                "push ax\n" +
                "mov ah, 2\n" +
                "pop dx\n" +
                "int 21h\n" +
                "mov dl, dh\n" +
                "int 21h\n" +
                "mov dl, 10\n" +
                "int 21h\n" +
                "A2:\n" +
                "endm\n" +                        
                ".model small\n" +
                ".stack 200h\n" +
                ".data\n" +
                "@temp word ?\n" +
                "@temp0 word ?\n"
                
        );
        codigoIntermedio.add(
                indiceActual ++,
                ".code\n" +
                ".startup\n"
        );
        indiceFinal = indiceActual;
        codigoIntermedio.add(indiceFinal ++, ".exit\nend\n");
    }

    private void Sentencia_7() {
        pila.push(etiketaSuper);
        etiketaSuper = new Etiketa(true);
        codigoIntermedio.add(indiceActual ++, "E" + etiketaSuper.inicio + ":\n");
    }

    private void Sentencia_8() {
        pila.push(etiketaSuper);
        etiketaSuper = new Etiketa();
    }
    
    private void Sentencia_12(String var) {
        etiketaSuper = new Etiketa();
        etiketaSuper.var = var;
        etiketaSuper.siguiente = Etiketa.nueva ++;
        indiceFinal = indiceActual;
        codigoIntermedio.add(indiceFinal ++, "E" + etiketaSuper.siguiente + ":\n");
    }
    
    private void casos(String var) {
        Etiketa e = new Etiketa();
        codigoIntermedio.add(
                indiceActual ++,
                "compara " + var + "=" + etiketaSuper.var + "\n" +
                salto + " E" + e.verdadera1 + "\n" +
                "JMP E" + e.falsa1 + "\n"
        );
        indiceFinal = indiceActual;
        codigoIntermedio.add(
                indiceFinal ++,
                "E" + e.verdadera1  + ":\n" +
                "JMP E" + etiketaSuper.siguiente + "\n" + 
                "E" + e.falsa1 + ":\n"
        );
    }
    
    private void casos2() {
        indiceFinal = indiceActual;
        codigoIntermedio.add(indiceFinal ++, "JMP E" + etiketaSuper.siguiente + "\n");
    }
    
    private void Sentencia_15() {
        indiceActual = indiceFinal > indiceActual ? indiceFinal : indiceActual + 1;
        //indiceFinal = 0;
    }
    
    private int indiceCadena = 0;
    private void d_imprime(ArrayList<String> expresion) {
        ArrayList<String> posfijo = Compatibilidad.posFijo(expresion);
        ArrayList<String> asignaciones = temporales(posfijo);
        String aux = asignaciones.get(asignaciones.size() - 1);
        if (aux.contains("'")) {
            cadenas.add(aux + ", 10, '$'");
            aux =  "@cadena" + indiceCadena ++;
        }
        codigoIntermedio.add(indiceActual ++, "imprimir " +
                (aux.contains("T") ? aux.substring(0, 2) : aux + "\n")
        );
    }
    
    private void d_recorrido(ArrayList<String> expresion) {
        pila.add(etiketaSuper);
        etiketaSuper = new Etiketa(true);
        codigoIntermedio.add(
                indiceActual ++,
                "mov " + expresion.get(10) + ", " + expresion.get(2)+ "\n" +
                "E" + etiketaSuper.inicio + ":\n" +
                "compara " + operadores(expresion.get(10) + ("++".contains(expresion.get(6)) ? "<" : ">")) + expresion.get(4) + "\n" +
                salto + " E" + etiketaSuper.verdadera1 + "\n" +
                "JMP E" + etiketaSuper.falsa1 + "\n" +
                "E" + etiketaSuper.verdadera1 + ":\n"
        );
        indiceFinal = indiceActual;
        codigoIntermedio.add(
                indiceFinal ++,
                ("++".contains(expresion.get(6)) ? "add " : "sub ") + expresion.get(10) + ", " + expresion.get(7) + "\n" +
                "JMP E" + etiketaSuper.inicio + "\n" +
                "E" + etiketaSuper.falsa1 + ":\n"
        );
        etiketaSuper = pila.pop();
    }
    
    private void sino_62() {
        int siguiente = etiketaSuper.siguiente == 0 ? Etiketa.nueva ++ : etiketaSuper.siguiente;
        codigoIntermedio.add(
                indiceActual - 1,
                "JMP E" + siguiente + "\n" +
                "E" + etiketaSuper.falsa1 + ":\n"
        );
        etiketaSuper = new Etiketa();
        etiketaSuper.siguiente = siguiente;
    }
    
    private void no_64() {
        etiketaSuper.siguiente = etiketaSuper.siguiente == 0 ? Etiketa.nueva ++ : etiketaSuper.siguiente;
        codigoIntermedio.add(
                indiceActual - 1,
                "JMP E" + etiketaSuper.siguiente + "\n" +
                "E" + etiketaSuper.falsa1 + ":\n"
        );
        indiceFinal = indiceActual;
        codigoIntermedio.add(
                indiceFinal ++,
                "E" + etiketaSuper.siguiente +":\n"
        );
        etiketaSuper = pila.pop();
    }
    
    private void no_65() {
        codigoIntermedio.add(indiceActual - 1, "E" + etiketaSuper.falsa1 + ":\n");
        if (etiketaSuper.siguiente > 0) codigoIntermedio.add(indiceActual, "E" + etiketaSuper.siguiente + ":\n");
        etiketaSuper = pila.pop();
    }
    
    private void comp2_(ArrayList<String> expresion) {
        ArrayList<String> posfijo = Compatibilidad.posFijo(expresion);
        ArrayList<String> asignaciones = temporales(posfijo);
        acomodarEtiketas(asignaciones);
        if (etiketaSuper.inicio > 0) {
            indiceFinal = indiceActual;
            codigoIntermedio.add(
                    indiceFinal ++,
                    "JMP E" + etiketaSuper.inicio + "\n" +
                    "E" + etiketaSuper.falsa1 + ":\n"
            );
            etiketaSuper = pila.pop();
        }
    }
}
