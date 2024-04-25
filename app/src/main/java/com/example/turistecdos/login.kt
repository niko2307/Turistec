package com.example.turistecdos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class login : AppCompatActivity() {
    private lateinit var usuarios: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Encontrar vistas
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)
        loadUsuariosFromJson()
            btnLogin.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()

                if (username.isNotEmpty() && password.isNotEmpty()) {
                    // Verificar credenciales
                    if (this::usuarios.isInitialized && validateCredentials(username, password)) {
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Home::class.java)
                        startActivity(intent)
                    } else {
                        // Credenciales inválidas, mostrar mensaje de error
                        Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Campos vacíos, mostrar mensaje para completar los campos
                    Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            }

        tvRegister.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }
    }


    private fun loadUsuariosFromJson() {
        try {
            val inputStream: InputStream = assets.open("usuario.JSON")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)

            // Convertir el JSON en un objeto JSONObject
            val jsonObject = JSONObject(json)

            // Obtener el array "usuarios" del objeto JSONObject
            usuarios = jsonObject.getJSONArray("usuarios")
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al cargar usuarios", Toast.LENGTH_SHORT).show()
        } catch (e: JSONException) {
            e.printStackTrace()
            Toast.makeText(this, "Error al parsear JSON", Toast.LENGTH_SHORT).show()
        }
    }


    private fun validateCredentials(username: String, password: String): Boolean {
        for (i in 0 until usuarios.length()) {
            val usuario = usuarios.getJSONObject(i)
            val usuarioNombre = usuario.getString("nombre_usuario")
            val usuarioContrasena = usuario.getString("contraseña")
            if (username == usuarioNombre && password == usuarioContrasena) {
                return true
            }
        }
        return false
    }
}