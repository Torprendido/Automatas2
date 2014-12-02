
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Ensamblador {

    public Ensamblador() {
        generarCodigo();
    }

    private void generarCodigo() {
        int i = 1;
        String tipo = "";
        String valor = "";
        for (Variable v : Semantico.variablesGlobales) {
            switch (v.getTipoVariable()) {
                case "cadena":
                    tipo = "db";
                    valor = v.getValor().length() > 0 ? v.getValor() + ", 10, '$'" : "?";
                    break;
                case "entero":
                    tipo = "word";
                    valor = v.getValor().length() > 0 ? v.getValor() : "?";
                    break;
            }
            String aux = v.getNombreVariable() + ' ' + tipo + ' ' + valor + '\n';
            Intermedio.codigoIntermedio.add(i ++, aux);
        }
        Semantico.variablesGlobales.clear();
        int j = 0;
        for (String s: Intermedio.cadenas) Intermedio.codigoIntermedio.add(i ++, "@cadena" + j ++ + " db " + s + "\n");
        Intermedio.cadenas.clear();
        try {
            BufferedWriter archivo = new BufferedWriter(new FileWriter ("/home/torpre/.dosemu/drive_c/Autom2/autom2.asm", false));
            for (String c : Intermedio.codigoIntermedio) archivo.write(c);
            archivo.close();
        } catch (FileNotFoundException ex) {System.err.println(ex.getMessage());
        } catch (IOException ex) {System.err.println(ex.getMessage());}
        
    }

}
