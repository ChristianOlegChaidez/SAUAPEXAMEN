package mx.desarrollo.persistence.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.StoredProcedureQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractDAO<T> {

    private final Class<T> entityClass;

    protected AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void save(T entity) {
        executeInsideTransaction(em -> em.persist(entity));
    }

    public void update(T entity) {
        executeInsideTransaction(em -> em.merge(entity));
    }

    public void delete(T entity) {
        executeInsideTransaction(em -> em.remove(em.contains(entity) ? entity : em.merge(entity)));
    }

    public Optional<T> find(Object id) {
        return Optional.ofNullable(getEntityManager().find(entityClass, id));
    }

    public List<T> findAll() {
        return execute(em ->
                em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                        .getResultList()
        );
    }

    public T saveOrUpdate(T entity) {
        return execute(em -> {
            T merged = em.merge(entity);
            em.flush();
            return merged;
        });
    }

    public List<T> findByOneParameter(Object value, String field) {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName()
                + " e WHERE e." + field + " = :value";
        return execute(em ->
                em.createQuery(jpql, entityClass)
                        .setParameter("value", value)
                        .getResultList()
        );
    }

    public List<T> executeNativeQuery(String sql) {
        return execute(em ->
                em.createNativeQuery(sql, entityClass)
                        .getResultList()
        );
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        execute(em -> {
            action.accept(em);
            return null;
        });
    }

    protected <R> R execute(Function<EntityManager, R> function) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            R result = function.apply(em);
            tx.commit();
            return result;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
}