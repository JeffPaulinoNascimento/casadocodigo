package br.com.casadocodigo.loja.resources;

import br.com.casadocodigo.loja.daos.LivroDao;
import br.com.casadocodigo.loja.models.Livro;
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/*SERVIÇOS REST
* Para retornar serviço Rest atraves de um Json colocar o Path("caminho") e o metodo GET
* e anotar com o @Produces para informar que o retorno é um  JSON
* Para testar o serviço precisa colocar o /services que vem da clase JaxRsConfiguration do pacote conf
* */

@Path("livros")
public class LivroResource {

    @Inject
    private LivroDao dao;


    /*Esse metodo usa o Content Negociation
    * Quem escolhe se é json ou xml é o protocolo http (o accept)*/
    @GET
    @Path("lancamentos")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Wrapped(element = "livros") // para alterar o titulo de collection para texto colocado após element no XML
    public List<Livro> ultimosLancamentos(){
        return dao.ultimosLancamentos();
    }
}