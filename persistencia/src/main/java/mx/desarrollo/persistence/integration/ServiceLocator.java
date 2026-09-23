package mx.desarrollo.persistence.integration;

import jakarta.persistence.EntityManager;
import mx.desarrollo.persistence.dao.ProfesorDAO;
import mx.desarrollo.persistence.dao.UnidadaprendizajeDAO;
import mx.desarrollo.persistence.dao.AsignacionDAO;
import mx.desarrollo.persistence.persistence.HibernateUtil;

public class ServiceLocator {

    private static ProfesorDAO profesorDAO;
    private static UnidadaprendizajeDAO unidadDAO;
    private static AsignacionDAO asignacionDAO;

    private static EntityManager getEntityManager() {
        return HibernateUtil.getEntityManager();
    }

    public static ProfesorDAO getInstanceProfesorDAO() {
        if (profesorDAO == null) {
            profesorDAO = new ProfesorDAO(getEntityManager());
        }
        return profesorDAO;
    }

    public static UnidadaprendizajeDAO getInstanceUnidadDAO() {
        return new UnidadaprendizajeDAO(HibernateUtil.getEntityManager());
    }

    public static AsignacionDAO getInstanceAsignacionDAO() {
        if (asignacionDAO == null) {
            asignacionDAO = new AsignacionDAO(getEntityManager());
        }
        return asignacionDAO;
    }
}
