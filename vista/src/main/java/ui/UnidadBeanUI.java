package ui;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import mx.desarrollo.entity.Unidadaprendizaje;
import mx.desarrollo.integration.ServiceLocatorFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Named ("unidadUI")
@ViewScoped

public class UnidadBeanUI implements Serializable {
 private List<String> nombreDisponibles;
 private List<Unidadaprendizaje> resultados;
 private String nombreSeleccionado;

 @PostConstruct
    public void init(){
     List<Unidadaprendizaje> todas = ServiceLocatorFacade.getInstanceFacadeUnidad().obtenerTodos();
     nombreDisponibles = todas.stream()
             .map(Unidadaprendizaje :: getNombreUnidad)
             .distinct()
             .sorted()
             .collect(Collectors.toList());
     resultados = new ArrayList<>();
 }

 public void consultar() {
     if (nombreSeleccionado == null || nombreSeleccionado.isBlank()){
         resultados = new ArrayList<>();
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                 "Atencion:", "Seleccione una unidad de aprendizaje"));
         return;
     }
     resultados = ServiceLocatorFacade.getInstanceFacadeUnidad().buscarPorNombre(nombreSeleccionado);
     if (resultados.isEmpty()){
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                 "Error:",  "No hay unidad de aprendizaje disponibles"));

     }
 }
 public List<String> getNombreDisponibles(){
     return nombreDisponibles;
 }

 public List<Unidadaprendizaje> getResultados(){
     return resultados;
 }
 public String getNombreSeleccionado(){
     return nombreSeleccionado;
 }

 public void setNombreSeleccionado(String nombreSeleccionado){
     this.nombreSeleccionado = nombreSeleccionado;
 }
}

