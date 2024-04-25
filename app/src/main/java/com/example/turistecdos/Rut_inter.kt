package com.example.turistecdos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Rut_inter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rut_inter)

        val btnActual = findViewById<ImageButton>(R.id.rut_actual)
        btnActual.setOnClickListener {
            // Iniciar la actividad de mapa
           val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        val btnRecomendaciones = findViewById<ImageButton>(R.id.rut_recom)
        btnRecomendaciones.setOnClickListener {
            // Iniciar rutas recomendadas
            val intent2 = Intent(this, Rutas_Recom::class.java)
            startActivity(intent2)
        }
    }

}