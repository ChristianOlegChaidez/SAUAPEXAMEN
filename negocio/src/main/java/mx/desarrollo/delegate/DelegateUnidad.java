package mx.desarrollo.delegate;

import mx.desarrollo.entity.Unidadaprendizaje;
import mx.desarrollo.persistence.integration.ServiceLocator;

import java.util.List;
import java.util.Optional;

public class DelegateUnidad {
    public void saveUnidad(Unidadaprendizaje unidad) {
        ServiceLocator.getInstanceUnidadDAO().save(unidad);
    }

    public List<Unidadaprendizaje> obtenerTodos() {
        return ServiceLocator.getInstanceUnidadDAO().findAll();
    }

    public List<Unidadaprendizaje> buscarporNombre(String nombre){
        return ServiceLocator.getInstanceUnidadDAO().findByOneParameter(nombre,"nombreUnidad");
    }
    public Unidadaprendizaje obtenerPorId(Integer id){
        Optional<Unidadaprendizaje> encontrada = ServiceLocator.getInstanceUnidadDAO().find(id);
        return encontrada.orElse(null);
    }
    public void modificarUnidad(Unidadaprendizaje unidad){
        ServiceLocator.getInstanceUnidadDAO().update(unidad);
    }
}