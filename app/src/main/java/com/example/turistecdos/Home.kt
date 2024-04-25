package com.example.turistecdos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val btnReportar = findViewById<Button>(R.id.btnrepo)
        btnReportar.setOnClickListener {
            // Iniciar la actividad Avistamiento
            val intent = Intent(this, Avistamiento::class.java)
            startActivity(intent)
        }

        val btnmapa = findViewById<Button>(R.id.btnRuta)
        btnmapa.setOnClickListener {
            val intent2 = Intent(this, MapsActivity::class.java)
            startActivity(intent2)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}