package model
open class Trabajador(val nombre: String, val apellido: String, val dni: String) {
    companion object {
        private val dniMap = mutableMapOf<String, Trabajador>()

         fun isIdAlreadyRegistered(id: String) : Boolean {
            return dniMap.containsKey(id)
        }

        fun registrationsSummary() {
            println("Lista de trabajadores registrados: ")
            dniMap.forEach { (key, value) -> println("DNI: $key, Tipo: ${value::class.simpleName}, Nombre: ${value.nombre}")}
        }

        fun getById(dni: String) : Trabajador? {
            if (dniMap.containsKey(dni)) {
                return dniMap[dni]
            } else {
                return null
            }
        }

        fun clearIds() {
            dniMap.clear()
        }
    }

    // not ideal:
    // this validation would be properly done using factory methods
    // that would replace the default constructor (setting it private)
    // this is a temporary backup as I'm going to check using a getter before trying to create the object
    init {
        if (dniMap.containsKey(this.dni)) {
            throw IllegalArgumentException("Trabajador con DNI $dni ya ha sido creado.")
        } else {
            dniMap[dni] = this
        }
    }

    open fun ejecutarDespido() {
        dniMap.remove(dni)
        println("El trabajador")
    }

    override fun toString(): String {
        return "Trabajador(nombre=$nombre, apellido=$apellido, dni=$dni)"
    }
}