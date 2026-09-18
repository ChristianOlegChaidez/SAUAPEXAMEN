package mx.desarrollo.integration;

import mx.desarrollo.facade.FacadeProfesor;
import mx.desarrollo.facade.FacadeAsignacion;
import mx.desarrollo.facade.FacadeUnidad;

public class ServiceLocatorFacade {

    private static FacadeProfesor facadeProfesor;
    private static FacadeAsignacion facadeAsignacion;
    private static FacadeUnidad facadeUnidad;

    public static FacadeProfesor getInstanceFacadeProfesor() {
        if (facadeProfesor == null) {
            facadeProfesor = new FacadeProfesor();
        }
        return facadeProfesor;
    }

    public static FacadeAsignacion getInstanceFacadeAsignacion() {
        if (facadeAsignacion == null) {
            facadeAsignacion = new FacadeAsignacion();
        }
        return facadeAsignacion;
    }

    public static FacadeUnidad getInstanceFacadeUnidad() {
        if (facadeUnidad == null) {
            facadeUnidad = new FacadeUnidad();
        }
        return facadeUnidad;
    }
}