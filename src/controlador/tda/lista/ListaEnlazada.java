/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.lista;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 *
 * @author Jy
 */
public class ListaEnlazada<T> implements Serializable {

    private NodoLista cabecera;
    private Class clazz;
    public static final Integer ASCENDENTE = 1;
    public static final Integer DESENDENTE = 2;
    
    public NodoLista getCabecera() {
        return cabecera;
    }

    public void setCabecera(NodoLista cabecera) {
        this.cabecera = cabecera;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public boolean estaVacia() {
        return this.cabecera == null;
    }


    private void insertar(T dato) {
        NodoLista nodo = new NodoLista(dato, cabecera);
        cabecera = nodo;
    }

    private void insertarFinal(T dato) {
        insertar(dato, getSize());//lista 4  pos = 4 - 1 --> 3
    }

    public void insertar(T dato, int pos) {
        if (estaVacia() || pos <= 0) {
            insertar(dato);
        } else {
            NodoLista iterador = cabecera;
            for (int i = 0; i < pos - 1; i++) {
                if (iterador.getNodoSiguiente() == null) {
                    break;
                }
                iterador = iterador.getNodoSiguiente();
            }
            NodoLista tmp = new NodoLista(dato, iterador.getNodoSiguiente());
            iterador.setNodoSiguiente(tmp);
        }
    }

    public void insertarCabecera(T dato) {
        if (getSize() > 0) {
            insertarFinal(dato);
        } else {
            insertar(dato);
        }
    }

    public int getSize() {
        int cont = 0;
        NodoLista tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            cont++;
            tmp = tmp.getNodoSiguiente();
        }
        return cont;
    }


    public T extraer() {
        T dato = null;
        if (!estaVacia()) {
            dato = (T) cabecera.getDato();
            cabecera = cabecera.getNodoSiguiente();
        }
        return dato;
    }

    public T obtenerDato(int pos) {
        T dato = null;
        if (!estaVacia() && (pos >= 0 && pos <= getSize() - 1)) {
            NodoLista tmp = cabecera;
            for (int i = 0; i < pos; i++) {
                tmp = tmp.getNodoSiguiente();
                if (tmp == null) {
                    break;
                }
            }
            if (tmp != null) {
                dato = (T) tmp.getDato();
            }
        }
        return dato;
    }

    public void imprimir() {
        NodoLista tmp = cabecera;
        while (!estaVacia() && tmp != null) {
            System.out.println(tmp.getDato());
            tmp = tmp.getNodoSiguiente();
        }
    }

    public boolean modificarPorPos(T dato, int pos) {
        if (!estaVacia() && (pos <= getSize() - 1) && pos >= 0) {
            NodoLista iterador = cabecera;
            for (int i = 0; i < pos; i++) {
                iterador = iterador.getNodoSiguiente();
                if (iterador == null) {
                    break;
                }
            }
            if (iterador != null) {
                iterador.setDato(dato);
                return true;
            }
        }
        return false;

    }

