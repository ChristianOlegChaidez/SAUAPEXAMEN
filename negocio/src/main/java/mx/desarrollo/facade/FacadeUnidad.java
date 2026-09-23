package mx.desarrollo.facade;

import mx.desarrollo.delegate.DelegateUnidad;
import mx.desarrollo.entity.Unidadaprendizaje;
import mx.desarrollo.persistence.integration.ServiceLocator;

import java.util.List;
import java.util.Optional;

public class FacadeUnidad {

    private final DelegateUnidad delegateUnidad;

    public FacadeUnidad() {
        this.delegateUnidad = new DelegateUnidad();
    }

    public void guardarUnidad(Unidadaprendizaje unidad) {
        delegateUnidad.saveUnidad(unidad);
    }

    public void registrarUnidad(String nombre, String hrClase, String hrTaller, String hrLaboratorio) {
        delegateUnidad.registrarUnidad(nombre, hrClase, hrTaller, hrLaboratorio);
    }

    public List<Unidadaprendizaje> obtenerTodos() {
        return delegateUnidad.obtenerTodos();
    }

    public List<Unidadaprendizaje> consultarOrdenadasPorNombre() {
        return delegateUnidad.consultarOrdenadasPorNombre();
    }

    public List<Unidadaprendizaje> eliminarUnidad(Integer idUnidad) {
        return delegateUnidad.eliminarUnidad(idUnidad);
    }

    public List<Unidadaprendizaje> buscarPorNombre(String nombre){
        return delegateUnidad.buscarporNombre(nombre);
    }
    public Unidadaprendizaje obtenerPorId(Integer id){
        Optional<Unidadaprendizaje> encontrada = ServiceLocator.getInstanceUnidadDAO().find(id);
        return encontrada.orElse(null);
    }
    public void modificarUnidad(Unidadaprendizaje unidad){
        ServiceLocator.getInstanceUnidadDAO().update(unidad);
    }
}