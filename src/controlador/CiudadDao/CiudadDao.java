/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.CiudadDao;

import Modelo.Ciudad;
/**
 *
 * @author Jy
 */
public class CiudadDao extends AdaptadorDao<Ciudad> { 
    
    private Ciudad ciudad;

    public Ciudad getCiudad() {
        if(ciudad == null)
            ciudad = new Ciudad();
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
    
    public CiudadDao() {
        super(Ciudad.class);
        listarGrafo();
    }
    
    public Boolean guardar(){
        Integer aux = (getGrafoND() != null) ? getGrafoND().numVertices() + 1 : 1;
        ciudad.setId(new Integer(String.valueOf(aux)));
        return guardar(ciudad);
    }
    
    public Boolean modificar(){
        return modificar(ciudad);
    }
}
