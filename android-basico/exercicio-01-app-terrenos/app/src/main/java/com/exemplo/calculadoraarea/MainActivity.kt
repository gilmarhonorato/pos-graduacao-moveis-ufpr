package com.exemplo.calculadoraarea

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exemplo.calculadoraarea.R.id

class MainActivity : AppCompatActivity() {

    private lateinit var edit_largura: EditText
    private lateinit var edit_comprimento: EditText
    private lateinit var btn_calcular: Button
    private lateinit var btn_limpar: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edit_largura = findViewById(id.edit_largura)
        edit_comprimento = findViewById(id.edit_comprimento)
        btn_calcular = findViewById(id.btnCalcular)
        btn_limpar = findViewById(id.btnLimpar)
        tvResultado = findViewById(id.tvResultado)

        btn_calcular.setOnClickListener {
            calcularArea()
        }

        btn_limpar.setOnClickListener {
            limparCampos()
        }
    }

    private fun calcularArea() {
        val largura = edit_largura.text.toString().toDoubleOrNull() ?: 0.0
        val comprimento = edit_comprimento.text.toString().toDoubleOrNull() ?: 0.0
        val area = largura * comprimento
        tvResultado.text = "Área: $area"
    }

    private fun limparCampos() {
        edit_largura.text.clear()
        edit_comprimento.text.clear()
        tvResultado.text = ""
    }
}