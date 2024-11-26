package org.example

import org.example.models.Libro
import org.example.models.Autor
import org.example.services.LibroService
import org.example.services.AutorService

fun main() {
    println("Hello World!")
    val autorService = AutorService("src/main/resources/data/autores.txt");
    val libroService = LibroService("src/main/resources/data/libros.txt", autorService)


    while (true) {
        println("\n<<=== Menú Principal ===>>");
        println("1. Crear");
        println("2. Leer");
        println("3. Actualizar");
        println("4. Eliminar");
        println("5. Mostrar contenido");
        println("0. Salir");
        println("Seleccione una opción: ");

        when (readLine()?.toIntOrNull()) {
            1 -> menuCrear(autorService, libroService)
            2 -> menuLeer(autorService, libroService)
            3 -> menuActualizar(autorService, libroService)
            4 -> menuEliminar(autorService, libroService)
            5 -> mostrarContenidos(libroService, autorService)
            0 -> {
                println("Saliendo del programa. ¡Hasta luego!");
                break
            }
            else -> println("Opción no válida, intente nuevamente");
        }
    }
}

// Funciones
fun menuCrear(autorService: AutorService, libroService: LibroService) {
    println("\n--- Crear ---")
    println("1. Crear Autor")
    println("2. Crear Libro")
    print("Seleccione una opción: ")

    when (readLine()?.toIntOrNull()) {
        1 -> crearAutor(autorService)
        2 -> crearLibro(libroService, autorService)
        else -> println("Opción no válida.")
    }
}

fun crearAutor(autorService: AutorService) {
    var continuar = true
    while (continuar) {
        println("\nIngrese los datos del nuevo autor (escriba 'cancelar' en cualquier campo para salir):")

        print("ID: ")
        val idInput = readLine()
        if (idInput.equals("cancelar", ignoreCase = true)) {
            println("Creación de autor cancelada.")
            continuar = false
            return
        }
        val idAutor = idInput?.toIntOrNull()
        if (idAutor == null) {
            println("ID inválido. Intente nuevamente.")
            continue
        }

        if (autorService.obtenerAutorPorId(idAutor) != null) {
            println("El ID del autor ya existe. Intente con otro ID.")
            continue
        }

        print("Nombre: ")
        val nombre = readLine()
        if (nombre.equals("cancelar", ignoreCase = true)) {
            println("Creación de autor cancelada.")
            continuar = false
            return
        }
        if (nombre.isNullOrBlank()) {
            println("El nombre no puede estar vacío.")
            continue
        }

        print("Apellido: ")
        val apellido = readLine()
        if (apellido.equals("cancelar", ignoreCase = true)) {
            println("Creación de autor cancelada.")
            continuar = false
            return
        }
        if (apellido.isNullOrBlank()) {
            println("El apellido no puede estar vacío.")
            continue
        }

        print("Nacionalidad: ")
        val nacionalidad = readLine()
        if (nacionalidad.equals("cancelar", ignoreCase = true)) {
            println("Creación de autor cancelada.")
            continuar = false
            return
        }
        if (nacionalidad.isNullOrBlank()) {
            println("La nacionalidad no puede estar vacía.")
            continue
        }

        print("Fecha de nacimiento (YYYY-MM-DD): ")
        val fechaNacimiento = readLine()
        if (fechaNacimiento.equals("cancelar", ignoreCase = true)) {
            println("Creación de autor cancelada.")
            continuar = false
            return
        }
        if (fechaNacimiento.isNullOrBlank()) {
            println("La fecha de nacimiento no puede estar vacía.")
            continue
        }

        print("¿Sigue vivo? (true/false): ")
        val sigueVivoInput = readLine()
        if (sigueVivoInput.equals("cancelar", ignoreCase = true)) {
            println("Creación de autor cancelada.")
            continuar = false
            return
        }
        val sigueVivo = sigueVivoInput?.toBoolean() ?: false

        val autor = Autor(idAutor, nombre, apellido, nacionalidad, fechaNacimiento, sigueVivo)
        autorService.crearAutor(autor)
        println("Autor creado correctamente.")
        continuar = false
    }
}

