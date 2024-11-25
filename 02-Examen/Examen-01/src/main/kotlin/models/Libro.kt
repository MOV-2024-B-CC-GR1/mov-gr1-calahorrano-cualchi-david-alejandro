package org.example.models

data class Libro(
    val idLibro: Int,
    val titulo: String,
    val anioPublicacion: Int,
    val genero: String,
    val precio: Double,
    val autores: List<Autor> // Relación UNO a MUCHOS
)
