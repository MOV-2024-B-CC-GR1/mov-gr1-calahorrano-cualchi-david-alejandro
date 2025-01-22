package com.example.a02_deber1.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.a02_deber1.models.Libro

class LibroDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertarLibro(libro: Libro): Long {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("titulo", libro.titulo)
            put("anioPublicacion", libro.anioPublicacion)
            put("genero", libro.genero)
            put("precio", libro.precio)
            put("idAutor", libro.idAutor)
        }
        return db.insert(DatabaseHelper.TABLE_LIBRO, null, values)
    }

    fun obtenerLibrosPorAutor(idAutor: Int): List<Libro> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_LIBRO,
            null,
            "idAutor = ?",
            arrayOf(idAutor.toString()),
            null,
            null,
            null
        )
        val libros = mutableListOf<Libro>()

        if (cursor.moveToFirst()) {
            do {
                val libro = Libro(
                    idLibro = cursor.getInt(cursor.getColumnIndexOrThrow("idLibro")),
                    titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo")),
                    anioPublicacion = cursor.getInt(cursor.getColumnIndexOrThrow("anioPublicacion")),
                    genero = cursor.getString(cursor.getColumnIndexOrThrow("genero")),
                    precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio")),
                    idAutor = cursor.getInt(cursor.getColumnIndexOrThrow("idAutor"))
                )
                libros.add(libro)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return libros
    }
}