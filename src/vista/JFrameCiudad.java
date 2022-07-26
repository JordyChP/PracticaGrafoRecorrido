/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ciudadController.CiudadController;
import controlador.tda.grafo.exception.GrafoConnectionException;
import controlador.tda.lista.ListaEnlazada;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.modeloTabla.ModeloTablaCiudad;

/**
 *
 * @author Jy
 */
public class JFrameCiudad extends javax.swing.JFrame {

    private CiudadController cc = new CiudadController();
    private ModeloTablaCiudad mtc = new ModeloTablaCiudad();

    public JFrameCiudad() throws Exception {
        initComponents();
        setLocationRelativeTo(null);
        limpiar();
    }

    private void limpiar() throws Exception {
        cc.setCiudad(null);
        cargarTabla();
        cargarCombos();
    }

    private void cargarTabla() throws Exception {
        if (cc.getGrafo() != null) {
            mtc.setGrafoend(cc.getGrafo());
            jtable.setModel(mtc);
            mtc.fireTableStructureChanged();
            jtable.updateUI();
        } else {
            DefaultTableModel modelo = new DefaultTableModel(2, 1);
            jtable.setModel(modelo);
            jtable.updateUI();
        }

    }

    private void guardar() {

        try {
 
            cc.getCiudad().setCiudad(txtCiudad.getText());
            cc.getCiudad().setCanton(txtCanton.getText());

            if (cc.guardar()) {
                JOptionPane.showMessageDialog(null, "Se ha guardado los vertices", "OK", JOptionPane.INFORMATION_MESSAGE);
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "No se ha Guardado error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cargarCombos() throws Exception {
        cbxOrigen.removeAllItems();
        cbxDestino.removeAllItems();
        if (cc.getGrafo() != null) {
            for (int i = 0; i < cc.getGrafo().numVertices(); i++) {
                String etiqueta = cc.getGrafo().obtenerEtiqueta((i + 1)).toString();
                cbxOrigen.addItem(etiqueta);
                cbxDestino.addItem(etiqueta);
            }
        }
    }

   private void agregarAdyacencia() throws Exception {
        if (cc.getGrafo() != null) {
            if (cbxDestino.getSelectedIndex() == cbxOrigen.getSelectedIndex()) {
                JOptionPane.showMessageDialog(null, "No hay insertar arista con el mismo valor", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (!txtDistancia.getText().isEmpty()) {
                    Integer p1 = cbxOrigen.getSelectedIndex();
                    Integer p2 = cbxDestino.getSelectedIndex();
                    Double ti = (Double.parseDouble(txtDistancia.getText()) / 50);
                    Double tiempo = ti * 60;
                    cc.getGrafo().insertarArista2(p1 + 1, p2 + 1, tiempo);
                    if (cc.actualizarGrafo(cc.getGrafoObjeto())) {
                        JOptionPane.showMessageDialog(null, "Grafo Actualizado ", "OK", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Faltan Datos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Llene el campo vacio ", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Faltan Datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void BusquedaAnchura() {
        Integer nodo = Integer.parseInt(JOptionPane.showInputDialog("Desde que numero nodo quiere comenzar"));
        if (nodo > cc.getGrafoObjeto().numVertices() || nodo <= 0) {
            JOptionPane.showMessageDialog(null, "El nodo no existe");
        } else {
            String aux = null;
            for (int i = 4; i < Arrays.toString(cc.getGrafoObjeto().toStringBFS(nodo)).length(); i += 3) {
                String h = Arrays.toString(cc.getGrafoObjeto().toStringBFS(nodo));
                aux = aux + ">>" + cc.getGrafoObjeto().obtenerEtiqueta(Character.getNumericValue(h.charAt(i))).getCiudad();
            }
            if (cc.getGrafoObjeto().toStringBFS(nodo) == null) {
                JOptionPane.showMessageDialog(null, "No hay las suficientes adyacencias ");
            } else {
                JOptionPane.showMessageDialog(null, "Busqueda en Anchura: " + aux.substring(6));
            }
        }
    }

    private void BusquedaLongitud() {
        Integer nodo = Integer.parseInt(JOptionPane.showInputDialog("Desde que numero nodo quiere comenzar"));
        if (nodo > cc.getGrafoObjeto().numVertices() || nodo <= 0) {
            JOptionPane.showMessageDialog(null, "El nodo no existe");
        } else {
            String aux = null;
            for (int i = 4; i < Arrays.toString(cc.getGrafoObjeto().toStringDFS(nodo)).length(); i += 3) {
                String h = Arrays.toString(cc.getGrafoObjeto().toStringDFS(nodo));
                aux = aux + ">>" + cc.getGrafoObjeto().obtenerEtiqueta(Character.getNumericValue(h.charAt(i))).getCiudad();
            }
            if (cc.getGrafoObjeto().toStringDFS(nodo) == null) {
                JOptionPane.showMessageDialog(null, "No hay las suficientes adyacencias ");
            } else {
                JOptionPane.showMessageDialog(null, "Busqueda en Longitud: " + aux.substring(6));
            }
        }
    }

    private void generarCaminos() throws GrafoConnectionException {
        if (cbxDestino.getSelectedIndex() == cbxOrigen.getSelectedIndex()) {
            JOptionPane.showMessageDialog(null, "No hay insertar arista con el mismo valor", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Integer p1 = cbxOrigen.getSelectedIndex() + 1;
            Integer p2 = cbxDestino.getSelectedIndex() + 1;
            ListaEnlazada caminos = cc.getGrafoObjeto().caminoMinimo(p1, p2);
            String h = null;
            for (int i = 0; i < caminos.getSize(); i++) {
                h = h + ">>" + cc.getGrafoObjeto().obtenerEtiqueta(Integer.parseInt(caminos.obtenerDato(i).toString())).getCiudad();
            }
            JOptionPane.showMessageDialog(null, "Para ir de la Ciudad: " + cc.getGrafoObjeto().obtenerEtiqueta(p1).getCiudad()+ " a la Ciudad: " + cc.getGrafoObjeto().obtenerEtiqueta(p2).getCiudad()+ " hay que ir por: \n" + h.substring(6));
        }
    }

    private void generarFloyd() {
        if (cbxDestino.getSelectedIndex() == cbxOrigen.getSelectedIndex()) {
            JOptionPane.showMessageDialog(null, "No hay insertar arista con el mismo valor", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Integer p1 = cbxOrigen.getSelectedIndex() + 1;
            Integer p2 = cbxDestino.getSelectedIndex() + 1;
            try {
                String aux = null;
                float auxMatriz[][] = cc.getGrafo().generarMatrizAd();
                auxMatriz = validarMatriz(auxMatriz);
                String h = cc.getGrafo().algoritmoFloyd(auxMatriz, p1, p2);
                for (int i = 1; i < h.length()-1; i+=2) {
                   aux = aux+">>"+cc.getGrafoObjeto().obtenerEtiqueta(Character.getNumericValue(h.charAt(i))).getCiudad();
                }
                JOptionPane.showMessageDialog(null,"El mejor camino con el menor peso para ir desde la Ciudad: "+cc.getGrafoObjeto().obtenerEtiqueta(p1).getCiudad()+" a la Ciudad: "+cc.getGrafoObjeto().obtenerEtiqueta(p2).getCiudad()+"\n es desde "+
                aux.substring(6));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    

    public float[][] validarMatriz(float ma[][]) {
        try {
            for (int i = 0; i < cc.getGrafo().numVertices(); i++) {
                for (int j = 0; j < cc.getGrafo().numVertices(); j++) {
                    if (cc.getGrafo().generarMatrizAd()[i][j] == 0) {
                        ma[i][j] = 999999999;
                    }
                }
            }
        } catch (Exception e) {
        }
        return ma;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCanton = new javax.swing.JTextField();
        txtCiudad = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtPersona = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxDestino = new javax.swing.JComboBox<>();
        cbxOrigen = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtDistancia = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Canton");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 130, -1));

        jLabel2.setText("Ciudad");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, -1));
        jPanel2.add(txtCanton, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 180, -1));
        jPanel2.add(txtCiudad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 160, -1));

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 550, -1));

        jLabel4.setText("Persona");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, 90, -1));
        jPanel2.add(txtPersona, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 170, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 570, 100));

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtable);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 550, 250));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 570, 270));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Destino");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 110, -1));

