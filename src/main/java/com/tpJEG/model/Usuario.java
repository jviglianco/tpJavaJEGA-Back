package com.tpJEG.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "usuario")
@ApiModel(description = "Representacion de Usuario" )
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idUsuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idUsuario", updatable = false, nullable = false)
    @ApiModelProperty("Identifier")
    private Long idUsuario;

    @Column(name = "nombreUsuario")
    @ApiModelProperty("Nombre del Usuario")
    private String nombreUsuario;

    @Column(name = "apellidoUsuario")
    @ApiModelProperty("Apellido del Usuario")
    private String apellidoUsuario;

    @Column(name = "email")
    @ApiModelProperty("Email del Usuario")
    private String email;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "idRol")
    @ApiModelProperty("Rol del Usuario")
    private Rol idRol;

    @Column(name = "username")
    @ApiModelProperty("Nombre de Usuario")
    private String username;

    @Column(name = "password")
    @ApiModelProperty("Password de Usuario")
    private String password;

    @OneToMany(
            mappedBy = "idUsuario",
            orphanRemoval=true,
            fetch = FetchType.EAGER
    )
    private List<Pedido> pedidosUsuario = new ArrayList<>();

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
