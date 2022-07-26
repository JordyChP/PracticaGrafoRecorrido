/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.CiudadDao;


import controlador.tda.graafo.Adyacencia;
import controlador.tda.graafo.GrafoEND;
import controlador.tda.lista.ListaEnlazada;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 *
 * @author Jy
 */
public class AdaptadorDao<T> implements Interfazdao<T> {
    
    private Class<T> clazz;
    private String carpeta = "datos" + File.separatorChar;
    private GrafoEND<T> gend;
    
    public AdaptadorDao(Class<T> clazz) {
        this.clazz = clazz;
        carpeta += this.clazz.getSimpleName() + ".txt";
    }

    public GrafoEND<T> getGrafoND() {
        return gend;
    }

    public void setGrafoND(GrafoEND<T> grafoND) {
        this.gend = grafoND;
    }

    
    @Override
    public boolean guardar(T dato) {
       try {
            listarGrafo();
            anadirvertice();
            gend.EtiquetaVertice(gend.numVertices(),dato);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(carpeta));
            oos.writeObject(gend);
            oos.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error en guardar: " + e);
        }
        return false;
    }

    @Override
    public boolean modificar(Object dato) {
        try {
           ObjectOutputStream oss = new ObjectOutputStream(new FileOutputStream(carpeta));
           gend = listarGrafo();
           oss.writeObject(gend);
           oss.close();
           return true;
        } catch (Exception e) {
            System.out.println("Error en modificar "+e);
        }
        return false;
    }
    
    public boolean actualizarGrafo(GrafoEND<T> grafoAux){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(carpeta));
            oos.writeObject(gend);
            oos.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error en actualizarGrafo: " + e);
        }
        return false; 
    }

    @Override
    public GrafoEND listarGrafo() {
        try {
           ObjectInputStream ois = new ObjectInputStream(new FileInputStream(carpeta));
           gend = (GrafoEND<T>) ois.readObject();
           ois.close();
        } catch (Exception e) {
            System.out.println("Error en listar "+e);
        }
        return gend;
    }

    public T buscarId(Long id, T dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void anadirvertice() {
        if (gend == null) {
            gend = new GrafoEND<>(1);
        } else {
            GrafoEND aux = new GrafoEND(gend.numVertices() + 1);
            for (int i = 1; i <= gend.numVertices(); i++) {
                aux.EtiquetaVertice(i, gend.obtenerEtiqueta(i));
                ListaEnlazada<Adyacencia> lista = gend.adyacentes(i);
                for (int j = 0; j < lista.getSize(); j++) {
                    Adyacencia ad = lista.obtenerDato(j);
                    aux.insertarArista2(i, ad.getDestino(), ad.getPeso());
                }
            }
            gend = aux;
        }
    }
    
}
