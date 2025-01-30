package com.example.ccgr12024b_dacc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.ccgr12024b_dacc.databinding.ActivityGgoogleMapsPlaceholderBinding

class GGoogleMapsPlaceholder : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGgoogleMapsPlaceholderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGgoogleMapsPlaceholderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el SupportMapFragment y configurar el mapa cuando esté listo
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Habilitar controles de zoom
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true

        // Establecer ubicación en Quito, Ecuador
        val quito = LatLng(-0.1807, -78.4678)
        mMap.addMarker(
            MarkerOptions().position(quito)
                .title("Quito, Ecuador")
                .snippet("Capital de Ecuador")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quito, 12f)) // Zoom más cercano

        // Agregar más marcadores si se necesita
        agregarMarcadoresAdicionales()
    }

    // Método para agregar más marcadores de ejemplo
    private fun agregarMarcadoresAdicionales() {
        val guayaquil = LatLng(-2.1709, -79.9224)
        mMap.addMarker(
            MarkerOptions().position(guayaquil)
                .title("Guayaquil")
                .snippet("Puerto principal de Ecuador")
        )

        val cuenca = LatLng(-2.9006, -79.0045)
        mMap.addMarker(
            MarkerOptions().position(cuenca)
                .title("Cuenca")
                .snippet("Ciudad Patrimonio Cultural de la Humanidad")
        )
    }
}