package com.example.ccgr12024b_dacc

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class GGoogleMaps : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var permisos = false
    private val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ggoogle_maps)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        solicitarPermisos()

        // Inicializar el mapa con un pequeño retraso para evitar bloqueos
        Handler(Looper.getMainLooper()).postDelayed({
            iniciarGoogleMaps()
        }, 500)

        val botonCarolina = findViewById<Button>(R.id.btn_ir_carolina)
        botonCarolina.setOnClickListener {
            val carolina = LatLng(-0.18221288005854652, -78.48553955554578)
            moverCamaraConZoom(carolina)
        }

        findViewById<FloatingActionButton>(R.id.btn_mi_ubicacion).setOnClickListener {
            if (tengoPermisos()) {
                habilitarUbicacionUsuario()
            } else {
                solicitarPermisos()
            }
        }

        // Inicializar LocationCallback correctamente
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    val ubicacionActual = LatLng(location.latitude, location.longitude)
                    moverCamaraConZoom(ubicacionActual)
                    mapa.addMarker(MarkerOptions().position(ubicacionActual).title("Mi Ubicación"))
                }
            }
        }
    }

    private fun tengoPermisos(): Boolean {
        val contexto = applicationContext
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        permisos = permisoFine == PackageManager.PERMISSION_GRANTED && permisoCoarse == PackageManager.PERMISSION_GRANTED
        return permisos
    }

    private fun solicitarPermisos() {
        if (!tengoPermisos()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }

    private fun iniciarGoogleMaps() {
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        fragmentoMapa.getMapAsync { googleMap ->
            Handler(Looper.getMainLooper()).post {
                mapa = googleMap
                establecerConfiguracionMapa()
                habilitarUbicacionUsuario()
                moverQuicentro()
                anadirPolilinea()
                anadirPoligono()
            }
        }
    }

    fun moverQuicentro(){
        val quicentro = LatLng(-0.17584202368791677, -78.4802112850314)
        val titulo = "Quicentro"
        val marcadorQuicentro = anadirMarcador(quicentro, titulo)
        marcadorQuicentro.tag = titulo
        moverCamaraConZoom(quicentro)
    }

    fun anadirPolilinea(){
        with(mapa){
            val polilinea = mapa.addPolyline( PolylineOptions().clickable(true)
                .add(
                    LatLng(-0.17791267925471754, -78.48185816127831),
                    LatLng(-0.18019791013168018, -78.48539867691878),
                    LatLng(-0.1822149211841061, -78.48320999452285)
                )
            )
            polilinea.tag = "polilinea-uno"
        }
    }

    fun anadirPoligono(){
        with(mapa){
            val poligono = mapa.addPolygon(
                PolygonOptions()
                    .clickable(true)
                    .add(
                        LatLng(-0.17810579736798682, -78.48025956482248),
                        LatLng(-0.18047685848208925, -78.47937980033),
                        LatLng(-0.17664668268438008, -78.4796694788824),
                    )
            )
            poligono.tag = "poligono-uno"
        }
    }

    fun anadirMarcador(latLang: LatLng, title: String): Marker {
        return mapa.addMarker(MarkerOptions().position(latLang).title(title))!!
    }

    @SuppressLint("MissingPermission")
    private fun establecerConfiguracionMapa() {
        with(mapa) {
            if (tengoPermisos()) {
                isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isCompassEnabled = true
            uiSettings.isMapToolbarEnabled = true
        }
    }

    @SuppressLint("MissingPermission")
    private fun habilitarUbicacionUsuario() {
        if (!tengoPermisos()) {
            mostrarSnackbar("Se necesitan permisos de ubicación.")
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val miUbicacion = LatLng(location.latitude, location.longitude)
                moverCamaraConZoom(miUbicacion)
                mapa.addMarker(MarkerOptions().position(miUbicacion).title("Mi Ubicación"))
            } else {
                solicitarActualizacionUbicacion()
            }
        }.addOnFailureListener {
            mostrarSnackbar("Error al obtener ubicación.")
        }
    }

    @SuppressLint("MissingPermission")
    private fun solicitarActualizacionUbicacion() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
            .setMinUpdateIntervalMillis(2000)
            .build()

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)
    }

    private fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 17f) {
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar.make(findViewById(R.id.main), texto, Snackbar.LENGTH_SHORT).show()
    }
}