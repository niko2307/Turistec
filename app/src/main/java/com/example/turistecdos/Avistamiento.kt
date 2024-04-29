package com.example.turistecdos

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.res.Resources
import android.location.LocationManager
import android.net.Uri
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar


class Avistamiento : AppCompatActivity() {

    private lateinit var spinnerEspecie: Spinner
    private lateinit var etPlaceDescription: EditText
    private lateinit var etNombreComun: EditText
    private lateinit var etNombreCientifico: EditText
    private lateinit var etcantidad: EditText
    private lateinit var editTextDate: EditText
    private lateinit var editTextTime: EditText
    private lateinit var btnReportar: Button
    private lateinit var btnTomarFoto:ImageButton
    private lateinit var btnimagenes_real: Button

    private lateinit var locationManager: LocationManager

    private lateinit var menuHome: ImageButton
    private lateinit var menuSearch: ImageButton
    private lateinit var menuRules: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_avistamiento)
        spinnerEspecie = findViewById(R.id.spinnerEspecie)
        etPlaceDescription = findViewById(R.id.etPlaceDescription)
        etNombreComun = findViewById(R.id.etNombreComun)
        etNombreCientifico = findViewById(R.id.etNombreCientifico)
        etcantidad = findViewById(R.id.etcantidad)
        editTextDate = findViewById(R.id.editTextDate)
        editTextTime = findViewById(R.id.editTextTime)
        btnReportar = findViewById(R.id.btnReportar)
        btnTomarFoto = findViewById(R.id.addImages)
        btnimagenes_real = findViewById(R.id.btnImagenes)

        btnTomarFoto.setOnClickListener {
            if (checkPermissions()) {
                dispatchTakePictureIntent()
            } else {
                requestPermissions()
            }
        }
        btnReportar.setOnClickListener {
            if (validateFields()) {
                // Todos los campos están llenos, realizar la acción de reportar
                // Aquí podrías implementar la lógica para enviar los datos al servidor, por ejemplo
                // Puedes acceder a los datos de los campos usando las variables que definiste anteriormente
                // y la foto usando la variable photoBitmap
                // Por ahora, mostraremos un mensaje de toast para demostración
                Toast.makeText(this, "¡Reporte enviado!", Toast.LENGTH_SHORT).show()
            } else {
                // Mostrar un mensaje indicando que todos los campos son obligatorios
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
        btnimagenes_real.setOnClickListener {
            val intent = Intent(this, imagenes_real::class.java)
            startActivity(intent)
        }

        editTextDate.setOnClickListener {
            showDatePickerDialog()
        }

        editTextTime.setOnClickListener {
            showTimePickerDialog()
        }

        // Configurar botones menu inferior
        setupButtonClickListeners()

    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    // Método para solicitar permisos
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
    }

    // Método para iniciar la actividad de la cámara y capturar una foto
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap? // Obtener la imagen capturada
            if (imageBitmap != null) {
                val imageView = createImageView()
                imageView.setImageBitmap(imageBitmap) // Configurar la imagen capturada en la ImageView
                addImageViewToGridLayout(imageView)
            } else {
                Toast.makeText(this, "¡No se pudo tomar la foto!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // Funciones para crear un grid dinamico
    private fun createImageView(): ImageView {
        val imageView = ImageView(this)
        imageView.layoutParams = GridLayout.LayoutParams().apply {
            width = 0
            height = 120.dpToPx() // altura deseada
            columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f) // distribuir uniformemente en columnas
            rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1) // insertar en la siguiente fila si es necesario
            setMargins(8.dpToPx(), 0, 8.dpToPx(), 0) // márgenes
        }
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        imageView.adjustViewBounds = true
        return imageView
    }

    private fun addImageViewToGridLayout(imageView: ImageView) {
        val preview = findViewById<GridLayout>(R.id.imagesGrid)

        // Agregar la nueva ImageView al GridLayout
        preview.addView(imageView)
    }

    fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    // Método para validar que todos los campos estén llenos
    private fun validateFields(): Boolean {
        return spinnerEspecie.selectedItemPosition != 0 &&
                etPlaceDescription.text.isNotEmpty() &&
                etNombreComun.text.isNotEmpty() &&
                etNombreCientifico.text.isNotEmpty() &&
                etcantidad.text.isNotEmpty() &&
                editTextDate.text.isNotEmpty() &&
                editTextTime.text.isNotEmpty()

    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_CAMERA_PERMISSION = 101
    }
    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Aquí puedes manejar la fecha seleccionada
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                editTextDate.setText(selectedDate)
            },
            // Establecer la fecha predeterminada al valor actual
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                // Aquí puedes manejar la hora seleccionada
                val selectedTime = "$hourOfDay:$minute"
                editTextTime.setText(selectedTime)
            },
            // Establecer la hora predeterminada al valor actual
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            true // Establecer a true si deseas usar el formato de 24 horas
        )
        timePicker.show()
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

