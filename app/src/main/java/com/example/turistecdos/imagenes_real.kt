package com.example.turistecdos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class imagenes_real : AppCompatActivity() {

    private lateinit var menuHome: ImageButton
    private lateinit var menuSearch: ImageButton
    private lateinit var menuRules: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagenes_real)

        // Configurar botones menu inferior
        setupButtonClickListeners()
    }

    private fun setupButtonClickListeners() {
        menuHome = findViewById(R.id.menu_home)
        menuSearch = findViewById(R.id.menu_search)
        menuRules = findViewById(R.id.menu_rules)

        // Escuchador de clics para el botón de inicio
        menuHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
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