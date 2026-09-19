package mx.desarrollo.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.persistence.persistence.AbstractDAO;
import mx.desarrollo.entity.Asignacion;

import java.util.List;

public class AsignacionDAO extends AbstractDAO<Asignacion> {

    private final EntityManager entityManager;

    public AsignacionDAO(EntityManager em) {
        super(Asignacion.class);
        this.entityManager = em;
    }

    public List<Asignacion> obtenerTodos() {
        return entityManager
                .createQuery("SELECT a FROM Asignacion a", Asignacion.class)
                .getResultList();
    }

    public List<Asignacion> obtenerPorProfesor(Integer idProfesor) {
        return entityManager
                .createQuery("SELECT a FROM Asignacion a WHERE a.idProfesor = :id", Asignacion.class)
                .setParameter("id", idProfesor)
                .getResultList();
    }

    public List<Asignacion> consultarAsignaciones(){
        return entityManager
                .createQuery("FROM Asignacion a JOIN FETCH a.idProfesor JOIN FETCH a.idUnidad ORDER BY a.idProfesor.nombres", Asignacion.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}