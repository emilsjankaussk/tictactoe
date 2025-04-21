package com.example.tictactoee

class GameModel(private val isSinglePlayer: Boolean = false) {

    val board = MutableList(9) { "" }
    var currentPlayer = "X"
    var winner = ""

    private val winPositions = arrayOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )

    fun togglePlayer() {
        currentPlayer = if (currentPlayer == "X") "O" else "X"
    }

    fun checkWinOrDraw(): Boolean {
        winPositions.forEach { positions ->
            val (a, b, c) = positions
            if (board[a].isNotEmpty() && board[a] == board[b] && board[b] == board[c]) {
                winner = board[a]
                return true
            }
        }

        if (board.all { it.isNotEmpty() }) {
            winner = "DRAW"
            return true
        }

        return false
    }

    fun isGameOver(): Boolean = winner.isNotEmpty()

    fun isDraw(): Boolean = winner == "DRAW"

    fun makeComputerMove() {
        if (isGameOver()) return

        val emptyCells = board.indices.filter { board[it].isEmpty() }
        if (emptyCells.isNotEmpty()) {
            board[emptyCells.random()] = currentPlayer
            checkWinOrDraw()
            togglePlayer()
        }
    }

    fun reset() {
        board.indices.forEach { board[it] = "" }
        winner = ""
        currentPlayer = "X"
    }
}