fun crearLibro(libroService: LibroService, autorService: AutorService) {
    var continuar = true
    while (continuar) {
        println("\nIngrese los datos del nuevo libro (escriba 'cancelar' en cualquier campo para salir):")

        print("ID: ")
        val idInput = readLine()
        if (idInput.equals("cancelar", ignoreCase = true)) {
            println("Creación de libro cancelada.")
            continuar = false
            return
        }
        val idLibro = idInput?.toIntOrNull()
        if (idLibro == null) {
            println("ID inválido. Intente nuevamente.")
            continue
        }

        if (libroService.obtenerLibroPorId(idLibro) != null) {
            println("El ID del libro ya existe. Intente con otro ID.")
            continue
        }

        print("Título: ")
        val titulo = readLine()
        if (titulo.equals("cancelar", ignoreCase = true)) {
            println("Creación de libro cancelada.")
            continuar = false
            return
        }
        if (titulo.isNullOrBlank()) {
            println("El título no puede estar vacío.")
            continue
        }

        print("Año de publicación: ")
        val anioPublicacionInput = readLine()
        if (anioPublicacionInput.equals("cancelar", ignoreCase = true)) {
            println("Creación de libro cancelada.")
            continuar = false
            return
        }
        val anioPublicacion = anioPublicacionInput?.toIntOrNull()
        if (anioPublicacion == null) {
            println("Año de publicación inválido. Intente nuevamente.")
            continue
        }

        print("Género: ")
        val genero = readLine()
        if (genero.equals("cancelar", ignoreCase = true)) {
            println("Creación de libro cancelada.")
            continuar = false
            return
        }
        if (genero.isNullOrBlank()) {
            println("El género no puede estar vacío.")
            continue
        }

        print("Precio: ")
        val precioInput = readLine()
        if (precioInput.equals("cancelar", ignoreCase = true)) {
            println("Creación de libro cancelada.")
            continuar = false
            return
        }
        val precio = precioInput?.toDoubleOrNull()
        if (precio == null) {
            println("Precio inválido. Intente nuevamente.")
            continue
        }

        print("IDs de autores separados por punto y coma (;): ")
        val autoresInput = readLine()
        if (autoresInput.equals("cancelar", ignoreCase = true)) {
            println("Creación de libro cancelada.")
            continuar = false
            return
        }
        val autorIds = autoresInput?.split(";")?.mapNotNull { it.toIntOrNull() } ?: emptyList()
        val autores = autorIds.mapNotNull { autorService.obtenerAutorPorId(it) }
        if (autores.isEmpty()) {
            println("Debe seleccionar al menos un autor válido.")
            continue
        }

        val libro = Libro(idLibro, titulo, anioPublicacion, genero, precio, autores)
        libroService.crearLibro(libro)
        println("Libro creado correctamente.")
        continuar = false
    }
}

fun menuLeer(autorService: AutorService, libroService: LibroService) {
    println("\n--- Leer ---")
    println("1. Leer Autores")
    println("2. Leer Libros")
    print("Seleccione una opción: ")

    when (readLine()?.toIntOrNull()) {
        1 -> {
            println("\nAutores registrados:")
            autorService.obtenerAutores().forEach { autor ->
                println(
                    "Autor(idAutor=${autor.idAutor}, nombre=${autor.nombre}, apellido=${autor.apellido}, " +
                            "nacionalidad=${autor.nacionalidad}, fechaNacimiento=${autor.fechaNacimiento}, " +
                            "sigueVivo=${autor.sigueVivo}, cantidadLibros=${autor.cantidadLibros ?: "No disponible"})"
                )
            }
        }
        2 -> {
            println("\nLibros registrados:")
            libroService.obtenerLibros().forEach { libro ->
                val autores = libro.autores.joinToString(", ") { "${it.nombre} ${it.apellido}" }
                println(
                    "Libro(idLibro=${libro.idLibro}, titulo=${libro.titulo}, anioPublicacion=${libro.anioPublicacion}, " +
                            "genero=${libro.genero}, precio=${libro.precio}, autores=[$autores])"
                )
            }
        }
        else -> println("Opción no válida.")
    }
}


