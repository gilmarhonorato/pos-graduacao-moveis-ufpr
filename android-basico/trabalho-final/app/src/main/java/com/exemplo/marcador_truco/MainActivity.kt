package com.exemplo.marcador_truco

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.exemplo.marcador_truco.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var scoreP1 = 0
    private var scoreP2 = 0
    private var winsP1 = 0
    private var winsP2 = 0

    private val editNamesLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val name1 = data?.getStringExtra("NAME_P1") ?: ""
            val name2 = data?.getStringExtra("NAME_P2") ?: ""
            
            if (name1.isNotEmpty()) binding.tvPlayer1Name.text = if (name1.endsWith(":")) name1 else "$name1:"
            if (name2.isNotEmpty()) binding.tvPlayer2Name.text = if (name2.endsWith(":")) name2 else "$name2:"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState != null) {
            scoreP1 = savedInstanceState.getInt("SCORE_P1")
            scoreP2 = savedInstanceState.getInt("SCORE_P2")
            winsP1 = savedInstanceState.getInt("WINS_P1")
            winsP2 = savedInstanceState.getInt("WINS_P2")
            binding.tvPlayer1Name.text = savedInstanceState.getString("NAME_P1")
            binding.tvPlayer2Name.text = savedInstanceState.getString("NAME_P2")
            binding.tvPlayer1Score.text = scoreP1.toString()
            binding.tvPlayer2Score.text = scoreP2.toString()
        }

        setupListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("SCORE_P1", scoreP1)
        outState.putInt("SCORE_P2", scoreP2)
        outState.putInt("WINS_P1", winsP1)
        outState.putInt("WINS_P2", winsP2)
        outState.putString("NAME_P1", binding.tvPlayer1Name.text.toString())
        outState.putString("NAME_P2", binding.tvPlayer2Name.text.toString())
    }

    private fun setupListeners() {
        // Botões Jogador 1
        binding.btnP1Add1.setOnClickListener { updateScore(1, 1) }
        binding.btnP1Add3.setOnClickListener { updateScore(1, 3) }
        binding.btnP1Add6.setOnClickListener { updateScore(1, 6) }
        binding.btnP1Add9.setOnClickListener { updateScore(1, 9) }
        binding.btnP1Add12.setOnClickListener { updateScore(1, 12) }

        // Botões Jogador 2
        binding.btnP2Add1.setOnClickListener { updateScore(2, 1) }
        binding.btnP2Add3.setOnClickListener { updateScore(2, 3) }
        binding.btnP2Add6.setOnClickListener { updateScore(2, 6) }
        binding.btnP2Add9.setOnClickListener { updateScore(2, 9) }
        binding.btnP2Add12.setOnClickListener { updateScore(2, 12) }

        // Botão Histórico
        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("NAME_P1", binding.tvPlayer1Name.text.toString())
                putExtra("NAME_P2", binding.tvPlayer2Name.text.toString())
                putExtra("WINS_P1", winsP1)
                putExtra("WINS_P2", winsP2)
            }
            startActivity(intent)
        }

        // Botão Zerar Histórico
        binding.btnReset.setOnClickListener {
            resetAll()
        }

        // Botão Informar Nomes
        binding.btnEditNames.setOnClickListener {
            val intent = Intent(this, EditNamesActivity::class.java).apply {
                putExtra("NAME_P1", binding.tvPlayer1Name.text.toString().replace(":", "").trim())
                putExtra("NAME_P2", binding.tvPlayer2Name.text.toString().replace(":", "").trim())
            }
            editNamesLauncher.launch(intent)
        }
    }

    private fun updateScore(player: Int, points: Int) {
        if (player == 1) {
            scoreP1 += points
            binding.tvPlayer1Score.text = scoreP1.toString()
            if (scoreP1 >= 12) {
                showWinner(binding.tvPlayer1Name.text.toString(), 1)
            }
        } else {
            scoreP2 += points
            binding.tvPlayer2Score.text = scoreP2.toString()
            if (scoreP2 >= 12) {
                showWinner(binding.tvPlayer2Name.text.toString(), 2)
            }
        }
    }

    private fun showWinner(playerName: String, playerNumber: Int) {
        if (playerNumber == 1) winsP1++ else winsP2++

        val cleanName = playerName.replace(":", "").trim()

        AlertDialog.Builder(this)
            .setTitle(R.string.win_title)
            .setMessage(getString(R.string.win_message, cleanName))
            .setPositiveButton(R.string.ok) { _, _ ->
                resetScores()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetScores() {
        scoreP1 = 0
        scoreP2 = 0
        binding.tvPlayer1Score.text = scoreP1.toString()
        binding.tvPlayer2Score.text = scoreP2.toString()
    }

    private fun resetAll() {
        scoreP1 = 0
        scoreP2 = 0
        winsP1 = 0
        winsP2 = 0
        binding.tvPlayer1Score.text = scoreP1.toString()
        binding.tvPlayer2Score.text = scoreP2.toString()
        
        val snackbar = Snackbar.make(binding.main, R.string.reset_toast, Snackbar.LENGTH_SHORT)
        val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cards, 0, 0, 0)
        textView.setCompoundDrawableTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.WHITE))
        textView.compoundDrawablePadding = 24
        snackbar.show()
    }
}
