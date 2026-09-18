package ui;

import helper.LoginHelper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.desarrollo.entity.Profesor;

import java.io.IOException;
import java.io.Serializable;

@Named("loginUI")
@SessionScoped
public class LoginBeanUI implements Serializable {

    private LoginHelper loginHelper;
    private Profesor profesor;
    private String rfc;

    public LoginBeanUI() {
        loginHelper = new LoginHelper();
    }

    @PostConstruct
    public void init() {
        profesor = new Profesor();
        rfc = "";
    }

    public void login() throws IOException {
        String appURL = "/index.xhtml";

        Profesor encontrado = loginHelper.buscarPorRFC(rfc);

        if (encontrado != null && encontrado.getId() != null) {
            profesor = encontrado;
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect(FacesContext.getCurrentInstance()
                            .getExternalContext()
                            .getRequestContextPath() + appURL);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "RFC no encontrado:", "Intente de nuevo"));
        }
    }

    // Getters y setters
    public Profesor getProfesor() { return profesor; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }
}