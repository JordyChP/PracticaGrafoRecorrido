/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.modeloTabla;

import controlador.tda.graafo.GrafoEND;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Pc
 */
public class ModeloTablaCiudad extends AbstractTableModel {
     private GrafoEND Grafoend;
    private String[] columnas;

    public GrafoEND getGrafoend() {
        return Grafoend;
    }

    public void setGrafoend(GrafoEND Grafoend) {
        this.Grafoend = Grafoend;
        generarColumnas();
    }

    
    @Override
    public int getRowCount() {
       return Grafoend.numVertices();
    }

    @Override
    public int getColumnCount() {
        return Grafoend.numVertices()+1;
    }
    
    private String[] generarColumnas(){
        columnas = new String[Grafoend.numVertices()+1];
        columnas[0] = "-";
        for (int i = 1; i < columnas.length; i++) {
            columnas[i] = Grafoend.obtenerEtiqueta(i).toString();
        }
        return columnas;
    }

    @Override
    public Object getValueAt(int i, int i1) {
       if(i1==0){
            return columnas[i+1];
        }else{
            try {
                if(Grafoend.existeArista((i+1), i1)){
                    return Grafoend.pesoArista((i+1), i1)+" minutos";
                }else{
                    return "----";
                }
            } catch (Exception e) {
                System.out.println("Error en valores de la tabla");
            }
        }
        return "";
    }
    
    @Override
    public String getColumnName(int column) {
         return columnas[column];
    }
}
