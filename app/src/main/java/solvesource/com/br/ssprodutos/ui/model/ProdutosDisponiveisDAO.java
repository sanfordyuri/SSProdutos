package solvesource.com.br.ssprodutos.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProdutosDisponiveisDAO {

    public static List<Produto> produtosDisponiveis = new ArrayList<>();

    public ProdutosDisponiveisDAO() {
        produtosDisponiveis.add(new Produto("ID#1", "Maça", new BigDecimal(5.9), "maca_produto", 1));
        produtosDisponiveis.add(new Produto("ID#2", "YoPro", new BigDecimal(9.9), "yopro_produto", 1));
        produtosDisponiveis.add(new Produto("ID#3", "Arroz", new BigDecimal(4.9), "arroz_produto", 1));
        produtosDisponiveis.add(new Produto("ID#4", "Requeijao", new BigDecimal(3.9), "requeijao_produto", 1));
    }

    public void insere(Produto... produtos) {
        ProdutosDisponiveisDAO.produtosDisponiveis.addAll(Arrays.asList(produtos));
    }

    public void remove(int posicao) {
        produtosDisponiveis.remove(posicao);
    }

    public void altera(int posicao, Produto produto) {
        produtosDisponiveis.set(posicao, produto);
    }

    public void troca(int posicaoInicio, int posicaoFim) {
        Collections.swap(produtosDisponiveis, posicaoInicio, posicaoFim);
    }

    public List<Produto> todos() {
        return produtosDisponiveis;
    }

    public void removeTodos() {
        produtosDisponiveis.clear();
    }


    public Produto retornarProdutoCarrinhoPeloId(String id) {
        Produto produtoRetornado = new Produto();
        for (Produto produto : produtosDisponiveis) {
            if (produto.getId().equals(id)) {
                produtoRetornado = produto;
                break;
            }
        }
        return produtoRetornado;
    }

}
