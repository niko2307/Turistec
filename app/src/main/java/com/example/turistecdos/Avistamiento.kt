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
import android.view.View
import android.widget.ImageView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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

        editTextDate.setOnClickListener {
            showDatePickerDialog()
        }

        editTextTime.setOnClickListener {
            showTimePickerDialog()
        }


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
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val imageViewCaptured: ImageView = findViewById(R.id.imageViewCaptured)
            imageViewCaptured.setImageBitmap(imageBitmap)
            imageViewCaptured.visibility = View.VISIBLE


        }
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


}

