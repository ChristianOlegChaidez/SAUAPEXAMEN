package mx.desarrollo.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.persistence.persistence.AbstractDAO;
import mx.desarrollo.entity.Unidadaprendizaje;

import java.util.List;

public class UnidadaprendizajeDAO extends AbstractDAO<Unidadaprendizaje> {

    private final EntityManager entityManager;

    public UnidadaprendizajeDAO(EntityManager em) {
        super(Unidadaprendizaje.class);
        this.entityManager = em;
    }

    public List<Unidadaprendizaje> obtenerTodos() {
        return entityManager
                .createQuery("SELECT u FROM Unidadaprendizaje u", Unidadaprendizaje.class)
                .getResultList();
    }

    public List<Unidadaprendizaje> obtenerTodosOrdenadosPorNombre() {
        return entityManager
                .createQuery("SELECT u FROM Unidadaprendizaje u ORDER BY u.nombreUnidad", Unidadaprendizaje.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}