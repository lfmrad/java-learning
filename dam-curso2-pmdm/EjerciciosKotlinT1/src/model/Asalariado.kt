package model

class Asalariado(nombre: String, apellido: String, dni: String, val sueldo: Int, val numPagas: Int, val contratado: Boolean) : Trabajador(nombre, apellido, dni) {
    override fun toString(): String {
        return "Asalariado(nombre=$nombre, apellido=$apellido, dni=$dni, sueldo=$sueldo, numPagas=$numPagas, contratado=$contratado)"
    }
}