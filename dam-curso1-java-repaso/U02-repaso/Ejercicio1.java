/* lfmrad
Actividad Semana 4 Parte 3
1. Escribir un programa en Java que, dados 2 números con decimales por el usuario, 
muestre por pantalla la suma, resta, multiplicación, división y módulo de los mismos.  
(Subir archivo: Ejercicio1.java) */

import java.util.Scanner;
import java.util.InputMismatchException;
import java.text.DecimalFormat;

public class Ejercicio1 {
    static Scanner scanner = new Scanner(System.in);
    static DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        System.out.println("Introduce dos números para calcular su suma, resta, multiplicación, división y módulo.");
        System.out.println("Introduce el primer número:");
        double a = getNumber();
        System.out.println("Introduce el segundo número:");
        double b = getNumber();
        scanner.close();

        System.out.println("Suma: " + df.format(a + b));
        System.out.println("Resta: " + df.format(a - b));
        System.out.println("Multiplicación: " + df.format(a * b));
        System.out.println("Devisión: " + df.format(a / b));
        System.out.println("Módulo: " + df.format(a % b));
    }

    static double getNumber() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("La entrada no es válida. Introduce un número decimal.");
            } finally {
                scanner.nextLine();
            }
        }
    }
}