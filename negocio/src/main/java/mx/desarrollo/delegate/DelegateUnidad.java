package mx.desarrollo.delegate;

import mx.desarrollo.entity.Unidadaprendizaje;
import mx.desarrollo.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateUnidad {
    public void saveUnidad(Unidadaprendizaje unidad) {
        ServiceLocator.getInstanceUnidadDAO().save(unidad);
    }

    public List<Unidadaprendizaje> obtenerTodos() {
        return ServiceLocator.getInstanceUnidadDAO().findAll();
    }
}