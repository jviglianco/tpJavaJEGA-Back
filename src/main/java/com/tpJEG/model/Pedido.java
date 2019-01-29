package com.tpJEG.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@ApiModel(description = "Representacion de Pedido" )
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idPedido", updatable = false, nullable = false)
    @ApiModelProperty("Identifier")
    private Long idPedido;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "idUsuario")
    @ApiModelProperty("Usuario que realizo el pedido")
    private Usuario idUsuario;

    @Column(name = "totalPedido")
    @ApiModelProperty("Total del Pedido")
    private Double totalPedido;

    @Column(name = "fechaPedido")
    @ApiModelProperty("Fecha del Pedido")
    private Date fechaPedido;

    @Column(name = "estadoPedido")
    @ApiModelProperty("Estado del Pedido")
    private String estadoPedido;

    @OneToMany(
            mappedBy = "idPedido",
            orphanRemoval=true,
            fetch = FetchType.EAGER
    )
    private List<DetallePedido> idDetallePedido = new ArrayList<>();

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public List<DetallePedido> getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(List<DetallePedido> idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }
}
