/* lfmrad
2. Definir el programa en Java necesario para intercambiar los valores de dos variables numéricas.
(Subir archivo: Ejercicio2.java) */

import java.util.Scanner;
import java.util.InputMismatchException;
import java.text.DecimalFormat;

public class Ejercicio2 {
    static Scanner scanner = new Scanner(System.in);
    static DecimalFormat df = new DecimalFormat("#.##");
    public static void main(String[] args) {
        System.out.println("Intercambiador de valores de dos variables numéricas:");
        System.out.println("Introduce el valor de la variable 'a':");
        double a = getNumber();
        System.out.println("Introduce el valor de la variable 'b':");
        double b = getNumber();
        scanner.close();
        System.out.println("'a' contiene: " + df.format(a));
        System.out.println("'a' contiene: " + df.format(b));
    
        System.out.println("\n Se intercambian...");
        double aux;
        aux = a; a = b; b = aux;

        System.out.println("'a' contiene: " + df.format(a));
        System.out.println("'a' contiene: " + df.format(b));
    }

    static double getNumber() {
        while (true) {
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Error de entrada. Introduce un valor númerico.");
            } finally {
                scanner.nextLine();
            }
        }
    }
}
