/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.graafo;

/**
 *
 * @author Usuario
 */
public class GrafoND extends GrafoD{

    public GrafoND(Integer numV) {
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

    
}
