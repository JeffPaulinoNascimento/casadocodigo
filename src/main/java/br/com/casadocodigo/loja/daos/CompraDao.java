package br.com.casadocodigo.loja.daos;

import br.com.casadocodigo.loja.models.Compra;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

public class CompraDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager manager;

    public void salvar(Compra compra){
        manager.persist(compra);
    }

    public Compra buscaPorUuid(String uuid) {
        String jpql = "select c from Compra c where c.uuid = :uuid";
        return manager.createQuery(jpql, Compra.class).setParameter("uuid", uuid).getSingleResult();
    }
}
