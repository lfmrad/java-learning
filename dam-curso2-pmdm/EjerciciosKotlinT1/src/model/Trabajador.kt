package model
open class Trabajador(val nombre: String, val apellido: String, val dni: String) {
    override fun toString(): String {
        return "Trabajador(nombre=$nombre, apellido=$apellido, dni=$dni)"
    }
}