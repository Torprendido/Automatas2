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
        boolean entraImp = false;
        boolean entraFor = false;
        boolean entraSw = false;
        boolean entraCaso = false;
        boolean entraLeer = false;
        boolean entraTipo = false;
        boolean entraAsig = false;
        while (!ids.isEmpty()) {
            if (comapararTopes(ids, pila)) {
                if (entraif | entraImp | entraFor | entraSw | entraCaso | entraLeer | entraTipo | entraAsig) {
                    String aux = nombres.get(ids.size() - 1);
                    expresion.add(aux);
                    if (aux.compareTo("(") == 0) cuentaParentesis ++;
                    else if (aux.compareTo(")") == 0) cuentaParentesis --;
                    else if (aux.compareTo("¿") == 0) cuentaParentesis = 0;
                    else if (aux.compareTo(":") == 0) cuentaParentesis = 0;
                    else if (aux.compareTo("\\") == 0) cuentaParentesis = 0;
                    if (cuentaParentesis == 0) {
                        if (entraif) {
                            inter.setNumeroProduccion(0, expresion);
                            entraif = false;
                        } else if (entraImp) {
                            inter.setNumeroProduccion(410, expresion);
                            entraImp = false;
                        } else if (entraFor) {
                            inter.setNumeroProduccion(470, expresion);
                            entraFor = false;
                        } else if (entraSw) {
                            inter.setNumeroProduccion(120, expresion);
                            entraSw = false;
                        } else if (entraCaso) {
                            inter.setNumeroProduccion(730, expresion);
                            entraCaso = false;
                        } else if (entraLeer) {
                            inter.setNumeroProduccion(460, expresion);
                            entraLeer = false;
                        } else if (entraTipo) {
                            entraTipo =  false;
                        } else if (entraAsig) {
                            inter.setNumeroProduccion(750, expresion);
                            entraAsig = false;
                        }
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
                } else if (numPro == 42 | numPro == 42) {
                    if (!entraImp) {
                        expresion.add("(");
                        cuentaParentesis ++;
                        entraImp = true;
                    }
                } else if (numPro == 47) {
                    cuentaParentesis = 1;
                    entraFor = true;
                } else if (numPro == 12) {
                    cuentaParentesis ++;
                    entraSw = true;
                } else if (numPro == 73) {
                    cuentaParentesis = 1;
                    entraCaso = true;
                } else if (numPro == 46) {
                    cuentaParentesis = 1;
                    entraLeer = true;
                } else if (numPro == 16 | numPro == 18) {
                    cuentaParentesis = 1;
                    entraTipo = true;
                } else if (numPro == 75) {
                    cuentaParentesis = 1;
                    entraAsig = true;
                }
                inter.setNumeroProduccion(numPro, expresion);
            }
        }
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