fun menuActualizar(autorService: AutorService, libroService: LibroService) {
    println("\n--- Actualizar ---")
    println("1. Actualizar Autor")
    println("2. Actualizar Libro")
    print("Seleccione una opción: ")

    when (readLine()?.toIntOrNull()) {
        1 -> {
            print("Ingrese el ID del autor a actualizar (o escriba 'cancelar' para salir): ")
            val entrada = readLine()
            if (entrada.equals("cancelar", ignoreCase = true)) {
                println("Operación cancelada.")
                return
            }
            val idAutor = entrada?.toIntOrNull() ?: 0
            val autor = autorService.obtenerAutorPorId(idAutor)
            if (autor != null) {
                println("Datos actuales: $autor")
                print("Nuevo nombre (o presione 'Enter' para conservar el valor actual): ")
                val nombre = readLine()?.takeIf { it.isNotBlank() } ?: autor.nombre

                print("Nuevo apellido (o presione 'Enter' para conservar el valor actual): ")
                val apellido = readLine()?.takeIf { it.isNotBlank() } ?: autor.apellido

                print("Nueva nacionalidad (o presione 'Enter' para conservar el valor actual): ")
                val nacionalidad = readLine()?.takeIf { it.isNotBlank() } ?: autor.nacionalidad

                print("Nueva fecha de nacimiento (o presione 'Enter' para conservar el valor actual): ")
                val fechaNacimiento = readLine()?.takeIf { it.isNotBlank() } ?: autor.fechaNacimiento

                print("¿Sigue vivo? (actual: ${autor.sigueVivo}) (true/false, 'cancelar' para salir): ")
                val sigueVivoInput = readLine()
                if (sigueVivoInput.equals("cancelar", ignoreCase = true)) {
                    println("Operación cancelada.")
                    return
                }
                val sigueVivo = sigueVivoInput?.toBoolean() ?: autor.sigueVivo

                print("Nueva cantidad de libros (actual: ${autor.cantidadLibros}) (o presione 'Enter' para conservar el valor actual): ")
                val cantidadLibrosInput = readLine()
                val cantidadLibros = cantidadLibrosInput?.toIntOrNull() ?: autor.cantidadLibros ?: 0

                autorService.actualizarAutor(
                    idAutor,
                    Autor(
                        idAutor,
                        nombre,
                        apellido,
                        nacionalidad,
                        fechaNacimiento,
                        sigueVivo,
                        cantidadLibros
                    )
                )
                println("Autor actualizado correctamente.")
            } else {
                println("Autor no encontrado.")
            }
        }
        2 -> {
            print("Ingrese el ID del libro a actualizar (o escriba 'cancelar' para salir): ")
            val entrada = readLine()
            if (entrada.equals("cancelar", ignoreCase = true)) {
                println("Operación cancelada.")
                return
            }
            val idLibro = entrada?.toIntOrNull() ?: 0
            val libro = libroService.obtenerLibroPorId(idLibro)
            if (libro != null) {
                println("Datos actuales: $libro")
                print("Nuevo título (o presione 'Enter' para conservar el valor actual): ")
                val titulo = readLine()?.takeIf { it.isNotBlank() } ?: libro.titulo

                print("Nuevo año de publicación (o presione 'Enter' para conservar el valor actual): ")
                val anioPublicacion = readLine()?.toIntOrNull() ?: libro.anioPublicacion

                print("Nuevo género (o presione 'Enter' para conservar el valor actual): ")
                val genero = readLine()?.takeIf { it.isNotBlank() } ?: libro.genero

                print("Nuevo precio (o presione 'Enter' para conservar el valor actual): ")
                val precio = readLine()?.toDoubleOrNull() ?: libro.precio

                print("IDs de nuevos autores separados por punto y coma (actual: ${libro.autores.map { it.idAutor }}): ")
                val autoresInput = readLine()
                if (autoresInput.equals("cancelar", ignoreCase = true)) {
                    println("Operación cancelada.")
                    return
                }
                val autorIds = autoresInput?.split(";")?.mapNotNull { it.toIntOrNull() } ?: libro.autores.map { it.idAutor }
                val autores = autorIds.mapNotNull { autorService.obtenerAutorPorId(it) }

                libroService.actualizarLibro(
                    idLibro,
                    Libro(idLibro, titulo, anioPublicacion, genero, precio, autores)
                )
                println("Libro actualizado correctamente.")
            } else {
                println("Libro no encontrado.")
            }
        }
        else -> println("Opción no válida.")
    }
}