        jLabel6.setText("Origen ");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 110, -1));

        cbxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(cbxDestino, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 170, -1));

        cbxOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel5.add(cbxOrigen, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 170, -1));

        jButton3.setText("Agregar adyacencia");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 170, -1));

        jLabel3.setText("Distancia");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 90, -1));
        jPanel5.add(txtDistancia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 100, -1));

        jTextField3.setEditable(false);
        jTextField3.setText("Km");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 60, -1));

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 190));

        jButton2.setText("Generar camino");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 200, 30));

        jButton5.setText("Busqueda de anchura");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 200, 30));

        jButton6.setText("Busqueda de longitud");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 200, 30));

        jButton7.setText("Floyd");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 200, 30));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 230, 380));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            agregarAdyacencia();        // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(JFrameCiudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        guardar();        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
BusquedaAnchura();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
BusquedaLongitud();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
generarFloyd();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            generarCaminos();        // TODO add your handling code here:
        } catch (GrafoConnectionException ex) {
            Logger.getLogger(JFrameCiudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameCiudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFrameCiudad().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(JFrameCiudad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JComboBox<String> cbxDestino;
    private javax.swing.JComboBox<String> cbxOrigen;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTable jtable;
    private javax.swing.JTextField txtCanton;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtDistancia;
    private javax.swing.JTextField txtPersona;
    // End of variables declaration//GEN-END:variables
}
