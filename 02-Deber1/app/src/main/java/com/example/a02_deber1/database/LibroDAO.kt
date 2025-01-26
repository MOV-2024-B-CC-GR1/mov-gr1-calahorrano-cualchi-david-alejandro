package com.example.a02_deber1.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.a02_deber1.models.Libro

class LibroDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // Método para insertar un libro
    fun insertarLibro(libro: Libro): Long {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_TITULO, libro.titulo)
            put(DatabaseHelper.COLUMN_GENERO, libro.genero)
            put(DatabaseHelper.COLUMN_ANIO_PUBLICACION, libro.anioPublicacion)
            put(DatabaseHelper.COLUMN_PRECIO, libro.precio)
            put(DatabaseHelper.COLUMN_ID_AUTOR, libro.idAutor) // Relación con autor
        }
        return db.insert(DatabaseHelper.TABLE_LIBRO, null, values)
    }

    // Método para obtener todos los libros
    fun obtenerTodosLosLibros(): List<Libro> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_LIBRO, null, null, null, null, null, null)
        val libros = mutableListOf<Libro>()

        if (cursor.moveToFirst()) {
            do {
                val libro = Libro(
                    idLibro = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_LIBRO)),
                    titulo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITULO)),
                    anioPublicacion = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ANIO_PUBLICACION)),
                    genero = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENERO)),
                    precio = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRECIO)),
                    idAutor = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_AUTOR))
                )
                libros.add(libro)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return libros
    }

    // Método para obtener libros por autor
    fun obtenerLibrosPorAutor(idAutor: Int): List<Libro> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_LIBRO,
            null,
            "${DatabaseHelper.COLUMN_ID_AUTOR} = ?",
            arrayOf(idAutor.toString()),
            null, null, null
        )

        val libros = mutableListOf<Libro>()
        if (cursor.moveToFirst()) {
            do {
                val libro = Libro(
                    idLibro = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_LIBRO)),
                    titulo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITULO)),
                    anioPublicacion = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ANIO_PUBLICACION)),
                    genero = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENERO)),
                    precio = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRECIO)),
                    idAutor = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_AUTOR))
                )
                libros.add(libro)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return libros
    }

    // Método para borrar un libro por ID
    fun borrarLibroPorId(idLibro: Int): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.delete(
            DatabaseHelper.TABLE_LIBRO,
            "${DatabaseHelper.COLUMN_ID_LIBRO} = ?",
            arrayOf(idLibro.toString())
        )
    }

    // Método para actualizar un libro
    fun actualizarLibro(libro: Libro): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_TITULO, libro.titulo)
            put(DatabaseHelper.COLUMN_GENERO, libro.genero)
            put(DatabaseHelper.COLUMN_ANIO_PUBLICACION, libro.anioPublicacion)
            put(DatabaseHelper.COLUMN_PRECIO, libro.precio)
            put(DatabaseHelper.COLUMN_ID_AUTOR, libro.idAutor)
        }
        return db.update(
            DatabaseHelper.TABLE_LIBRO,
            values,
            "${DatabaseHelper.COLUMN_ID_LIBRO} = ?",
            arrayOf(libro.idLibro.toString())
        )
    }

    // Método para eliminar todos los libros
    fun borrarTodosLosLibros(): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.delete(DatabaseHelper.TABLE_LIBRO, null, null)
    }

    // Método para contar el número de libros
    fun contarLibros(): Int {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM ${DatabaseHelper.TABLE_LIBRO}", null)
        val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
        cursor.close()
        return count
    }
}