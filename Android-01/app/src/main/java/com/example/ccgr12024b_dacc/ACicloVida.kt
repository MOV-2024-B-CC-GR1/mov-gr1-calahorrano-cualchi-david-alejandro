package com.example.ccgr12024b_dacc

import android.os.Bundle
import android.os.PersistableBundle
import android.provider.CalendarContract.Instances
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
        mostrarSnackbar("onCreate");
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackbar("OnStart");
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackbar("OnResumen")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackbar("OnRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackbar("OnPause")
    }

    override fun onStop() {
        super.onStop()
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
        textoGlobal += text;
        val snack = Snackbar.make(findViewById(R.id.cl_ciclo_vida), textoGlobal, Snackbar.LENGTH_INDEFINITE);
        snack.show();
    }
}