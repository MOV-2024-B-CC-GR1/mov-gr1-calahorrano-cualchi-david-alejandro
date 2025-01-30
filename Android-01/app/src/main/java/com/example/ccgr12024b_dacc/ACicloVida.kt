package com.example.ccgr12024b_dacc

import android.os.Bundle
import android.os.PersistableBundle
import android.provider.CalendarContract.Instances
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_aciclo_vida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_ciclo_vida)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("CICLO_VIDA", "onCreate ejecutado")
        mostrarSnackbar("onCreate");

        val botonRegresar = findViewById<Button>(R.id.btn_regresar)
        botonRegresar.setOnClickListener {
            finish() // Cierra esta actividad y regresa a la anterior
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("CICLO_VIDA", "onStart ejecutado")
        mostrarSnackbar("OnStart");
    }

    override fun onResume() {
        super.onResume()
        Log.d("CICLO_VIDA", "onResume ejecutado")
        mostrarSnackbar("OnResumen")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("CICLO_VIDA", "onRestart ejecutado")
        mostrarSnackbar("OnRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d("CICLO_VIDA", "onPause ejecutado")
        mostrarSnackbar("OnPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CICLO_VIDA", "onStop ejecutado")
        mostrarSnackbar("OnStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            // Guardar las variables
            putString("variableTextoGuardado", textoGlobal);
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Recuperar las variables
        val textoRecuperado: String? = savedInstanceState.getString("variableTextoGuardado")
        if (textoRecuperado != null) {
            // textoGlobal = textoRecuperado
            mostrarSnackbar(textoRecuperado) // Ya guarda e; texto global
        }
    }


    // Thursday, 28th November, 2024. Topic: Introducción a la creación de Interfaces Básicas en Android Studio
    var textoGlobal = "";
    fun mostrarSnackbar(text: String) {
        val snack = Snackbar.make(findViewById(R.id.cl_ciclo_vida), text, Snackbar.LENGTH_SHORT)
        snack.show()
    }
}