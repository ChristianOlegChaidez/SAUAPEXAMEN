package mx.desarrollo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "unidadaprendizaje")
public class Unidadaprendizaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUnidad", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "nombreUnidad", nullable = false, length = 150)
    private String nombreUnidad;

    @NotNull
    @Column(name = "hrClase", nullable = false)
    private Integer hrClase;

    @NotNull
    @Column(name = "hrTaller", nullable = false)
    private Integer hrTaller;

    @NotNull
    @Column(name = "hrLaboratorio", nullable = false)
    private Integer hrLaboratorio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public Integer getHrClase() {
        return hrClase;
    }

    public void setHrClase(Integer hrClase) {
        this.hrClase = hrClase;
    }

    public Integer getHrTaller() {
        return hrTaller;
    }

    public void setHrTaller(Integer hrTaller) {
        this.hrTaller = hrTaller;
    }

    public Integer getHrLaboratorio() {
        return hrLaboratorio;
    }

    public void setHrLaboratorio(Integer hrLaboratorio) {
        this.hrLaboratorio = hrLaboratorio;
    }

}