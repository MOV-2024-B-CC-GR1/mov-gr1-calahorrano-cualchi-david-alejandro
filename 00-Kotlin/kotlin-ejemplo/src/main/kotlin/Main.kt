package org.example

import java.util.*

//  fun main(args: Array<String>)
fun main() {
    println("Hello World!");
    println("-----------------------------------------------------------------------------");
    // IMMUTABLES (No se RE ASIGNA "=")
    val inmutable: String = "David";
    println(inmutable);
    // inmutable = "David" // Error!

    //MUTABLES
    var mutable: String = "Alejandro";
    mutable = "David"; //Ok
    println(mutable);
    //VAL > VAR


    // Duck Typing
    val ejemploVariable = "David Calahorrano";
    ejemploVariable.trim()
    val edadEjemplo: Int = 22;
    // ejemploVariable = edadEjemplo // Error!

    // Variables primitivas
    val nombreProfesor: String = "Adrian Eguez";
    val sueldo: Double = 1.2;
    val estadoCivil: Char = 'C';
    val mayorEdad: Boolean = true;

    // Clases en Java
    val fechaNacimiento: Date = Date();

    // When (Switch)
    val estadoCivilWhen = "S";
    when (estadoCivilWhen) {
        ("S") -> {
            println("Soltero");
        }
        "C" -> {
            println("Casado");
        }
        "D" -> {
            println("Divorciado");
        }
        else -> {
            println("No definido");
        }
    }

    val esSoltero = (estadoCivilWhen == "S");
    val coqueteo = if (esSoltero) "Si" else "No" //if else chiquito
//    println(
//        when (esSoletro) {
//            true -> "Es soltero"
//            false -> "No es soltero"
//        }
//    )
    imprimirNombre("David");

    // Viernes
    println();
    println("Friday, 25th October, 2024. Topic: Funciones");
    println("-----------------------------------------------------------------------------");
    calcularSueldo(10.00); //Solo parametro requerido
    calcularSueldo(10.00, 15.00, 20.00); // Parametro requerido y sobreescribir parametros opcionales
    // Named parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00); // Usando el parametro bonoEspecial en 20.00
                                                        // Gracias a los parametros nombrados en 2da posicion
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00);
    // Usando el parametro bonoEspecial en 1ra posicion
    // Usando el parametro sueldo en 2da posicion
    // Usando el parametro tasa en 3era posicion
    // Gracias a los parametros nombradas
}

fun imprimirNombre(nombre: String): Unit {
    fun otraFuncionAdentro(){
        println("Otra funcion adentro");
    }

    println("Nombre: $nombre"); // Uso sin llaves
    println("Nombre: ${nombre}"); // Uso con llaves opcional
    println("Nombre: ${nombre + nombre}"); // Uso con llaves (concatenacion)
    println("Nombre: ${nombre.toString()}"); // Uso con llaves (funcion)
    println("Nombre: $nombre.toString()"); // INCORRECTO!

    // (No pueden usar sin llaves)
    otraFuncionAdentro()
}

// Viernes 25 de octubre de 2024
// Tema: Funciones
fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null //Opcional (nullable)
    // Variable? -> "?" Es Nullable (osea que puede en algun momento ser nulo
): Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null) {
        return sueldo * (100/tasa);
    }else{
        return sueldo * (100/tasa) * bonoEspecial;
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int;
    private val numeroDos: Int;
    constructor(
        uno: Int,
        dos: Int
    ) {
        this.numeroUno = uno;
        this.numeroDos = dos;
        println("Inicializando");
    }
}

abstract class Numeros( // Constructor Primario
    // Caso 1: Parametro normal
    // uno: Int, (parametro (sin modificador acceso))

    // Caso 2: Parametro y propiedad (atributo) (parametro)
    // private var uno: Int (propiedad "instancia.uno")
    protected val numeroUno: Int, // instancia.numeroUno
    protected val numeroDos: Int, // instancia.numeroDos
    parametroNoUsadoNoPropiedadDeLaClase: Int? = null
){
    init { // Bloque constructor primario OPCIONAL
        this.numeroUno;
        this.numeroDos;
        println("Inicializando");
    }
}

class Suma( // Constructor Primario
    unoParametro: Int, // Parametro
    dosParametro: Int  // Parametros
): Numeros( // Clase papa, Nuemros (extendiendo)
    unoParametro,
    dosParametro) {
    
}