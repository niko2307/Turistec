package com.example.turistecdos

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data class Ruta(val nombre: String, val distancia: String, val duracion: String, val descripcion: String, val tipo: String)

class Rutas_Recom : AppCompatActivity() {
    private lateinit var listaRutas: MutableList<Ruta>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rutas_recom)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener referencia al ListView
        val listView = findViewById<ListView>(R.id.listViewRutas)

        // Inicializar la lista de rutas directamente en el código
        inicializarRutas()

        // Crear el adaptador y asignarlo al ListView
        val adapter = RutaAdapter(this, listaRutas)
        listView.adapter = adapter
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
}