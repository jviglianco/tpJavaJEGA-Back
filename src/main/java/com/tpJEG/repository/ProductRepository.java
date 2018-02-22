package com.tpJEG.repository;

import com.tpJEG.model.Product;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.*;
import javax.transaction.RollbackException;
import javax.ws.rs.Produces;
import java.util.List;

public class ProductRepository {

    @PersistenceContext(unitName = "ProductPU")
    private EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    public Product find(Long idProducto) {
        return em.find(Product.class, idProducto);
    }

    public List<Product> findAll() {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createNamedQuery("Product.countAll", Long.class);
        return query.getSingleResult();
    }

    public Product create( Product producto) throws Exception {
        userTransaction.begin();
        em.persist(producto);
        userTransaction.commit();
        return producto;
    }

    public void update( Product producto) throws Exception {
        userTransaction.begin();
        em.merge(producto);
        userTransaction.commit();
    }

    public void delete(Long idProducto) throws Exception {
        userTransaction.begin();
        Product productDelete;
        productDelete = em.find(Product.class, idProducto);
        em.remove(productDelete);
        userTransaction.commit();
    }

}

