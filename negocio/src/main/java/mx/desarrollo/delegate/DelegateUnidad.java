package mx.desarrollo.delegate;

import mx.desarrollo.entity.Unidadaprendizaje;
import mx.desarrollo.persistence.integration.ServiceLocator;

import java.util.List;
import java.util.Optional;

public class DelegateUnidad {

    private static final int LONGITUD_MAX_NOMBRE = 150;
    private static final int HORAS_MIN = 0;
    private static final int HORAS_MAX = 4;

    public void saveUnidad(Unidadaprendizaje unidad) {
        ServiceLocator.getInstanceUnidadDAO().save(unidad);
    }

    public void registrarUnidad(String nombre, String hrClase, String hrTaller, String hrLaboratorio) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la unidad no puede quedar vacío.");
        }

        if (nombre.trim().length() > LONGITUD_MAX_NOMBRE) {
            throw new IllegalArgumentException("El nombre no puede exceder " + LONGITUD_MAX_NOMBRE + " caracteres.");
        }

        if (esVacio(hrClase) || esVacio(hrTaller) || esVacio(hrLaboratorio)) {
            throw new IllegalArgumentException("Debe ingresar las horas de clase, taller y laboratorio.");
        }

        Unidadaprendizaje unidad = new Unidadaprendizaje();
        unidad.setNombreUnidad(nombre.trim());
        unidad.setHrClase(convertirHoras(hrClase));
        unidad.setHrTaller(convertirHoras(hrTaller));
        unidad.setHrLaboratorio(convertirHoras(hrLaboratorio));

        ServiceLocator.getInstanceUnidadDAO().save(unidad);
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.isBlank();
    }

    private Integer convertirHoras(String valor) {
        String horas = valor.trim();

        if (!horas.matches("-?\\d+")) {
            throw new IllegalArgumentException("Ingrese solo números en las horas de clase, taller y laboratorio por favor.");
        }

        int numero;
        try {
            numero = Integer.parseInt(horas);
        } catch (NumberFormatException e) {
            // Es un entero, pero demasiado grande para int: está fuera de rango.
            numero = Integer.MAX_VALUE;
        }

        if (numero < HORAS_MIN || numero > HORAS_MAX) {
            throw new IllegalArgumentException("Por favor, ingrese un rango de horas válido (" + HORAS_MIN + "-" + HORAS_MAX + ").");
        }
        return numero;
    }

    public List<Unidadaprendizaje> obtenerTodos() {
        return ServiceLocator.getInstanceUnidadDAO().findAll();
    }

    public List<Unidadaprendizaje> consultarOrdenadasPorNombre() {
        return ServiceLocator.getInstanceUnidadDAO().obtenerTodosOrdenadosPorNombre();
    }

    public List<Unidadaprendizaje> eliminarUnidad(Integer idUnidad) {
        Unidadaprendizaje unidad = ServiceLocator.getInstanceUnidadDAO().find(idUnidad)
                .orElseThrow(() -> new IllegalArgumentException("La unidad de aprendizaje ya no existe."));

        if (ServiceLocator.getInstanceAsignacionDAO().existenPorUnidad(idUnidad)) {
            throw new IllegalStateException("No se puede eliminar la unidad porque tiene asignaciones registradas.");
        }

        ServiceLocator.getInstanceUnidadDAO().delete(unidad);
        return consultarOrdenadasPorNombre();
    }

    public List<Unidadaprendizaje> buscarporNombre(String nombre){
        return ServiceLocator.getInstanceUnidadDAO().findByOneParameter(nombre,"nombreUnidad");
    }
    public Unidadaprendizaje obtenerPorId(Integer id){
        Optional<Unidadaprendizaje> encontrada = ServiceLocator.getInstanceUnidadDAO().find(id);
        return encontrada.orElse(null);
    }
    public void modificarUnidad(Unidadaprendizaje unidad){
        ServiceLocator.getInstanceUnidadDAO().update(unidad);
    }
}