package model

class Asalariado(nombre: String, apellido: String, dni: String, val sueldo: Int, val numPagas: Int, val contratado: Boolean) : Trabajador(nombre, apellido, dni) {
    override fun toString(): String {
        return "Asalariado(Nombre: $nombre, Apellido: $apellido, DNI: $dni, Salario Anual: $sueldo, Mensual: ${ getSalarioMensual() } , Número de Pagas: $numPagas)"
    }

    fun getSalarioMensual(): String {
        return String.format("%.2f",sueldo.toDouble() / 12)
    }
}