package com.example.a02_deber1.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.a02_deber1.models.Autor
import com.example.a02_deber1.models.Libro

class AutorDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertarAutor(autor: Autor): Long {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", autor.nombre)
            put("apellido", autor.apellido)
            put("nacionalidad", autor.nacionalidad)
            put("fechaNacimiento", autor.fechaNacimiento)
            put("sigueVivo", if (autor.sigueVivo) 1 else 0)
        }
        return db.insert(DatabaseHelper.TABLE_AUTOR, null, values)
    }

    fun obtenerTodosLosAutores(): List<Autor> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_AUTOR, null, null, null, null, null, null)
        val autores = mutableListOf<Autor>()

        if (cursor.moveToFirst()) {
            do {
                val autor = Autor(
                    idAutor = cursor.getInt(cursor.getColumnIndexOrThrow("idAutor")),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido")),
                    nacionalidad = cursor.getString(cursor.getColumnIndexOrThrow("nacionalidad")),
                    fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow("fechaNacimiento")),
                    sigueVivo = cursor.getInt(cursor.getColumnIndexOrThrow("sigueVivo")) == 1,
                    libros = obtenerLibrosDeAutor(cursor.getInt(cursor.getColumnIndexOrThrow("idAutor")))
                )
                autores.add(autor)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return autores
    }

    private fun obtenerLibrosDeAutor(idAutor: Int): MutableList<Libro> {
        val libroDAO = LibroDAO(dbHelper.context)
        return libroDAO.obtenerLibrosPorAutor(idAutor).toMutableList()
    }
}