package com.tpJEG.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;


@Entity
@Table(name = "detallepedido")
@ApiModel(description = "Representacion de Pedido" )
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idDetallePedido", updatable = false, nullable = false)
    @ApiModelProperty("Identifier")
    private Long idDetallePedido;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "idProducto")
    @ApiModelProperty("Pedido del Detalle de Pedido")
    private Product idProducto;

    @Column(name = "cantidad")
    @ApiModelProperty("Imagen de la categoria")
    private Long cantidad;

    @Column(name = "subtotal")
    @ApiModelProperty("Imagen de la categoria")
    private Double subtotal;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "idPedido")
    @ApiModelProperty("Pedido del Detalle de Pedido")
    @JsonIgnore
    private Pedido idPedido;

    public Long getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(Long idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public Product getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Product idProducto) {
        this.idProducto = idProducto;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Pedido getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Pedido idPedido) {
        this.idPedido = idPedido;
    }
}
