package com.example.ccgr12024b_dacc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerViewAdaptadorNombreDescripcion(
    private val contexto: FRecyclerView,
    private val lista: ArrayList<BEntrenador>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<FRecyclerViewAdaptadorNombreDescripcion.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nombreTextView: TextView = view.findViewById(R.id.tv_nombre)
        val descripcionTextView: TextView = view.findViewById(R.id.tv_descripcion)
        val likesTextView: TextView = view.findViewById(R.id.tv_likes)
        val accionBoton: Button = view.findViewById(R.id.btn_dar_like)
        var numeroLikes = 0

        init{
            accionBoton.setOnClickListener { anadirLikes() } // Activar el botón para dar like
        }
        fun anadirLikes() {
            numeroLikes++
            likesTextView.text = numeroLikes.toString()
            contexto.aumentarTotalLikes() // Actualizar el total de likes en FRecyclerView
        }
    }

    // Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_vista, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    // Seteamos los datos para la iteracion
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entrenadorActual = lista[position]
        holder.nombreTextView.text = entrenadorActual.nombre
        holder.descripcionTextView.text = entrenadorActual.descripcion
        holder.likesTextView.text = holder.numeroLikes.toString()
    }
}