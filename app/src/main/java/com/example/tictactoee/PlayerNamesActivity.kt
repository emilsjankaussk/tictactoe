package com.example.tictactoee

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PlayerNamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_names)

        val isSinglePlayer = intent.getBooleanExtra("IS_SINGLE_PLAYER", false)

        val player1EditText = findViewById<EditText>(R.id.editTextPlayer1)
        val player2EditText = findViewById<EditText>(R.id.editTextPlayer2)
        val player2Label = findViewById<TextView>(R.id.textViewPlayer2Label)

        if (isSinglePlayer) {
            player2EditText.setText(getString(R.string.computer_name))
            player2EditText.isEnabled = false
        }

        findViewById<Button>(R.id.btnStartGame).setOnClickListener {
            val player1Name = player1EditText.text.toString().takeIf { it.isNotBlank() } ?: "Player 1"
            val player2Name = player2EditText.text.toString().takeIf { it.isNotBlank() } ?: if (isSinglePlayer) getString(R.string.computer_name) else "Player 2"

            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("PLAYER1_NAME", player1Name)
                putExtra("PLAYER2_NAME", player2Name)
                putExtra("IS_SINGLE_PLAYER", isSinglePlayer)
            }
            startActivity(intent)
            finish()
        }
    }
}

