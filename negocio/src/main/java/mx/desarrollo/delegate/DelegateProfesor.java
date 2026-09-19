package mx.desarrollo.delegate;

import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistence.integration.ServiceLocator;

import java.util.List;

public class DelegateProfesor {

    public boolean validarRFC(String rfc) {
        if (rfc == null) return false;
        String patron = "^[A-ZÑ&]{3,4}[0-9]{6}[A-Z0-9]{3}$";
        return rfc.matches(patron);
    }

    public void saveProfesor(Profesor profesor) {
        if (!validarRFC(profesor.getRfc())) {
            throw new IllegalArgumentException("RFC con formato inválido");
        }

        List<Profesor> existentes = ServiceLocator.getInstanceProfesorDAO()
                .findByOneParameter(profesor.getRfc(), "rfc");

        if (!existentes.isEmpty()) {
            throw new IllegalStateException("RFC duplicado");
        }

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