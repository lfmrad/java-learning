import model.*

val listaTrabajadores: MutableList<Trabajador> = mutableListOf()
val errMsg = "Entrada inválida. Introduce de nuevo."
val typeMsg = "Selecciona tipo: 1. Asalariado, 2. Autónomo, 3. Jefe"
var exitProgram: Boolean = false

fun main() {
    println("Bienvenido al sistema gestor de RRHH")
    println()

    do {
        println("1. Añadir trabajador.")
        println("2. Listar trabajadores.")
        println("3. Mostrar datos del trabajador.")
        println("4. Despedir trabajador.")
        println("5. Cerrar sistema gestor.")
        println()
        val selectedOption = getValidInt("Selecciona una opción: ")
        when (selectedOption) {
            1 -> { registrarTrabajador() }
            2 -> { listarTrabajadores() }
            3 -> { mostrarDatosTrabajador() }
            4 -> { despedirTrabajador() }
            5 -> {
                exitProgram = true
                println("Cerrando sistema...")
            }
            else -> {println(errMsg)}
        }
    } while (!exitProgram)
}

fun registrarTrabajador() {
    val newTrabajador: Trabajador
    while(true) {
        val selectedOption = getValidInt(typeMsg)
        if (selectedOption !in 1..3) {
            println(errMsg)
            continue
        } else if (selectedOption == 3 && listaTrabajadores.filterIsInstance<Jefe>().isNotEmpty()) {
            println("Ya existe un jefe. Introduzca un tipo de trabajador diferente.")
            continue
        }
        println("Introduce los datos básicos del nuevo trabajador: ")
        val nombre = getText("Introduce nombre: ")
        val apellido = getText("Introduce apellido: ")
        val dni = getText("Introduce DNI: ")
        if (Trabajador.isIdAlreadyRegistered(dni)) {
            println("Ya hay un trabajador con este DNI. Introduce un trabajador diferente diferente.")
            continue
        }

        when (selectedOption) {
            1 -> {
                val sueldo = getValidInt("Introduce sueldo bruto anual: ")
                val numPagas = getValidInt("Introduce número de pagas: ")
                newTrabajador = Asalariado(nombre, apellido, dni, sueldo, numPagas, true)
                break
            }

            2 -> {
                val sueldo = getValidInt("Introduce sueldo bruto anual: ")
                newTrabajador = Autonomo(nombre, apellido, dni, sueldo, true)
                break
            }
            3 -> {
                val beneficio = getValidInt("Introduce beneficio: ")
                val acciones = getValidInt("Introduce número de acciones: ")
                newTrabajador = Jefe(nombre, apellido, dni, beneficio, acciones)
                break

            }
        }
    }
    listaTrabajadores.add(newTrabajador)
    println("Trabajador añadido con éxito: " + newTrabajador.toString())
}

fun listarTrabajadores() {
    println("Listar trabajadores: ")
    val selectedOption = getValidInt(typeMsg)
    when (selectedOption) {
        1 -> { listarTrabajadoresPorTipo(Asalariado::class.java) }
        2 -> { listarTrabajadoresPorTipo(Autonomo::class.java) }
        3 -> { listarTrabajadoresPorTipo(Jefe::class.java) }
        else -> { println(errMsg) }
    }
}

private fun <T> listarTrabajadoresPorTipo(type: Class<T>) {
    val filteredList = listaTrabajadores.filterIsInstance(type)

    if (filteredList.isNotEmpty()) {
        println("'${filteredList.size}' trabajadores tipo ${type.simpleName} registrados: ")
        listaTrabajadores.filterIsInstance(type).forEach { println(it) }
    } else {
        println("No hay ningún trabajador tipo '${type.simpleName}' registrado.")
    }
}

fun mostrarDatosTrabajador() {
    Trabajador.registrationsSummary()
    while(true) {
        val dni = getText("Introduzca el DNI del trabajador solicitado: ")
        if (Trabajador.isIdAlreadyRegistered(dni)) {
            println(Trabajador.getById(dni).toString())
            break
        } else {
            println("Trabajador no registrado.")
        }
    }
}

fun despedirTrabajador() {
    Trabajador.registrationsSummary()
    while(true) {
        val dniJefe = getText("Introduce el DNI del jefe al que quieras asignar la tarea despido:")
        if (Trabajador.isIdAlreadyRegistered(dniJefe)) {
            val candidatoJefe = Trabajador.getById(dniJefe)!!
            if (candidatoJefe is Jefe) {
                while(true) {
                    val dniDespido = getText("Introduce el DNI del trabajador a despedir.")
                    if (Trabajador.isIdAlreadyRegistered(dniDespido) && !dniDespido.equals(dniJefe)) {
                        val candidatoDespido = Trabajador.getById(dniDespido)!!
                        val jefe = candidatoJefe as Jefe
                        jefe.despedirTrabajador(candidatoDespido, listaTrabajadores)
                        return
                    } else {
                        println("DNI inválido.")
                    }
                }
            } else {
                println("NO has seleccionado un jefe. Solo los jefes pueden despedir.")
                continue
            }
        } else {
            println("No existe un trabajador para el dni proporcioando.")
            continue
        }
    }
}

fun getText(userMsg: String) : String {
    println(userMsg)
    return readLine()!!
}

fun getValidInt(userMsg: String) : Int {
    while(true) {
        println(userMsg)
        val userInput = readln().toIntOrNull()
        if (userInput != null) {
            return userInput
        } else {
            println(errMsg)
        }
    }
}
