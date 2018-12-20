package br.com.casadocodigo.loja.models;

import org.hibernate.validator.constraints.Email;

import javax.inject.Named;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    private String email;

    private String senha;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<SystemRole> roles = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}