    private Field getField(String nombre) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().equalsIgnoreCase(nombre)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }

    public Object Value(T dato, String atributo) throws Exception {
        return getField(atributo).get(dato);
    }

    public ListaEnlazada<T> seleccionClase(String atributo, Integer seleccion) {
        try {
            int i, j, k = 0;
            T t = null;
            int n = getSize();
            for (i = 0; i < n - 1; i++) {
                k = i;
                t = obtenerDato(i);
                for (j = i + 1; j < n; j++) {
                    boolean band = false;
                    Object datot = Value(t, atributo);
                    Object datoj = Value(obtenerDato(j), atributo);
                    if (datot instanceof Number) {
                        Number aux = (Number) datot;
                        Number numero = (Number) datoj;
                        band = (seleccion.intValue() == ListaEnlazada.ASCENDENTE.intValue())
                                ? numero.doubleValue() < aux.doubleValue()
                                : numero.doubleValue() > aux.doubleValue();
                    } else {
                        /*if (datot.toString().compareTo(datoj.toString()) > 0) {
                        t = a.consultarDatoPosicion(j);
                        k = j;
                    }*/
                        band = (seleccion.intValue() == ListaEnlazada.ASCENDENTE.intValue())
                                ? datot.toString().compareTo(datoj.toString()) > 0
                                : datot.toString().compareTo(datoj.toString()) < 0;
                    }
                    if (band) {
                        t = obtenerDato(j);
                        k = j;
                    }
                }
                modificarPorPos(obtenerDato(i), k);
                modificarPorPos(t, i);
            }
        } catch (Exception e) {
            System.out.println("Error ordenacion" + e);
        }
        return this;
    }

    public ListaEnlazada<T> QuisortClase(String atributo, int primero, int ultimo, Integer direccion) {
        try {
            int i, j, central;
            Object pivote;
            central = (primero + ultimo) / 2;
            pivote = obtenerDato(central);
            i = primero;
            j = ultimo;
            if (pivote instanceof Number) {
                do {
                    if (direccion.intValue() == ListaEnlazada.ASCENDENTE) {
                        while (((Number) Value(obtenerDato(i), atributo)).doubleValue() < ((Number) Value(obtenerDato(central), atributo)).doubleValue()) {
                            i++;
                        }
                        while (((Number) Value(obtenerDato(j), atributo)).doubleValue() > ((Number) Value(obtenerDato(central), atributo)).doubleValue()) {
                            j--;
                        }
                    } else {
                        while (((Number) Value(obtenerDato(i), atributo)).doubleValue() > ((Number) Value(obtenerDato(central), atributo)).doubleValue()) {
                            i++;
                        }
                        while (((Number) Value(obtenerDato(j), atributo)).doubleValue() < ((Number) Value(obtenerDato(central), atributo)).doubleValue()) {
                            j--;
                        }
                    }

                    if (i <= j) {
                        T auxiliar = obtenerDato(i);
                        modificarPorPos(obtenerDato(j), i);
                        modificarPorPos(auxiliar, j);
                        i++;
                        j--;
                    }
                } while (i <= j);

            } else {
                do {
                    if (direccion.intValue() == ListaEnlazada.ASCENDENTE) {
                        while (Value(obtenerDato(central), atributo).toString().compareTo(Value(obtenerDato(i), atributo).toString()) > 0) {
                            i++;
                        }
                        while (Value(obtenerDato(j), atributo).toString().compareTo(Value(obtenerDato(central), atributo).toString()) > 0) {
                            j--;
                        }
                    } else {
                        while (Value(obtenerDato(central), atributo).toString().compareTo(Value(obtenerDato(i), atributo).toString()) < 0) {
                            i++;
                        }
                        while (Value(obtenerDato(j), atributo).toString().compareTo(Value(obtenerDato(central), atributo).toString()) < 0) {
                            j--;
                        }
                    }
                    if (i <= j) {
                        T auxiliar = obtenerDato(i);
                        modificarPorPos(obtenerDato(j), i);
                        modificarPorPos(auxiliar, j);
                        i++;
                        j--;
                    }
                } while (i <= j);

            }
            if (primero < j) {
                QuisortClase(atributo, primero, j, direccion);
            }
            if (i < ultimo) {
                QuisortClase(atributo, i, ultimo, direccion);
            }
        } catch (Exception e) {
            System.out.println("Error quiscksort" + e);
        }
        return this;
    }

    public ListaEnlazada<T> ShellClase(Integer direccion, String atributo) {
        try {
            int intervalo, i, j, k;
            int n = getSize();
            intervalo = n / 2;
            while (intervalo > 0) {
                for (i = intervalo; i < n; i++) {
                    j = i - intervalo;
                    if (obtenerDato(intervalo) instanceof Number) {
                        while (j >= 0) {
                            k = j + intervalo;
                            if (direccion.intValue() == ListaEnlazada.ASCENDENTE.intValue()) {
                                if (((Number) Value(obtenerDato(j), atributo)).intValue() <= ((Number) Value(obtenerDato(k), atributo)).intValue()) {
                                    j = -1;
                                } else {
                                    T aux = obtenerDato(j);
                                    modificarPorPos(obtenerDato(j + 1), j);
                                    modificarPorPos(aux, j + 1);
                                    j -= intervalo;
                                }
                            } else {
                                if (((Number) Value(obtenerDato(j), atributo)).intValue() >= ((Number) Value(obtenerDato(k), atributo)).intValue()) {
                                    j = -1;
                                } else {
                                    T aux = obtenerDato(j);
                                    modificarPorPos(obtenerDato(j + 1), j);
                                    modificarPorPos(aux, j + 1);
                                    j -= intervalo;
                                }
                            }
                        }
                    } else {
                        while (j >= 0) {
                            k = j + intervalo;
                            if (direccion.intValue() == ListaEnlazada.ASCENDENTE.intValue()) {
                                if (Value(obtenerDato(k), atributo).toString().compareTo(Value(obtenerDato(j), atributo).toString()) >= 0) {
                                    j = -1;
                                } else {
                                    T aux = obtenerDato(j);
                                    modificarPorPos(obtenerDato(j + 1), j);
                                    modificarPorPos(aux, j + 1);
                                    j -= intervalo;
                                }
                            } else {
                                if (Value(obtenerDato(j), atributo).toString().compareTo(Value(obtenerDato(k), atributo).toString()) >= 0) {
                                    j = -1;
                                } else {
                                    T aux = obtenerDato(j);
                                    modificarPorPos(obtenerDato(j + 1), j);
                                    modificarPorPos(aux, j + 1);
                                    j -= intervalo;
                                }
                            }
                        }
                    }
                }
                intervalo = intervalo / 2;
            }
        } catch (Exception e) {
            System.out.println("Error en Shell: "+e);
        }
        return this;
    }
    
    public ListaEnlazada<T> BusquedaBinariaClase(Object elemento, String atributo) {
        try {
            ListaEnlazada auxiliar = new ListaEnlazada();
            int centro, primero, ultimo;
            primero = 0;
            ultimo = getSize() - 1;
            while (primero <= ultimo) {
                if (obtenerDato(0) instanceof Number) {
                    centro = (primero + ultimo) / 2;
                    if ( ((Number) Value((T) elemento, atributo)).intValue() == ((Number) Value(obtenerDato(centro), atributo)).intValue()) {
                        T po = obtenerDato(centro);
                        auxiliar.insertarCabecera(po);
                        return auxiliar;
                    } else if (((Number) Value((T) elemento, atributo)).intValue() < ((Number) Value(obtenerDato(centro), atributo)).intValue()){
                        ultimo = centro - 1;
                    } else {
                        primero = centro + 1;
                    }
                } else {
                    centro = (primero + ultimo) / 2;
                    if (Value(obtenerDato(centro), atributo).toString().toLowerCase().contains(elemento.toString().toLowerCase())) {
                        auxiliar.insertarCabecera(obtenerDato(centro));
                        return auxiliar;
                    } else if (Value(obtenerDato(centro), atributo).toString().toLowerCase().compareTo(elemento.toString().toLowerCase()) > 0) {
                        ultimo = centro - 1;
                    } else {
                        primero = centro + 1;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Hay un error en busqueda: "+e);
        }
        return null;
    }
}
