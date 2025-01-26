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

        // Configuración del botón Guardar
        btnGuardar.setOnClickListener {
            if (!validarCampo(etNombre, "El nombre es obligatorio.") ||
                !validarCampo(etApellido, "El apellido es obligatorio.") ||
                !validarCampo(etNacionalidad, "La nacionalidad es obligatoria.") ||
                !validarCampo(etFechaNacimiento, "La fecha de nacimiento es obligatoria.")
            ) return@setOnClickListener

            val nuevoAutor = Autor(
                idAutor = 0,
                nombre = etNombre.text.toString(),
                apellido = etApellido.text.toString(),
                nacionalidad = etNacionalidad.text.toString(),
                fechaNacimiento = etFechaNacimiento.text.toString(),
                sigueVivo = rbSigueVivoSi.isChecked
            )

            val id = autorDAO.insertarAutor(nuevoAutor)

            // Aquí va el fragmento de código mencionado
            if (id > 0) {
                Toast.makeText(this, "Autor guardado con éxito.", Toast.LENGTH_SHORT).show()
                finish() // Cierra la actividad después de guardar
            } else {
                Toast.makeText(this, "Error al guardar el autor.", Toast.LENGTH_SHORT).show()
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