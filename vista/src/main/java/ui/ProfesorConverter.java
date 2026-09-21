package ui;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.integration.ServiceLocatorFacade;

@FacesConverter("profesorConverter")
public class ProfesorConverter implements Converter<Profesor> {

    @Override
    public Profesor getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        Integer id = Integer.valueOf(value);
        return ServiceLocatorFacade.getInstanceFacadeProfesor()
                .obtenerTodos()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Profesor value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}
