
import java.util.ArrayList;

public class Intermedio {
    
    private final ArrayList<String> codigoIntermedio;
    private int numeroProduccion;
    
    public Intermedio() {
        codigoIntermedio = new ArrayList();
        Prog_PS();
    }

    public void setNumeroProduccion(int numeroProduccion) {
        this.numeroProduccion = numeroProduccion;
    }
    
    private void Prog_PS() {
        codigoIntermedio.add(
                ".model small\n" +
                ".stack\n" +
                ".data\n" +
                ".code\n" +
                "main proc\n"
        );
        Sentencia();
        codigoIntermedio.add(
                "main endp\n" +
                "end main\n"
        );
    }

    private void Sentencia() {
        switch (numeroProduccion) {
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                d_Si();
                Sentencia();
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 15:
                break;
            default:
                break;
        }
    }
    
    private void d_Si() {
        comp();
        Sentencia();
        codigoIntermedio.add(
                "goto 30\n" +
                "20:\n" +
                "30:\n"
        );
    }
    
    private void comp() {
        codigoIntermedio.add("posfijos");
        codigoIntermedio.add("if ... goto fin\n");
    }
}
