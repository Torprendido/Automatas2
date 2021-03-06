
package Modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Modelo {
    
    public Modelo() {}
    
    public static boolean AbrirCodigo(JTextArea area) {
        FileNameExtensionFilter extension = new FileNameExtensionFilter("Abrir Archivo", "txt");
        JFileChooser gestor = new JFileChooser();
        gestor.setFileFilter(extension);
        int n = gestor.showOpenDialog(null);
        if (n == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = gestor.getSelectedFile();
                BufferedReader txt = new BufferedReader(new FileReader(archivo));
                String aux;
                String codigo = "";
                do {
                    aux = txt.readLine();
                    if (aux != null) codigo = codigo + aux + '\n';
                } while (aux != null);
                String encoded = new String(codigo.getBytes("UTF-8"));
                area.setText(encoded);
            } catch (IOException e) {}
            return true;
        }
        return false;
    }
    
    public static String[][] ExtraerCarctaeresDeArchivo() {
        try {
            int n = 0;
            BufferedReader caracteres = new BufferedReader(new FileReader(new File("src/txts/caracteres.txt")));
            while (caracteres.readLine() != null) n ++;
            String aux[][] = new String[n][3];
            caracteres = new BufferedReader(new FileReader(new File("src/txts/caracteres.txt")));
            for (String[] a: aux) {
                String linea = caracteres.readLine();
                a[0] = linea.substring(25, linea.length());
                a[1] = linea.substring(0, 2).replaceAll(" ", "");
                a[2] = linea.substring(2, 25);
            }
            return aux;
        } catch (IOException ex) {
            return new String[3][0];
        }
    }

    public static String[][] ExtraerPalabraReservada() {
        try{
            int n = 0;
            BufferedReader palabras = new BufferedReader(new FileReader(new File("src/txts/palabras.txt")));
            while (palabras.readLine() != null) n ++;
            String aux[][] = new String[n][2];
            palabras = new BufferedReader(new FileReader(new File("src/txts/palabras.txt")));
            for (String[] a : aux) {
                String linea = palabras.readLine();
                a[0] = linea.substring(2, linea.length()); //substrae desde el caracter 2
                a[1] = linea.substring(0, 2);
            }
            return aux;
        } catch (IOException ex) {
            return new String[2][0];
        }
    }
    
    public static String[][] ExtraerAutomatas() {
        try{
            int n = 0;
            BufferedReader palabras = new BufferedReader(new FileReader(new File("src/txts/automatas.txt")));
            while (palabras.readLine() != null) n ++;
            String aux[][] = new String[n][2];
            palabras = new BufferedReader(new FileReader(new File("src/txts/automatas.txt")));
            for (String[] a : aux) {
                String linea = palabras.readLine();
                a[0] = linea.substring(2, linea.length()); //substrae desde el caracter 2
                a[1] = linea.substring(0, 2);
            }
            return aux;
        } catch (IOException ex) {
            return new String[2][0];
        }
    }

    public static String[][] ExtraerTiposDeDatos() {
        try{
            int n = 0;
            BufferedReader palabras = new BufferedReader(new FileReader(new File("src/txts/tiposDeDatos.txt")));
            while (palabras.readLine() != null) n ++;
            String aux[][] = new String[n][2];
            palabras = new BufferedReader(new FileReader(new File("src/txts/tiposDeDatos.txt")));
            for (String[] a : aux) {
                String linea = palabras.readLine();
                a[0] = linea.substring(2, linea.length()); //substrae desde el caracter 2
                a[1] = linea.substring(0, 2);
            }
            return aux;
        } catch (IOException ex) {
            return new String[2][0];
        }
    }
    
    public static String tokenToLexema(String numeroToken) {
        String[][] caracteres = ExtraerCarctaeresDeArchivo();
        String[][] palabras = ExtraerPalabraReservada();
        String[][] automatas = ExtraerAutomatas();
        switch (numeroToken) {
            case "1":
                return "Operador Aritmetico";
            case "2":
                return "Operador Logico";
            case "3":
                return "Operador Relacional";
        }
        for (String[] c: caracteres) if (numeroToken.compareTo(c[1]) == 0) return c[0];
        for (String[] p: palabras) if (numeroToken.compareTo(p[1]) == 0) return p[0];
        for (String[] a: automatas) if (numeroToken.compareTo(a[1]) == 0) return a[0];
        return "";//espero que la encuentre
    }
    
    public static String producto(String archivo, String factorA, String factorB) {
        try {
            Workbook libro = Workbook.getWorkbook(new File("src/txts/" + archivo));
            int altoTabla = libro.getSheet(0).getRows();
            String[][] tabla = new String[libro.getSheet(0).getColumns()][altoTabla];
            for (int i = 0; i < tabla.length; i++)
                for (int j = 0; j < altoTabla; j++)
                    tabla[i][j] = libro.getSheet(0).getCell(i, j).getContents();
            int x = 0, y = 0;
            for (int i = 0; i < tabla.length; i++) if (tabla[i][0].compareTo(factorA) == 0) x = i;
            for (int j = 0; j < altoTabla; j++) if (tabla[0][j].compareTo(factorB) == 0) y = j;
            return tabla[x][y];
        } catch (BiffException | IOException ex) {
            return "";
        }
    }
}
