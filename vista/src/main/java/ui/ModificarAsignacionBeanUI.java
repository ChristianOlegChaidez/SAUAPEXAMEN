package ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import mx.desarrollo.entity.Asignacion;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.integration.ServiceLocatorFacade;

import java.io.Serializable;
import java.util.List;

@Named("modificarAsignacionUI")
@SessionScoped
public class ModificarAsignacionBeanUI implements Serializable{

    private String nombreUnidadBusqueda;
    private List<Asignacion> resultadosBusqueda;
    private Asignacion asignacionSeleccionada;
    private String mensajeError;
    private List<Profesor> listaProfesores;

    public String buscarPorUnidad() {
        resultadosBusqueda = ServiceLocatorFacade.getInstanceFacadeAsignacion()
                .consultarPorNombreUnidad(nombreUnidadBusqueda);
        return "modificarAsignacionResultados?faces-redirect=true";
    }

    public void seleccionarParaModificar(Asignacion asignacion) {
        this.asignacionSeleccionada = asignacion;
    }

    public void modificar() {
        ServiceLocatorFacade.getInstanceFacadeAsignacion()
                .modificarAsignacion(asignacionSeleccionada);
        buscarPorUnidad();
    }

    public String seleccionarYNavegar(Asignacion asignacion) {
        this.asignacionSeleccionada = asignacion;
        return "modificarAsignacionEditar?faces-redirect=true";
    }

    public String modificarConNavegacion() {
        mensajeError = null;

        if (asignacionSeleccionada.getHrFin().isBefore(asignacionSeleccionada.getHrInicio())
                || asignacionSeleccionada.getHrFin().equals(asignacionSeleccionada.getHrInicio())) {
            mensajeError = "La hora de fin debe ser posterior a la hora de inicio.";
            return null;
        }

        long horasCapturadas = java.time.Duration.between(
                asignacionSeleccionada.getHrInicio(),
                asignacionSeleccionada.getHrFin()
        ).toHours();

        Integer horasEsperadas = switch (asignacionSeleccionada.getTipoHora()) {
            case "Clase" -> asignacionSeleccionada.getIdUnidad().getHrClase();
            case "Taller" -> asignacionSeleccionada.getIdUnidad().getHrTaller();
            case "Laboratorio" -> asignacionSeleccionada.getIdUnidad().getHrLaboratorio();
            default -> null;
        };

        if (horasEsperadas != null && horasCapturadas != horasEsperadas) {
            mensajeError = "El horario debe durar exactamente " + horasEsperadas + " hora(s) para " + asignacionSeleccionada.getTipoHora() + ".";
            return null;
        }

        boolean traslape = ServiceLocatorFacade.getInstanceFacadeAsignacion()
                .existeTraslape(
                        asignacionSeleccionada.getIdProfesor().getId(),
                        asignacionSeleccionada.getDiaSemana(),
                        asignacionSeleccionada.getHrInicio(),
                        asignacionSeleccionada.getHrFin(),
                        asignacionSeleccionada.getId()
                );

        if (traslape) {
            mensajeError = "El profesor ya tiene una asignación en ese día y horario.";
            return null;
        }

        modificar();
        return "modificarAsignacionResultados?faces-redirect=true";
    }

    public String getMensajeError() { return mensajeError; }

    public String getNombreUnidadBusqueda() { return nombreUnidadBusqueda; }
    public void setNombreUnidadBusqueda(String nombreUnidadBusqueda) { this.nombreUnidadBusqueda = nombreUnidadBusqueda; }

    public List<Asignacion> getResultadosBusqueda() { return resultadosBusqueda; }

    public Asignacion getAsignacionSeleccionada() { return asignacionSeleccionada; }
    public void setAsignacionSeleccionada(Asignacion asignacionSeleccionada) { this.asignacionSeleccionada = asignacionSeleccionada; }

    public List<Profesor> getListaProfesores() {
        if (listaProfesores == null) {
            listaProfesores = ServiceLocatorFacade.getInstanceFacadeProfesor().obtenerTodos();
        }
        return listaProfesores;
    }
}