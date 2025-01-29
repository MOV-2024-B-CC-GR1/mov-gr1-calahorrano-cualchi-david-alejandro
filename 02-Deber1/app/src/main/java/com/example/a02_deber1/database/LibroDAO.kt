package com.example.a02_deber1.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.a02_deber1.models.Libro

class LibroDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // Método para insertar un libro
    fun insertarLibro(libro: Libro): Long {
        return try {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(DatabaseHelper.COLUMN_TITULO, libro.titulo)
                put(DatabaseHelper.COLUMN_ANIO_PUBLICACION, libro.anioPublicacion)
                put(DatabaseHelper.COLUMN_GENERO, libro.genero)
                put(DatabaseHelper.COLUMN_PRECIO, libro.precio)
                put(DatabaseHelper.COLUMN_ID_AUTOR_LIBRO, libro.idAutor)
            }
            db.insert(DatabaseHelper.TABLE_LIBRO, null, values)
        } catch (e: Exception) {
            Log.e("LibroDAO", "Error al insertar libro: ${e.message}")
            -1L
        }
    }

    // Método para obtener todos los libros
    fun obtenerTodosLosLibros(): List<Libro> {
        val libros = mutableListOf<Libro>()
        try {
            val db: SQLiteDatabase = dbHelper.readableDatabase
            val cursor = db.query(DatabaseHelper.TABLE_LIBRO, null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    val libro = Libro(
                        idLibro = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_LIBRO)),
                        titulo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITULO)),
                        anioPublicacion = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ANIO_PUBLICACION)),
                        genero = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENERO)),
                        precio = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRECIO)),
                        idAutor = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_AUTOR_LIBRO))
                    )
                    libros.add(libro)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e("LibroDAO", "Error al obtener todos los libros: ${e.message}")
        }
        return libros
    }

    // Método para obtener libros por autor
    fun obtenerLibrosPorAutor(idAutor: Int): List<Libro> {
        val libros = mutableListOf<Libro>()
        val db: SQLiteDatabase = dbHelper.readableDatabase

        val cursor = db.query(
            DatabaseHelper.TABLE_LIBRO,
            null,
            "${DatabaseHelper.COLUMN_ID_AUTOR_LIBRO} = ?", // Filtra por idAutor
            arrayOf(idAutor.toString()),
            null, null, null
        )

        if (cursor.moveToFirst()) {
            do {
                val libro = Libro(
                    idLibro = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_LIBRO)),
                    titulo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITULO)),
                    anioPublicacion = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ANIO_PUBLICACION)),
                    genero = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENERO)),
                    precio = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRECIO)),
                    idAutor = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_AUTOR_LIBRO))
                )
                libros.add(libro)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return libros
    }

    // Método para borrar un libro por ID
    fun borrarLibroPorId(idLibro: Int): Int {
        return try {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            db.delete(
                DatabaseHelper.TABLE_LIBRO,
                "${DatabaseHelper.COLUMN_ID_LIBRO} = ?",
                arrayOf(idLibro.toString())
            )
        } catch (e: Exception) {
            Log.e("LibroDAO", "Error al borrar libro: ${e.message}")
            0
        }
    }

    // Método para actualizar un libro
    fun actualizarLibro(libro: Libro): Int {
        return try {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(DatabaseHelper.COLUMN_TITULO, libro.titulo)
                put(DatabaseHelper.COLUMN_GENERO, libro.genero)
                put(DatabaseHelper.COLUMN_ANIO_PUBLICACION, libro.anioPublicacion)
                put(DatabaseHelper.COLUMN_PRECIO, libro.precio)
                put(DatabaseHelper.COLUMN_ID_AUTOR_LIBRO, libro.idAutor)
            }
            db.update(
                DatabaseHelper.TABLE_LIBRO,
                values,
                "${DatabaseHelper.COLUMN_ID_LIBRO} = ?",
                arrayOf(libro.idLibro.toString())
            )
        } catch (e: Exception) {
            Log.e("LibroDAO", "Error al actualizar libro: ${e.message}")
            0
        }
    }

    // Método para eliminar todos los libros
    fun borrarTodosLosLibros(): Int {
        return try {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            db.delete(DatabaseHelper.TABLE_LIBRO, null, null)
        } catch (e: Exception) {
            Log.e("LibroDAO", "Error al borrar todos los libros: ${e.message}")
            0
        }
    }

    // Método para contar el número de libros
    fun contarLibros(): Int {
        return try {
            val db: SQLiteDatabase = dbHelper.readableDatabase
            val cursor = db.rawQuery("SELECT COUNT(*) FROM ${DatabaseHelper.TABLE_LIBRO}", null)
            val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
            cursor.close()
            count
        } catch (e: Exception) {
            Log.e("LibroDAO", "Error al contar libros: ${e.message}")
            0
        }
    }
}