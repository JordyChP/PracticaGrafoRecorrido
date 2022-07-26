/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.CiudadDao;

import controlador.tda.graafo.GrafoEND;

/**
 *
 * @author Jy
 */
public interface Interfazdao <T> {
    public boolean guardar(T dato);
    public boolean modificar(T dato);
    public GrafoEND<T> listarGrafo();
    public T buscarId(Long id,T dato);
}
