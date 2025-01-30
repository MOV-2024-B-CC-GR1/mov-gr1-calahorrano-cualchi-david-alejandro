package com.example.ccgr12024b_dacc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ECrudEntrenador : AppCompatActivity() {
    fun mostrarSnackbar(texto: String){
        val snack = Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ecrud_entrenador)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Buscar entrenador
        val botonBuscarBDD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val entrenador = EBaseDeDatos.obtenerBaseDatos(this)
                .consultarEntrenadorPorId(id.text.toString().toInt())

            if (entrenador == null) {
                id.setText("")
                nombre.setText("")
                descripcion.setText("")
                mostrarSnackbar("Entrenador no encontrado")
            } else {
                nombre.setText(entrenador.nombre)
                descripcion.setText(entrenador.descripcion)
                mostrarSnackbar("Entrenador ${entrenador.nombre} encontrado")
            }
        }

        // Eliminar entrenador
        val botonEliminarBDD = findViewById<Button>(R.id.btn_eliminar_bdd)
        botonEliminarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = EBaseDeDatos.obtenerBaseDatos(this)
                .eliminarEntrenador(id.text.toString().toInt())

            if (respuesta) mostrarSnackbar("Entrenador eliminado")
            else mostrarSnackbar("No encontrado")
        }

        // Crear entrenador
        val botonCrearBDD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBDD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.obtenerBaseDatos(this)
                .crearEntrenador(
                    nombre.text.toString(),
                    descripcion.text.toString()
                )

            if (respuesta) mostrarSnackbar("Entrenador creado")
            else mostrarSnackbar("Fallo al crear")
        }

        // Actualizar entrenador
        val botonActualizarBDD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBDD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.obtenerBaseDatos(this)
                .actualizarEntrenador(
                    nombre.text.toString(),
                    descripcion.text.toString(),
                    id.text.toString().toInt()
                )

            if (respuesta) mostrarSnackbar("Entrenador actualizado")
            else mostrarSnackbar("Fallo al actualizar")
        }

        // 🔹 NUEVO: Mostrar todos los entrenadores
        val botonMostrarTodos = findViewById<Button>(R.id.btn_mostrar_todos)
        botonMostrarTodos.setOnClickListener {
            mostrarTodosEntrenadores()
        }

        // 🔹 NUEVO: Mostrar cantidad de entrenadores
        val botonMostrarCantidad = findViewById<Button>(R.id.btn_mostrar_cantidad)
        botonMostrarCantidad.setOnClickListener {
            mostrarCantidadEntrenadores()
        }
    }

    // 🔹 NUEVA FUNCIÓN: Muestra todos los entrenadores en un Snackbar
    fun mostrarTodosEntrenadores() {
        val listaEntrenadores = EBaseDeDatos.obtenerBaseDatos(this).consultarTodosEntrenadores()
        if (listaEntrenadores.isEmpty()) {
            mostrarSnackbar("No hay entrenadores registrados")
        } else {
            val nombres = listaEntrenadores.joinToString("\n") { "${it.id}: ${it.nombre}" }
            mostrarSnackbar("Entrenadores:\n$nombres")
        }
    }

    // 🔹 NUEVA FUNCIÓN: Muestra la cantidad total de entrenadores en la base de datos
    fun mostrarCantidadEntrenadores() {
        val cantidad = EBaseDeDatos.obtenerBaseDatos(this).contarEntrenadores()
        mostrarSnackbar("Total de entrenadores: $cantidad")
    }
}