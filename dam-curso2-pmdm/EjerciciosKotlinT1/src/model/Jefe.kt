package model

class Jefe(nombre: String, apellido: String, dni: String, val beneficio: Int, val acciones: Int) : Trabajador(nombre, apellido, dni) {

}