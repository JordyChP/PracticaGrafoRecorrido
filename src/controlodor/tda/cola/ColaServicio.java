package controlodor.tda.cola;

import controlador.tda.lista.ListaEnlazada;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Usuario
 */
public class ColaServicio<T> extends ListaEnlazada<T>{

    //private Nodo ce =  new Nodo();
    private int cima = 0;
    private int ta;
    private String tipo;

    public ColaServicio(String tipo, int tamanio) {
        this.tipo = tipo;
        this.ta = tamanio;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        
    }

    public boolean push(T dato) {
        if (tipo.equals("PILA")) {
            if (!estaLlena()) {
                this.insertar(dato, 0);
                //cima++;
                return true;
            }else{
                System.out.println("La pila esta llena");
            }

        } else {
            System.out.println("no es una Pila");
        }
        return false;

    }

    public T pop() {
        T tmp = null;
        if (tipo.equals("PILA")) {
            if (this.estaVacia()) {
                System.out.println("No hay elementos");
            } else {
                tmp = (T) this.extraer();
            }

        }else{
            System.out.println("No es una Pila");
        }

        return tmp;
    }

    public T top(){
        if(tipo.equals("PILA")){
        return (T) this.getCabecera().getDato();
        }
        else{
            System.out.println("No es una Pila");
        }
        return null;
    }
    public boolean estaLlena() {
        return ta == this.getSize() - 1;
    }

    public boolean queue(T dato) {
        if (tipo.equals("COLA")) {
            if (!estaLlena()) {
                this.insertarCabecera(dato);
                //cima++;
                return true;
            }
            else{
                System.out.println("La cola esta llena, no se puede ");
            }

        } else {
            System.out.println("no es una COLA");
        }
        return false;

    }

    public T dequeue(){
        T tmp = null;
        if (tipo.equals("COLA")) {
            if (this.estaVacia()) {
                System.out.println("No hay elementos");
            } else {
                tmp = (T) this.extraer();
            }

        }else{
            System.out.println("No es una Cola");
        }

        return tmp;
    }

    public int getTa() {
        return ta;
    }

    public void setTa(int ta) {
        this.ta = ta;
    }

//    public void inicio() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public boolean esFin() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public void siguiente() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    
}

