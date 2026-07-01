package com.exemplo.myapplication.ui.extrato

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.myapplication.R
import com.exemplo.myapplication.data.Movimentacao
import com.exemplo.myapplication.data.TipoMovimentacao
import com.exemplo.myapplication.databinding.ItemMovimentacaoBinding
import com.exemplo.myapplication.ui.Formatadores

class MovimentacaoAdapter(
    private val aoClicar: (Movimentacao) -> Unit
) : ListAdapter<Movimentacao, MovimentacaoAdapter.ItemViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemMovimentacaoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ItemViewHolder(binding, aoClicar)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.vincular(getItem(position))
    }

    class ItemViewHolder(
        private val binding: ItemMovimentacaoBinding,
        private val aoClicar: (Movimentacao) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun vincular(movimentacao: Movimentacao) {
            val contexto = binding.root.context
            val receita = movimentacao.tipo == TipoMovimentacao.RECEITA
            val cor = ContextCompat.getColor(
                contexto,
                if (receita) R.color.verde_receita else R.color.vermelho_despesa
            )
            val sinal = if (receita) "+" else "\u2212"

            binding.textoDescricao.text = movimentacao.descricao
            binding.textoData.text = Formatadores.data(movimentacao.data)
            binding.textoValor.text =
                contexto.getString(
                    R.string.formato_valor_lista,
                    sinal,
                    Formatadores.moeda(movimentacao.valor)
                )
            binding.textoValor.setTextColor(cor)
            binding.indicadorTipo.setBackgroundColor(cor)
            binding.root.setOnClickListener { aoClicar(movimentacao) }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Movimentacao>() {
            override fun areItemsTheSame(anterior: Movimentacao, novo: Movimentacao): Boolean =
                anterior.id == novo.id

            override fun areContentsTheSame(anterior: Movimentacao, novo: Movimentacao): Boolean =
                anterior == novo
        }
    }
}
