package org.example.services
import org.example.models.Libro
import org.example.utility.FileUtils

class LibroService(private val filePath: String, private val autorService: AutorService) {
    private var libros: MutableList<Libro> = mutableListOf()

    init {
        libros = cargarLibrosDesdeTxt(filePath)
    }

    fun crearLibro(libro: Libro) {
        libros.add(libro)
        guardarLibrosEnTxt(filePath, libros)
    }

    fun obtenerLibros(): List<Libro> = libros

    fun obtenerLibroPorId(idLibro: Int): Libro? = libros.find { it.idLibro == idLibro }

    fun actualizarLibro(idLibro: Int, libroActualizado: Libro) {
        val index = libros.indexOfFirst { it.idLibro == idLibro }
        if (index != -1) {
            libros[index] = libroActualizado
            guardarLibrosEnTxt(filePath, libros)
        }
    }

    fun eliminarLibro(idLibro: Int) {
        libros.removeIf { it.idLibro == idLibro }
        guardarLibrosEnTxt(filePath, libros)
    }

    private fun cargarLibrosDesdeTxt(filePath: String): MutableList<Libro> {
        val lines = FileUtils.leerArchivoTxt(filePath)
        val libros = mutableListOf<Libro>()

        lines.forEach { line ->
            if (line.isBlank()) {
                println("Línea vacía ignorada.")
                return@forEach
            }
            val parts = line.split(",")
            try {
                // Validar que haya al menos 6 campos (ID, Título, Año, Género, Precio, Autores)
                if (parts.size < 6) {
                    println("Línea malformada ignorada: $line")
                    return@forEach
                }

                val idLibro = parts[0].toIntOrNull() ?: throw NumberFormatException("ID inválido")
                val titulo = parts[1]
                val anioPublicacion = parts[2].toIntOrNull() ?: throw NumberFormatException("Año inválido")
                val genero = parts[3]
                val precio = parts[4].toDoubleOrNull() ?: throw NumberFormatException("Precio inválido")
                val autorIds = parts[5].split(";").mapNotNull { it.toIntOrNull() }
                val autores = autorIds.mapNotNull { autorService.obtenerAutorPorId(it) }

                val libro = Libro(idLibro, titulo, anioPublicacion, genero, precio, autores)
                libros.add(libro)
            } catch (e: NumberFormatException) {
                println("Error al procesar línea: $line. Detalles: ${e.message}")
            }
        }

        return libros
    }

    private fun guardarLibrosEnTxt(filePath: String, libros: List<Libro>) {
        val lines = libros.map { libro ->
            val autorIds = libro.autores.joinToString(";") { it.idAutor.toString() }
            "${libro.idLibro},${libro.titulo},${libro.anioPublicacion},${libro.genero},${libro.precio},$autorIds"
        }
        FileUtils.guardarArchivoTxt(filePath, lines)
    }
}