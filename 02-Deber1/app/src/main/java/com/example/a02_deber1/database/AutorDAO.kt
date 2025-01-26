package com.example.a02_deber1.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.a02_deber1.models.Autor
import com.example.a02_deber1.models.Libro

class AutorDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // Método para insertar un autor
    fun insertarAutor(autor: Autor): Long {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NOMBRE, autor.nombre)
            put(DatabaseHelper.COLUMN_APELLIDO, autor.apellido)
            put(DatabaseHelper.COLUMN_NACIONALIDAD, autor.nacionalidad)
            put(DatabaseHelper.COLUMN_FECHA_NACIMIENTO, autor.fechaNacimiento)
            put(DatabaseHelper.COLUMN_SIGUE_VIVO, if (autor.sigueVivo) 1 else 0)
        }
        return db.insert(DatabaseHelper.TABLE_AUTOR, null, values)
    }

    // Método para obtener todos los autores
    fun obtenerTodosLosAutores(): List<Autor> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_AUTOR, null, null, null, null, null, null)
        val autores = mutableListOf<Autor>()

        if (cursor.moveToFirst()) {
            do {
                val autor = Autor(
                    idAutor = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_AUTOR)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE)),
                    apellido = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APELLIDO)),
                    nacionalidad = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NACIONALIDAD)),
                    fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_NACIMIENTO)),
                    sigueVivo = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SIGUE_VIVO)) == 1
                )
                autores.add(autor)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return autores
    }

    // Método para obtener un autor por ID
    fun obtenerAutorPorId(idAutor: Int): Autor? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_AUTOR,
            null,
            "${DatabaseHelper.COLUMN_ID_AUTOR} = ?",
            arrayOf(idAutor.toString()),
            null, null, null
        )

        var autor: Autor? = null
        if (cursor.moveToFirst()) {
            autor = Autor(
                idAutor = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_AUTOR)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE)),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APELLIDO)),
                nacionalidad = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NACIONALIDAD)),
                fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_NACIMIENTO)),
                sigueVivo = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SIGUE_VIVO)) == 1
            )
        }

        cursor.close()
        return autor
    }

    // Método para borrar un autor por ID
    fun borrarAutorPorId(idAutor: Int): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.delete(
            DatabaseHelper.TABLE_AUTOR,
            "${DatabaseHelper.COLUMN_ID_AUTOR} = ?",
            arrayOf(idAutor.toString())
        )
    }

    // Método para actualizar un autor
    fun actualizarAutor(autor: Autor): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", autor.nombre)
            put("apellido", autor.apellido)
            put("nacionalidad", autor.nacionalidad)
            put("fechaNacimiento", autor.fechaNacimiento)
            put("sigueVivo", if (autor.sigueVivo) 1 else 0)
        }
        return db.update(
            DatabaseHelper.TABLE_AUTOR,
            values,
            "idAutor = ?",
            arrayOf(autor.idAutor.toString())
        )
    }

    // Método para eliminar todos los autores
    fun borrarTodosLosAutores(): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.delete(DatabaseHelper.TABLE_AUTOR, null, null)
    }

    // Método para contar el número de autores
    fun contarAutores(): Int {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM ${DatabaseHelper.TABLE_AUTOR}", null)
        val count = if (cursor.moveToFirst()) cursor.getInt(0) else 0
        cursor.close()
        return count
    }
}