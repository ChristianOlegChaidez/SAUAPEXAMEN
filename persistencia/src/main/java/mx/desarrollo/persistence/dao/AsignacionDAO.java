package mx.desarrollo.persistence.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.persistence.persistence.AbstractDAO;
import mx.desarrollo.entity.Asignacion;

import java.time.LocalTime;
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

    public List<Asignacion> consultarPorNombreUnidad(String nombreUnidad) {
        return entityManager
                .createQuery("FROM Asignacion a JOIN FETCH a.idProfesor JOIN FETCH a.idUnidad WHERE LOWER(a.idUnidad.nombreUnidad) LIKE LOWER(:nombre)", Asignacion.class)
                .setParameter("nombre", "%" + nombreUnidad + "%")
                .getResultList();
    }

    public boolean existenPorUnidad(Integer idUnidad) {
        Long total = entityManager
                .createQuery("SELECT COUNT(a) FROM Asignacion a WHERE a.idUnidad.id = :idUnidad", Long.class)
                .setParameter("idUnidad", idUnidad)
                .getSingleResult();
        return total > 0;
    }

    public boolean existeTraslape(Integer idProfesor, String diaSemana, LocalTime hrInicio, LocalTime hrFin, Integer idAsignacionActual) {
        List<Asignacion> resultado = entityManager
                .createQuery("FROM Asignacion a WHERE a.idProfesor.id = :idProfesor " +
                        "AND a.diaSemana = :dia " +
                        "AND (:idActual IS NULL OR a.id != :idActual) " +
                        "AND a.hrInicio < :hrFin AND a.hrFin > :hrInicio", Asignacion.class)
                .setParameter("idProfesor", idProfesor)
                .setParameter("dia", diaSemana)
                .setParameter("idActual", idAsignacionActual)
                .setParameter("hrInicio", hrInicio)
                .setParameter("hrFin", hrFin)
                .getResultList();
        return !resultado.isEmpty();
    }

    public boolean eliminarAsignacion(Integer idAsignacion) {
        return execute(em -> {
            Asignacion asignacion = em.find(Asignacion.class, idAsignacion);

            if (asignacion == null) {
                return false;
            }

            em.remove(asignacion);
            return true;
        });
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
