
import java.util.ArrayList;

public class Semantico extends Arbol{

    public Semantico() {}
    
    public static String MensajeSemantico(ArrayList<Lexema> lexemas) {
        Arbol arb = new Arbol();
        String[][] tipos = Frame.ExtraerTiposDeDatos();
        for (int i = 0; i < lexemas.size(); i ++) {
            if (lexemas.get(i).getToken().compareTo("Variable") == 0) {
                if (contiene(lexemas.get(i - 1).getId(), tipos)) {
                    
                } else {
                    
                }
            }
        }
        return "";
    }
    
    private static boolean contiene(String id, String[][] tipos) {
        boolean existe = false;
        for (String[] t: tipos) {
            if (t[1].compareTo(id) == 0) {
                existe = true;
                break;
            }
        }
        return existe;
    }
    
}
