package com.example.a02_deber1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a02_deber1.R.id.tvAuthorName
import com.example.a02_deber1.database.AutorDAO
import com.example.a02_deber1.models.Autor

class AutoresActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_autores)

        // Ajustar padding para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Acción del botón "+ Agregar Autor"
        val btnAddAuthor = findViewById<Button>(R.id.btnAddAuthor)
        btnAddAuthor.setOnClickListener {
            addAuthor()
        }

        // Configurar el botón "Regresar"
        val btnBack = findViewById<Button>(R.id.btnBackAuthor)
        btnBack.setOnClickListener {
            finish() // Finaliza la actividad actual y regresa a la anterior
        }
    }

    private fun addAuthor() {
        // Inflar la vista desde el XML de author_item
        val authorsContainer = findViewById<LinearLayout>(R.id.authorsContainer)
        val authorView: View = LayoutInflater.from(this).inflate(R.layout.author_item, authorsContainer, false)

        // Actualizar los datos del autor
        val tvAuthorName = authorView.findViewById<TextView>(R.id.tvAuthorName)
        tvAuthorName.text = "Autor ${authorsContainer.childCount + 1}"

        val tvAuthorDetails = authorView.findViewById<TextView>(R.id.tvAuthorDetails)
        tvAuthorDetails.text = "Nacionalidad: Desconocida / Fecha Nac: -- / Sigue Vivo: Sí"

        // Agregar la vista inflada al contenedor
        authorsContainer.addView(authorView)

        // Ocultar el mensaje vacío si es necesario
        if (authorsContainer.childCount > 0) {
            val tvEmptyMessage = findViewById<TextView>(R.id.tvEmptyMessage)
            tvEmptyMessage.visibility = View.GONE
        }

        // Mostrar un mensaje de retroalimentación
        Toast.makeText(this, "Autor agregado: ${tvAuthorName.text}", Toast.LENGTH_SHORT).show()
    }
}