package com.example.tictactoee

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnPlayerVsPlayer).setOnClickListener {
            startActivity(Intent(this, PlayerNamesActivity::class.java).apply {
                putExtra("IS_SINGLE_PLAYER", false)
            })
        }

        findViewById<Button>(R.id.btnPlayerVsComputer).setOnClickListener {
            startActivity(Intent(this, PlayerNamesActivity::class.java).apply {
                putExtra("IS_SINGLE_PLAYER", true)
            })
        }
    }
}