package com.exemplo.marcador_truco

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exemplo.marcador_truco.databinding.ActivityEditNamesBinding

class EditNamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNamesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNamesBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.etPlayer1.setText(intent.getStringExtra("NAME_P1"))
        binding.etPlayer2.setText(intent.getStringExtra("NAME_P2"))

        binding.btnConfirm.setOnClickListener {
            val intent = Intent().apply {
                putExtra("NAME_P1", binding.etPlayer1.text.toString())
                putExtra("NAME_P2", binding.etPlayer2.text.toString())
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
