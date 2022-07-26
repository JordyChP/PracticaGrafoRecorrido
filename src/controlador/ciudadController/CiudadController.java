/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.ciudadController;

import Modelo.Ciudad;
import controlador.CiudadDao.CiudadDao;
import controlador.tda.graafo.GrafoEND;

/**
 *
 * @author Pc
 */
public class CiudadController {

   CiudadDao ciudadDao = new CiudadDao();

    public Ciudad getCiudad() {
        return ciudadDao.getCiudad();
    }

    public void setCiudad(Ciudad ciudad) {
        ciudadDao.setCiudad(ciudad);
    }
    
    public boolean guardar(){
        return ciudadDao.guardar();
    }
    
    public void imprimirGrafoPersona(){
        System.out.println(ciudadDao.getGrafoND().toString());
    }
    
    
     public GrafoEND<Ciudad> getGrafo(){
        return ciudadDao.listarGrafo();
    }
    
    public GrafoEND<Ciudad> getGrafoObjeto(){
        return ciudadDao.getGrafoND();
    }
    
    public boolean actualizarGrafo(GrafoEND<Ciudad> grafoAux){
        return ciudadDao.actualizarGrafo(grafoAux);
    }

}
