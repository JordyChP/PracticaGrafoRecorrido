/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.graafo;

import controlador.tda.lista.ListaEnlazada;
/**
 *
 * @author Jy
 */
public class GrafoD extends Grafo {

    private Integer numV;
    protected Integer numA;
    protected ListaEnlazada<Adyacencia> listaAdyacente[];

    public GrafoD(Integer numV) {
        this.numV = numV;
        numA = 0;
        listaAdyacente = new ListaEnlazada[this.numV + 1];
        for (int i = 1; i <= this.numV; i++) {
            listaAdyacente[i] = new ListaEnlazada<>();
        }
    }
    

    @Override
    public Integer numVertices() {
        return this.numV;
    }

    @Override
    public Integer numAristas() {
        return this.numA;
    }

    @Override
    public Boolean existeArista(Integer i, Integer j) throws Exception {
        Boolean esta = false;
        if (i.intValue() <= numV && j.intValue() <= numV) {
            ListaEnlazada<Adyacencia> lista_adyacentes = listaAdyacente[i];
            for (int k = 0; k < lista_adyacentes.getSize(); k++) {
                Adyacencia aux = lista_adyacentes.obtenerDato(k);
                if (aux.getDestino().intValue() == j.intValue()) {
                    esta = true;
                    break;
                }
            }
        }
        return esta;
    }

    @Override
    public Double pesoArista(Integer i, Integer j) {
        Double peso = Double.NaN;
        try {
            if (existeArista(i, j)) {
                ListaEnlazada<Adyacencia> adyacentes = listaAdyacente[i];
                for (int k = 0; k < adyacentes.getSize(); k++) {
                    Adyacencia aux = adyacentes.obtenerDato(k);
                    if (aux.getDestino().intValue() == j.intValue()) {
                        peso = aux.getPeso();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo encontrar el dato");
        }
        return peso;
    }

    @Override
    public void insertarArista(Integer i, Integer j) {
           insertarArista2(i, j, Double.NaN);
    }

    @Override
    public void insertarArista2(Integer i, Integer j, Double peso) {
        try {
            if (i.intValue() <= numV && j.intValue() <= numV) {
                if (!existeArista(i, j)) {
                    numA++;
                    listaAdyacente[i].insertarCabecera(new Adyacencia(j,peso ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ListaEnlazada<Adyacencia> adyacentes(Integer i) {
        try {
            return listaAdyacente[i];
        } catch (Exception e) {
            return null;
        }
    }
    
   

}
