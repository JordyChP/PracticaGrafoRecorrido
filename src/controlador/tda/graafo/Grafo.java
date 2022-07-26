/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.tda.graafo;

import controlador.tda.cola.ColaServicio;
import controlador.tda.grafo.exception.GrafoConnectionException;
import controlador.tda.lista.ListaEnlazada;
import java.io.Serializable;

/**
 *
 * @author Jy
 */
public abstract class Grafo<T> implements Serializable {

    protected int visitados[];
    protected int ordenVisita;
    protected ColaServicio<Integer> cola;
    protected float MatrizAdyacencias[][];

    public abstract Integer numVertices();

    public abstract Integer numAristas();

    public abstract Boolean existeArista(Integer i, Integer j) throws Exception;

    public abstract Double pesoArista(Integer i, Integer j);

    public abstract void insertarArista(Integer i, Integer j);

    public abstract void insertarArista2(Integer i, Integer j, Double peso);

    public abstract ListaEnlazada<Adyacencia> adyacentes(Integer i);

    @Override
    public String toString() {
        String grafo = "";
        for (int i = 1; i <= numVertices(); i++) {
            grafo += "Vertice: " + i;
            ListaEnlazada<Adyacencia> lista = adyacentes(i);
            for (int j = 0; j < lista.getSize(); j++) {
                Adyacencia aux = lista.obtenerDato(j);
                if (aux.getPeso().toString().equalsIgnoreCase(String.valueOf(Double.NaN))) {
                    grafo += " --Vertice destino " + aux.getDestino() + "-- SP ";
                } else {
                    grafo += " --Vertice destino " + aux.getDestino() + "-- Peso " + aux.getPeso() + "--";
                }
            }
            grafo += "\n";
        }
        return grafo;
    }

    public ListaEnlazada caminoMinimo(Integer Verticei, Integer Verticef) throws GrafoConnectionException {
        ListaEnlazada<Integer> caminos = new ListaEnlazada<>();
        ListaEnlazada<Double> listapesos = new ListaEnlazada<>();
        Integer inicial = Verticei;
        caminos.insertarCabecera(inicial);
        boolean finalizar = false;
        while (!finalizar) {
            ListaEnlazada<Adyacencia> adyacencias = adyacentes(inicial);
            if (adyacencias == null) {
                throw new GrafoConnectionException("No existe Adyacencias");
            }
            Double peso = 100000000.0;
            Integer t = -1;
            for (int i = 0; i < adyacencias.getSize(); i++) {
                Adyacencia ad = adyacencias.obtenerDato(i);
                if (!estaEnCamino(caminos, ad.getDestino())) {
                    Double pesoArista = ad.getPeso();
                    if (Verticef.intValue() == ad.getDestino().intValue()) {
                        t = ad.getDestino();
                        peso = pesoArista;
                        break;
                    } else if (pesoArista < peso) {
                        t = ad.getDestino();
                        peso = pesoArista;
                    }
                }
            }
            listapesos.insertarCabecera(peso);
            caminos.insertarCabecera(t);
            inicial = t;
            if (Verticef.intValue() == inicial.intValue()) {
                finalizar = true;
            }
        }
        return caminos;
    }

    public Boolean estaEnCamino(ListaEnlazada<Integer> lista, Integer vertice) {
        Boolean band = false;
        for (int i = 0; i < lista.getSize(); i++) {
            if (lista.obtenerDato(i).intValue() == vertice.intValue()) {
                band = true;
                break;
            }
        }
        return band;
    }

    public int[] toArrayDFS(int origen) {
        int res[] = new int[numVertices() + 1];
        visitados = new int[numVertices() + 1];
        ordenVisita = 1;
        for (int i = 1; i <= numVertices(); i++) {
            if (visitados[i] == 0) {
                toArrayDFS(origen, res);
            }
        }
        return res;
    }

    protected void toArrayDFS(int origen, int[] res) {
        res[ordenVisita] = origen;
        visitados[origen] = ordenVisita++;
        ListaEnlazada<Adyacencia> lista1 = adyacentes(origen);
        for (int i = 0; i < lista1.getSize(); i++) {
            Adyacencia a = lista1.obtenerDato(i);
            if (visitados[a.getDestino()] == 0) {
                toArrayDFS(a.getDestino(), res);
            }
        }
    }

    public int[] toStringDFS(int origen) {
        for (int i = 2; i < arrayToString(toArrayDFS(origen)).length; i++) {
            if (origen == arrayToString(toArrayDFS(origen))[i]) {
                return null;
            }
        }
        return arrayToString(toArrayDFS(origen));
    }

