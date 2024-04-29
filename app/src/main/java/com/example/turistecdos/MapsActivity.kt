package com.example.turistecdos

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import android.Manifest
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.turistecdos.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: LatLng? = null
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Obtener la ubicación actual del usuario
        if (checkLocationPermission()) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation!!, 15f))
                        addMarker(currentLocation!!)
                    }
                }
        } else {
            requestLocationPermission()
            if (checkLocationPermission()) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        if (location != null) {
                            currentLocation = LatLng(location.latitude, location.longitude)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation!!, 15f))
                            addMarker(currentLocation!!)
                        }
                    }
            }
        }

        mMap.setOnMapClickListener { latLng ->
            addMarker(latLng)
            calculateDistance(latLng)
        }

    }


    private fun addMarker(latLng: LatLng) {
        currentMarker = mMap.addMarker(MarkerOptions().position(latLng).title("Nueva Ubicación"))
    }

    private fun calculateDistance(destination: LatLng) {
        currentLocation?.let { currentLatLng ->
            val distance = calculateDistance(currentLatLng, destination)
            val formattedDistance = String.format("%.2f", distance / 1000) // Convertir a kilómetros y redondear a dos decimales
            Toast.makeText(this, "Distancia: $formattedDistance km", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calculateDistance(start: LatLng, end: LatLng): Float {
        val results = FloatArray(1)
        android.location.Location.distanceBetween(
            start.latitude, start.longitude,
            end.latitude, end.longitude,
            results
        )
        return results[0]
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }
}