fun menuEliminar(autorService: AutorService, libroService: LibroService) {
    println("\n--- Eliminar ---")
    println("1. Eliminar Autor")
    println("2. Eliminar Libro")
    print("Seleccione una opción: ")

    when (readLine()?.toIntOrNull()) {
        1 -> {
            print("Ingrese el ID del autor a eliminar: ")
            val idAutor = readLine()?.toIntOrNull() ?: run {
                println("ID inválido. Operación cancelada.")
                return
            }

            val autor = autorService.obtenerAutorPorId(idAutor)
            if (autor == null) {
                println("Autor no encontrado. Operación cancelada.")
                return
            }

            println("¿Está seguro de que desea eliminar el siguiente autor?")
            println("Autor: $autor")
            print("Escriba 'si' o 'sí' para confirmar o cualquier otra tecla para cancelar: ")
            val confirmacion = readLine()
            if (confirmacion.equals("si", ignoreCase = true) || confirmacion.equals("sí", ignoreCase = true)) {
                autorService.eliminarAutor(idAutor)
                println("Autor eliminado correctamente.")
            } else {
                println("Operación cancelada.")
            }
        }
        2 -> {
            print("Ingrese el ID del libro a eliminar: ")
            val idLibro = readLine()?.toIntOrNull() ?: run {
                println("ID inválido. Operación cancelada.")
                return
            }

            val libro = libroService.obtenerLibroPorId(idLibro)
            if (libro == null) {
                println("Libro no encontrado. Operación cancelada.")
                return
            }

            println("¿Está seguro de que desea eliminar el siguiente libro?")
            println("Libro: $libro")
            print("Escriba 'si' o 'sí' para confirmar o cualquier otra tecla para cancelar: ")
            val confirmacion = readLine()
            if (confirmacion.equals("si", ignoreCase = true) || confirmacion.equals("sí", ignoreCase = true)) {
                libroService.eliminarLibro(idLibro)
                println("Libro eliminado correctamente.")
            } else {
                println("Operación cancelada.")
            }
        }
        else -> println("Opción no válida.")
    }
}

fun mostrarContenidos(libroService: LibroService, autorService: AutorService) {
    println("\n--- Mostrar Contenidos ---")

    // Encabezado de autores
    println("Autores:")
    println("+----+-------------------+-------------------+--------------+------------+----------+--------------+")
    println("| ID | Nombre            | Apellido          | Nacionalidad | Fecha Nac. | Sigue V. | Cant. Libros |")
    println("+----+-------------------+-------------------+--------------+------------+----------+--------------+")
    autorService.obtenerAutores().forEach { autor ->
        println(
            String.format(
                "| %-2d | %-17s | %-17s | %-12s | %-10s | %-8s | %-12d |",
                autor.idAutor,
                autor.nombre.padEnd(17),
                autor.apellido.padEnd(17),
                autor.nacionalidad.padEnd(12),
                autor.fechaNacimiento.padEnd(10),
                autor.sigueVivo.toString().padEnd(8),
                autor.cantidadLibros ?: 0
            )
        )
    }
    println("+----+-------------------+-------------------+--------------+------------+----------+--------------+")

    // Encabezado de libros
    println("\nLibros:")
    println("+----+-------------------------------------------+----------+---------------------------------+--------+--------------------------------------+")
    println("| ID | Título                                    | Año Pub. | Género                          | Precio | Autores                              |")
    println("+----+-------------------------------------------+----------+---------------------------------+--------+--------------------------------------+")
    libroService.obtenerLibros().forEach { libro ->
        val autores = libro.autores.joinToString(", ") { "${it.nombre} ${it.apellido}" }
        val autoresAjustados = ajustarTexto(autores, 34) // Ajustar texto largo para autores

        // Obtener autores actualizados desde el servicio de autores
        val autoresActualizados = libro.autores.mapNotNull { autorService.obtenerAutorPorId(it.idAutor) }
        val nombresAutores = autoresActualizados.joinToString(", ") { "${it.nombre} ${it.apellido}" }

        println(
            String.format(
                "| %-2d | %-41s | %-8d | %-25s | %-6.2f | %-36s |",
                libro.idLibro,
                libro.titulo.padEnd(31),
                libro.anioPublicacion,
                libro.genero.padEnd(31),
                libro.precio,
                nombresAutores.padEnd(36),
                autoresAjustados.first()
            )
        )
        if (autoresAjustados.size > 1) {
            autoresAjustados.drop(1).forEach { linea ->
                println(
                    String.format(
                        "| %-2s | %-31s | %-8s | %-25s | %-6s | %-34s |",
                        "", "", "", "", "", linea
                    )
                )
            }
        }
    }
    println("+----+-------------------------------------------+----------+---------------------------------+--------+--------------------------------------+")
}

/**
 * Ajusta el texto largo para que no exceda el ancho máximo de la columna, dividiéndolo en varias líneas si es necesario.
 */
fun ajustarTexto(texto: String, anchoMax: Int): List<String> {
    val lineas = mutableListOf<String>()
    var actual = texto
    while (actual.length > anchoMax) {
        val corte = actual.substring(0, anchoMax)
        val espacio = corte.lastIndexOf(" ")
        val linea = if (espacio > 0) corte.substring(0, espacio) else corte
        lineas.add(linea)
        actual = actual.substring(linea.length).trimStart()
    }
    if (actual.isNotEmpty()) {
        lineas.add(actual)
    }
    return lineas
}
