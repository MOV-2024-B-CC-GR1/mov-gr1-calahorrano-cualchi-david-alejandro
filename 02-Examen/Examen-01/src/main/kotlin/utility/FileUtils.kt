package org.example.utility
import java.io.File

object FileUtils {
    /**
     * Lee un archivo de texto y retorna una lista de líneas.
     * @param filePath Ruta del archivo.
     * @return Lista de líneas leídas del archivo.
     */
    fun leerArchivoTxt(filePath: String): List<String> {
        val file = File(filePath)
        if (!file.exists()) {
            println("El archivo $filePath no existe. Creando un archivo vacío...")
            file.createNewFile()
            return emptyList()
        }
        return file.readLines()
    }

    /**
     * Escribe una lista de líneas en un archivo de texto.
     * Sobrescribe el contenido existente.
     * @param filePath Ruta del archivo.
     * @param lines Lista de líneas a escribir.
     */
    fun guardarArchivoTxt(filePath: String, lines: List<String>) {
        val file = File(filePath)
        file.writeText(lines.joinToString("\n"))
        println("Archivo guardado en $filePath.")
    }

    /**
     * Agrega nuevas líneas al final de un archivo de texto.
     * @param filePath Ruta del archivo.
     * @param lines Lista de líneas a agregar.
     */
    fun agregarLineasAlArchivo(filePath: String, lines: List<String>) {
        val file = File(filePath)
        file.appendText(lines.joinToString("\n") + "\n")
        println("Líneas agregadas al archivo $filePath.")
    }

    /**
     * Limpia un archivo de texto eliminando líneas malformadas o vacías.
     * @param filePath Ruta del archivo.
     * @param validColumns Número mínimo de columnas requeridas para que una línea sea considerada válida (por defecto: 6).
     */
    fun limpiarArchivoTxt(filePath: String) {
        val file = File(filePath)
        if (!file.exists()) return

        val validLines = file.readLines().filter { line ->
            val parts = line.split(",")
            parts.size >= 6 && parts[0].isNotBlank() && parts[2].isNotBlank() && parts[4].isNotBlank()
        }

        file.writeText(validLines.joinToString("\n"))
        println("Archivo $filePath limpiado y líneas inválidas eliminadas.")
    }
}