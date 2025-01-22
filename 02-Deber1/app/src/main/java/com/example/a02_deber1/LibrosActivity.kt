package com.example.a02_deber1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LibrosActivity : AppCompatActivity() {
    private lateinit var booksContainer: LinearLayout
    private lateinit var btnAddBook: Button
    private var bookCount = 0 // Contador para los libros dinámicos
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_libros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        booksContainer = findViewById(R.id.booksContainer)
        btnAddBook = findViewById(R.id.btnAddBook)

        // Agregar libros iniciales
        addBook("Cien Años de Soledad", "1967", "Realismo Mágico", 20.99)
        addBook("La Casa de los Espíritus", "1982", "Drama", 15.50)

        // Configurar el botón para agregar un nuevo libro
        btnAddBook.setOnClickListener {
            bookCount++
            addBook("Nuevo Libro $bookCount", "2025", "Ficción", 10.00 + bookCount)
        }
    }

    private fun addBook(title: String, year: String, genre: String, price: Double) {
        // Inflar el diseño del libro desde el archivo book_item.xml
        val inflater = LayoutInflater.from(this)
        val bookView = inflater.inflate(R.layout.libro_item, booksContainer, false)

        // Configurar los detalles del libro
        val tvBookTitle = bookView.findViewById<TextView>(R.id.tvBookTitle)
        val tvBookYear = bookView.findViewById<TextView>(R.id.tvBookYear)
        val tvBookGenre = bookView.findViewById<TextView>(R.id.tvBookGenre)
        val tvBookPrice = bookView.findViewById<TextView>(R.id.tvBookPrice)

        tvBookTitle.text = "$bookCount. $title"
        tvBookYear.text = "Año: $year"
        tvBookGenre.text = "Género: $genre"
        tvBookPrice.text = "Precio: $${"%.2f".format(price)}"

        // Agregar la vista inflada al contenedor de libros
        booksContainer.addView(bookView)
    }
}