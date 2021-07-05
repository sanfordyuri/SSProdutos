package solvesource.com.br.ssprodutos.ui.recyclerview.helper.callback;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import solvesource.com.br.ssprodutos.ui.activity.ListaProdutosCarrinhoActivity;
import solvesource.com.br.ssprodutos.ui.model.ProdutosCarrinhoDAO;
import solvesource.com.br.ssprodutos.ui.recyclerview.adapter.ListaProdutosCarrinhosAdapter;

public class ProdutoItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ListaProdutosCarrinhosAdapter adapter;


    public ProdutoItemTouchHelperCallback(ListaProdutosCarrinhosAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(0, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoNotaDeslizada = viewHolder.getAdapterPosition();
        remove(posicaoNotaDeslizada);
    }

    private void remove(int posicaoNotaDeslizada) {
        new ProdutosCarrinhoDAO().remove(posicaoNotaDeslizada);
        adapter.remove(posicaoNotaDeslizada);
        atualizaValorTotal();
    }

    private void atualizaValorTotal() {
        new ListaProdutosCarrinhoActivity().AtualizaValorTotal(new ProdutosCarrinhoDAO().todos());
    }

}
