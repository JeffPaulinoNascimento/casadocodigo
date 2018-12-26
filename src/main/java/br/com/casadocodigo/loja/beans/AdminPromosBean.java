package br.com.casadocodigo.loja.beans;

import br.com.casadocodigo.loja.models.Promo;

import javax.enterprise.inject.Model;

@Model
public class AdminPromosBean {

    private Promo promo = new Promo();

    public void enviar(){
        System.out.println(promo.getTitulo());
        System.out.println(promo.getLivro());
    }

    public Promo getPromo() {
        return promo;
    }

    public void setPromo(Promo promo) {
        this.promo = promo;
    }

}
