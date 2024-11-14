package org.example

import java.util.*
import kotlin.collections.ArrayList

//  fun main(args: Array<String>)
fun main() {
    println("Hello World!")
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


    println();
    println("Friday, 9th November, 2024. Topic: Constructores");
    println("-----------------------------------------------------------------------------");
    val sumaA = Suma(1, 1);
    val sumaB = Suma(null, 1);
    val sumaC = Suma(1, null);
    val sumaD = Suma(null, null);

    // Calcular
    sumaA.sumar();
    sumaB.sumar();
    sumaC.sumar();
    sumaD.sumar();

    // Imprimir
    println("Valor de pi: " + Suma.pi);
    println("Valor de elevarAlCuadrado: " + Suma.elevarAlCuadrado(2));
    println("Hstorial de los resultados: " + Suma.historialSumas);


    println();
    println("Thursday, 14th November, 2024. Topic: Tipos de arreglos");
    println("-----------------------------------------------------------------------------");
    // Arreglos
    // Estáticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3);
    println("Arreglo estatico: " + arregloEstatico);

    // Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    println("Arreglo dinamico: " + arregloDinamico);
    arregloDinamico.add(11);
    arregloDinamico.add(12);
    println("Arreglo dinamico: $arregloDinamico");


    // FOR EACH => Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach {
        valorActual: Int -> // - > = >
        println("Valor actual: ${valorActual}");
    }

    // "it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach { println("Valor actual (it): ${it}")}

    // MAP -> MUTA(Modificado cambio) el arreglo
    // 1) Enviamos el nuevo valor de la iteracion
    // 2) Nos devuelve un NUEVO ARREGLO con valores
    // de las interaciones
    val respuestaMap: List<Double> = arregloDinamico.map { valorActual: Int ->
        return@map valorActual.toDouble() + 100.00;
    }

    println(respuestaMap);
    val respuestaMapDos = arregloDinamico.map { it + 15};
    println(respuestaMapDos);

    // Filter -> Filtrar el ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo FILTRADO
    val respuestaFilter: List<Int> = arregloDinamico.filter {
        valorActual: Int ->
        // Expresion o CONDICION
        val mayoresACinco: Boolean = valorActual > 5
        return@filter mayoresACinco;
    };

    val respuestaFilterDos = arregloDinamico.filter { it <= 5 };
    println("Respuesta Filter: $respuestaFilter");
    println("Respuesta Filter Dos: $respuestaFilterDos");

    // OR AND
    // OR -> ANY (Alguna Cumple?)
    // And - ALL (Todos cumplen?)
    val respuestaAny: Boolean = arregloDinamico.any {
            valorActual: Int ->
        return@any (valorActual > 5);
    };
    println("Respuesta Any: $respuestaAny"); // True

    val respuestaAll: Boolean = arregloDinamico.all {
            valorActual: Int ->
        return@all (valorActual > 5);
    };
    println("Respuesta All: $respuestaAll"); // False


    // REDUCE -> Valor acumulado
    // Valor acumulado = 0 (Siempre empieza en 0 en Kotlin)
    // [1, 2, 3, 4, 5] -> Acumular "SUMAR" estos valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Interacion1
    // valorIteracion2 = valorAcumuladoIteracion1 + 2 = 1 + 2 = 3 -> Interacion2
    // valorIteracion3 = valorAcumuladoIteracion2 + 3 = 3 + 3 = 6 -> Interacion3
    // valorIteracion4 = valorAcumuladoIteracion3 + 4 = 6 + 4 = 10 -> Interacion4
    // valorIteracion5 = valorAcumuladoIteracion4 + 5 = 10 + 5 = 15 -> Interacion5
    val respuestaReduce: Int = arregloDinamico.reduce{
        acumulado: Int, valorActual: Int ->
        return@reduce (acumulado + valorActual); // -> Cambiar o usar la lgoica de negocio
    }
    println("Respuesta Reduce: $respuestaReduce");
    // return@reduce acumulado + (itemCarrito.cantidad + itemCarrito.precio)
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

    // Viernes 9 de noviembre de 2024
    public val soyPublicoExplicito: String = "Publicas";
    val soyPublicoImplicite: String = "Publico implicito";
    init { // Bloque constructor primario
        this.numeroUno;
        numeroUno; // this. OPCIONAL [propiedades, metodos]
        numeroDos; // this. OPCIONAL [propiedades, metodos]
        this.soyPublicoImplicite;
        soyPublicoExplicito;
    }

    constructor( // Constructor secundario
        uno: Int?, // Enteronullable
        dos: Int
    ): this(
        if (uno == null) 0 else uno,
        dos
    ){
        // Bloque de codigo de constructor secundario
    }

    constructor( // Constructor secundario
        uno: Int,
        dos: Int? // Entero nullable
    ):this(
        uno,
        if (dos == null) 0 else dos
    )

    constructor(
        uno: Int?, // Entero nullable
        dos: Int? // Entero nullable
    ):this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )

    fun sumar ():Int{
        val total = numeroUno + numeroDos;
        agregarHistorial(total);
        return total;
    }

    companion object { // Comparte entre todas las instancias, similar al STATIC
        // Funciones, variables
        // NombreClase.metodo, NombreClase.funcion ==>
        // Suma.pi
        val pi = 3.14159;

        // Suma.elevarAlCuadrado
        fun elevarAlCuadrado(num: Int): Int{
            return num * num;
        }

        val historialSumas = arrayListOf<Int>();
        fun agregarHistorial(valorTotalSuma: Int) { // Sumar.agregarHistorial
            historialSumas.add(valorTotalSuma);
        }
    }
}