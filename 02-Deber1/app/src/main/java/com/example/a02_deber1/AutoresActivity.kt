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
    private lateinit var autorDAO: AutorDAO

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_autores)

        autorDAO = AutorDAO(this)

        // Ajustar padding para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargar autores desde la base de datos
        cargarAutores()

        // Acción del botón "+ Agregar Autor"
        val btnAddAuthor = findViewById<Button>(R.id.btnAddAuthor)
        btnAddAuthor.setOnClickListener {
            val intent = Intent(this, FormularioAutorActivity::class.java)
            startActivity(intent)
        }

        // Configurar el botón "Regresar"
        val btnBack = findViewById<Button>(R.id.btnBackAuthor)
        btnBack.setOnClickListener {
            finish() // Finaliza la actividad actual y regresa a la anterior
        }
    }

    override fun onResume() {
        super.onResume()
        cargarAutores() // Recargar la lista de autores al regresar
    }

    private fun cargarAutores() {
        val authorsContainer = findViewById<LinearLayout>(R.id.authorsContainer)
        authorsContainer.removeAllViews() // Limpia la lista para evitar duplicados

        val autores = autorDAO.obtenerTodosLosAutores()
        if (autores.isEmpty()) {
            val tvEmptyMessage = findViewById<TextView>(R.id.tvEmptyMessage)
            tvEmptyMessage.visibility = View.VISIBLE
        } else {
            val tvEmptyMessage = findViewById<TextView>(R.id.tvEmptyMessage)
            tvEmptyMessage.visibility = View.GONE
        }

        autores.forEach { autor ->
            val authorView: View = LayoutInflater.from(this).inflate(R.layout.author_item, authorsContainer, false)

            // Configurar datos del autor
            val tvAuthorName = authorView.findViewById<TextView>(R.id.tvAuthorName)
            tvAuthorName.text = "Autor: ${autor.nombre} ${autor.apellido}"

            val tvAuthorDetails = authorView.findViewById<TextView>(R.id.tvAuthorDetails)
            tvAuthorDetails.text = "Nacionalidad: ${autor.nacionalidad}\nFecha Nac: ${autor.fechaNacimiento}\nSigue Vivo: ${if (autor.sigueVivo) "Sí" else "No"}"

            // Configurar botones de la vista inflada
            setupButtons(authorView, autor)

            // Agregar la vista inflada al contenedor
            authorsContainer.addView(authorView)
        }
    }

    private fun addAuthor() {
        val authorsContainer = findViewById<LinearLayout>(R.id.authorsContainer)
        val authorView: View = LayoutInflater.from(this).inflate(R.layout.author_item, authorsContainer, false)

        // Datos ficticios del autor
        val autor = Autor(
            idAutor = 0,
            nombre = "Autor ${authorsContainer.childCount + 1}",
            apellido = "Apellido",
            nacionalidad = "Desconocida",
            fechaNacimiento = "--",
            sigueVivo = true
        )

        // Insertar en la base de datos
        val autorId = autorDAO.insertarAutor(autor)
        if (autorId > 0) {
            autor.idAutor = autorId.toInt()
        }


        // Actualizar los datos del autor en la vista
        val tvAuthorName = authorView.findViewById<TextView>(R.id.tvAuthorName)
        tvAuthorName.text = "${autor.nombre} ${autor.apellido}"

        val tvAuthorDetails = authorView.findViewById<TextView>(R.id.tvAuthorDetails)
        tvAuthorDetails.text = "Nacionalidad: ${autor.nacionalidad} / Fecha Nac: ${autor.fechaNacimiento} / Sigue Vivo: Sí"

        // Configurar botones de la vista inflada
        setupButtons(authorView, autor)

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

    private fun setupButtons(authorView: View, autor: Autor) {
        // Botón Editar
        val btnEdit = authorView.findViewById<Button>(R.id.btnEdit)
        btnEdit.setOnClickListener {
            editarAutor(autor)
        }

        // Botón Eliminar
        val btnDelete = authorView.findViewById<Button>(R.id.btnDelete)
        btnDelete.setOnClickListener {
            eliminarAutor(authorView, autor)
        }

        // Botón Libros
        val btnBooks = authorView.findViewById<Button>(R.id.btnBooks)
        btnBooks.setOnClickListener {
            mostrarLibros(autor) // Llama al método y pasa el ID del autor
        }
    }

    private fun agregarLibro(idAutor: Int) {
        val intent = Intent(this, FormularioLibroActivity::class.java).apply {
            putExtra("idAutor", idAutor) // Relaciona el libro con el autor
        }
        startActivity(intent)
    }

    private fun editarAutor(autor: Autor) {
        val intent = Intent(this, FormularioAutorActivity::class.java).apply {
            putExtra("idAutor", autor.idAutor) // Pasar el ID del autor
            putExtra("nombre", autor.nombre) // Pasar el nombre
            putExtra("apellido", autor.apellido) // Pasar el apellido
            putExtra("nacionalidad", autor.nacionalidad) // Pasar la nacionalidad
            putExtra("fechaNacimiento", autor.fechaNacimiento) // Pasar la fecha de nacimiento
            putExtra("sigueVivo", autor.sigueVivo) // Pasar el estado de si está vivo o no
        }
        startActivity(intent) // Inicia la actividad del formulario
    }

    private fun eliminarAutor(authorView: View, autor: Autor) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Autor")
            .setMessage("¿Estás seguro de que deseas eliminar a ${autor.nombre}?")
            .setPositiveButton("Sí") { _, _ ->
                val result = autorDAO.borrarAutorPorId(autor.idAutor)
                if (result > 0) {
                    val authorsContainer = findViewById<LinearLayout>(R.id.authorsContainer)
                    authorsContainer.removeView(authorView)
                    Toast.makeText(this, "Autor eliminado: ${autor.nombre}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al eliminar el autor", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun mostrarLibros(autor: Autor) {
        // Redirigir a otra actividad para mostrar los libros del autor
        Toast.makeText(this, "Mostrar libros de: ${autor.nombre}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LibrosActivity::class.java)
        intent.putExtra("idAutor", autor.idAutor)
        startActivity(intent)
    }
}