package model

class Jefe(nombre: String, apellido: String, dni: String, val beneficio: Int, val acciones: Int) : Trabajador(nombre, apellido, dni) {
    override fun toString(): String {
        return "Jefe(Nombre: $nombre, Apellido: $apellido, DNI: $dni, Beneficio: $beneficio, Acciones: $acciones)"
    }

    open fun despedirTrabajador(despedido: Trabajador, listaTrabajadores: MutableList<Trabajador>) {
        println("Se ha despedido al trabajador: " + despedido.toString())
        despedido.ejecutarDespido()
        listaTrabajadores.remove(despedido)
    }
}