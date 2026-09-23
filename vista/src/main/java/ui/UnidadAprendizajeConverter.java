package ui;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import mx.desarrollo.entity.Unidadaprendizaje;
import mx.desarrollo.integration.ServiceLocatorFacade;

@FacesConverter("unidadAprendizajeConverter")
public class UnidadAprendizajeConverter implements Converter<Unidadaprendizaje> {

    @Override
    public Unidadaprendizaje getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Integer id = Integer.valueOf(value);
        return ServiceLocatorFacade.getInstanceFacadeUnidad().obtenerTodos().stream()
                .filter(unidad -> unidad.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Unidadaprendizaje value) {
        return value == null ? "" : String.valueOf(value.getId());
    }
}
