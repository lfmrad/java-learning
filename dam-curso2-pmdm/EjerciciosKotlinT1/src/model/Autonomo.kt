package model

class Autonomo(nombre: String, apellido: String, dni: String, val sueldo: Int, val contratado: Boolean) : Trabajador(nombre, apellido, dni) {
    override fun toString(): String {
        return "Autonomo(nombre=$nombre, apellido=$apellido, dni=$dni, sueldo=$sueldo)"
    }
}