package mx.desarrollo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

@Entity
@Table(name = "asignacion")
public class Asignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAsignacion", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idProfesor", nullable = false)
    private Profesor idProfesor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUnidad", nullable = false)
    private Unidadaprendizaje idUnidad;

    @Size(max = 10)
    @NotNull
    @Column(name = "diaSemana", nullable = false, length = 10)
    private String diaSemana;

    @NotNull
    @Column(name = "hrInicio", nullable = false)
    private LocalTime hrInicio;

    @NotNull
    @Column(name = "hrFin", nullable = false)
    private LocalTime hrFin;

    @Size(max = 20)
    @NotNull
    @Column(name = "tipoHora", nullable = false, length = 20)
    private String tipoHora;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profesor getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Profesor idProfesor) {
        this.idProfesor = idProfesor;
    }

    public Unidadaprendizaje getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Unidadaprendizaje idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHrInicio() {
        return hrInicio;
    }

    public void setHrInicio(LocalTime hrInicio) {
        this.hrInicio = hrInicio;
    }

    public LocalTime getHrFin() {
        return hrFin;
    }

    public void setHrFin(LocalTime hrFin) {
        this.hrFin = hrFin;
    }

    public String getTipoHora() {
        return tipoHora;
    }

    public void setTipoHora(String tipoHora) {
        this.tipoHora = tipoHora;
    }

}