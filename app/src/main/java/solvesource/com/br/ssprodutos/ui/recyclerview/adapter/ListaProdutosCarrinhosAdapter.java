package solvesource.com.br.ssprodutos.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import solvesource.com.br.ssprodutos.R;
import solvesource.com.br.ssprodutos.ui.model.Produto;
import solvesource.com.br.ssprodutos.util.MoedaUtil;
import solvesource.com.br.ssprodutos.util.ResourceUtil;

public class ListaProdutosCarrinhosAdapter extends RecyclerView.Adapter {

    private List<Produto> produtos;
    private Context context;

    public ListaProdutosCarrinhosAdapter(Context context, List<Produto> produtos) {
        this.produtos = produtos;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_produto_cardview, parent, false);
        return new ProdutoViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        ProdutoViewHolder produtoViewHolder = (ProdutoViewHolder) holder;
        produtoViewHolder.vincula(produto, context);
    }

    @Override
    public int getItemCount() {
        return (null != produtos ? produtos.size() : 0);
    }


    public void remove(int posicaoNotaDeslizada) {
        produtos.remove(posicaoNotaDeslizada);
        notifyItemRemoved(posicaoNotaDeslizada);
    }

    public void adiciona(Produto produto) {
        produtos.add(produto);
        notifyDataSetChanged();
    }

    public void altera(int posicao, Produto produtoAtualizado) {
        produtos.set(posicao, produtoAtualizado);
        notifyItemChanged(posicao);
    }
}

class ProdutoViewHolder extends RecyclerView.ViewHolder {

    private final TextView nome;
    private final TextView preco;
    private final TextView quantidade;
    private final ImageView image;
    private Produto produto;
    private Context context;

    public ProdutoViewHolder(@NonNull View itemView) {
        super(itemView);
        nome = itemView.findViewById(R.id.item_produto_cardview_nome);
        preco = itemView.findViewById(R.id.item_produto_cardview_preco);
        quantidade = itemView.findViewById(R.id.item_produto_cardview_quantidade);
        image = itemView.findViewById(R.id.item_produto_cardview_imagem);
    }

    public void vincula(Produto produto, Context context) {
        this.produto = produto;
        this.context = context;
        preencheCampos(context);
    }

    private void preencheCampos(Context context) {
        nome.setText(this.produto.getNome());
        preco.setText(MoedaUtil.formataParaBr(this.produto.getPreco()));
        image.setImageDrawable(ResourceUtil.devolveUmDrawable(context, this.produto.getImagem()));
        quantidade.setText(this.produto.getQuantidade() + "x");
    }


}
