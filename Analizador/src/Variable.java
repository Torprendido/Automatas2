public class Variable {
    
    private final String nombreVariable;
    private final boolean declaracion;
    private final String tipoVariable;
    private final String linea;
    private final int nivel;
    private boolean usada;
    private String valor;

    public Variable(String nombreVariable, boolean declaracion, String tipoVariable, String linea, int nivel, String valor) {
        this.nombreVariable = nombreVariable;
        this.declaracion = declaracion;
        this.tipoVariable = tipoVariable;
        this.linea = linea;
        this.nivel = nivel;
        usada = true;
        this.valor = valor;
    }

    public String getValor() {
        return valor;
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

    public int getNivel() {
        return nivel;
    }

    public boolean esUsada() {
        return usada;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }
}
