package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.desarrollo.entity.Unidadaprendizaje;
import mx.desarrollo.integration.ServiceLocatorFacade;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named("modificarUnidadUI")
@ViewScoped
public class ModificarUnidadBean implements Serializable {

    private List<String> nombreDisponibles;
    private String nombreSeleccionado;

    private Unidadaprendizaje unidad;
    private boolean listoParaConfirmar;

    private String nombreOriginal;
    private Integer hrClaseOriginal;
    private Integer hrTallerOriginal;
    private Integer hrLaboratorioOriginal;

    @PostConstruct
    public void init() {
        List<Unidadaprendizaje> todas = ServiceLocatorFacade.getInstanceFacadeUnidad().obtenerTodos();
        nombreDisponibles = todas.stream()
                .map(Unidadaprendizaje::getNombreUnidad)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public void cargarUnidad() {
        unidad = null;

        if (nombreSeleccionado == null || nombreSeleccionado.isBlank()) {
            return;
        }

        List<Unidadaprendizaje> encontradas =
                ServiceLocatorFacade.getInstanceFacadeUnidad().buscarPorNombre(nombreSeleccionado);

        if (encontradas.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error:", "No se encontro la unidad de aprendizaje"));
            return;
        }

        unidad = encontradas.get(0);
        guardarSnapshot();
    }

    private void guardarSnapshot() {
        nombreOriginal = unidad.getNombreUnidad();
        hrClaseOriginal = unidad.getHrClase();
        hrTallerOriginal = unidad.getHrTaller();
        hrLaboratorioOriginal = unidad.getHrLaboratorio();
    }

    public void prepararConfirmacion() {
        listoParaConfirmar = validar();
    }

    private boolean validar() {
        if (unidad == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Atencion:", "Seleccione una unidad de aprendizaje"));
            return false;
        }

        if (unidad.getHrClase() == null || unidad.getHrTaller() == null || unidad.getHrLaboratorio() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error:", "Al ingresar datos en la hora tiene que agregar numeros"));
            return false;
        }

        if (fueraDeRango(unidad.getHrClase()) || fueraDeRango(unidad.getHrTaller()) || fueraDeRango(unidad.getHrLaboratorio())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error:", "El rango de horas debe de ser de 0 a 4"));
            return false;
        }

        if (unidad.getNombreUnidad() == null || unidad.getNombreUnidad().isBlank()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error:", "El nombre de la unidad no puede quedar vacio"));
            return false;
        }

        return true;
    }

    private boolean fueraDeRango(Integer horas) {
        return horas < 0 || horas > 4;
    }

    public void confirmarModificacion() {
        boolean cambioNombre = !unidad.getNombreUnidad().equals(nombreOriginal);
        boolean cambioHoras = !unidad.getHrClase().equals(hrClaseOriginal)
                || !unidad.getHrTaller().equals(hrTallerOriginal)
                || !unidad.getHrLaboratorio().equals(hrLaboratorioOriginal);

        try {
            ServiceLocatorFacade.getInstanceFacadeUnidad().modificarUnidad(unidad);

            if (cambioNombre) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Exito:", "El cambio de nombre de la unidad ha sido realizado correctamente"));
            }
            if (cambioHoras) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Exito:", "El cambio de horas ha sido realizado correctamente"));
            }
            if (!cambioNombre && !cambioHoras) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Exito:", "No hubo cambios que guardar"));
            }

            guardarSnapshot();
            init();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error:", "No se pudo modificar la unidad de aprendizaje"));
        }
    }

    public List<String> getNombreDisponibles() {
        return nombreDisponibles;
    }

    public String getNombreSeleccionado() {
        return nombreSeleccionado;
    }

    public void setNombreSeleccionado(String nombreSeleccionado) {
        this.nombreSeleccionado = nombreSeleccionado;
    }

    public Unidadaprendizaje getUnidad() {
        return unidad;
    }

    public boolean isListoParaConfirmar() {
        return listoParaConfirmar;
    }
}