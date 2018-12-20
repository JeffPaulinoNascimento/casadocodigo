package br.com.casadocodigo.loja.service;

import br.com.casadocodigo.loja.daos.CompraDao;
import br.com.casadocodigo.loja.infra.MailSender;
import br.com.casadocodigo.loja.models.Compra;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/pagamento")
public class PagamentoService {

    @Inject
    private HttpServletRequest request;

    @Inject
    private CompraDao compraDao;
    @Inject
    private PagamentoGateway pagamentoGateway;

    @Inject
    private JMSContext jmsContext;
    @Resource(name="java:/jms/topics/CarrinhoComprasTopico")
    private Destination destination;

    private static ExecutorService executor = Executors.newFixedThreadPool(50); //Gerenciador de thread

    @POST
    public void pagar(@Suspended final AsyncResponse ar, @QueryParam("uuid") String uuid) { //QueryParam -> eu informo qual o termo que esta vindo do navegador para passar como parametro para o serviço
        Compra compra = compraDao.buscaPorUuid(uuid);
        String contextPath = request.getContextPath();

        JMSProducer producer = jmsContext.createProducer();

        executor.submit(() -> {
            try {
                String resposta = pagamentoGateway.pagar(compra.getTotal());

                producer.send(destination, compra.getUuid());

                URI responseUri = UriBuilder.fromPath("http://localhost:8080" + contextPath + "/index.xhtml").queryParam("msg", "Compra realizada com sucesso").build();
                Response response = Response.seeOther(responseUri).build();

                ar.resume(response);
            } catch (Exception e) {
                ar.resume(new WebApplicationException(e));
            }
        });
    }
}
