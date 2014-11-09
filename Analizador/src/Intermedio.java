
import java.util.ArrayList;
import java.util.Stack;

public class Intermedio {
    
    public final ArrayList<String> codigoIntermedio;
    private Etiketa etiketaSuper;
    private Etiketa etiketaUltima;
    public int indiceActual;
    private int indiceFinal;
    private ArrayList<Comp> comps;
    private Stack<Etiketa> pila;
    
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
    
    private ArrayList<String> orAnd(ArrayList<String> posfijo) {
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
        return asignaciones;
    }
    
    public void setNumeroProduccion(int numeroProduccion, ArrayList<String> expresion) {
        switch (numeroProduccion) {
            case 1:
                Prog_PS();
                break;
            case 2:
                Sentencia_2();
                break;
            case 3:
                Sentencia_3();
                break;
            case 4:
                Sentencia_4();
                break;
            case 5:
                Sentencia_5();
                break;
            case 6:
                Sentencia_6();
                break;
            case 7:
                Sentencia_7();
                break;
            case 8:
                Sentencia_8();
                break;
            case 9:
                Sentencia_9();
                break;
            case 10:
                Sentencia_10();
                break;
            case 11:
                Sentencia_11();
                break;
            case 12:
                Sentencia_12();
                break;
            case 13:
                Sentencia_13();
                break;
            case 14:
                Sentencia_14();
                break;
            case 15:
                Sentencia_15();
                break;
            case 16:
                break;
            case 17:
                break;
            case 18:
                break;
            case 19:
                break;
            case 20:
                break;
            case 21:
                break;
            case 22:
                break;
            case 23:
                break;
            case 24:
                break;
            case 25:
                break;
            case 26:
                break;
            case 27:
                break;
            case 28:
                break;
            case 29:
                break;
            case 62:
                sino_62();
                break;
            case 64:
                no_64();
                break;
            case 65:
                no_65();
                break;
            case 0:
                comp2_(expresion);
                break;
            case 200:
                //entrafor(expresion);
                break;
            default:
                break;
        }
    }
    
    private void Prog_PS() {
        codigoIntermedio.add(indiceActual ++, "inicio\n");
        indiceFinal = indiceActual;
        codigoIntermedio.add(indiceFinal ++, "fin\n");
    }

    private void Sentencia_2() {}

    private void Sentencia_3() {}

    private void Sentencia_4() {}

    private void Sentencia_5() {}

    private void Sentencia_6() {
        
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

    private void Sentencia_9() {}

    private void Sentencia_10() {}

    private void Sentencia_11() {}

    private void Sentencia_12() {}

    private void Sentencia_13() {}

    private void Sentencia_14() {}

    private void Sentencia_15() {
        //indiceActual = indiceFinal > 0 ? indiceFinal : indiceActual;
        indiceFinal = 0;
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
        ArrayList<String> asignaciones = orAnd(posfijo);
        acomodarEtiketas(asignaciones);
        if (etiketaSuper.inicio > 0) {
            indiceFinal = indiceActual;
            codigoIntermedio.add(indiceFinal ++, "goto " + etiketaSuper.inicio + "\n");
            codigoIntermedio.add(indiceFinal ++, etiketaSuper.falsa1 + ":\n");
            etiketaSuper = pila.pop();
        }
    }
    
    private void entrafor(ArrayList<String> expresion) {
        System.out.println(expresion);
    }
}
