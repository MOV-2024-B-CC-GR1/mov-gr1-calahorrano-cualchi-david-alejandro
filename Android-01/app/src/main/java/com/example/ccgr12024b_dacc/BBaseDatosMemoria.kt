package com.example.ccgr12024b_dacc

class BBaseDatosMemoria {
    companion object {
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador(1, "David", "a@a.com"))
            arregloBEntrenador.add(BEntrenador(2, "Alejandro", "b@b.com"))
            arregloBEntrenador.add(BEntrenador(3, "Angela", "c@c.com"))
        }
    }
}