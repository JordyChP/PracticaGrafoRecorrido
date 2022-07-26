/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.graafo;

import controlador.tda.lista.ListaEnlazada;
import java.util.HashMap;


/**
 *
 * @author Jy
 */
public class GrafoED <E> extends GrafoD{

    protected E Etiquetas[];
    protected HashMap<E,Integer> dicVertices;
    
    public GrafoED(Integer numV) {
        super(numV);
        this.Etiquetas = (E[]) new Object[numV+1];
        this.dicVertices = new HashMap<>(numV);
    }
    
    public boolean existeAristaE(E i, E j){
        try {
            return this.existeArista(obtenerCodigo(i), obtenerCodigo(j));
        } catch (Exception e) {
            //System.out.println("");
            return false;
        }
    }
    
    public void insertarAristaE(E i,E j, Double Peso){
        try {
            this.insertarArista2(obtenerCodigo(i), obtenerCodigo(j), Peso);
        } catch (Exception e) {
            System.out.println("error");
        }
    }
    
    public void insertarAristaE(E i,E j){
        try {
            this.insertarArista(obtenerCodigo(i), obtenerCodigo(j));
        } catch (Exception e) {
        }
    }
    
    public Integer obtenerCodigo(E Etiqueta){
        try {
            return dicVertices.get(Etiqueta);
        } catch (Exception e) {
            //System.out.println("Ha ocurrido un error en obtener el codigo "+e);
            return -1;
        }
    }
    
    public ListaEnlazada<Adyacencia> adyacentesE(E i){
        return adyacentes(obtenerCodigo(i));
    }
    
    public void EtiquetaVertice(Integer codigo,E etiqueta){
        Etiquetas[codigo] = etiqueta;
        dicVertices.put(etiqueta, codigo);
    }
    
    public E obtenerEtiqueta(Integer Codigo){
        return Etiquetas[Codigo];
    }

    @Override
    public String toString() {
        String grafo = "";
        for (int i = 1; i <= numVertices(); i++) {
            grafo += "Vertice: " +i+ "E ("+ obtenerEtiqueta(i) + ")";
            ListaEnlazada<Adyacencia> lista = adyacentes(i);
            for (int j = 0; j < lista.getSize(); j++) {
                Adyacencia aux = lista.obtenerDato(j);
                if(aux.getPeso().toString().equalsIgnoreCase(String.valueOf(Double.NaN)))
                    grafo += " --Vertice destino " + aux.getDestino() + "E ("+obtenerEtiqueta(aux.getDestino())+" )"+ "-- SP ";
                else 
                    grafo += " --Vertice destino " + aux.getDestino()+ "E ("+obtenerEtiqueta(aux.getDestino())+" )"+ "-- Peso "+ aux.getPeso()+"--";
            }
            grafo += "\n";
        }
        return grafo; 
    }
    
    
}
