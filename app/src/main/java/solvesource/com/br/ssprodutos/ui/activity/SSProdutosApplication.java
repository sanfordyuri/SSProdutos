package solvesource.com.br.ssprodutos.ui.activity;

import android.app.Application;

import java.math.BigDecimal;

import solvesource.com.br.ssprodutos.ui.model.Produto;
import solvesource.com.br.ssprodutos.ui.model.ProdutosCarrinhoDAO;

public class SSProdutosApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InsereProdutosTeste();
    }

    private void InsereProdutosTeste() {
        new ProdutosCarrinhoDAO().insere(new Produto("ID#1", "Maça", new BigDecimal(5.9), "maca_produto", 1));
        new ProdutosCarrinhoDAO().insere(new Produto("ID#3", "Arroz", new BigDecimal(4.9), "arroz_produto", 1));
    }
}
