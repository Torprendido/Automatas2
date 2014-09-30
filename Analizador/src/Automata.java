public class Automata {
    
    public Automata() {}
    
    public static boolean EsValido(String s, char[][] tabla, Lexema lex) {
        int estado = 0 + 1;
        char[] array = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            boolean encuentra = false;
            for (int j = 0; j < tabla.length - 2; j++) {//no toma en cuenta el caracter 'P' ni N
                if (array[i] == tabla[j][0]) {
                    if (tabla[j][estado] != 'x') {
                        estado = Integer.parseInt(tabla[j][estado] + "") + 1;
                        encuentra = true;
                        break;
                    }
                }
            }
            boolean aux = tabla[tabla.length - 2][estado] != 'v' & i == array.length - 1;
            if (!encuentra || aux) {
                TokensError(estado, lex);
                return false;
            }
        }
        //si todo esta vien mandara el numero de token
        switch (tabla[tabla.length - 1][estado]) {
            case '3':
                lex.setToken("Comentario");
                lex.setId("33");
                break;
            case '4':
                lex.setToken("Variable");
                lex.setId("34");
                break;
            case '5':
                lex.setToken("Numero");
                lex.setId("35");
                break;
            case '8':
                lex.setToken("Cadena");
                lex.setId("36");
                break;
            case '9':
                lex.setToken("Doble");
                lex.setId("52");
                break;
        }
        return true;
    }

    private static void TokensError(int estado, Lexema lex) {
        switch (estado) {
            case 1:
            case 4:
                lex.setToken("Variable no valida");
                lex.setId("Variable no valida");
                break;
            case 2:
                lex.setToken("Comentario no valido");
                lex.setId("Comentario no valido");
                break;
            case 6:
                lex.setToken("Numero no valido");
                lex.setId("Numero no valido");
                break;
            case 8:
                lex.setToken("Cadena no valida");
                lex.setId("Cadena no valida");
                break;
            default:
                lex.setToken("Error no identificado");
                lex.setId("Error no identificado");
                break;
        }
    }
}