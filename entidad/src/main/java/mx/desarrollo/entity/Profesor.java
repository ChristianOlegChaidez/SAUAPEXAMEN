package mx.desarrollo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "profesor")
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProfesor", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Size(max = 50)
    @NotNull
    @Column(name = "apellidoPaterno", nullable = false, length = 50)
    private String apellidoPaterno;

    @Size(max = 50)
    @Column(name = "apellidoMaterno", length = 50)
    private String apellidoMaterno;

    @Size(max = 13)
    @NotNull
    @Column(name = "RFC", nullable = false, length = 13)
    private String rfc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

}