package br.com.casadocodigo.loja.daos;

import br.com.casadocodigo.loja.models.Livro;
import org.hibernate.jpa.QueryHints;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

//@Stateful
public class LivroDao {

    @PersistenceContext//(type = PersistenceContextType.EXTENDED)
    private EntityManager manager;

    public void salvar(Livro livro) {
        manager.persist(livro);
    }

    public List<Livro> listar() {
        String jpql = " select distinct(l) from Livro l "
                + " join fetch l.autores ";
        return manager.createQuery(jpql, Livro.class).getResultList();
    }

    @Transactional
    public List<Livro> ultimosLancamentos() {
        String jpql = "select l from Livro l order by l.dataPublicacao desc ";
        List<Livro> livros = manager.createQuery(jpql, Livro.class).setMaxResults(5).setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
        for (Livro livro : livros) livro.getAutores().size();
        return livros;
    }

    public List<Livro> demaisLivros() {
        String jpql = "select l from Livro l order by l.id desc";
        return manager.createQuery(jpql, Livro.class).setFirstResult(4).setHint(QueryHints.HINT_CACHEABLE, true).getResultList();
    }

    public Livro buscarPorId(Integer id) {
        //Livro livro = manager.find(Livro.class, id);

        String jpql = "select l from Livro l join fetch l.autores "
                + "where l.id = :id";
        return manager.createQuery(jpql, Livro.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}