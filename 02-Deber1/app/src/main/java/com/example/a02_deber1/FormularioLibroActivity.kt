package com.example.a02_deber1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a02_deber1.database.AutorDAO
import com.example.a02_deber1.database.LibroDAO
import com.example.a02_deber1.models.Libro

class FormularioLibroActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    private lateinit var libroDAO: LibroDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario_libro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar el DAO
        libroDAO = LibroDAO(this)

        // Referencias a los campos del formulario
        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etAnioPublicacion = findViewById<EditText>(R.id.etAnioPublicacion)
        val etGenero = findViewById<EditText>(R.id.etGenero)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)

        // Configurar botones
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        // Verificar si se pasó un libro para editar
        val libroId = intent.getIntExtra("idLibro", -1)
        if (libroId != -1) {
            // Precargar los datos del libro para editar
            val titulo = intent.getStringExtra("titulo")
            val anioPublicacion = intent.getIntExtra("anioPublicacion", 0)
            val genero = intent.getStringExtra("genero")
            val precio = intent.getDoubleExtra("precio", 0.0)

            etTitulo.setText(titulo)
            etAnioPublicacion.setText(if (anioPublicacion != 0) anioPublicacion.toString() else "")
            etGenero.setText(genero)
            etPrecio.setText(if (precio != 0.0) precio.toString() else "")
        }

        // Configurar el Spinner para autores
        val spinnerAutores = findViewById<Spinner>(R.id.spinnerAutores)
        val autorDAO = AutorDAO(this)

        // Obtener la lista de autores
        val listaAutores = autorDAO.obtenerTodosLosAutores()
        if (listaAutores.isEmpty()) {
            Toast.makeText(this, "No hay autores disponibles. Por favor, crea un autor primero.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Configurar el Spinner con la lista de autores
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaAutores.map { "${it.nombre} ${it.apellido}" }
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAutores.adapter = adapter

        // Acción del botón "Guardar"
        btnGuardar.setOnClickListener {
            if (!validarCampo(etTitulo, "El título es obligatorio.") ||
                !validarCampo(etAnioPublicacion, "El año de publicación es obligatorio.") ||
                !validarCampo(etGenero, "El género es obligatorio.") ||
                !validarCampo(etPrecio, "El precio es obligatorio.")
            ) return@setOnClickListener

            val titulo = etTitulo.text.toString()
            val anioPublicacion = try {
                etAnioPublicacion.text.toString().toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "El año de publicación debe ser un número.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val genero = etGenero.text.toString()
            val precio = try {
                etPrecio.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Por favor, ingresa un precio válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // val idAutor = intent.getIntExtra("idAutor", -1) // Recibir idAutor

            // Obtener el autor seleccionado
            val idAutor = listaAutores[spinnerAutores.selectedItemPosition].idAutor

            if (idAutor == -1) {
                Toast.makeText(this, "Por favor, selecciona un autor válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (libroId == -1) {
                // Crear un nuevo libro
                val nuevoLibro = Libro(
                    idLibro = 0,
                    titulo = titulo,
                    anioPublicacion = anioPublicacion,
                    genero = genero,
                    precio = precio,
                    idAutor = idAutor
                )
                val id = libroDAO.insertarLibro(nuevoLibro)
                if (id > 0) {
                    Toast.makeText(this, "Libro guardado con éxito.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar el libro.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Actualizar un libro existente
                val libroActualizado = Libro(
                    idLibro = libroId,
                    titulo = titulo,
                    anioPublicacion = anioPublicacion,
                    genero = genero,
                    precio = precio,
                    idAutor = idAutor
                )
                val filasActualizadas = libroDAO.actualizarLibro(libroActualizado)

                if (filasActualizadas > 0) {
                    Toast.makeText(this, "Libro actualizado con éxito.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al actualizar el libro.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Acción del botón "Cancelar"
        btnCancelar.setOnClickListener {
            finish() // Finaliza la actividad
        }
    }

    private fun validarCampo(campo: EditText, mensajeError: String): Boolean {
        return if (campo.text.toString().isBlank()) {
            campo.error = mensajeError
            campo.requestFocus()
            false
        } else {
            true
        }
    }
}