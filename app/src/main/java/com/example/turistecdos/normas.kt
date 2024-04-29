package com.example.turistecdos

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

data class Norma(val titulo: String, val descripcion: String)
class normas : AppCompatActivity() {
    private lateinit var menuHome: ImageButton
    private lateinit var menuSearch: ImageButton
    private lateinit var menuRules: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normas)

        val listaNormas = listOf(
            Norma("Respetar la flora y fauna", "Evitar dañar plantas, animales y ecosistemas naturales. No recolectar especies ni interferir con su hábitat."),
            Norma("No dejar basura", "Mantener limpio el entorno y utilizar los contenedores de basura adecuados. Reciclar cuando sea posible."),
            Norma("Seguir senderos designados", "No salirse de los caminos establecidos para evitar dañar la vegetación y protegerse de posibles peligros."),
            Norma("No alimentar a los animales", "Evitar dar comida a los animales salvajes, ya que puede alterar su comportamiento natural y crear dependencia."),
            Norma("Respetar las señales e indicaciones", "Seguir las instrucciones del personal de la estación y respetar las áreas restringidas o cerradas al público."),
            Norma("No hacer ruido excesivo", "Mantener la tranquilidad del entorno natural y respetar la paz de los demás visitantes y la vida silvestre."),
            Norma("No recolectar o perturbar objetos naturales", "Dejar intactos los elementos naturales como piedras, conchas, y otros objetos para mantener el equilibrio del ecosistema."),
            Norma("Respetar los horarios y normativas de la estación", "Cumplir con los horarios de apertura y cierre, así como con las normas establecidas para la visita."),
            Norma("Ser responsable con el uso del agua y la energía", "Utilizar estos recursos de manera responsable y contribuir al cuidado del medio ambiente."),
            Norma("Informarse y aprender", "Aprovechar la visita para conocer y aprender sobre la flora, fauna y características únicas de la estación biológica.")
        )
        var listView = findViewById<ListView>(R.id.normas)
        val adapter = NormaAdapter(this, listaNormas)
        listView.adapter = adapter

        val editTextFilterNormas = findViewById<EditText>(R.id.BuscarNormas)
        editTextFilterNormas.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filtrar los datos cuando el texto cambia
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        setupButtonClickListeners()
    }
    private fun setupButtonClickListeners() {
        menuHome = findViewById<ImageButton>(R.id.menu_home)
        menuSearch = findViewById<ImageButton>(R.id.menu_search)
        menuRules = findViewById<ImageButton>(R.id.menu_rules)

        // Escuchador de clics para los botones del menú
        menuHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        menuSearch.setOnClickListener {
            val intent = Intent(this, Rutas_Recom::class.java)
            startActivity(intent)
        }
        menuRules.setOnClickListener {
            Toast.makeText(this, "Estas acá", Toast.LENGTH_SHORT).show()
        }
    }
}