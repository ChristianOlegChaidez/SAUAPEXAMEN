package mx.desarrollo.facade;

import mx.desarrollo.delegate.DelegateAsignacion;
import mx.desarrollo.entity.Asignacion;
import mx.desarrollo.persistence.integration.ServiceLocator;

import java.time.LocalTime;
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

    public List<Asignacion> consultarPorNombreUnidad(String nombreUnidad) {
        return ServiceLocator.getInstanceAsignacionDAO().consultarPorNombreUnidad(nombreUnidad);
    }

    public boolean existeTraslape(Integer idProfesor, String diaSemana, LocalTime hrInicio, LocalTime hrFin, Integer idAsignacionActual) {
        return delegateAsignacion.existeTraslape(idProfesor, diaSemana, hrInicio, hrFin, idAsignacionActual);
    }

    public void modificarAsignacion(Asignacion asignacion) {
        delegateAsignacion.modificarAsignacion(asignacion);
    }
}