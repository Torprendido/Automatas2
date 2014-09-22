import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Sintaxis {
    
    private final ArrayList<String> ids;
    private String[][] tabla; int altoTabla = 0;
    private final ArrayList<Produccion> producciones = new ArrayList();
    private final ArrayList<String> lineas;
    
    public Sintaxis(ArrayList<String> ids, ArrayList<String> lineas) {
        this.ids = ids;
        this.lineas = lineas;
        try {
            CrearTabla();
            CrearListaProduccion();
        } catch (IOException e) {}
    }
    
    private void CrearTabla() throws FileNotFoundException, IOException {
        BufferedReader txt = new BufferedReader(new FileReader(new File("src/txts/tablaSintaxis.txt")));
        int largoTabla = txt.readLine().length()/2;
        altoTabla = 0;
        String aux;
        do {
            altoTabla ++;
            aux = txt.readLine();
        } while (aux != null);
        tabla = new String[largoTabla][altoTabla];
        txt = new BufferedReader(new FileReader(new File("src/txts/tablaSintaxis.txt")));
        int j = 0;
        do {
            aux = txt.readLine();
            if (aux != null) {
                for (int i = 0; i < largoTabla; i++) {
                    if (aux.length() > largoTabla*2 & i == largoTabla - 1) {
                        tabla[largoTabla - 1][j] = aux.substring(largoTabla*2 - 2, aux.length());
                    }
                    else tabla[i][j] = aux.charAt(i*2) + "" + aux.charAt(i*2 + 1);
                }
            }
            j ++;
        } while (aux != null);
        for (int i = 1; i < altoTabla; i++) {
            for (int k = 0; k < largoTabla; k++) {
                if (tabla[k][i].contains(" ")) tabla[k][i] = QutarBasío(tabla[k][i]);
            }
        }
    }
    
    private void CrearListaProduccion() throws FileNotFoundException, IOException {
        BufferedReader txt = new BufferedReader(new FileReader(new File("src/txts/listaProduccion.txt")));
        String aux = "";
        do {
            if (aux != null & aux.compareTo("") != 0) producciones.add(new Produccion(aux));
            aux = txt.readLine();
        }
        while (aux != null);
    }
    
    private String AnalizarGramatica() {
        ArrayList<String> pila = new ArrayList();
        pila.add("$");
        pila.add(tabla[tabla.length - 1][1]);
        int x, y;
        while (!ids.isEmpty()) {
            if (comapararTopes(ids, pila)) cotarCabezas(ids, pila);
            else if (checarλ(pila)) pila.remove(pila.size() - 1);
            else {
                x = BuscarX(ids.get(ids.size() - 1));
                y = BuscarY(pila.get(pila.size() - 1));
                if (x == tabla.length) 
                    return ids.get(ids.size() - 1) + " en la linea numero: " + lineas.get(lineas.size() - 1) + "\n";
                if (y == altoTabla) 
                    return "Esperaba " + pila.get(pila.size() - 1) + " en la linea numero: " + lineas.get(lineas.size() - 1) + "\n";
                if (tabla[x][y].compareTo("E") == 0) 
                    return "Esperaba: " + Esperando(y) + " en la linea numero: " + lineas.get(lineas.size() - 1)  + "\n";
                int numPro = Integer.parseInt(tabla[x][y]);
                pila.remove(pila.size() - 1);
                agregarGramtica(pila, producciones.get(numPro - 1).getListas());
            }
        }
        return "";
    }
    
    public String MensajeSintaxis() {
        return AnalizarGramatica();
    }

    private String QutarBasío(String cadena) {
        String s = "";
        for (int i = 0; i < cadena.length(); i++) if (cadena.charAt(i) != ' ') s = s + cadena.charAt(i);
        return s;
    }

    private int BuscarX(String s) {
        System.out.print(s + " -> codigo ");
        for (int i = 0; i < tabla.length; i++) if (tabla[i][0].compareTo(s) == 0) return i;
        return tabla.length;
    }

    private int BuscarY(String s) {
        System.out.println(s + " t-> pila");
        for (int i = 0; i < altoTabla; i++) if (tabla[tabla.length - 1][i].compareTo(s) == 0) return i;
        return altoTabla;
    }

    private boolean comapararTopes(ArrayList<String> ids, ArrayList<String> pila) {
        return (ids.get(ids.size() - 1).compareTo(pila.get(pila.size() - 1)) == 0);
    }

    private void cotarCabezas(ArrayList<String> ids, ArrayList<String> pila) {
        System.out.print(ids.get(ids.size() - 1) + " ");
        System.out.println(pila.get(pila.size() - 1) + ".                        Cortar cabezas");
        ids.remove(ids.size() - 1);
        lineas.remove(lineas.size() - 1);
        pila.remove(pila.size() - 1);
    }

    private void agregarGramtica(ArrayList<String> pila, ArrayList<String> l) {
        for (int i = l.size() - 1; i > 0; i --) {
            pila.add(l.get(i));
        }
    }

    private boolean checarλ(ArrayList<String> pila) {
        return (pila.get(pila.size() - 1).compareTo("λ") == 0);
    }

    private String Esperando(int y) {
        String s = "";
        for (int i = 0; i < tabla.length - 1; i++) {
            if (tabla[i][y].compareTo("E") != 0) s = s  + token(tabla[i][0]) + ", ";
        }
        return s;
    }
    
    public String token(String numeroToken) {
        String[][] caracteres = Frame.ExtraerCarctaeresDeArchivo();
        String[][] palabras = Frame.ExtraerPalabraReservada();
        for (String[] c: caracteres) {
            if (numeroToken.compareTo(c[1]) == 0) {
                return c[0];
            }
        }
        for (String[] p: palabras) {
            if (numeroToken.compareTo(p[1]) == 0) {
                return p[0];
            }
        }
        return "";//las tiene que encontrar, por lo tanto nunca retorna ""
    }
}