package com.tpJEG.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "categoria")
@ApiModel(description = "Representacion de Categoria" )
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idCategoria", updatable = false, nullable = false)
    @ApiModelProperty("Identifier")
    private Long idCategoria;

    @Column(name = "nombreCategoria")
    @ApiModelProperty("Nombre del Producto")
    private String nombreCategoria;

    @Column(name = "imagenUrl")
    @ApiModelProperty("Imagen de la categoria")
    private String imagenUrl;

    @OneToMany(
            mappedBy = "idCategoria",
            orphanRemoval=true,
            fetch = FetchType.EAGER
    )
        @JsonIgnore
    private List<Product> productos = new ArrayList<>();

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public List<Product> getProductos() {
        return productos;
    }

    public void setProductos(List<Product> productos) {
        this.productos = productos;
    }

    public String getImagenUrl() { return imagenUrl; }

    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}
