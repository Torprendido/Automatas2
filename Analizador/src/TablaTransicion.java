import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TablaTransicion {
    
    private static char[][] tabla;
    
    public TablaTransicion () {}

    public static char[][] getTabla() {
        AbrirExpresion();
        return tabla;
    }
    
    private static void AbrirExpresion(){
        try{
            File archivo = new File("src/txts/Tebla.txt");
            BufferedReader txt = new BufferedReader(new FileReader(archivo));
            String s = "";
            String aux;
            int largo = 0, alto = 0;
            do {
                aux = txt.readLine();
                if (aux != null) {
                    alto ++;
                    largo = aux.length();
                }
            } while (aux != null);
            tabla = new char[largo][alto];
            txt = new BufferedReader(new FileReader(archivo));
            int c = 0;
            do {
                aux = txt.readLine();
                if (aux != null) {
                    for (int i = 0; i < largo; i++) {
                        tabla[i][c] = aux.charAt(i);
                    }
                    c ++;
                }
            } while (aux != null);
        } catch(IOException e){}
    }
}
