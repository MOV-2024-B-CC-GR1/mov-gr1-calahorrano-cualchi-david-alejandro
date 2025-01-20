package com.example.a02_deber1.models

data class Autor(
    val idAutor: Int,
    val nombre: String,
    val apellido: String,
    val nacionalidad: String,
    val fechaNacimiento: String,
    val sigueVivo: Boolean,
    var cantidadLibros: Int = 0 // Inicia en 0 por defecto
)