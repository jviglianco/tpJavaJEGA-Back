package com.tpJEG.repository;

import com.tpJEG.model.Rol;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.*;
import java.util.List;

public class RolRepository {

    @PersistenceContext(unitName = "ProductPU")
    private EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    public Rol find(Long idRol) {
        return em.find(Rol.class, idRol);
    }

    public List<Rol> findAll() {
        TypedQuery<Rol> query = em.createQuery("SELECT r FROM Rol r", Rol.class);
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(r) FROM Rol r", Long.class);
        return query.getSingleResult();
    }

    public Rol create( Rol rol) throws Exception {
        userTransaction.begin();
        em.persist(rol);
        userTransaction.commit();
        return rol;
    }

    public void update( Rol rol) throws Exception {
        userTransaction.begin();
        em.merge(rol);
        userTransaction.commit();
    }

    public void delete(Long idRol) throws Exception {
        userTransaction.begin();
        Rol rolDelete;
        rolDelete = em.find(Rol.class, idRol);
        em.remove(rolDelete);
        userTransaction.commit();
    }
}