    protected int[] arrayToString(int[] res) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < res.length; i++) {
            sb.append(res[i]).append("\n");
        }
        return res;
    }

    public int[] toStringBFS(int origen) {
        for (int i = 2; i < arrayToString(toArrayBFS(origen)).length; i++) {
            if (origen == arrayToString(toArrayBFS(origen))[i]) {
                return null;
            }

        }
        return arrayToString(toArrayBFS(origen));
    }

    public int[] toArrayBFS(int origen) {
        int res[] = new int[numVertices() + 1];
        visitados = new int[numVertices() + 1];
        ordenVisita = 1;
        cola = new ColaServicio<Integer>("COLA", numVertices());
        for (int i = 1; i <= numVertices(); i++) {
            if (visitados[i] == 0) {
                toArrayBFS(origen, res);
            }
        }
        return res;
    }

    protected void toArrayBFS(int origen, int res[]) {
        res[ordenVisita] = origen;
        visitados[origen] = ordenVisita++;
        cola.queue(origen);
        while (!cola.estaVacia()) {
            int u = cola.dequeue();
            ListaEnlazada<Adyacencia> lista = adyacentes(u);
            for (int i = 0; i < lista.getSize(); i++) {
                Adyacencia a = lista.obtenerDato(i);
                if (visitados[a.getDestino()] == 0) {
                    res[ordenVisita] = a.getDestino();
                    visitados[a.getDestino()] = ordenVisita++;
                    cola.queue(a.getDestino());
                }
            }
        }
    }

    public float[][] generarMatrizAd() throws Exception {
        MatrizAdyacencias = new float[numVertices()][numVertices()];

        for (int i = 0; i < numVertices(); i++) {
            ListaEnlazada<Adyacencia> ad = adyacentes((i + 1));
            for (int j = 0; j < ad.getSize(); j++) {
                Adyacencia aux = ad.obtenerDato(j);
                if (aux.getPeso() != null) {
                    MatrizAdyacencias[i][aux.getDestino() - 1] = ad.obtenerDato(j).getPeso().floatValue();
                }
                if (ad.obtenerDato(j).getPeso() == null) {
                    MatrizAdyacencias[i][j] = -1;
                }
                if (i == j) {
                    MatrizAdyacencias[i][j] = 0;
                }
            }
        }
        return MatrizAdyacencias;
    }

    public String algoritmoFloyd(float[][] adyacencia, int x, int y) {
        float[][] matrizAd = adyacencia;
        String caminos[][] = new String[numVertices()][numVertices()];
        String[][] caminosAux = new String[numVertices()][numVertices()];
        String caminoRecorrido = "", caminosaux = "";
        int i, j, k;
        float ij, ik, kj, sum, minimo;

        for (i = 0; i < numVertices(); i++) {
            for (j = 0; j < numVertices(); j++) {
                caminos[i][j] = "";
                caminosAux[i][j] = "";
            }
        }

        for (k = 0; k < numVertices(); k++) {
            for (i = 0; i < numVertices(); i++) {
                for (j = 0; j < numVertices(); j++) {
                    ij = matrizAd[i][j];
                    ik = matrizAd[i][k];
                    kj = matrizAd[k][j];
                    sum = ik + kj;
                    minimo = Math.min(ij, sum);
                    if (ij != sum) {
                        if (minimo == sum) {
                            caminoRecorrido = "";
                            caminosAux[i][j] = k + "";
                            caminos[i][j] = caminosR(i, k, caminosAux, caminoRecorrido) + (k + 1);

                        }
                    }
                    matrizAd[i][j] = minimo;
                }
            }
        }

        for (i = 0; i < x; i++) {
            for (j = 0; j < y; j++) {
                if (matrizAd[i][j] != 1000000000) {
                    if (i != j) {
                        if (caminos[i][j].equals("")) {
                            caminosaux = "(" + (i + 1) + "," + (j + 1) + ")\n";
                        } else {
                            caminosaux = "(" + (i + 1) + "," + caminos[i][j] + "," + (j + 1) + ")\n";
                        }
                    }
                }
            }
        }
        return caminosaux;
    }

    public String caminosR(int i, int k, String[][] caminosAuxiliares, String caminoRecorrido) {
        if (caminosAuxiliares[i][k].equals("")) {
            return "";
        } else {
            caminoRecorrido += caminosR(i, Integer.parseInt(caminosAuxiliares[i][k].toString()), caminosAuxiliares, caminoRecorrido) + (Integer.parseInt(caminosAuxiliares[i][k].toString()) + 1) + ",";
            return caminoRecorrido;
        }
    }
}
