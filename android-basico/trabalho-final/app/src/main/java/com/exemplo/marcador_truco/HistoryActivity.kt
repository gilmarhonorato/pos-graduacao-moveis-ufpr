package com.exemplo.marcador_truco

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exemplo.marcador_truco.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameP1 = intent.getStringExtra("NAME_P1") ?: ""
        val nameP2 = intent.getStringExtra("NAME_P2") ?: ""
        val winsP1 = intent.getIntExtra("WINS_P1", 0)
        val winsP2 = intent.getIntExtra("WINS_P2", 0)

        binding.tvHistoryP1.text = "$nameP1 $winsP1"
        binding.tvHistoryP2.text = "$nameP2 $winsP2"
    }
}
