package com.example.a02_deber1.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Crear tabla Autor
        val createTableAutor = """
            CREATE TABLE $TABLE_AUTOR (
                idAutor INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                apellido TEXT NOT NULL,
                nacionalidad TEXT,
                fechaNacimiento TEXT,
                sigueVivo INTEGER
            );
        """.trimIndent()

        // Crear tabla Libro
        val createTableLibro = """
            CREATE TABLE $TABLE_LIBRO (
                idLibro INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                anioPublicacion INTEGER,
                genero TEXT,
                precio REAL,
                idAutor INTEGER NOT NULL,
                FOREIGN KEY (idAutor) REFERENCES $TABLE_AUTOR(idAutor) ON DELETE CASCADE
            );
        """.trimIndent()

        db?.execSQL(createTableAutor)
        db?.execSQL(createTableLibro)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_LIBRO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_AUTOR")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "libros_autores.db"
        const val DATABASE_VERSION = 1

        const val TABLE_AUTOR = "Autor"
        const val TABLE_LIBRO = "Libro"
    }
}