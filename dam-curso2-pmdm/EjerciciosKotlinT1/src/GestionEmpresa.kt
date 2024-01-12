import model.*

class GestionEmpresa {
    val listaTrabajadores: MutableList<Trabajador> = mutableListOf()

    fun registrarTrabajador() {
        println("Introduce los datos básicos del nuevo trabajador: ")
        val errMsg = "Entrada inválida. Introduce de nuevo."
        val newTrabajador: Trabajador
        val nombre = getText("Introduce nombre: ")
        val apellido = getText("Introduce apellido: ")
        val dni = getText("Introduce DNI: ")
        while(true) {
            println("Selecciona tipo: 1. Asalariado, 2. Autónomo, 3. Jefe")
            val selectedOption = readln().toIntOrNull()
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
                else -> {
                    println(errMsg)
                }
            }
        }
        listaTrabajadores.add(newTrabajador)
        println("Trabajador añadido con éxito: " + newTrabajador.toString())
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
                println("Error de entrada. Tiene que ser un número.")
            }
        }
    }
}