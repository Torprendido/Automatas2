public class Lexema {
    
    private String lexema = "", id = "", token = "", linea = "", lexemaTipo = "";
    
    public Lexema() {}

    public String getLexemaTipo() {
        return lexemaTipo;
    }

    public void setLexemaTipo(String lexemaTipo) {
        this.lexemaTipo = lexemaTipo;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }
    
    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        setLexemaTipo(lexema);
        this.lexema = lexema;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
