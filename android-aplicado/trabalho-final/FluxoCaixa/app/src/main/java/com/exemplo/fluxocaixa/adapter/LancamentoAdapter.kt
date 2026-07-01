package com.exemplo.fluxocaixa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.exemplo.fluxocaixa.R
import com.exemplo.fluxocaixa.model.Lancamento
import com.exemplo.fluxocaixa.util.FormatadorMoeda

class LancamentoAdapter(
    private var listaLancamentos: List<Lancamento> = emptyList()
) : RecyclerView.Adapter<LancamentoAdapter.LancamentoViewHolder>() {

    fun atualizarLista(novaLista: List<Lancamento>) {
        listaLancamentos = novaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(pai: ViewGroup, tipoView: Int): LancamentoViewHolder {
        val view = LayoutInflater.from(pai.context)
            .inflate(R.layout.item_lancamento, pai, false)
        return LancamentoViewHolder(view)
    }

    override fun onBindViewHolder(holder: LancamentoViewHolder, posicao: Int) {
        holder.vincular(listaLancamentos[posicao])
    }

    override fun getItemCount(): Int = listaLancamentos.size

    class LancamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textoDescricao: TextView = itemView.findViewById(R.id.textoDescricao)
        private val textoData: TextView = itemView.findViewById(R.id.textoData)
        private val textoValor: TextView = itemView.findViewById(R.id.textoValor)
        private val textoTipo: TextView = itemView.findViewById(R.id.textoTipo)

        fun vincular(lancamento: Lancamento) {
            textoDescricao.text = lancamento.descricao
            textoData.text = lancamento.dataLancamento
            textoValor.text = FormatadorMoeda.formatar(lancamento.valor)
            textoTipo.text = lancamento.tipoLancamento.obterRotulo(itemView.context)

            val corValor = if (lancamento.tipoLancamento.ehReceita()) {
                R.color.verde_receita
            } else {
                R.color.vermelho_despesa
            }
            val cor = ContextCompat.getColor(itemView.context, corValor)
            textoValor.setTextColor(cor)
            textoTipo.setTextColor(cor)
        }
    }
}
