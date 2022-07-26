/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador.tda.lista;

import java.io.Serializable;

/**
 *
 * @author Jy
 */

public class NodoLista <T> implements Serializable {
    private T dato;
    private NodoLista nodoSiguiente;

    public NodoLista(T dato, NodoLista nodoSiguiente) {
        this.dato = dato;
        this.nodoSiguiente = nodoSiguiente;
    }
    public NodoLista() {
        this.dato = null;
        this.nodoSiguiente = null;
    }
    public T getDato() {
        return dato;
    }
    public void setDato(T dato) {
        this.dato = dato;
    }
    public NodoLista getNodoSiguiente() {
        return nodoSiguiente;
    }
    public void setNodoSiguiente(NodoLista nodoSiguiente) {
        this.nodoSiguiente = nodoSiguiente;
    }
    
}
