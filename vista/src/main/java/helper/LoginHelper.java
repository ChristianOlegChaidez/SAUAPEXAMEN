package helper;

import mx.desarrollo.facade.FacadeProfesor;
import mx.desarrollo.entity.Profesor;
import java.io.Serializable;

public class LoginHelper implements Serializable {

    private final FacadeProfesor facadeProfesor;

    public LoginHelper() {
        facadeProfesor = new FacadeProfesor();
    }

    public Profesor buscarPorRFC(String rfc) {
        return facadeProfesor.buscarPorRFC(rfc);
    }
}