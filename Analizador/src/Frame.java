import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Frame extends javax.swing.JFrame {
    
    private JFileChooser gestor;
    private ArrayList<Lexema> lexemas;
    
    public Frame() {
        initComponents();
        lineasJSP.getVerticalScrollBar().addAdjustmentListener(
                new AdjustmentListener() {
                        @Override
                        public void adjustmentValueChanged(AdjustmentEvent e) {
                            areaJSP.getVerticalScrollBar().setValue(e.getValue());
                        }
                }
        );
        analizar.setEnabled(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        areaJSP = new javax.swing.JScrollPane();
        area = new javax.swing.JTextArea();
        abrir = new javax.swing.JButton();
        analizar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaJT = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        mensaje = new javax.swing.JTextArea();
        compilar = new javax.swing.JButton();
        lineasJSP = new javax.swing.JScrollPane();
        areaLineas = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        area.setColumns(20);
        area.setRows(5);
        areaJSP.setViewportView(area);

        abrir.setText("Abrir Codigo");
        abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirActionPerformed(evt);
            }
        });

        analizar.setText("Analizar");
        analizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analizarActionPerformed(evt);
            }
        });

        tablaJT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Lexama", "Token", "ID", "Linea"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaJT);

        mensaje.setColumns(20);
        mensaje.setRows(5);
        jScrollPane3.setViewportView(mensaje);

        compilar.setText("Compilar");
        compilar.setEnabled(false);
        compilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compilarActionPerformed(evt);
            }
        });

        areaLineas.setColumns(20);
        areaLineas.setRows(5);
        lineasJSP.setViewportView(areaLineas);

        jButton1.setText("Tal Chois!!!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(abrir)
                                .addGap(18, 18, 18)
                                .addComponent(analizar))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lineasJSP, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(areaJSP, javax.swing.GroupLayout.DEFAULT_SIZE, 645, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(compilar)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(areaJSP, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(abrir)
                            .addComponent(analizar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2))
                    .addComponent(lineasJSP))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compilar)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirActionPerformed
        AbrirCodigo();
    }//GEN-LAST:event_abrirActionPerformed

    private void analizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analizarActionPerformed
        lexemas = new ArrayList();
        tablaJT.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"Lexama", "Token", "ID", "Linea"}
        ));
        Analizar();
        compilar.setEnabled(true);
        enumerarLineas(area.getText());
    }//GEN-LAST:event_analizarActionPerformed

    private void compilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compilarActionPerformed
        ArrayList<String> ids = new ArrayList();
        ArrayList<String> lineas =  new ArrayList();
        ids.add("$");
        lineas.add(null);
        for (int j = lexemas.size() - 1; j >= 0; j --) {
            ids.add(lexemas.get(j).getId());
            lineas.add(lexemas.get(j).getLinea());
        }
        mensaje.setText(new Sintaxis(ids, lineas).MensajeSintaxis());
        compilar.setEnabled(false);
    }//GEN-LAST:event_compilarActionPerformed

    private void AbrirCodigo() {
        FileNameExtensionFilter extension = new FileNameExtensionFilter("Abrir Archivo", "txt");
        gestor = new JFileChooser();
        gestor.setFileFilter(extension);
        int n = gestor.showOpenDialog(null);
        if (n == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = gestor.getSelectedFile();
                BufferedReader txt = new BufferedReader(new FileReader(archivo));
                String aux;
                String codigo = "";
                do {
                    aux = txt.readLine();
                    if (aux != null) codigo = codigo + aux + '\n';
                } while (aux != null);
                String encoded = new String(codigo.getBytes("UTF-8"));
                area.setText(encoded);
                enumerarLineas(area.getText());
            } catch (IOException e) {}
            analizar.setEnabled(true);
        }
    }
    
    public static void main(String args[]) {
        Frame ventana = new Frame();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrir;
    private javax.swing.JButton analizar;
    private javax.swing.JTextArea area;
    private javax.swing.JScrollPane areaJSP;
    private javax.swing.JTextArea areaLineas;
    private javax.swing.JButton compilar;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane lineasJSP;
    private javax.swing.JTextArea mensaje;
    private javax.swing.JTable tablaJT;
    // End of variables declaration//GEN-END:variables


    private void SepararCodigo(String codigo) {
        codigo = codigo + "\n";
        String[][] aux = ExtraerCarctaeresDeArchivo();
        boolean arroba = false, comilla = false;
        String lexema = "";
        int linea = 1;
        for (int i = 0; i < codigo.length(); i++) {
            for (int j = 0; j < aux.length; j++) {
                if (codigo.charAt(i) == '\'') {
                    lexema = lexema + codigo.charAt(i);
                    comilla = !comilla;
                    break;
                } else if (comilla == true) {
                    lexema = lexema + codigo.charAt(i);
                    break;
                }
                if (codigo.charAt(i) == '@') {
                    lexema = lexema + codigo.charAt(i);
                    arroba = !arroba;
                    break;
                } else if (arroba == true) {
                    lexema = lexema + codigo.charAt(i);
                    break;
                }
                Lexema lex = new Lexema();
                Lexema lex2 = new Lexema();
                if (aux[j][0].length() == 1) {
                    if (aux[j][0].compareTo(codigo.charAt(i) + "") == 0 || codigo.charAt(i) == ' ' || codigo.charAt(i) == '\n' || codigo.charAt(i) == '\t') {
                        if (lexema.compareTo("") != 0) {
                            lex.setLexema(lexema);
                            lex.setLinea(linea + "");
                            lexema = "";
                            lexemas.add(lex);
                        }
                        if (codigo.charAt(i) == '\n') linea ++;
                        if (codigo.charAt(i) != ' ' & codigo.charAt(i) != '\n' & codigo.charAt(i) != '\t') {
                            lex2.setLexema(codigo.charAt(i) + "");
                            lex2.setId(aux[j][1]);
                            lex2.setToken(aux[j][2]);
                            lex2.setLinea(linea + "");
                            lexemas.add(lex2);
                        }
                        break;
                    } else if (j == aux.length - 1) lexema = lexema + codigo.charAt(i);
                } else if (i != codigo.length() - 1) {//checar si es <= o algo paresido
                    if (aux[j][0].compareTo(codigo.charAt(i) + "" + codigo.charAt(i + 1)) == 0) {
                        if (lexema.compareTo("") != 0) { //para que no se pasen palabras basias
                            lex.setLexema(lexema);
                            lex.setLinea(linea + "");
                            lexema = "";
                            lexemas.add(lex);
                        }
                        lex2.setLexema(aux[j][0]);
                        lex2.setId(aux[j][1]);
                        lex2.setToken(aux[j][2]);
                        lex2.setLinea(linea + "");
                        lexemas.add(lex2);
                        i ++;
                        break;
                    }
                }
            }
        }
    }

    private void Analizar() {
        SepararCodigo(area.getText());
        String[][] aux = ExtraerPalabraReservada();
        for (String[] a: aux) {
            for (Lexema l : lexemas) {
                if (a[0].compareTo(l.getLexema()) == 0) {
                    l.setId(a[1]);
                    l.setToken("Palabra reservada");
                }
            }
        }
        char[][] array = TablaTransicion.getTabla();
        for (Lexema l: lexemas) {
            if (l.getId().compareTo("") == 0) Automata.EsValido(l.getLexema(), array, l);
        }
        //finalmente imprimir resultados
        for (Lexema l: lexemas) {
            ((DefaultTableModel) tablaJT.getModel()).addRow(
                new Object[] {l.getLexema(), l.getToken(), l.getId(), l.getLinea()}
            );
        }
    }

    public static String[][] ExtraerCarctaeresDeArchivo() {
        try {
            int n = 0;
            BufferedReader caracteres = new BufferedReader(new FileReader(new File("src/txts/caracteres.txt")));
            while (caracteres.readLine() != null) n ++;
            String aux[][] = new String[n][3];
            caracteres = new BufferedReader(new FileReader(new File("src/txts/caracteres.txt")));
            for (String[] a: aux) {
                String linea = caracteres.readLine();
                a[0] = linea.substring(25, linea.length());
                a[1] = linea.substring(0, 2);
                a[2] = linea.substring(2, 25);
            }
            return aux;
        } catch (IOException ex) {
            return new String[3][0];
        }
    }

    public static String[][] ExtraerPalabraReservada() {
        try{
            int n = 0;
            BufferedReader palabras = new BufferedReader(new FileReader(new File("src/txts/palabras.txt")));
            while (palabras.readLine() != null) n ++;
            String aux[][] = new String[n][2];
            palabras = new BufferedReader(new FileReader(new File("src/txts/palabras.txt")));
            for (String[] a : aux) {
                String linea = palabras.readLine();
                a[0] = linea.substring(2, linea.length()); //substrae desde el caracter 2
                a[1] = linea.substring(0, 2);
            }
            return aux;
        } catch (IOException ex) {
            return new String[2][0];
        }
    }

    private void enumerarLineas(String codigo) {
        int k = 0;
        areaLineas.setText("");
        for (int i = 0; i < codigo.length(); i++) {
            if (codigo.codePointAt(i) == '\n') areaLineas.setText(areaLineas.getText() + ++k + '\n');
        }
    }
}