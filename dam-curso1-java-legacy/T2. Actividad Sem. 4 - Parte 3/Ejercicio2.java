/* Luis Fernando Martínez Andreu
Actividad Semana 4 Parte 3
2. Definir el programa en Java necesario para intercambiar los valores de dos variables numéricas.
(Subir archivo: Ejercicio2.java) */

import java.util.Scanner;

public class Ejercicio2
{
    public static void main(String[] args) {

        System.out.println("Introduce dos números para ser intercambiados...");

        System.out.println("Introduce el primer número:");
        double a = getNumber();

        System.out.println("Introduce el segundo número:");
        double b = getNumber();

        System.out.println("Números introducidos: " + a + " y  " + b);

        double aux = a;
        a = b; 
        b = aux;

        System.out.println("Valores intercambiados: " + a + " y  " + b);
    }

    static double getNumber() {

        Scanner in = new Scanner(System.in);

        while (!in.hasNextDouble()) {
            System.out.println("¡No has introducido un número!");
            in.next();
        }
        return in.nextDouble();
    }
}