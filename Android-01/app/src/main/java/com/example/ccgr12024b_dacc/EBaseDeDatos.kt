package com.example.ccgr12024b_dacc

import android.content.Context

class EBaseDeDatos {
    companion object{
        var tablaEntrenador: ESqliteHelperEntrenador? = null
        // 🔹 NUEVA FUNCIÓN: Inicializar la base de datos si aún no está creada
        fun inicializarBaseDatos(context: Context) {
            if (tablaEntrenador == null) {
                tablaEntrenador = ESqliteHelperEntrenador(context)
            }
        }

        // 🔹 NUEVA FUNCIÓN: Obtener la instancia de la base de datos asegurando que está inicializada
        fun obtenerBaseDatos(context: Context): ESqliteHelperEntrenador {
            if (tablaEntrenador == null) {
                inicializarBaseDatos(context)
            }
            return tablaEntrenador!!
        }
    }
}