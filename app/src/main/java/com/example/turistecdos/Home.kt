package com.example.turistecdos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Home : AppCompatActivity() {

    private lateinit var menuHome: ImageButton
    private lateinit var menuSearch: ImageButton
    private lateinit var menuRules: ImageButton

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
            val intent2 = Intent(this, Rut_inter::class.java)
            startActivity(intent2)
        }

        val btnLogout = findViewById<ImageButton>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            val intent3 = Intent(this, login::class.java)
            startActivity(intent3)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar botones menu inferior
        setupButtonClickListeners()
    }

    private fun setupButtonClickListeners() {
        menuHome = findViewById(R.id.menu_home)
        menuSearch = findViewById(R.id.menu_search)
        menuRules = findViewById(R.id.menu_rules)

        // Escuchador de clics para el botón de inicio
        menuHome.setOnClickListener {
            Toast.makeText(this@Home, "Estas acá", Toast.LENGTH_SHORT).show()
        }

        // Escuchador de clics para el botón de búsqueda
        menuSearch.setOnClickListener {
            val intent = Intent(this, Rutas_Recom::class.java)
            startActivity(intent)
        }

        // Escuchador de clics para el botón de normas
        menuRules.setOnClickListener {
            val intent = Intent(this, normas::class.java)
            startActivity(intent)
        }
    }
}