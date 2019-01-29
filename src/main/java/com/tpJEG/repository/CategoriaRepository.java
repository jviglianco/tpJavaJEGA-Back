package com.tpJEG.repository;

import com.tpJEG.model.Categoria;
import com.tpJEG.model.Product;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.*;
import java.util.List;


public class CategoriaRepository {
    @PersistenceContext(unitName = "ProductPU")
    private EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    public Categoria find(Long idCategoria) {
        return em.find(Categoria.class, idCategoria);
    }

    public List<Categoria> findAll() {
        TypedQuery<Categoria> query = em.createQuery("SELECT c FROM Categoria c", Categoria.class);
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Categoria c", Long.class);
        return query.getSingleResult();
    }

    public Categoria create( Categoria categoria) throws Exception {
        userTransaction.begin();
        em.persist(categoria);
        userTransaction.commit();
        return categoria;
    }

    public void update( Categoria categoria) throws Exception {
        userTransaction.begin();
        em.merge(categoria);
        userTransaction.commit();
    }

    public void delete(Long idCategoria) throws Exception {
        userTransaction.begin();
        Categoria categoriaDelete;
        categoriaDelete = em.find(Categoria.class, idCategoria);
        em.remove(categoriaDelete);
        userTransaction.commit();
    }
}
