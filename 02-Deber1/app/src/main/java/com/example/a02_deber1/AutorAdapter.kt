package com.example.a02_deber1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a02_deber1.models.Autor

class AutorAdapter(
    private val autores: List<Autor>,
    private val onEdit: (Autor) -> Unit,
    private val onDelete: (Autor) -> Unit,
    private val onBooks: (Autor) -> Unit
) : RecyclerView.Adapter<AutorAdapter.AutorViewHolder>() {

    inner class AutorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAuthorName: TextView = view.findViewById(R.id.tvAuthorName)
        val tvAuthorDetails: TextView = view.findViewById(R.id.tvAuthorDetails)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
        val btnBooks: Button = view.findViewById(R.id.btnBooks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.author_item, parent, false)
        return AutorViewHolder(view)
    }

    override fun onBindViewHolder(holder: AutorViewHolder, position: Int) {
        val autor = autores[position]
        holder.tvAuthorName.text = "${autor.nombre} ${autor.apellido}"
        holder.tvAuthorDetails.text = "Nacionalidad: ${autor.nacionalidad} / Fecha Nac: ${autor.fechaNacimiento} / Sigue Vivo: ${if (autor.sigueVivo) "Sí" else "No"}"

        holder.btnEdit.setOnClickListener { onEdit(autor) }
        holder.btnDelete.setOnClickListener { onDelete(autor) }
        holder.btnBooks.setOnClickListener { onBooks(autor) }
    }

    override fun getItemCount(): Int = autores.size
}