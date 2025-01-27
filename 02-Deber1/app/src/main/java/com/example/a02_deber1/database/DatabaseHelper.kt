package com.example.a02_deber1.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // Información de la base de datos
        const val DATABASE_NAME = "libros_autores.db"
        const val DATABASE_VERSION = 1

        // Tabla Autor
        const val TABLE_AUTOR = "Autor"
        const val COLUMN_ID_AUTOR = "idAutor"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APELLIDO = "apellido"
        const val COLUMN_NACIONALIDAD = "nacionalidad"
        const val COLUMN_FECHA_NACIMIENTO = "fechaNacimiento"
        const val COLUMN_SIGUE_VIVO = "sigueVivo"

        // Tabla Libro
        const val TABLE_LIBRO = "Libro"
        const val COLUMN_ID_LIBRO = "idLibro"
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_ANIO_PUBLICACION = "anioPublicacion"
        const val COLUMN_GENERO = "genero"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_ID_AUTOR_LIBRO = "idAutor"
    }

    // Habilitar restricciones de claves foráneas
    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true) // Activa las restricciones de claves foráneas
    }

    // Crear las tablas
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            createTableAutor(db)
            createTableLibro(db)
            android.util.Log.d("DatabaseHelper", "Base de datos creada exitosamente.")
        } catch (e: Exception) {
            android.util.Log.e("DatabaseHelper", "Error al crear las tablas", e)
        }
    }

    // Actualizar la base de datos
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_LIBRO")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_AUTOR")
        onCreate(db)
        android.util.Log.d("DatabaseHelper", "Base de datos actualizada de versión $oldVersion a $newVersion.")
    }

    // Crear tabla Autor
    private fun createTableAutor(db: SQLiteDatabase?) {
        val createTableAutor = """
            CREATE TABLE $TABLE_AUTOR (
                $COLUMN_ID_AUTOR INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_APELLIDO TEXT NOT NULL,
                $COLUMN_NACIONALIDAD TEXT,
                $COLUMN_FECHA_NACIMIENTO TEXT,
                $COLUMN_SIGUE_VIVO INTEGER
            );
        """.trimIndent()
        db?.execSQL(createTableAutor)
    }

    // Crear tabla Libro
    private fun createTableLibro(db: SQLiteDatabase?) {
        val createTableLibro = """
            CREATE TABLE $TABLE_LIBRO (
                $COLUMN_ID_LIBRO INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITULO TEXT NOT NULL,
                $COLUMN_ANIO_PUBLICACION INTEGER,
                $COLUMN_GENERO TEXT,
                $COLUMN_PRECIO REAL,
                $COLUMN_ID_AUTOR_LIBRO INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_ID_AUTOR_LIBRO) REFERENCES $TABLE_AUTOR($COLUMN_ID_AUTOR) ON DELETE CASCADE
            );
        """.trimIndent()
        db?.execSQL(createTableLibro)
    }
}