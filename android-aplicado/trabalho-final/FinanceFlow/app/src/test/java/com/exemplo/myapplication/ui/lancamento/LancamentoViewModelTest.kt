package com.exemplo.myapplication.ui.lancamento

import com.exemplo.myapplication.data.Movimentacao
import com.exemplo.myapplication.data.MovimentacaoDao
import com.exemplo.myapplication.data.MovimentacaoRepository
import com.exemplo.myapplication.data.TipoMovimentacao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LancamentoViewModelTest {

    private val dao = DaoFake()
    private val repositorio = MovimentacaoRepository(dao)
    private lateinit var viewModel: LancamentoViewModel

    @Before
    fun preparar() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        dao.limpar()
        viewModel = LancamentoViewModel(repositorio)
    }

    @After
    fun encerrar() {
        Dispatchers.resetMain()
    }

    @Test
    fun salvarComCamposInvalidosRetornaErros() = runTest {
        viewModel.salvar("", "")

        val resultado = viewModel.resultados.first()
        assertTrue(resultado is ResultadoLancamento.Invalido)
        val invalido = resultado as ResultadoLancamento.Invalido
        assertTrue(invalido.campos.contains(CampoInvalido.VALOR))
        assertTrue(invalido.campos.contains(CampoInvalido.DESCRICAO))
    }

    @Test
    fun salvarComValorZeroRetornaErroDeValor() = runTest {
        viewModel.salvar("0", "Aluguel")

        val resultado = viewModel.resultados.first()
        assertTrue(resultado is ResultadoLancamento.Invalido)
        assertTrue((resultado as ResultadoLancamento.Invalido).campos.contains(CampoInvalido.VALOR))
    }

    @Test
    fun salvarComDadosValidosPersisteMovimentacao() = runTest {
        viewModel.salvar("150,50", "Salário")

        val resultado = viewModel.resultados.first()
        assertTrue(resultado is ResultadoLancamento.Salvo)
        assertEquals(1, dao.salvos.size)
        assertEquals(150.5, dao.salvos.last().valor, 0.001)
        assertEquals(TipoMovimentacao.RECEITA, dao.salvos.last().tipo)
    }

    @Test
    fun atualizarMovimentacaoExistente() = runTest {
        val existente = Movimentacao(
            id = 1,
            valor = 100.0,
            descricao = "Aluguel",
            data = 0L,
            tipo = TipoMovimentacao.DESPESA
        )
        dao.salvos += existente
        viewModel.carregarEdicao(existente)
        viewModel.salvar("200", "Condomínio")

        val resultado = viewModel.resultados.first()
        assertTrue(resultado is ResultadoLancamento.Salvo)
        assertEquals(1, dao.salvos.size)
        assertEquals("Condomínio", dao.salvos.first().descricao)
        assertEquals(200.0, dao.salvos.first().valor, 0.001)
    }

    @Test
    fun excluirMovimentacaoEmEdicao() = runTest {
        val existente = Movimentacao(
            id = 2,
            valor = 50.0,
            descricao = "Lanche",
            data = 0L,
            tipo = TipoMovimentacao.DESPESA
        )
        dao.salvos += existente
        viewModel.carregarEdicao(existente)
        viewModel.excluir()

        val resultado = viewModel.resultados.first()
        assertTrue(resultado is ResultadoLancamento.Excluido)
        assertTrue(dao.salvos.isEmpty())
    }

    @Test
    fun repositorioBuscaPorId() = runTest {
        val movimentacao = Movimentacao(
            id = 3,
            valor = 80.0,
            descricao = "Freela",
            data = 0L,
            tipo = TipoMovimentacao.RECEITA
        )
        dao.salvos += movimentacao

        val encontrada = repositorio.buscarPorId(3)
        assertEquals("Freela", encontrada?.descricao)
        assertNull(repositorio.buscarPorId(99))
    }

    private class DaoFake : MovimentacaoDao {
        val salvos = mutableListOf<Movimentacao>()

        fun limpar() = salvos.clear()

        override suspend fun inserir(movimentacao: Movimentacao): Long {
            val novo = movimentacao.copy(id = (salvos.maxOfOrNull { it.id } ?: 0L) + 1)
            salvos += novo
            return novo.id
        }

        override suspend fun atualizar(movimentacao: Movimentacao) {
            val indice = salvos.indexOfFirst { it.id == movimentacao.id }
            if (indice >= 0) salvos[indice] = movimentacao
        }

        override suspend fun excluir(id: Long) {
            salvos.removeAll { it.id == id }
        }

        override suspend fun buscarPorId(id: Long): Movimentacao? =
            salvos.find { it.id == id }

        override fun listarTodas() = flowOf(salvos.toList())
    }
}
