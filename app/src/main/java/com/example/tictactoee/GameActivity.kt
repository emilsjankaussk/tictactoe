package com.example.tictactoee

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout

class GameActivity : AppCompatActivity() {

    private lateinit var player1Name: String
    private lateinit var player2Name: String
    private var isSinglePlayer: Boolean = false

    private lateinit var statusTextView: TextView
    private lateinit var playerNamesTextView: TextView
    private lateinit var gridLayout: GridLayout
    private lateinit var restartButton: Button

    private lateinit var gameModel: GameModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        player1Name = intent.getStringExtra("PLAYER1_NAME") ?: "Player 1"
        player2Name = intent.getStringExtra("PLAYER2_NAME") ?: "Player 2"
        isSinglePlayer = intent.getBooleanExtra("IS_SINGLE_PLAYER", false)

        statusTextView = findViewById(R.id.statusTextView)
        playerNamesTextView = findViewById(R.id.playerNamesTextView)
        gridLayout = findViewById(R.id.gridLayout)
        restartButton = findViewById(R.id.restartButton)

        playerNamesTextView.text = "$player1Name vs $player2Name"

        gameModel = GameModel(isSinglePlayer)

        setupBoard()

        restartButton.setOnClickListener {
            gameModel.reset()
            setupBoard()
            updateUI()
        }

        updateUI()
    }

    private fun setupBoard() {
        gridLayout.removeAllViews()
        gridLayout.rowCount = 3
        gridLayout.columnCount = 3

        for (i in 0..8) {
            val button = Button(this)
            button.textSize = 32f
            button.layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = 0
                columnSpec = GridLayout.spec(i % 3, 1f)
                rowSpec = GridLayout.spec(i / 3, 1f)
                setMargins(8, 8, 8, 8)
            }

            button.setOnClickListener {
                if (gameModel.board[i].isEmpty() && !gameModel.isGameOver()) {
                    gameModel.board[i] = gameModel.currentPlayer
                    if (!gameModel.checkWinOrDraw()) {
                        gameModel.togglePlayer()
                        if (isSinglePlayer && gameModel.currentPlayer == "O") {
                            gameModel.makeComputerMove()
                        }
                    }
                    updateUI()
                }
            }

            gridLayout.addView(button)
        }
    }

    private fun updateUI() {
        for (i in 0 until 9) {
            val button = gridLayout.getChildAt(i) as Button
            button.text = gameModel.board[i]
        }

        if (gameModel.isGameOver()) {
            val message = when {
                gameModel.isDraw() -> getString(R.string.game_draw)
                else -> getString(R.string.game_winner, if (gameModel.winner == "X") player1Name else player2Name)
            }
            statusTextView.text = message
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            val currentPlayerName = if (gameModel.currentPlayer == "X") player1Name else player2Name
            statusTextView.text = getString(R.string.game_current_turn, currentPlayerName, gameModel.currentPlayer)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_go_back -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
