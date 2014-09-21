public class Variable {
    
    private final String nombreVariable;
    private final String tipoVariable;
    private final String linea;

    public Variable(String nombreVariable, String tipoVariable, String linea) {
        this.nombreVariable = nombreVariable;
        this.tipoVariable = tipoVariable;
        this.linea = linea;
    }

    public String getNombreVariable() {
        return nombreVariable;
    }

    public String getTipoVariable() {
        return tipoVariable;
    }

    public String getLinea() {
        return linea;
    }
}
