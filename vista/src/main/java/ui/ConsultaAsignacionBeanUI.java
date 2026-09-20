package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.desarrollo.entity.Asignacion;
import mx.desarrollo.integration.ServiceLocatorFacade;

import java.io.Serializable;
import java.util.List;

@Named("consultaAsignacionUI")
@ViewScoped
public class ConsultaAsignacionBeanUI implements Serializable{

    private List<Asignacion> listaAsignaciones;

    @PostConstruct
    public void init(){
        listaAsignaciones = ServiceLocatorFacade.getInstanceFacadeAsignacion().consultarAsignaciones();
    }

    public List<Asignacion> getListaAsignaciones(){
        return listaAsignaciones;
    }
}
