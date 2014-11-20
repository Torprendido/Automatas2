
public class Etiketa {

    public int verdadera1 = 0, falsa1 = 0, verdadera2 = 0, falsa2, inicio = 0, siguiente = 0;
    public String var = "";
    public static int nueva = 10;
    public String opR = "";
    
    public Etiketa() {
        verdadera1 = nueva ++;
        falsa1 = nueva ++;
    }
    
    public Etiketa(boolean inicio) {
        this.inicio = inicio?nueva ++:0;
        verdadera1 = nueva ++;
        falsa1 = nueva ++;
    }
    
    public Etiketa(String logico, int verdadera, int falsa) {
        switch (logico) {
            case "|":
                verdadera1 = verdadera;
                falsa1 = nueva ++;
                verdadera2 = verdadera;
                falsa2 = falsa;
                break;
            case "&":
                verdadera1 = nueva ++;
                falsa1 = falsa;
                verdadera2 = verdadera;
                falsa2 = falsa;
                break;
        }
        opR = logico;
    }
}
