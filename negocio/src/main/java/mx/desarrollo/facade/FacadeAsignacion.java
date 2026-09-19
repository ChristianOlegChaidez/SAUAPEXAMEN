package mx.desarrollo.facade;

import mx.desarrollo.delegate.DelegateAsignacion;
import mx.desarrollo.entity.Asignacion;

import java.util.List;

public class FacadeAsignacion {

    private final DelegateAsignacion delegateAsignacion;

    public FacadeAsignacion() {
        this.delegateAsignacion = new DelegateAsignacion();
    }

    public void guardarAsignacion(Asignacion asignacion) {
        delegateAsignacion.saveAsignacion(asignacion);
    }

    public List<Asignacion> obtenerTodos() {
        return delegateAsignacion.obtenerTodos();
    }

    public List<Asignacion> consultarAsignaciones() {
        return delegateAsignacion.consultarAsignaciones();
    }
}