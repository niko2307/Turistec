package com.example.turistecdos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data class Ruta(val nombre: String, val distancia: String, val duracion: String, val descripcion: String, val tipo: String)

class Rutas_Recom : AppCompatActivity() {
    private lateinit var listaRutas: MutableList<Ruta>

    private lateinit var menuHome: ImageButton
    private lateinit var menuSearch: ImageButton
    private lateinit var menuRules: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutas_recom)

        // Obtener referencia al ListView
        val listView = findViewById<ListView>(R.id.listViewRutas)

        // Inicializar la lista de rutas directamente en el código
        inicializarRutas()

        // Crear el adaptador y asignarlo al ListView
        val adapter = RutaAdapter(this, listaRutas)
        listView.adapter = adapter

        // Configurar botones menu inferior
        setupButtonClickListeners()
    }

    private fun inicializarRutas() {
        listaRutas = mutableListOf(
            Ruta("Ruta del Lago", "5 km", "2 horas", "Recorrido por el lago con vistas a montañas.", "Fácil"),
            Ruta("Sendero de la Montaña", "10 km", "5 horas", "Ascenso a la montaña con guía de flora y fauna local.", "Intermedio"),
            Ruta("Ruta de la Cascada", "3 km", "1.5 horas", "Caminata hasta una cascada oculta en el bosque.", "Fácil"),
            Ruta("Camino del Desierto", "15 km", "6 horas", "Exploración del paisaje desértico y sus formaciones rocosas únicas.", "Difícil")
            // Puedes agregar más rutas aquí según sea necesario
        )
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
            Toast.makeText(this@Rutas_Recom, "Estas acá", Toast.LENGTH_SHORT).show()
        }

        // Escuchador de clics para el botón de normas
        menuRules.setOnClickListener {
            val intent = Intent(this, normas::class.java)
            startActivity(intent)
        }
    }
}