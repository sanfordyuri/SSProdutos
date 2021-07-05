package solvesource.com.br.ssprodutos.ui.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Produto implements Serializable {

    private String id;
    private String nome;
    private BigDecimal preco;
    private String imagem;
    private int quantidade;

    public Produto(String id, String nome, BigDecimal preco, String imagem, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.imagem = imagem;
        this.quantidade = quantidade;
    }

    public Produto() {

    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getImagem() {
        return imagem;
    }

    public int getQuantidade() {return quantidade;}



}
