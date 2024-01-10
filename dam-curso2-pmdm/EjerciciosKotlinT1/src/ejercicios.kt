fun main() {
    greaterThan(8, 6)
    greaterThan(6, 7)
    multipleOf20And100Range(80) // yes, yes
    multipleOf20And100Range(100) // yes, no
    multipleOf20And100Range(50) // no, yes
    multipleOf20And100Range(130) // no, no
    demoEx3()
    demoEx4() // and Ex5
}
fun greaterThan(x: Int, y: Int) {
    if (x > y) {
        println("El primero es mayor que el segundo")
    } else {
        println("El primero no es mayor que el segundo")
    }
    println()
}

fun multipleOf20And100Range(x: Int) {
    val multipleOf20: Boolean = x % 20 == 0
    println("El número $x:")
    if (multipleOf20 && withinMinus100To100(x)) {
        println("Es múltiplo de 20 y está entre -100 y 100")
    } else if (multipleOf20) {
        println("Es múltiplo de 20 y no está entre -100 y 100")
    } else if (withinMinus100To100(x)) {
        println("No es múltiplo de 20 y está entre -100 y 100")
    } else {
        println("No es múltiplo de 20 y no está entre -100 y 100")
    }
    println()
}

fun withinMinus100To100(x: Int): Boolean {
    return x in 2..<100
}

fun demoEx3() {
    // en línea en vez de con salto por claridad
    for (i in 1..100) {
        val multipleOf3: Boolean = (i % 3 == 0)
        val multipleOf5: Boolean = (i % 5 == 0)
        val w1 = "fizz"
        val w2 = "buzz"
        if (multipleOf3 && multipleOf5) {
            print(w1 + w2)
        } else if (multipleOf3) {
            print(w1)
        } else if (multipleOf5) {
            print(w2)
        } else {
            print(i)
        }
        print(" ")
    }
    println()
}

fun demoEx4() {
    val random: Int = (1..20).random()
    var found = false
    do {
        println("Adivina el número generado aleatoriamente entre el 1 y el 20 (inclusive): ")
        if ((readln().toIntOrNull() ?: 0) == random) {
            found = true
            println("Enhorabuena, has acertado el número en 5 intentos")
        } else {
            println("Lo siento, inténtalo de nuevo")
        }
    } while (!found)

    // modificación ejercicio 5:
    println("Quieres volver a jugar (S/N)")
    while(true) {
        val userInput = readln()
        if (userInput == "S" || userInput == "s") {
            demoEx4()
        } else if (userInput == "N" || userInput == "n") {
            println("Juego finalizado")
            break
        } else {
            println("Entrada incorrecta. Vuelve a introducir.")
        }
    }
}

