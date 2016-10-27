package it.e.pgwt.client.shared;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * Created by mancini on 21/06/16.
 */

@Portable
public class Customer {

    private String nome;


    public Customer(@MapsTo("nome") String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}
