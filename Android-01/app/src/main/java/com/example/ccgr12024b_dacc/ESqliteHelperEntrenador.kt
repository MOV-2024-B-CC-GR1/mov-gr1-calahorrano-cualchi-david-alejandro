package com.example.ccgr12024b_dacc

import android.content.ContentValues
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
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun crearEntrenador(nombre: String, descripcion: String): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresGuardar = ContentValues()
        valoresGuardar.put("nombre", nombre)
        valoresGuardar.put("descripcion", descripcion)
        val resultadoGuardar = baseDatosEscritura.insert("ENTRENADOR", null, valoresGuardar)
        baseDatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarEntrenador(id: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        // where .. ID=? and nombre=? [?=1,?=2]
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminar = baseDatosEscritura.delete("ENTRENADOR", "id=?", parametrosConsultaDelete)
        baseDatosEscritura.close()
        return resultadoEliminar != -1
    }

    fun actualizarEntrenador(nombre: String, descripcion: String, id: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizar = baseDatosEscritura.update("ENTRENADOR", valoresAActualizar, "id=?", parametrosConsultaActualizar)
        baseDatosEscritura.close()
        return resultadoActualizar != -1
    }

    fun consultarEntrenadorPorId(id: Int): BEntrenador? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = "SELECT * FROM ENTRENADOR WHERE ID = ?"
        val parametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)
        return if (resultadoConsultaLectura.moveToFirst()) {
            BEntrenador(
                resultadoConsultaLectura.getInt(0),
                resultadoConsultaLectura.getString(1),
                resultadoConsultaLectura.getString(2)
            )
        } else {
            null
        }.also { resultadoConsultaLectura.close() }
    }

    // 🔹 NUEVA FUNCIÓN: Obtener todos los entrenadores
    fun consultarTodosEntrenadores(): ArrayList<BEntrenador> {
        val listaEntrenadores = arrayListOf<BEntrenador>()
        val baseDatosLectura = readableDatabase
        val scriptConsulta = "SELECT * FROM ENTRENADOR"
        val resultadoConsulta = baseDatosLectura.rawQuery(scriptConsulta, null)
        while (resultadoConsulta.moveToNext()) {
            val entrenador = BEntrenador(
                resultadoConsulta.getInt(0),
                resultadoConsulta.getString(1),
                resultadoConsulta.getString(2)
            )
            listaEntrenadores.add(entrenador)
        }
        resultadoConsulta.close()
        return listaEntrenadores
    }

    // 🔹 NUEVA FUNCIÓN: Contar la cantidad de entrenadores en la base de datos
    fun contarEntrenadores(): Int {
        val baseDatosLectura = readableDatabase
        val scriptConsulta = "SELECT COUNT(*) FROM ENTRENADOR"
        val resultadoConsulta = baseDatosLectura.rawQuery(scriptConsulta, null)
        val total = if (resultadoConsulta.moveToFirst()) resultadoConsulta.getInt(0) else 0
        resultadoConsulta.close()
        return total
    }
}