package mx.desarrollo.facade;

import mx.desarrollo.delegate.DelegateUnidad;
import mx.desarrollo.entity.Unidadaprendizaje;

import java.util.List;

public class FacadeUnidad {

    private final DelegateUnidad delegateUnidad;

    public FacadeUnidad() {
        this.delegateUnidad = new DelegateUnidad();
    }

    public void guardarUnidad(Unidadaprendizaje unidad) {
        delegateUnidad.saveUnidad(unidad);
    }

    public List<Unidadaprendizaje> obtenerTodos() {
        return delegateUnidad.obtenerTodos();
    }
}