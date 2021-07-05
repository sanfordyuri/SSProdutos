package solvesource.com.br.ssprodutos.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import solvesource.com.br.ssprodutos.R;
import solvesource.com.br.ssprodutos.ui.model.Produto;
import solvesource.com.br.ssprodutos.ui.model.ProdutosCarrinhoDAO;
import solvesource.com.br.ssprodutos.ui.recyclerview.adapter.ListaProdutosCarrinhosAdapter;
import solvesource.com.br.ssprodutos.util.MoedaUtil;
import solvesource.com.br.ssprodutos.util.ResourceUtil;

import static solvesource.com.br.ssprodutos.util.Constantes.CHAVE_PRODUTO;

public class InsereProdutoActivity extends AppCompatActivity {

    public static final String TITLE_APPBAR = "Adicionar produto ao carrinho";
    private Produto produto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insere_produto);
        setTitle(TITLE_APPBAR);
        vinculaComProdutoRecebidoPeloQrCode();
    }

    private void vinculaComProdutoRecebidoPeloQrCode() {
        TextView nome = findViewById(R.id.insere_produto_activity_nome);
        ImageView image = findViewById(R.id.insere_produto_activity_imagem);
        TextView preco = findViewById(R.id.insere_produto_activity_preco);
        TextView quantidade = findViewById(R.id.insere_produto_activity_quantidade);

        if (getIntent().hasExtra(CHAVE_PRODUTO)) {
            produto = (Produto) getIntent().getSerializableExtra(CHAVE_PRODUTO);
            vinculaCampos(nome, image, preco, quantidade);
        }
    }

    private void vinculaCampos(TextView nome, ImageView image, TextView preco, TextView quantidade) {
        int quantidadep = produto.getQuantidade();
        nome.setText(produto.getNome());
        preco.setText(MoedaUtil.formataParaBr(produto.getPreco()));
        image.setImageDrawable(ResourceUtil.devolveUmDrawable(this, produto.getImagem()));
        quantidade.setText(String.valueOf(quantidadep));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.insere_produto_activity_menu_confirma, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.insere_produto_activity_menu_confirma_botao) {
            ProdutosCarrinhoDAO dao = new ProdutosCarrinhoDAO();
            atualizaProdutoCarrinho(dao);
        }
        return super.onOptionsItemSelected(item);
    }

    private void atualizaProdutoCarrinho(ProdutosCarrinhoDAO dao) {
        EditText quantidade = findViewById(R.id.insere_produto_activity_quantidade);
        ListaProdutosCarrinhosAdapter adapter = new ListaProdutosCarrinhoActivity().adapter;
        int quantidadep = Integer.parseInt(quantidade.getText().toString());
        if (verificaSeCarrinhoTemProduto(dao.todos(), produto.getId())) {
            Produto produtoAntigo = dao.retornarProdutoCarrinhoPeloId(produto.getId());
            Produto produtoAtualizado = new Produto(this.produto.getId(), this.produto.getNome(), this.produto.getPreco(), this.produto.getImagem(), produtoAntigo.getQuantidade() + quantidadep);
            produto = produtoAtualizado;
            int posicao = dao.todos().indexOf(produtoAntigo);
            dao.altera(posicao, produtoAtualizado);
            adapter.altera(posicao, produtoAtualizado);
            finish();
        } else {
            Produto produto_reformado = new Produto(produto.getId(), produto.getNome(), produto.getPreco(), produto.getImagem(), quantidadep);
            produto = produto_reformado;
            dao.insere(produto);
            adapter.adiciona(produto);
            finish();
        }
    }

    private boolean verificaSeCarrinhoTemProduto(List<Produto> produtos, String ID) {
        boolean temProduto = false;
        for (Produto p : produtos) {
            if (ID.equals(p.getId())) {
                temProduto = true;
                break;
            }
        }

        return temProduto;
    }
}