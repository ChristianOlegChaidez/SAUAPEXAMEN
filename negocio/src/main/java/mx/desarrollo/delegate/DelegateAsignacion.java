package mx.desarrollo.delegate;

import mx.desarrollo.entity.Asignacion;
import mx.desarrollo.persistence.integration.ServiceLocator;

import java.time.LocalTime;
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

    public List<Asignacion> consultarPorNombreUnidad(String nombreUnidad) {
        return ServiceLocator.getInstanceAsignacionDAO().consultarPorNombreUnidad(nombreUnidad);
    }

    public void modificarAsignacion(Asignacion asignacion) {
        ServiceLocator.getInstanceAsignacionDAO().saveOrUpdate(asignacion);
    }

    public boolean existeTraslape(Integer idProfesor, String diaSemana, LocalTime hrInicio, LocalTime hrFin, Integer idAsignacionActual) {
        return ServiceLocator.getInstanceAsignacionDAO().existeTraslape(idProfesor, diaSemana, hrInicio, hrFin, idAsignacionActual);
    }
}