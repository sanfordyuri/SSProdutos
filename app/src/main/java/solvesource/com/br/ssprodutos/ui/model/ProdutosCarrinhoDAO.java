package solvesource.com.br.ssprodutos.ui.model;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import solvesource.com.br.ssprodutos.R;
import solvesource.com.br.ssprodutos.util.MoedaUtil;

import static solvesource.com.br.ssprodutos.util.Constantes.CHAVE_VALOR_TOTAL;

public class ProdutosCarrinhoDAO {

    public static ArrayList<Produto> produtosCarrinho = new ArrayList<>();

    public List<Produto> todos() {
        return (List<Produto>) produtosCarrinho.clone() ;
    }

    public void insere(Produto... produtos) {
        ProdutosCarrinhoDAO.produtosCarrinho.addAll(Arrays.asList(produtos));
    }

    public void remove(int posicao) {
        produtosCarrinho.remove(posicao);
    }

    public void altera(int posicao, Produto produto) {
        produtosCarrinho.set(posicao, produto);
    }

    public void troca(int posicaoInicio, int posicaoFim) {
        Collections.swap(produtosCarrinho, posicaoInicio, posicaoFim);
    }

    public void removeTodos() {
        produtosCarrinho.clear();
    }

    public Produto retornarProdutoCarrinhoPeloId(String id) {
        Produto produtoRetornado = new Produto();
        for (Produto produto : produtosCarrinho) {
            if (produto.getId().equals(id)) {
                produtoRetornado = produto;
                break;
            }
        }
        return produtoRetornado;
    }


}
