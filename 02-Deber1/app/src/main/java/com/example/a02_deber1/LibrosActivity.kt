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
import com.example.a02_deber1.database.AutorDAO
import com.example.a02_deber1.database.LibroDAO
import com.example.a02_deber1.models.Autor
import com.example.a02_deber1.models.Libro

class LibrosActivity : AppCompatActivity() {
    private lateinit var libroDAO: LibroDAO

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_libros)

        libroDAO = LibroDAO(this)

        // Ajustar padding para barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargar libros desde la base de datos
        cargarLibros()

        // Acción del botón "+ Agregar Libro"
        val btnAddBook = findViewById<Button>(R.id.btnAddBoook)
        btnAddBook.setOnClickListener {
            val intent = Intent(this, FormularioLibroActivity::class.java)
            startActivity(intent)
        }

        // Configurar el botón "Regresar"
        val btnBack = findViewById<Button>(R.id.btnBackBook)
        btnBack.setOnClickListener {
            finish() // Finaliza la actividad actual y regresa a la anterior
        }
    }

    override fun onResume() {
        super.onResume()
        cargarLibros() // Recargar la lista de libros al regresar
    }

    private fun cargarLibros() {
        val booksContainer = findViewById<LinearLayout>(R.id.booksContainer)
        booksContainer.removeAllViews() // Limpia la lista para evitar duplicados

        val libros = libroDAO.obtenerTodosLosLibros()
        if (libros.isEmpty()) {
            val tvEmptyMessage = findViewById<TextView>(R.id.tvEmptyMessage)
            tvEmptyMessage.visibility = View.VISIBLE
        } else {
            val tvEmptyMessage = findViewById<TextView>(R.id.tvEmptyMessage)
            tvEmptyMessage.visibility = View.GONE
        }

        libros.forEach { libro ->
            val bookView: View = LayoutInflater.from(this).inflate(R.layout.author_item, booksContainer, false)

            // Configurar datos del libro
            val tvBookTitle = bookView.findViewById<TextView>(R.id.tvAuthorName)
            tvBookTitle.text = libro.titulo

            val tvBookDetails = bookView.findViewById<TextView>(R.id.tvAuthorDetails)
            tvBookDetails.text = "Género: ${libro.genero}\nAño: ${libro.anioPublicacion}\nPrecio: $${libro.precio}"

            // Configurar botones de la vista inflada
            setupButtons(bookView, libro)

            // Agregar la vista inflada al contenedor
            booksContainer.addView(bookView)
        }
    }

    private fun setupButtons(bookView: View, libro: Libro) {
        // Botón Editar
        val btnEdit = bookView.findViewById<Button>(R.id.btnEdit)
        btnEdit.setOnClickListener {
            editarLibro(libro)
        }

        // Botón Eliminar
        val btnDelete = bookView.findViewById<Button>(R.id.btnDelete)
        btnDelete.setOnClickListener {
            eliminarLibro(bookView, libro)
        }
    }

    private fun editarLibro(libro: Libro) {
        val intent = Intent(this, FormularioLibroActivity::class.java).apply {
            putExtra("idLibro", libro.idLibro) // Pasar el ID del libro
            putExtra("titulo", libro.titulo) // Pasar el título
            putExtra("genero", libro.genero) // Pasar el género
            putExtra("anioPublicacion", libro.anioPublicacion) // Pasar el año de publicación
            putExtra("precio", libro.precio) // Pasar el precio
            putExtra("idAutor", libro.idAutor) // Pasar el ID del autor relacionado
        }
        startActivity(intent) // Inicia la actividad del formulario
    }

    private fun eliminarLibro(bookView: View, libro: Libro) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Libro")
            .setMessage("¿Estás seguro de que deseas eliminar el libro '${libro.titulo}'?")
            .setPositiveButton("Sí") { _, _ ->
                val result = libroDAO.borrarLibroPorId(libro.idLibro)
                if (result > 0) {
                    val booksContainer = findViewById<LinearLayout>(R.id.booksContainer)
                    booksContainer.removeView(bookView)
                    Toast.makeText(this, "Libro eliminado: ${libro.titulo}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al eliminar el libro", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}