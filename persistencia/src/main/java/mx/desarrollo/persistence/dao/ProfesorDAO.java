package mx.desarrollo.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.persistence.persistence.AbstractDAO;
import mx.desarrollo.entity.Profesor;

import java.util.List;

public class ProfesorDAO extends AbstractDAO<Profesor> {

    private final EntityManager entityManager;

    public ProfesorDAO(EntityManager em) {
        super(Profesor.class);
        this.entityManager = em;
    }

    public List<Profesor> obtenerTodos() {
        return entityManager
                .createQuery("SELECT p FROM Profesor p", Profesor.class)
                .getResultList();
    }

    public void insertarProfesor(Profesor profesor) {
        save(profesor);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}