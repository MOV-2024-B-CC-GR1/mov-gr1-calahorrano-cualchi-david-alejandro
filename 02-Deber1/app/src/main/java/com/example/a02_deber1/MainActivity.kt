package com.example.a02_deber1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVerAutores = findViewById<Button>(R.id.btn_ver_autores)
        val btnCrearAutor = findViewById<Button>(R.id.btn_crear_autor)
        val btnVerLibrosPorAutor = findViewById<Button>(R.id.btn_ver_libros_por_autor)
        val btnCrearLibro = findViewById<Button>(R.id.btn_crear_libro)
        val btnSalir = findViewById<Button>(R.id.btn_salir)

        btnVerAutores.setOnClickListener {
            // Acción al hacer clic en "Ver Autores"
            val intent = Intent(this, AutoresActivity::class.java)
            startActivity(intent)
        }

        btnCrearAutor.setOnClickListener {
            // Acción al hacer clic en "Crear Autor"
            val intent = Intent(this, FormularioAutorActivity::class.java)
            startActivity(intent)
        }

        btnVerLibrosPorAutor.setOnClickListener {
            // Acción al hacer clic en "Ver Libros por Autor"
            val intent = Intent(this, LibrosActivity::class.java)
            startActivity(intent)
        }

        btnCrearLibro.setOnClickListener {
            // Acción al hacer clic en "Crear Libro"
            val intent = Intent(this, FormularioLibroActivity::class.java)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            // Acción para salir de la aplicación
            finish()
        }
    }
}