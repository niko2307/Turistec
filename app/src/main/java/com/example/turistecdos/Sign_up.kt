package com.example.turistecdos

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Intents
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener

class Sign_up : AppCompatActivity() {

    private lateinit var btnRegistro: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        btnRegistro = findViewById(R.id.btnregistro)

// Añadir escuchador para cambios en los campos de entrada
        val etNombreCompleto = findViewById<EditText>(R.id.etnombreCompleto)
        val etUsuario = findViewById<EditText>(R.id.etusuario)
        val etContrasena = findViewById<EditText>(R.id.etcontrasena)

        val etConfirmarContrasena = findViewById<EditText>(R.id.etconfirmarContrasena)
        val etCorreo = findViewById<EditText>(R.id.etcorreo)

        etNombreCompleto.addTextChangedListener { validarCampos() }
        etUsuario.addTextChangedListener { validarCampos() }
        etContrasena.addTextChangedListener { validarCampos() }
        etConfirmarContrasena.addTextChangedListener { validarCampos() }
        etCorreo.addTextChangedListener { validarCampos() }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Añadir escuchador para el botón de registro
        btnRegistro.setOnClickListener {
            if (validarCampos()) {
                // Aquí agregar la lógica para enviar el formulario de registro
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validarCampos(): Boolean {
        val etNombreCompleto = findViewById<EditText>(R.id.etnombreCompleto)
        val etUsuario = findViewById<EditText>(R.id.etusuario)
        val etContrasena = findViewById<EditText>(R.id.etcontrasena)
        val etConfirmarContrasena = findViewById<EditText>(R.id.etconfirmarContrasena)
        val etCorreo = findViewById<EditText>(R.id.etcorreo)

        val nombreCompleto = etNombreCompleto.text.toString()
        val usuario = etUsuario.text.toString()
        val contrasena = etContrasena.text.toString()
        val confirmarContrasena = etConfirmarContrasena.text.toString()
        val correo = etCorreo.text.toString()

        val camposCompletos = nombreCompleto.isNotBlank() &&
                usuario.isNotBlank() &&
                contrasena.isNotBlank() &&
                confirmarContrasena.isNotBlank() &&
                correo.isNotBlank()

        val correoValido = android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()

        btnRegistro.isEnabled = camposCompletos && correoValido && contrasena == confirmarContrasena

        return btnRegistro.isEnabled
    }




}
