package mx.desarrollo.delegate;

import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateProfesor {

    public void saveProfesor(Profesor profesor) {
        ServiceLocator.getInstanceProfesorDAO().save(profesor);
    }

    public List<Profesor> obtenerTodos() {
        return ServiceLocator.getInstanceProfesorDAO().findAll();
    }

    public Profesor buscarPorRFC(String rfc) {
        return ServiceLocator.getInstanceProfesorDAO().findAll()
                .stream()
                .filter(p -> p.getRfc().equalsIgnoreCase(rfc))
                .findFirst()
                .orElse(null);
    }
}