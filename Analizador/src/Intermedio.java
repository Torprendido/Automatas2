
import java.util.ArrayList;
import java.util.Stack;

public class Intermedio {
    
    public final ArrayList<String> codigoIntermedio;
    private Etiketa etiketaSuper;
    private Etiketa etiketaUltima;
    public int indiceActual;
    private int indiceFinal;
    private ArrayList<Comp> comps;
    private final Stack<Etiketa> pila;
    
    public Intermedio() {
        pila = new Stack();
        codigoIntermedio = new ArrayList();
        indiceActual = 0; indiceFinal = 0;
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
            if (
                    a.contains(">") | a.contains(">=")| a.contains("<")|
                    a.contains("<=") | a.contains("=") | a.contains("><")
            )  {
                for (Comp c: comps) {
                    if (a.contains(c.asignacion))
                        if (!c.lado) {
                            codigoIntermedio.add(
                                    indiceActual ++,
                                    "if (" + a.substring(3, a.length() - 1)+") goto " + c.verdadera +"\n" +
                                    "goto " + c.falsa + "\n" +
                                    (c.opR.contains("&")?c.verdadera + ":\n":c.falsa + ":\n")
                            );
                            break;
                        } else {
                            codigoIntermedio.add(
                                    indiceActual ++,
                                    "if (" + a.substring(3, a.length() - 1)+") goto " + c.verdadera +"\n" +
                                    "goto " + c.falsa + "\n" +
                                    aDonde(c) + ":\n"
                            );
                            break;
                        }
                }
                if (comps.isEmpty()) {
                    codigoIntermedio.add(
                            indiceActual ++,
                            "if (" + a.substring(3, a.length() - 1)+") goto " + e.verdadera1 +"\n" +
                            "goto " + e.falsa1 + "\n" +
                            e.verdadera1+ ":\n"

                    );
                }
            }
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
    
    private ArrayList<String> temporales(ArrayList<String> posfijo) {
        ArrayList<String> asignaciones = new ArrayList();
        int k = 0; int i = 0; String T; String aux;
        while (posfijo.size() > 1) {
            switch (posfijo.get(i)) {
                case "+": case "-": case "*": case "/": case "~":
                    T = "t" + k++;
                    aux = T + "@" +  posfijo.get(i-2) + posfijo.get(i) + posfijo.get(i-1) + "\n";
                    asignaciones.add(aux);
                    codigoIntermedio.add(indiceActual ++, aux);
                    posfijo.add(i + 1, T);
                    posfijo.remove(i - 2);
                    posfijo.remove(i - 2);
                    posfijo.remove(i - 2);
                    i = 0;
                    break;
                case "|": case "&": case ">": case "<": case "><": case ">=": case "<=": case "=":
                    T = "t" + k++;
                    aux = T + "=" +  posfijo.get(i-2) + posfijo.get(i) + posfijo.get(i-1) + "\n";
                    asignaciones.add(aux);
                    posfijo.add(i + 1, T);
                    posfijo.remove(i - 2);
                    posfijo.remove(i - 2);
                    posfijo.remove(i - 2);
                    i = 0;
                    break;
                case "<-":
                    
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
            case 12: Sentencia_12(); break;
            case 15: Sentencia_15(); break;
            case 410: d_imprime(expresion); break;
            case 470: d_recorrido(expresion); break;
            case 62: sino_62(); break;
            case 64: no_64(); break;
            case 65: no_65(); break;
            case 0: comp2_(expresion); break;
            default:
                break;
        }
    }
    
    private void Prog_PS() {
        codigoIntermedio.add(indiceActual ++, "inicio\n");
        indiceFinal = indiceActual;
        codigoIntermedio.add(indiceFinal ++, "fin\n");
    }

    private void Sentencia_7() {
        pila.push(etiketaSuper);
        etiketaSuper = new Etiketa(true);
        codigoIntermedio.add(indiceActual ++, etiketaSuper.inicio + ":\n");
    }

    private void Sentencia_8() {
        pila.push(etiketaSuper);
        etiketaSuper = new Etiketa();
    }
    
    private void Sentencia_12() {
        
    }
    
    private void Sentencia_15() {
        //indiceActual = indiceFinal > 0 ? indiceFinal : indiceActual;
        indiceFinal = 0;
    }
    
    private void d_imprime(ArrayList<String> expresion) {
        ArrayList<String> posfijo = Compatibilidad.posFijo(expresion);
        ArrayList<String> asignaciones = temporales(posfijo);
        String aux = asignaciones.get(asignaciones.size() - 1);
        codigoIntermedio.add(indiceActual ++, "imprimir " +
                (aux.contains("t") ? aux.substring(0, 2) : aux) + "\n"
        );
    }
    
    private void d_recorrido(ArrayList<String> expresion) {
        etiketaSuper = new Etiketa(true);
        codigoIntermedio.add(
                indiceActual ++,
                expresion.get(10) + " = " + expresion.get(2)+ "\n" +
                etiketaSuper.inicio + ":\n" +
                "if (" + expresion.get(10) + ("++".contains(expresion.get(6)) ? "<" : ">") + expresion.get(4) + ") goto " + etiketaSuper.verdadera1 + "\n" +
                "goto " + etiketaSuper.falsa1 + "\n" +
                etiketaSuper.verdadera1 + ":\n"
        );
        indiceFinal = indiceActual;
        codigoIntermedio.add(
                indiceFinal ++,
                expresion.get(10) + "=" + expresion.get(10) + ("++".contains(expresion.get(6)) ? "+" : "-") + expresion.get(7) + "\n" +
                "goto " + etiketaSuper.inicio + "\n" +
                etiketaSuper.falsa1 + ":\n"
        );
    }
    
    private void sino_62() {
        int siguiente = etiketaSuper.siguiente == 0 ? Etiketa.nueva ++ : etiketaSuper.siguiente;
        codigoIntermedio.add(
                indiceActual ++,
                "goto " + siguiente + "\n" +
                etiketaSuper.falsa1 + ":\n"
        );
        etiketaSuper = new Etiketa();
        etiketaSuper.siguiente = siguiente;
    }
    
    private void no_64() {
        codigoIntermedio.add(
                indiceActual ++,
                "goto " + etiketaSuper.siguiente + "\n" +
                etiketaSuper.falsa1 + ":\n"
        );
        indiceFinal = indiceActual;
        codigoIntermedio.add(indiceFinal ++, etiketaSuper.siguiente + ":\n"
        );
        etiketaSuper = pila.pop();
    }
    
    private void no_65() {
        codigoIntermedio.add(indiceActual ++, etiketaSuper.falsa1 + ":\n");
        if (etiketaSuper.siguiente > 0) codigoIntermedio.add(indiceActual ++, etiketaSuper.siguiente + ":\n");
        etiketaSuper = pila.pop();
    }
    
    private void comp2_(ArrayList<String> expresion) {
        ArrayList<String> posfijo = Compatibilidad.posFijo(expresion);
        ArrayList<String> asignaciones = temporales(posfijo);
        acomodarEtiketas(asignaciones);
        if (etiketaSuper.inicio > 0) {
            indiceFinal = indiceActual;
            codigoIntermedio.add(indiceFinal ++, "goto " + etiketaSuper.inicio + "\n");
            codigoIntermedio.add(indiceFinal ++, etiketaSuper.falsa1 + ":\n");
            etiketaSuper = pila.pop();
        }
    }
}
