package org.example.services
import org.example.models.Autor
import org.example.utility.FileUtils

class AutorService(private val filePath: String) {
    private var autores: MutableList<Autor> = mutableListOf()

    init {
        autores = cargarAutoresDesdeTxt(filePath)
    }

    fun crearAutor(autor: Autor) {
        autores.add(autor)
        guardarAutoresEnTxt(filePath, autores)
    }

    fun obtenerAutores(): List<Autor> = autores

    fun obtenerAutorPorId(idAutor: Int): Autor? = autores.find { it.idAutor == idAutor }

    fun actualizarAutor(idAutor: Int, autorActualizado: Autor) {
        val index = autores.indexOfFirst { it.idAutor == idAutor }
        if (index != -1) {
            autores[index] = autorActualizado
            guardarAutoresEnTxt(filePath, autores)
        }
    }

    fun eliminarAutor(idAutor: Int) {
        autores.removeIf { it.idAutor == idAutor }
        guardarAutoresEnTxt(filePath, autores)
    }

    private fun cargarAutoresDesdeTxt(filePath: String): MutableList<Autor> {
        val lines = FileUtils.leerArchivoTxt(filePath)
        return lines.map { line ->
            val parts = line.split(",")
            Autor(
                idAutor = parts[0].toInt(),
                nombre = parts[1],
                apellido = parts[2],
                nacionalidad = parts[3],
                fechaNacimiento = parts[4],
                sigueVivo = parts[5].toBoolean(),
                cantidadLibros = parts.getOrNull(6)?.toInt() ?: 0 // Valor predeterminado 0
            )
        }.toMutableList()
    }

    private fun guardarAutoresEnTxt(filePath: String, autores: List<Autor>) {
        val lines = autores.map { autor ->
            "${autor.idAutor}," +
                    "${autor.nombre},${autor.apellido}," +
                    "${autor.fechaNacimiento},${autor.nacionalidad},${autor.sigueVivo}" +
                    "${autor.cantidadLibros}"
        }
        FileUtils.guardarArchivoTxt(filePath, lines)
    }
}