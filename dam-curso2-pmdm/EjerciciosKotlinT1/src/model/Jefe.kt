package model

class Jefe(nombre: String, apellido: String, dni: String, val beneficio: Int, val acciones: Int) : Trabajador(nombre, apellido, dni) {
    override fun toString(): String {
        return "Asalariado(nombre=$nombre, apellido=$apellido, dni=$dni, beneficio=$beneficio, acciones=$acciones)"
    }
}