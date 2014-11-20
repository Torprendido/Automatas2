public class Ensamblador {
    
    public Ensamblador() {
        generarCodigo();
    }
    
    private void generarCodigo() {
        int i = 1;
        String tipo = "";
        String valor = "";
        for (Variable v: Semantico.variablesGlobales) {
            switch (v.getTipoVariable()) {
                case "cadena":
                    tipo = "db";
                    valor = v.getValor().length() > 0 ? v.getValor().substring(0, v.getValor().length()-1) + "$'": "?";
                    break;
                case "entero":
                    tipo = "word";
                    valor = v.getValor().length() > 0 ? v.getValor(): "?";
                    break;
            }
            Intermedio.codigoIntermedio.add(i ++, v.getNombreVariable() + ' ' + tipo + ' ' + valor + '\n');
        }
        for (String c: Intermedio.codigoIntermedio) {
            System.out.print(c);
        }
    }
    
}
