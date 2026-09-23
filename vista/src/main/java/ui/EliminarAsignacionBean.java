package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.desarrollo.entity.Asignacion;
import mx.desarrollo.integration.ServiceLocatorFacade;

import java.io.Serializable;
import java.util.List;

@Named("eliminarAsignacionUI")
@ViewScoped
public class EliminarAsignacionBean implements Serializable {

    private List<Asignacion> listaAsignaciones;
    private Asignacion asignacionSeleccionada;
    private boolean eliminacionExitosa;

    @PostConstruct
    public void init() {
        cargarAsignaciones();
    }

    public void seleccionarAsignacion(Asignacion asignacion) {
        asignacionSeleccionada = asignacion;
        eliminacionExitosa = false;
    }

    public void eliminarAsignacion() {
        if (asignacionSeleccionada == null || asignacionSeleccionada.getId() == null) {
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "Selecciona una asignacion para eliminar.");
            return;
        }

        boolean eliminada = ServiceLocatorFacade.getInstanceFacadeAsignacion()
                .eliminarAsignacion(asignacionSeleccionada.getId());

        if (eliminada) {
            cargarAsignaciones();
            eliminacionExitosa = true;
        } else {
            eliminacionExitosa = false;
            mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error", "La asignacion ya no existe.");
        }

        asignacionSeleccionada = null;
    }

    private void cargarAsignaciones() {
        listaAsignaciones = ServiceLocatorFacade.getInstanceFacadeAsignacion().consultarAsignaciones();
    }

    private void mostrarMensaje(FacesMessage.Severity severidad, String resumen, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, resumen, detalle));
    }

    public List<Asignacion> getListaAsignaciones() {
        return listaAsignaciones;
    }

    public Asignacion getAsignacionSeleccionada() {
        return asignacionSeleccionada;
    }

    public boolean isEliminacionExitosa() {
        return eliminacionExitosa;
    }

    public void ocultarMensajeExito() {
        eliminacionExitosa = false;
    }
}
