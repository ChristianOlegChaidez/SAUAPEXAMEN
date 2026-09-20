package mx.desarrollo.delegate;

import mx.desarrollo.entity.Asignacion;
import mx.desarrollo.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateAsignacion {
    public void saveAsignacion(Asignacion asignacion) {
        ServiceLocator.getInstanceAsignacionDAO().save(asignacion);
    }

    public List<Asignacion> obtenerTodos() {
        return ServiceLocator.getInstanceAsignacionDAO().findAll();
    }

    public List<Asignacion> consultarAsignaciones() {
        return ServiceLocator.getInstanceAsignacionDAO().consultarAsignaciones();
    }
}