package com.exemplo.myapplication

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.exemplo.myapplication.data.AppDatabase
import com.exemplo.myapplication.ui.extrato.ExtratoActivity
import com.exemplo.myapplication.ui.lancamento.LancamentoActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FinanceFlowFluxoTest {

    private lateinit var contexto: Context

    @Before
    fun preparar() {
        contexto = ApplicationProvider.getApplicationContext()
        reiniciarBanco()
    }

    @After
    fun limpar() {
        reiniciarBanco()
    }

    @Test
    fun extratoVazioExibeMensagem() {
        ActivityScenario.launch(ExtratoActivity::class.java).use {
            onView(withText(R.string.extrato_vazio)).check(matches(isDisplayed()))
            onView(withText(R.string.rotulo_saldo_atual)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun salvarSemPreencherExibeErros() {
        ActivityScenario.launch(LancamentoActivity::class.java).use {
            onView(withId(R.id.botaoSalvar)).perform(click())
            onView(withText(R.string.erro_valor)).check(matches(isDisplayed()))
            onView(withText(R.string.erro_descricao)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun salvarReceitaENavegarParaExtrato() {
        ActivityScenario.launch(LancamentoActivity::class.java).use { cenario ->
            preencherLancamento("2500", "Salário")
            onView(withId(R.id.botaoSalvar)).perform(click())
            onView(withText(R.string.lancamento_salvo)).check(matches(isDisplayed()))

            cenario.onActivity { activity ->
                activity.startActivity(Intent(activity, ExtratoActivity::class.java))
            }
        }

        onView(withText("Salário")).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.textoValor), withText(containsString("+"))))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.textoSaldo), withText(containsString("2.500"))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun salvarDespesaAtualizaSaldoNegativo() {
        ActivityScenario.launch(LancamentoActivity::class.java).use { cenario ->
            preencherLancamento("1000", "Freelance")
            onView(withId(R.id.botaoSalvar)).perform(click())
            onView(withId(R.id.opcaoDespesa)).perform(click())
            preencherLancamento("300", "Mercado")
            onView(withId(R.id.botaoSalvar)).perform(click())

            cenario.onActivity { activity ->
                activity.startActivity(Intent(activity, ExtratoActivity::class.java))
            }
        }

        onView(withText("Mercado")).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.textoValor), withText(containsString("\u2212"))))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.textoSaldo), withText(containsString("700"))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun fabAbreTelaDeNovoLancamento() {
        ActivityScenario.launch(ExtratoActivity::class.java).use {
            onView(withId(R.id.fabNovoLancamento)).perform(click())
            onView(withText(R.string.titulo_lancamento)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun editarLancamentoAtualizaExtrato() {
        ActivityScenario.launch(LancamentoActivity::class.java).use { cenario ->
            preencherLancamento("500", "Internet")
            onView(withId(R.id.botaoSalvar)).perform(click())
            cenario.onActivity { activity ->
                activity.startActivity(Intent(activity, ExtratoActivity::class.java))
            }
        }

        onView(withText("Internet")).perform(click())
        onView(withId(R.id.entradaDescricao)).perform(replaceText("Internet fibra"), closeSoftKeyboard())
        onView(withId(R.id.botaoSalvar)).perform(click())

        onView(withText("Internet fibra")).check(matches(isDisplayed()))
    }

    @Test
    fun excluirLancamentoRemoveDoExtrato() {
        ActivityScenario.launch(LancamentoActivity::class.java).use { cenario ->
            preencherLancamento("120", "Assinatura")
            onView(withId(R.id.botaoSalvar)).perform(click())
            cenario.onActivity { activity ->
                activity.startActivity(Intent(activity, ExtratoActivity::class.java))
            }
        }

        onView(withText("Assinatura")).perform(click())
        onView(withId(R.id.botaoExcluir)).perform(click())

        onView(withText(R.string.extrato_vazio)).check(matches(isDisplayed()))
    }

    private fun preencherLancamento(valor: String, descricao: String) {
        onView(withId(R.id.entradaValor))
            .perform(replaceText(valor), closeSoftKeyboard())
        onView(withId(R.id.entradaDescricao))
            .perform(replaceText(descricao), closeSoftKeyboard())
    }

    private fun reiniciarBanco() {
        val campo = AppDatabase::class.java.getDeclaredField("instancia")
        campo.isAccessible = true
        (campo.get(null) as AppDatabase?)?.close()
        campo.set(null, null)
        contexto.deleteDatabase("financeflow.db")
    }
}
