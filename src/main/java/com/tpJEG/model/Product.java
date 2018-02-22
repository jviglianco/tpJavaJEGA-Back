package com.tpJEG.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@NamedQueries(value = {
        @NamedQuery(
                name = "Product.findAll",
                query = "SELECT p FROM Product p"),
        @NamedQuery(
                name = "Product.countAll",
                query = "SELECT COUNT (p) FROM Product p")
})
@Table(name = "producto")
@ApiModel(description = "Book resource representation" )
public class Product {

    @Id
    @Column(name = "idProducto")
    @ApiModelProperty("Identifier")
    private Long idProducto;

    @Column(name = "nombreProducto")
    @ApiModelProperty("Nombre del Producto")
    private String nombreProducto;

    @Column(name = "categoriaProducto")
    @ApiModelProperty("Categoria del Producto")
    private String categoriaProducto;

    @Column(name = "imagenUrl")
    @ApiModelProperty("Imagen del Producto")
    private String imagenUrl;

    @Column(name = "precioUnitario")
    @ApiModelProperty("Precio Unitario del Producto")
    private Double precioUnitario;

    public Product() {
    }

    public Product(Long idProducto, String nombreProducto, String categoriaProducto, String imagenUrl, Double precioUnitario) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.categoriaProducto = categoriaProducto;
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

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
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

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", categoriaProducto='" + categoriaProducto + '\'' +
                ", imagenUrl='" + imagenUrl + '\'' +
                ", precioUnitario=" + precioUnitario +
                '}';
    }
}