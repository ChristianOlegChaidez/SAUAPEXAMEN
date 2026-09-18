package mx.desarrollo.facade;

import mx.desarrollo.delegate.DelegateProfesor;
import mx.desarrollo.entity.Profesor;

import java.util.List;

public class FacadeProfesor {

    private final DelegateProfesor delegateProfesor;

    public FacadeProfesor() {
        this.delegateProfesor = new DelegateProfesor();
    }

    public void guardarProfesor(Profesor profesor) {
        delegateProfesor.saveProfesor(profesor);
    }

    public List<Profesor> obtenerTodos() {
        return delegateProfesor.obtenerTodos();
    }

    public Profesor buscarPorRFC(String rfc) {
        return delegateProfesor.buscarPorRFC(rfc);
    }
}