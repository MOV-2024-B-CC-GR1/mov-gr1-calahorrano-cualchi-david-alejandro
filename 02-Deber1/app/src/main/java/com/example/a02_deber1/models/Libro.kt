package com.example.a02_deber1.models

data class Libro(
    val idLibro: Int,
    val titulo: String,
    val anioPublicacion: Int,
    val genero: String,
    val precio: Double,
    val idAutor: Int // Relación UNO a MUCHOS (Libro pertenece a un autor)
)