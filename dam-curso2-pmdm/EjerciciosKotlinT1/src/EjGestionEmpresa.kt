import model.*

class EjGestionEmpresa {

    fun registrarTrabajador() {
        // WIP: pending data validation, etc
        println("Selecciona tipo: 1. Asalariado, 2. Autónomo, 3. Jefe")
        val selectedOption = readln().toInt()
        var newTrabajador: Trabajador
        println("Introduce nombre: ")
        val nombre = readLine()
        println("Introduce apellido: ")
        val apellido = readLine()
        println("Introduce dni")
        val dni = readLine()

        when (selectedOption) {
            1 -> {
                val sueldo = readln().toInt()
                val numPagas = readln().toInt()
                newTrabajador = Asalariado(nombre!!, apellido!!, dni!!, sueldo, numPagas, true)
            }

            2 -> {
                val sueldo = readln().toInt()
                newTrabajador = Autonomo(nombre!!, apellido!!, dni!!, sueldo, true)
            }

            3 -> {
                val beneficio = readln().toInt()
                val acciones = readln().toInt()
                newTrabajador = Jefe(nombre!!, apellido!!, dni!!, beneficio, acciones)
            }

            else -> {
                println("Selecciona una opción válida.")
            }
        }
    }
}