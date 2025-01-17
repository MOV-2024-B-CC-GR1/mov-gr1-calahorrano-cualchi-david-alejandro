package com.example.ccgr12024b_dacc

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto: Context? //this
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }
    override fun onUpgrade(
        db: SQLiteDatabase?,
        p1: Int,
        p2: Int
    ) {}
}