package com.tpJEG.repository;

import com.tpJEG.model.Usuario;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.transaction.*;
import java.util.List;

public class UsuarioRepository {

    @PersistenceContext(unitName = "ProductPU")
    private EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    public Usuario find(Long idUsuario) {
        return em.find(Usuario.class, idUsuario);
    }

    public Usuario findByUserAndPass(String username, String password) {
        TypedQuery<Usuario> query = em.createQuery("FROM Usuario u WHERE u.username = :username AND u.password = :password", Usuario.class);
        List<Usuario> usuarios = query
                .setParameter("username", username)
                .setParameter("password", password)
                .getResultList();

        if (usuarios.size() > 0){
            return query
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        }
        return null;
    }
    public List<Usuario> findAll() {
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM Usuario u", Long.class);
        return query.getSingleResult();
    }

    public Usuario create( Usuario usuario) throws Exception {
        userTransaction.begin();
        em.persist(usuario);
        userTransaction.commit();
        return usuario;
    }

    public void update( Usuario usuario) throws Exception {
        userTransaction.begin();
        em.merge(usuario);
        userTransaction.commit();
    }

    public void delete(Long idUsuario) throws Exception {
        userTransaction.begin();
        Usuario usuarioDelete;
        usuarioDelete = em.find(Usuario.class, idUsuario);
        em.remove(usuarioDelete);
        userTransaction.commit();
    }

}
