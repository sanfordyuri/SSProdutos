package solvesource.com.br.ssprodutos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.math.BigDecimal;
import java.util.List;

import solvesource.com.br.ssprodutos.R;
import solvesource.com.br.ssprodutos.ui.model.Produto;
import solvesource.com.br.ssprodutos.ui.model.ProdutosCarrinhoDAO;
import solvesource.com.br.ssprodutos.ui.model.ProdutosDisponiveisDAO;
import solvesource.com.br.ssprodutos.ui.recyclerview.adapter.ListaProdutosCarrinhosAdapter;
import solvesource.com.br.ssprodutos.ui.recyclerview.helper.callback.ProdutoItemTouchHelperCallback;
import solvesource.com.br.ssprodutos.util.MoedaUtil;

import static solvesource.com.br.ssprodutos.util.Constantes.CAMERA_ID;
import static solvesource.com.br.ssprodutos.util.Constantes.CHAVE_CAMERA;
import static solvesource.com.br.ssprodutos.util.Constantes.CHAVE_PRODUTO;
import static solvesource.com.br.ssprodutos.util.Constantes.CHAVE_VALOR_TOTAL;
import static solvesource.com.br.ssprodutos.util.Constantes.TITLE_APPBAR;

public class ListaProdutosCarrinhoActivity extends AppCompatActivity {

    public static ListaProdutosCarrinhosAdapter adapter;
    public static RecyclerView listaProdutosCarrinhoRecyclerview;
    public static TextView valor_total_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_produtos_carrinho_activity);
        List<Produto> todosProdutosCarrinho = retornarProdutos();
        configuraRecyclerview(todosProdutosCarrinho);
        setTitle(TITLE_APPBAR);
        valor_total_txt = findViewById(R.id.lista_produtos_activity_total);

    }

    public List<Produto> retornarProdutos() {
        ProdutosCarrinhoDAO dao = new ProdutosCarrinhoDAO();
        return dao.todos();
    }

    private void configuraRecyclerview(List<Produto> produtosCarrinho) {
        listaProdutosCarrinhoRecyclerview = findViewById(R.id.lista_produtos_carrinho_activity_recyclerview);
        configuraAdapter(produtosCarrinho, listaProdutosCarrinhoRecyclerview);
        configuraItemTouchHelper(listaProdutosCarrinhoRecyclerview);
    }

    private void configuraItemTouchHelper(RecyclerView listaProdutosCarrinhoRecyclerview) {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ProdutoItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(listaProdutosCarrinhoRecyclerview);
    }

    private void configuraAdapter(List<Produto> produtosCarrinho, RecyclerView listaProdutosCarrinhoRecyclerview) {
        adapter = new ListaProdutosCarrinhosAdapter(this, produtosCarrinho);
        listaProdutosCarrinhoRecyclerview.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lista_produtos_acitivity_menu_scanner, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.lista_produtos_activity_menu_scanner_botao) {
            configuraIntegrator();
        }
        return super.onOptionsItemSelected(item);
    }

    private void configuraIntegrator() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt(CHAVE_CAMERA);
        integrator.setCameraId(CAMERA_ID); // Camera traseira
        integrator.initiateScan();
    }


    @Override
    protected void onResume() {
        super.onResume();
        List<Produto> produtos = retornarProdutos();
        //configuraRecyclerview(produtos);
        AtualizaValorTotal(produtos);
    }

    public void AtualizaValorTotal(List<Produto> produtos) {

        Double valorTotalCarrinho = 0.0;
        for (Produto produto : produtos) {
            valorTotalCarrinho = incrementaValorAoTotal(valorTotalCarrinho, produto);
        }
        valor_total_txt.setText(CHAVE_VALOR_TOTAL + MoedaUtil.formataParaBr(new BigDecimal(valorTotalCarrinho)));

    }

    private Double incrementaValorAoTotal(Double valorTotalCarrinho, Produto produto) {
        valorTotalCarrinho += (Double.parseDouble(produto.getPreco().toString()) * produto.getQuantidade());
        return valorTotalCarrinho;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) { // Uma informacao foi recebida pelo QR CODE
                enviaProdutoDoQrCodeParaOutraActivity(result);
            } else {
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void enviaProdutoDoQrCodeParaOutraActivity(IntentResult result) {
        String id = result.getContents();
        Produto produtoRetornado = new ProdutosDisponiveisDAO().retornarProdutoCarrinhoPeloId(id);
        Intent intent = new Intent(ListaProdutosCarrinhoActivity.this, InsereProdutoActivity.class);
        intent.putExtra(CHAVE_PRODUTO, produtoRetornado);
        startActivity(intent);
    }
}