package com.example.ccgr12024b_dacc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.textView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Thursday, 28th November, 2024. Topic: Introducción a la creación de Interfaces Básicas en Android Studio
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida);
        botonCicloVida.setOnClickListener {
            irActividad(ACicloVida::class.java)
        }

        // Friday, 29th November, 2024. Topic: Parte 2
        val botonIrListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonIrListView.setOnClickListener{
            irActividad(BListView::class.java)
        }
    }

    fun irActividad(clase: Class<*>){
        startActivity(Intent(this, clase))
    }
}