package ui;

import helper.LoginHelper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.desarrollo.entity.Profesor;

import java.io.Serializable;

@Named("loginUI")
@SessionScoped
public class LoginBeanUI implements Serializable {

    private LoginHelper loginHelper;
    private Profesor profesor;
    private String rfc;
    private String password;

    public LoginBeanUI() {
        loginHelper = new LoginHelper();
    }

    @PostConstruct
    public void init() {
        profesor = new Profesor();
        rfc = "";
        password = "";
    }

    public String login() {
        if (rfc == null || rfc.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "RFC requerido:", "Capture su RFC"));
            return null;
        }

        Profesor encontrado = loginHelper.buscarPorRFC(rfc.trim());

        if (encontrado != null && encontrado.getId() != null) {
            profesor = encontrado;
            return "index?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "RFC no encontrado:", "Intente de nuevo"));
            return null;
        }
    }

    // Getters y setters
    public Profesor getProfesor() { return profesor; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }

    // Alias para compatibilidad con vistas que usen "usuario"
    public Profesor getUsuario() { return profesor; }
    public void setUsuario(Profesor usuario) { this.profesor = usuario; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
