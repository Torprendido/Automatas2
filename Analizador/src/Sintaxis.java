import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import Modelo.Modelo;

public class Sintaxis {
    
    private final ArrayList<String> ids;
    private final ArrayList<String> lineas;
    private final ArrayList<String> nombres;
    private static String[][] tabla; static int altoTabla = 0;
    private final ArrayList<Produccion> producciones = new ArrayList();
    
    public Sintaxis(ArrayList<Lexema> lexemas) {
        ids = new ArrayList();
        lineas = new ArrayList();
        nombres = new ArrayList();
        ids.add("$");
        nombres.add("");
        lineas.add("");
        for (int j = lexemas.size() - 1; j >= 0; j --) {
            ids.add(lexemas.get(j).getId());
            lineas.add(lexemas.get(j).getLinea());
            nombres.add(lexemas.get(j).getLexema());
        }
        CrearTablaSintaxis();
        try {
            CrearListaProduccion();
        } catch (IOException e) {}
    }
    
    private void CrearTablaSintaxis() {
        try {
            Workbook libro = Workbook.getWorkbook(new File("src/txts/tablaSintaxis.xls"));
            altoTabla = libro.getSheet(0).getRows();
            tabla = new String[libro.getSheet(0).getColumns()][altoTabla];
            for (int i = 0; i < tabla.length; i++)
                for (int j = 0; j < altoTabla; j++)
                    tabla[i][j] = libro.getSheet(0).getCell(i, j).getContents();
        } catch (BiffException | IOException ex) {}
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
        Intermedio inter = new Intermedio();
        ArrayList<String> expresion = new ArrayList();
        int cuentaParentesis = 0;
        boolean entraif = false;
        boolean entrafor = false;
        while (!ids.isEmpty()) {
            if (comapararTopes(ids, pila)) {
                if (entraif | entrafor) {
                    String aux = nombres.get(ids.size() - 1);
                    if (!aux.contains(",")) expresion.add(aux);
                    if (aux.compareTo("(") == 0) cuentaParentesis ++;
                    else if (aux.compareTo(")") == 0) cuentaParentesis --;
                    else if (aux.compareTo(",") == 0) cuentaParentesis --;
                    if (cuentaParentesis == 0) {
                        if (entraif) {
                            inter.setNumeroProduccion(0, expresion);
                            entraif = false;
                        } //else if (entrafor) {
//                            inter.setNumeroProduccion(200, expresion);
//                            entrafor = false;
//                        }
                        expresion = new ArrayList();
                    }
                }
                cotarCabezas(ids, pila);
            } else if (checarλ(pila)) pila.remove(pila.size() - 1);
            else {
                x = BuscarX(ids.get(ids.size() - 1));
                y = BuscarY(pila.get(pila.size() - 1));
                if (x == tabla.length) 
                    return ids.get(ids.size() - 1) + " en la linea numero: " + lineas.get(lineas.size() - 1) + "\n";
                if (y == altoTabla) 
                    return "Esperaba:" +
                            Modelo.tokenToLexema(pila.get(pila.size() - 1)) +
                            " en la linea numero: " +
                            lineas.get(lineas.size() - 1) +
                            ", encontro " + Modelo.tokenToLexema(ids.get(ids.size() - 1)) + "\n";
                if (tabla[x][y].compareTo("E") == 0) 
                    return "Esperaba: " + Esperando(y) +
                            " en la linea numero: " +
                            lineas.get(lineas.size() - 1) +
                            ", encontro " + Modelo.tokenToLexema(ids.get(ids.size() - 1)) + "\n";
                int numPro = Integer.parseInt(tabla[x][y]);
                pila.remove(pila.size() - 1);
                agregarGramtica(pila, producciones.get(numPro - 1).getListas());
                if (numPro == 51) {
                    if (!entraif) {
                        expresion.add("(");
                        cuentaParentesis ++;
                        entraif = true;
                    }
                } else if (numPro == 59 | numPro == 60) {
//                    if (!entrafor) {
//                        cuentaParentesis ++;
//                        entraif = true;
//                    }
                }
                inter.setNumeroProduccion(numPro, expresion);
            }
        }
        System.out.println(inter.codigoIntermedio);
        return "";
    }
    
    public String MensajeSintaxis() {
        return AnalizarGramatica();
    }

    private int BuscarX(String s) {
        //System.out.print(s + " -> codigo ");
        for (int i = 0; i < tabla.length; i++) if (tabla[i][0].compareTo(s) == 0) return i;
        return tabla.length;
    }

    private int BuscarY(String s) {
        //System.out.println(s + " -> pila");
        for (int i = 0; i < altoTabla; i++) if (tabla[tabla.length - 1][i].compareTo(s) == 0) return i;
        return altoTabla;
    }

    private boolean comapararTopes(ArrayList<String> ids, ArrayList<String> pila) {
        return (ids.get(ids.size() - 1).compareTo(pila.get(pila.size() - 1)) == 0);
    }

    private void cotarCabezas(ArrayList<String> ids, ArrayList<String> pila) {
//        System.out.print(ids.get(ids.size() - 1) + " ");
//        System.out.println(pila.get(pila.size() - 1) + ".                        Cortar cabezas");
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
        for (int i = 0; i < tabla.length - 1; i++)
            if (tabla[i][y].compareTo("E") != 0) s = s  + Modelo.tokenToLexema(tabla[i][0]) + ", ";
        return s;
    }    
}