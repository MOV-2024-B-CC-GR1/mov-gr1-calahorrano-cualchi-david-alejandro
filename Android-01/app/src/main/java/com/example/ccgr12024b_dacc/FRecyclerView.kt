package com.example.ccgr12024b_dacc

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FRecyclerView : AppCompatActivity() {
    private var totalLikes = 0
    private lateinit var textViewLikes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_frecycler_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar la vista de "Total Likes"
        textViewLikes = findViewById(R.id.tv_total_likes)
        actualizarTextoTotalLikes() // Mostrar "Total Likes: 0" al inicio

        // Inicializar RecyclerView
        inicializarRecyclerView()
    }

    private fun inicializarRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_entrenadores)

        // Verificar si la lista está vacía y agregar datos de prueba
        if (BBaseDatosMemoria.arregloBEntrenador.isEmpty()) {
            BBaseDatosMemoria.arregloBEntrenador.addAll(
                listOf(
                    BEntrenador(1, "Entrenador 1", "Especialista en fútbol"),
                    BEntrenador(2, "Entrenador 2", "Coach de natación"),
                    BEntrenador(3, "Entrenador 3", "Preparador físico"),
                    BEntrenador(4, "Entrenador 4", "Entrenador de tenis"),
                    BEntrenador(5, "Entrenador 5", "Instructor de yoga")
                )
            )
        }

        // Configurar el adaptador con el callback para actualizar likes
        val adaptador = FRecyclerViewAdaptadorNombreDescripcion(
            this, BBaseDatosMemoria.arregloBEntrenador, recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    fun aumentarTotalLikes() {
        totalLikes++
        actualizarTextoTotalLikes()
    }

    private fun actualizarTextoTotalLikes() {
        textViewLikes.text = "Total Likes: $totalLikes"
    }
}