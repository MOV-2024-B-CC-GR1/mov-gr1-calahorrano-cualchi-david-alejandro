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

        // Cargar libros al iniciar la actividad
        cargarLibros()

        // Botón para agregar un nuevo libro
        val btnAddBook = findViewById<Button>(R.id.btnAddBoook)
        btnAddBook.setOnClickListener {
            //agregarLibro()
            val intent = Intent(this, FormularioLibroActivity::class.java)
            startActivity(intent)
        }

        // Botón para regresar a la actividad anterior
        val btnBack = findViewById<Button>(R.id.btnBackBook)
        btnBack.setOnClickListener {
            finish() // Cierra esta actividad
        }
    }

    override fun onResume() {
        super.onResume()
        cargarLibros() // Recarga la lista de libros al volver a la actividad
    }

    private fun cargarLibros() {
        val booksContainer = findViewById<LinearLayout>(R.id.booksContainer)
        booksContainer.removeAllViews() // Limpia la lista para evitar duplicados
    }

    private fun addBook() {
        val booksContainer = findViewById<LinearLayout>(R.id.booksContainer)
        val bookView = LayoutInflater.from(this).inflate(R.layout.libro_item, null)

        // Datos ficticios del libro
        val libro = Libro(
            idLibro = 0,
            titulo = "El Principito",
            anioPublicacion = 1943,
            genero = "Ficción",
            precio = 10.99
        )

        // Insertar en la base de datos
        val libroId = libroDAO.insertarLibro(libro)
        if (libroId > 0) {
            libro.idLibro = libroId.toInt()
        }

        // Actualizar los datos del libro en la vista
        val tvBookTitle = bookView.findViewById<TextView>(R.id.tvBookTitle)
        tvBookTitle.text = libro.titulo

        val tvBookDetails = bookView.findViewById<TextView>(R.id.tvBookDetails)
        tvBookDetails.text = "Año: ${libro.anioPublicacion} Género: ${libro.genero}Precio: $${libro.precio}"

        // Configurar botones
        setupButtons(bookView, libro)

        // Agregar la vista inflada al contenedor
        booksContainer.addView(bookView)

        // Ocultar el mensaje vacio si es necesario.
        if (booksContainer.childCount > 0) {
            val tvEmptyMessage = findViewById<TextView>(R.id.tvEmptyMessage)
            tvEmptyMessage.visibility = View.GONE
        }

        // Mostrar un mensaje de retroalimentacion
        Toast.makeText(this, "Libro agregado: ${tvBookTitle.text}", Toast.LENGTH_SHORT).show()
    }

    private fun setupButtons(bookView: View, libro: Libro) {
        // Botón para editar
        val btnEdit = bookView.findViewById<Button>(R.id.btnEditBook)
        btnEdit.setOnClickListener {
            editarLibro(libro)
        }

        // Botón para eliminar
        val btnDelete = bookView.findViewById<Button>(R.id.btnDeleteBook)
        btnDelete.setOnClickListener {
            eliminarLibro(bookView, libro)
        }
    }

    private fun editarLibro(libro: Libro) {
        val intent = Intent(this, FormularioLibroActivity::class.java).apply {
            putExtra("idLibro", libro.idLibro) // Pasa el ID del libro
            putExtra("titulo", libro.titulo)
            putExtra("anioPublicacion", libro.anioPublicacion)
            putExtra("genero", libro.genero)
            putExtra("precio", libro.precio)
        }
        startActivity(intent)
    }

    private fun eliminarLibro(bookView: View, libro: Libro) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Libro")
            .setMessage("¿Estás seguro de que deseas eliminar a ${libro.titulo}?")
            .setPositiveButton("Sí") { _, _ ->
                val result = libroDAO.borrarLibroPorId(libro.idLibro)
                if (result > 0) {
                    val booksContainer = findViewById<LinearLayout>(R.id.booksContainer)
                    booksContainer.removeView(bookView)
                    Toast.makeText(this, "Autor eliminado: ${libro.titulo}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al eliminar el libro", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }
}