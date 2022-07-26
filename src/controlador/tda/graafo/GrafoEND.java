/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.graafo;

import java.io.Serializable;

/**
 *
 * @author Usuario
 */
public class GrafoEND <E> extends GrafoED<E> implements Serializable{
    
    public GrafoEND(Integer numV) {
        super(numV);
    }

    @Override
    public void insertarArista2(Integer i, Integer j, Double peso) {
        try {
            if (i.intValue() <= numVertices() && j.intValue() <= numVertices()) {
                if (!existeArista(i, j)) {
                    numA++;
                    listaAdyacente[i].insertarCabecera(new Adyacencia(j,peso ));
                    listaAdyacente[j].insertarCabecera(new Adyacencia(i,peso ));
                }
            }
        } catch (Exception e) {
            System.out.println("Error en insertar grafo no dirijido");
        }
    }

    @Override
    public void insertarAristaE(E i, E j, Double Peso) {
        insertarArista2(obtenerCodigo(i), obtenerCodigo(j), Peso);
        insertarArista2(obtenerCodigo(j), obtenerCodigo(i), Peso);
    }
    
    
}
