import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

import Modelo.Modelo;

public class Frame extends javax.swing.JFrame {
    
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
        areaJSP.getVerticalScrollBar().addAdjustmentListener(
                new AdjustmentListener() {
                        @Override
                        public void adjustmentValueChanged(AdjustmentEvent e) {
                            lineasJSP.getVerticalScrollBar().setValue(e.getValue());
                        }
                }
        );
        lineasJSP.getVerticalScrollBar().setEnabled(false);
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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lineasJSP, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(areaJSP, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(compilar)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(compilar)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void abrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirActionPerformed
        if (Modelo.AbrirCodigo(area)) {
            analizar.setEnabled(true);
            enumerarLineas(area.getText());
        }
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
        String errores = new Sintaxis(lexemas).MensajeSintaxis();
        Semantico sem = new Semantico(lexemas);
        errores += sem.MensajeSemantico();
        Compatibilidad com = new Compatibilidad();
        com.setLineasError(sem.getLineasdeError());
        errores += com.MensajeCompativilidad(lexemas);
        mensaje.setText(errores);
        compilar.setEnabled(false);
        Etiketa.nueva = 10;
    }//GEN-LAST:event_compilarActionPerformed

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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane lineasJSP;
    private javax.swing.JTextArea mensaje;
    private javax.swing.JTable tablaJT;
    // End of variables declaration//GEN-END:variables


    private void SepararCodigo(String codigo) {
        codigo = codigo + "\n";
        String[][] aux = Modelo.ExtraerCarctaeresDeArchivo();
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
        String[][] aux = Modelo.ExtraerPalabraReservada();
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
    
    private void enumerarLineas(String codigo) {
        int k = 0;
        areaLineas.setText("");
        for (int i = 0; i < codigo.length(); i++) {
            if (codigo.codePointAt(i) == '\n') areaLineas.setText(areaLineas.getText() + ++k + '\n');
        }
    }
}