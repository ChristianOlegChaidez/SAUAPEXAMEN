package ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.desarrollo.entity.Asignacion;
import mx.desarrollo.integration.ServiceLocatorFacade;
import mx.desarrollo.persistence.integration.ServiceLocator;
import java.io.Serializable;
import java.util.List;

@Named("modificarAsignacionUI")
@SessionScoped
public class ModificarAsignacionBeanUI implements Serializable{

    private String nombreUnidadBusqueda;
    private List<Asignacion> resultadosBusqueda;
    private Asignacion asignacionSeleccionada;

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
        modificar();
        return "modificarAsignacionResultados?faces-redirect=true";
    }

    public String getNombreUnidadBusqueda() { return nombreUnidadBusqueda; }
    public void setNombreUnidadBusqueda(String nombreUnidadBusqueda) { this.nombreUnidadBusqueda = nombreUnidadBusqueda; }

    public List<Asignacion> getResultadosBusqueda() { return resultadosBusqueda; }

    public Asignacion getAsignacionSeleccionada() { return asignacionSeleccionada; }
    public void setAsignacionSeleccionada(Asignacion asignacionSeleccionada) { this.asignacionSeleccionada = asignacionSeleccionada; }
}
