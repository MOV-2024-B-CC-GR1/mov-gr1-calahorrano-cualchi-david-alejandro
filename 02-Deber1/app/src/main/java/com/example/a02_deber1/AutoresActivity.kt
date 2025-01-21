package com.example.a02_deber1

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a02_deber1.R.id.tvAuthorName

class AutoresActivity : AppCompatActivity() {
    private lateinit var authorsContainer: LinearLayout
    private lateinit var btnAddAuthor: Button
    private var authorCount = 0 // Contador para los autores dinámicos

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

        // Inicializar vistas
        authorsContainer = findViewById(R.id.authorsContainer)
        btnAddAuthor = findViewById(R.id.btnAddAuthor)

        // Agregar autores iniciales
        addAuthor("Gabriel García Márquez")
        addAuthor("Isabel Allende")

        // Configurar el botón para agregar un nuevo autor
        btnAddAuthor.setOnClickListener {
            authorCount++
            addAuthor("Nuevo Autor $authorCount")
        }
    }

    private fun addAuthor(authorName: String) {
        // Inflar el diseño del autor desde el archivo author_item.xml
        val inflater = LayoutInflater.from(this)
        val authorView = inflater.inflate(R.layout.author_item, authorsContainer, false)

        // Configurar el nombre del autor
        val tvAuthorName = authorView.findViewById<TextView>(R.id.tvAuthorName)
        tvAuthorName.text = "${authorCount}. $authorName"

        // Agregar la vista inflada al contenedor de autores
        authorsContainer.addView(authorView)
    }
}