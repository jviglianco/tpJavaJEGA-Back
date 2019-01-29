package com.tpJEG.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "producto")
@ApiModel(description = "Representacion de Producto" )
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idProducto")
public class Product implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idProducto", updatable = false, nullable = false)
    @ApiModelProperty("Identifier")
    private Long idProducto;

    @Column(name = "nombreProducto")
    @ApiModelProperty("Nombre del Producto")
    private String nombreProducto;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "idCategoria")
    @ApiModelProperty("Categoria del Producto")
    private Categoria idCategoria;

    @Column(name = "imagenUrl")
    @ApiModelProperty("Imagen del Producto")
    private String imagenUrl;

    @Column(name = "precioUnitario")
    @ApiModelProperty("Precio Unitario del Producto")
    private Double precioUnitario;

    @OneToMany(
            mappedBy = "idProducto",
            orphanRemoval=true,
            fetch = FetchType.EAGER
    )
    private List<DetallePedido> detallePedidoProductos = new ArrayList<>();

    public Product() {
    }

    public Product(Long idProducto, String nombreProducto, Categoria idCategoria, String imagenUrl, Double precioUnitario) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.idCategoria = idCategoria;
        this.imagenUrl = imagenUrl;
        this.precioUnitario = precioUnitario;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

}