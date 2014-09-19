public class Variable {
    
    private final String nombreVariable;
    private final String tipoVariable;
    private final String valor;
    private final String linea;

    public Variable(String nombreVariable, String tipoVariable, String valor, String linea) {
        this.nombreVariable = nombreVariable;
        this.tipoVariable = tipoVariable;
        this.valor = valor;
        this.linea = linea;
    }

    public String getNombreVariable() {
        return nombreVariable;
    }

    public String getTipoVariable() {
        return tipoVariable;
    }

    public String getValor() {
        return valor;
    }

    public String getLinea() {
        return linea;
    }
}
