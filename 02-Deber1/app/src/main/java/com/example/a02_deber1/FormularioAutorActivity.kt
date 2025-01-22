package com.example.a02_deber1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FormularioAutorActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario_autor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar botones
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        // Acción del boton "Guardar"
        btnGuardar.setOnClickListener {
            // Lógica para guardar los datos del autor
            Toast.makeText(this, "Guardando datos (simulado)", Toast.LENGTH_SHORT).show()
        }

        // Acción del boton "Cancelar"
        btnCancelar.setOnClickListener {
            // Lógica para cancelar la acción
            Toast.makeText(this, "Cancelando operación", Toast.LENGTH_SHORT).show()
            finish() // Finaliza la actividad
        }

    }
}