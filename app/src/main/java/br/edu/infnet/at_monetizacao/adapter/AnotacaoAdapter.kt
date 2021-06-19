package br.edu.infnet.at_monetizacao.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.edu.infnet.at_monetizacao.R
import br.edu.infnet.at_monetizacao.domain.entity.Anotacao
import kotlinx.android.synthetic.main.anotacao_list_adapter.view.*

class AnotacaoAdapter (
    private val anotacoesList: List<Anotacao>,
    private val actionClick: (Anotacao) -> Unit) : RecyclerView.Adapter<AnotacaoAdapter.AnotacaoViewHolder>() {

    class AnotacaoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.txtTitulo
        val description = view.txtDescricao
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnotacaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.anotacao_list_adapter, parent, false)
        return AnotacaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnotacaoViewHolder, position: Int) {
        val anotacao = anotacoesList.get(position)

        holder.title.text = anotacao.title.toString()
        holder.description.text = anotacao.description.toString()

        holder.itemView.setOnClickListener{
            actionClick(anotacao)
        }
    }

    override fun getItemCount(): Int = anotacoesList.size
}