package com.example.ccgr12024b_dacc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cintent_explicito_parametros)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener datos del Intent
        val nombre = intent.getStringExtra("nombre") ?: "Sin nombre"
        val apellido = intent.getStringExtra("apellido") ?: "Sin apellido"
        val edad = intent.getIntExtra("edad", 0)
        val entrenador = intent.getParcelableExtra<BEntrenador>("entrenador")

        // Depuración con Logcat
        Log.d("CIntentExplicitoParametros", "Recibido -> Nombre: $nombre, Apellido: $apellido, Edad: $edad, Entrenador: ${entrenador.toString()}")

        // Mostrar en pantalla para verificar
        Toast.makeText(this, "Recibido: $nombre, $apellido, $edad", Toast.LENGTH_LONG).show()

        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
        boton.setOnClickListener {
            val intentDevolverRespuesta = Intent()
            intentDevolverRespuesta.putExtra(
                "nombreModificado", "$nombre, $edad, $apellido, ${entrenador.toString()}"
            )
            setResult(RESULT_OK, intentDevolverRespuesta)

            Log.d("CIntentExplicitoParametros", "Enviando de vuelta: $nombre, $apellido, $edad, ${entrenador.toString()}")
            Toast.makeText(this, "Enviando: $nombre, $apellido, $edad", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}