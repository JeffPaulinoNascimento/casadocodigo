package br.com.casadocodigo.loja.models;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Cacheable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) //permite alterar os campos para exibir no xml
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String titulo;

    @Lob
    @Length(min = 10)
    @NotBlank
    private String descricao;

    @DecimalMin("20")
    private BigDecimal preco;

    @Min(50)
    private Integer numeroPaginas;

    @Temporal(TemporalType.DATE)
    private Date dataPublicacao;

    @ManyToMany
    @Size(min = 1)
    @NotNull
    @XmlElement(name = "autor") //altera o nome de autores(que ele pegava da variavel) para autor
    @XmlElementWrapper(name = "autores") //altera o nome da lista para autores
    private List<Autor> autores = new ArrayList<>();

    private String capaPath;

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getDataPublicacao() {
        String formato = "dd/MM/yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        if(dataPublicacao == null){
            return null;
        }
        return formatter.format(dataPublicacao);
    }

    public void setDataPublicacao(String dataPublicacao) throws ParseException {
        if (dataPublicacao == null || dataPublicacao.isEmpty()){
            return;
        }
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dataParseada = formatter.parse(dataPublicacao);
        this.dataPublicacao = dataParseada;
    }

    @Override
    public String toString() {
        return "Livro [id = " + id + ",titutlo = " + titulo + ", descricao = " + descricao + ", preco = " + preco + "numero de paginas = " + numeroPaginas +
                ", autores " + autores + "]";
    }

    public void setCapaPath(String capaPath) {
        this.capaPath = capaPath;
    }

    public String getCapaPath() {
        return capaPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}