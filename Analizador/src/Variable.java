public class Variable {
    
    private final String nombreVariable;
    private final boolean declaracion;
    private final String tipoVariable;
    private final String linea;
    private final int nivel;

    public Variable(String nombreVariable, boolean declaracion, String tipoVariable, String linea, int nivel) {
        this.nombreVariable = nombreVariable;
        this.declaracion = declaracion;
        this.tipoVariable = tipoVariable;
        this.linea = linea;
        this.nivel = nivel;
    }

    public String getNombreVariable() {
        return nombreVariable;
    }

    public boolean esDeclaracion() {
        return declaracion;
    }
    
    public String getTipoVariable() {
        return tipoVariable;
    }

    public String getLinea() {
        return linea;
    }

    public boolean isDeclaracion() {
        return declaracion;
    }

    public int getNivel() {
        return nivel;
    }
}
