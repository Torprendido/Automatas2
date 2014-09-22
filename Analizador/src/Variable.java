public class Variable {
    
    private final String nombreVariable;
    private final boolean declaracion;
    private final String tipoVariable;
    private final String linea;

    public Variable(String nombreVariable, boolean declaracion, String tipoVariable, String linea) {
        this.nombreVariable = nombreVariable;
        this.declaracion = declaracion;
        this.tipoVariable = tipoVariable;
        this.linea = linea;
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
}
