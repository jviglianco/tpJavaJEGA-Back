package com.tpJEG.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "rol")
@ApiModel(description = "Representacion de Rol" )
public class Rol implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idRol", updatable = false, nullable = false)
    @ApiModelProperty("Identifier")
    private Long idRol;

    @Column(name = "nombreRol")
    @ApiModelProperty("Nombre del Rol")
    private String nombreRol;

    @OneToMany(
            mappedBy = "idRol",
            orphanRemoval=true,
            fetch = FetchType.EAGER
    )
    @JsonIgnore
    private List<Usuario> usuarios = new ArrayList<>();

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
