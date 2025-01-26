package com.example.a02_deber1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a02_deber1.database.AutorDAO
import com.example.a02_deber1.models.Autor

class FormularioAutorActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var autorDAO: AutorDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario_autor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar el DAO
        autorDAO = AutorDAO(this)

        // Referencias a los campos del formulario
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etNacionalidad = findViewById<EditText>(R.id.etNacionalidad)
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNac)
        val rbSigueVivoSi = findViewById<RadioButton>(R.id.rbSi)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        // Verificar si se pasó un autor para editar
        val autorId = intent.getIntExtra("idAutor", -1)
        if (autorId != -1) {
            val nombre = intent.getStringExtra("nombre")
            val apellido = intent.getStringExtra("apellido")
            val nacionalidad = intent.getStringExtra("nacionalidad")
            val fechaNacimiento = intent.getStringExtra("fechaNacimiento")
            val sigueVivo = intent.getBooleanExtra("sigueVivo", true)
            val rbSigueVivoSi = findViewById<RadioButton>(R.id.rbSi)
            val rbSigueVivoNo = findViewById<RadioButton>(R.id.rbNo)

            // Precargar los datos en el formulario
            etNombre.setText(nombre)
            etApellido.setText(apellido)
            etNacionalidad.setText(nacionalidad)
            etFechaNacimiento.setText(fechaNacimiento)
            if (sigueVivo) {
                rbSigueVivoSi.isChecked = true
            } else {
                rbSigueVivoNo.isChecked = true
            }
        }

        // Configuración del botón Guardar
        btnGuardar.setOnClickListener {
            if (!validarCampo(etNombre, "El nombre es obligatorio.") ||
                !validarCampo(etApellido, "El apellido es obligatorio.") ||
                !validarCampo(etNacionalidad, "La nacionalidad es obligatoria.") ||
                !validarCampo(etFechaNacimiento, "La fecha de nacimiento es obligatoria.")
            ) return@setOnClickListener

            val nombre = etNombre.text.toString()
            val apellido = etApellido.text.toString()
            val nacionalidad = etNacionalidad.text.toString()
            val fechaNacimiento = etFechaNacimiento.text.toString()
            val sigueVivo = rbSigueVivoSi.isChecked

            if (autorId == -1) {
                // Crear un nuevo autor
                val nuevoAutor = Autor(
                    idAutor = 0,
                    nombre = nombre,
                    apellido = apellido,
                    nacionalidad = nacionalidad,
                    fechaNacimiento = fechaNacimiento,
                    sigueVivo = sigueVivo
                )

                val id = autorDAO.insertarAutor(nuevoAutor)

                if (id > 0) {
                    Toast.makeText(this, "Autor guardado con éxito.", Toast.LENGTH_SHORT).show()
                    finish() // Cierra la actividad después de guardar
                } else {
                    Toast.makeText(this, "Error al guardar el autor.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Actualizar autor existente
                val autorActualizado = Autor(
                    idAutor = autorId,
                    nombre = nombre,
                    apellido = apellido,
                    nacionalidad = nacionalidad,
                    fechaNacimiento = fechaNacimiento,
                    sigueVivo = sigueVivo
                )

                val filasActualizadas = autorDAO.actualizarAutor(autorActualizado)

                if (filasActualizadas > 0) {
                    Toast.makeText(this, "Autor actualizado con éxito.", Toast.LENGTH_SHORT).show()
                    finish() // Cierra la actividad después de actualizar
                } else {
                    Toast.makeText(this, "Error al actualizar el autor.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Configuración del botón Cancelar
        btnCancelar.setOnClickListener {
            finish()
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