package com.example.a02_deber1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a02_deber1.R.id.tvAuthorName
import com.example.a02_deber1.database.AutorDAO
import com.example.a02_deber1.models.Autor

class AutoresActivity : AppCompatActivity() {
    private lateinit var authorsContainer: LinearLayout
    private lateinit var btnAddAuthor: Button
    private val autorDAO by lazy { AutorDAO(this) }

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

        // Cargar autores desde SQLite
        loadAuthors()

        // Configurar botón para agregar autores
        btnAddAuthor.setOnClickListener {
            val intent = Intent(this, FormularioAutorActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadAuthors() {
        // Limpiar el contenedor antes de recargar
        authorsContainer.removeAllViews()

        val autores = autorDAO.obtenerTodosLosAutores()
        if (autores.isEmpty()) {
            Toast.makeText(this, "No hay autores registrados.", Toast.LENGTH_SHORT).show()
        } else {
            autores.forEach { autor ->
                addAuthorView(autor)
            }
        }
    }

    private fun addAuthorView(autor: Autor) {
        // Inflar el diseño de cada autor
        val inflater = LayoutInflater.from(this)
        val authorView = inflater.inflate(R.layout.author_item, authorsContainer, false)

        // Configurar los datos del autor
        val tvAuthorName = authorView.findViewById<TextView>(R.id.tvAuthorName)
        val tvAuthorDetails = authorView.findViewById<TextView>(R.id.tvAuthorDetails)
        val btnEdit = authorView.findViewById<Button>(R.id.btnEdit)
        val btnDelete = authorView.findViewById<Button>(R.id.btnDelete)
        val btnBooks = authorView.findViewById<Button>(R.id.btnBooks)

        // Configurar nombre y detalles del autor
        tvAuthorName.text = "${autor.nombre} ${autor.apellido}"
        tvAuthorDetails.text = "Nacionalidad: ${autor.nacionalidad} / Fecha Nac: ${autor.fechaNacimiento} / Sigue Vivo: ${if (autor.sigueVivo) "Sí" else "No"}"

        // Botón Editar
        btnEdit.setOnClickListener {
            val intent = Intent(this, FormularioAutorActivity::class.java)
            intent.putExtra("idAutor", autor.idAutor)
            intent.putExtra("nombre", autor.nombre)
            intent.putExtra("apellido", autor.apellido)
            intent.putExtra("nacionalidad", autor.nacionalidad)
            intent.putExtra("fechaNacimiento", autor.fechaNacimiento)
            intent.putExtra("sigueVivo", autor.sigueVivo)
            startActivity(intent)
        }

        // Botón Eliminar
        btnDelete.setOnClickListener {
            // Crear y mostrar un diálogo de confirmación
            val dialog = AlertDialog.Builder(this)
                .setTitle("Eliminar Autor")
                .setMessage("¿Estás seguro de que deseas eliminar a ${autor.nombre} ${autor.apellido}? Esta acción no se puede deshacer.")
                .setPositiveButton("Sí") { _, _ ->
                    // Eliminar el autor si el usuario confirma
                    autorDAO.borrarAutorPorId(autor.idAutor)
                    Toast.makeText(this, "Autor eliminado.", Toast.LENGTH_SHORT).show()
                    loadAuthors()
                }
                .setNegativeButton("No") { dialog, _ ->
                    // Cerrar el diálogo si el usuario cancela
                    dialog.dismiss()
                }
                .create()

            dialog.show()
        }

        // Botón Libros
        btnBooks.setOnClickListener {
            val intent = Intent(this, LibrosActivity::class.java)
            intent.putExtra("idAutor", autor.idAutor)
            startActivity(intent)
        }

        // Agregar la vista inflada al contenedor
        authorsContainer.addView(authorView)
    }
}