package ui;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.desarrollo.entity.Asignacion;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.entity.Unidadaprendizaje;
import mx.desarrollo.integration.ServiceLocatorFacade;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

@Named("registroAsignacionBean")
@ViewScoped
public class RegistroAsignacionBean implements Serializable {

    private Profesor profesorSeleccionado;
    private Unidadaprendizaje unidadSeleccionada;
    private String nombreProfesor;
    private String nombreUnidad;
    private String tipoHora;
    private String diaSemana;
    private LocalTime horaInicio;

    public List<String> completarProfesores(String consulta) {
        String texto = consulta == null ? "" : consulta.toLowerCase(Locale.ROOT);
        return ServiceLocatorFacade.getInstanceFacadeProfesor().obtenerTodos().stream()
                .filter(profesor -> nombreCompleto(profesor).toLowerCase(Locale.ROOT).contains(texto))
                .map(this::nombreCompleto)
                .toList();
    }

    public List<String> completarUnidades(String consulta) {
        String texto = consulta == null ? "" : consulta.toLowerCase(Locale.ROOT);
        return ServiceLocatorFacade.getInstanceFacadeUnidad().obtenerTodos().stream()
                .filter(unidad -> unidad.getNombreUnidad().toLowerCase(Locale.ROOT).contains(texto))
                .map(Unidadaprendizaje::getNombreUnidad)
                .toList();
    }

    public void validarDatosIniciales() {
        profesorSeleccionado = buscarProfesor(nombreProfesor);
        unidadSeleccionada = buscarUnidad(nombreUnidad);

        if (!estaVacio(nombreProfesor) && profesorSeleccionado == null) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "El profesor no existe.");
        }
        if (!estaVacio(nombreUnidad) && unidadSeleccionada == null) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "La unidad de aprendizaje no existe.");
        }
    }

    public void registrar() {
        if (estaVacio(nombreProfesor)) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "El profesor es obligatorio.");
            return;
        }
        if (estaVacio(nombreUnidad)) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "La unidad de aprendizaje es obligatoria.");
            return;
        }

        profesorSeleccionado = buscarProfesor(nombreProfesor);
        if (profesorSeleccionado == null) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "El profesor no existe.");
            return;
        }
        unidadSeleccionada = buscarUnidad(nombreUnidad);
        if (unidadSeleccionada == null) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "La unidad de aprendizaje no existe.");
            return;
        }
        if (tipoHora == null || tipoHora.isBlank() || diaSemana == null || diaSemana.isBlank() || horaInicio == null) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "Completa todos los campos para registrar la asignación.");
            return;
        }

        int horas = getHorasDelTipoSeleccionado();
        if (horas <= 0) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "La unidad no tiene horas configuradas para el tipo seleccionado.");
            return;
        }

        LocalTime horaFin = horaInicio.plusHours(horas);
        boolean traslape = ServiceLocatorFacade.getInstanceFacadeAsignacion().existeTraslape(
                profesorSeleccionado.getId(), diaSemana, horaInicio, horaFin, null);
        if (traslape) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "El profesor ya tiene una asignación que se traslapa con ese horario.");
            return;
        }

        try {
            Asignacion asignacion = new Asignacion();
            asignacion.setIdProfesor(profesorSeleccionado);
            asignacion.setIdUnidad(unidadSeleccionada);
            asignacion.setTipoHora(tipoHora);
            asignacion.setDiaSemana(diaSemana);
            asignacion.setHrInicio(horaInicio);
            asignacion.setHrFin(horaFin);
            ServiceLocatorFacade.getInstanceFacadeAsignacion().guardarAsignacion(asignacion);
            mensaje(FacesMessage.SEVERITY_INFO, "Éxito", "Asignación registrada correctamente.");
            limpiarHorario();
        } catch (Exception e) {
            mensaje(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo registrar la asignación.");
        }
    }

    public int getTotalHorasSemana() {
        if (unidadSeleccionada == null) {
            return 0;
        }
        return unidadSeleccionada.getHrClase() + unidadSeleccionada.getHrTaller()
                + unidadSeleccionada.getHrLaboratorio();
    }

    public int getHorasDelTipoSeleccionado() {
        if (unidadSeleccionada == null || tipoHora == null) {
            return 0;
        }
        return switch (tipoHora) {
            case "Clase" -> unidadSeleccionada.getHrClase();
            case "Taller" -> unidadSeleccionada.getHrTaller();
            case "Laboratorio" -> unidadSeleccionada.getHrLaboratorio();
            default -> 0;
        };
    }

    public String nombreCompleto(Profesor profesor) {
        if (profesor == null) {
            return "";
        }
        return String.join(" ", profesor.getNombres(), profesor.getApellidoPaterno(),
                profesor.getApellidoMaterno() == null ? "" : profesor.getApellidoMaterno()).trim();
    }

    private void limpiarHorario() {
        tipoHora = null;
        diaSemana = null;
        horaInicio = null;
    }

    private Profesor buscarProfesor(String nombre) {
        return ServiceLocatorFacade.getInstanceFacadeProfesor().obtenerTodos().stream()
                .filter(profesor -> nombreCompleto(profesor).equalsIgnoreCase(nombre.trim()))
                .findFirst()
                .orElse(null);
    }

    private Unidadaprendizaje buscarUnidad(String nombre) {
        if (estaVacio(nombre)) {
            return null;
        }
        return ServiceLocatorFacade.getInstanceFacadeUnidad().obtenerTodos().stream()
                .filter(unidad -> unidad.getNombreUnidad().equalsIgnoreCase(nombre.trim()))
                .findFirst()
                .orElse(null);
    }

    private boolean estaVacio(String valor) {
        return valor == null || valor.isBlank();
    }

    private void mensaje(FacesMessage.Severity severidad, String titulo, String detalle) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, titulo, detalle));
    }

    public Profesor getProfesorSeleccionado() { return profesorSeleccionado; }
    public void setProfesorSeleccionado(Profesor profesorSeleccionado) { this.profesorSeleccionado = profesorSeleccionado; }
    public Unidadaprendizaje getUnidadSeleccionada() { return unidadSeleccionada; }
    public void setUnidadSeleccionada(Unidadaprendizaje unidadSeleccionada) { this.unidadSeleccionada = unidadSeleccionada; }
    public String getNombreProfesor() { return nombreProfesor; }
    public void setNombreProfesor(String nombreProfesor) { this.nombreProfesor = nombreProfesor; }
    public String getNombreUnidad() { return nombreUnidad; }
    public void setNombreUnidad(String nombreUnidad) { this.nombreUnidad = nombreUnidad; }
    public String getTipoHora() { return tipoHora; }
    public void setTipoHora(String tipoHora) { this.tipoHora = tipoHora; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
}
