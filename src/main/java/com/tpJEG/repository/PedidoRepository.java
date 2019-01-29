package com.tpJEG.repository;

import com.tpJEG.model.Pedido;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.*;
import java.util.List;


public class PedidoRepository {
    @PersistenceContext(unitName = "ProductPU")
    private EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    public Pedido find(Long idCategoria) {
        return em.find(Pedido.class, idCategoria);
    }

    public Pedido findPedidoByUsuario(Long idUsuario) {
        String estadoPedido = "Activo";
        TypedQuery<Pedido> query = em.createQuery("FROM Pedido p WHERE p.idUsuario.idUsuario = :idUsuario AND p.estadoPedido = :estadoPedido", Pedido.class);
        List<Pedido> pedidos = query
                .setParameter("idUsuario", idUsuario)
                .setParameter("estadoPedido", estadoPedido)
                .getResultList();

        if (pedidos.size() > 0){
            return query
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("estadoPedido", estadoPedido)
                    .getSingleResult();
        }
        return null;
    }

    public List<Pedido> findAll() {
        TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p", Pedido.class);
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM Pedido p", Long.class);
        return query.getSingleResult();
    }

    public Pedido create(Pedido pedido) throws Exception {
        userTransaction.begin();
        em.persist(pedido);
        userTransaction.commit();
        return pedido;
    }

    public void update(Pedido pedido) throws Exception {
        userTransaction.begin();
        em.merge(pedido);
        userTransaction.commit();
    }

    public void delete(Long idPedido) throws Exception {
        userTransaction.begin();
        Pedido pedidoDelete;
        pedidoDelete = em.find(Pedido.class, idPedido);
        em.remove(pedidoDelete);
        userTransaction.commit();
    }
}