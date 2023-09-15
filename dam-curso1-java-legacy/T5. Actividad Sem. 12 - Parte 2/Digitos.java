/* Luis Fernando Martínez Andreu
 * Actividad Semana 12 - Parte 2
 * Crear una función de forma recursiva que cuente los dígitos que tiene un número entero que se pide al usuario por teclado.
 * Subir el archivo Digitos.java
 */

import java.util.Scanner;

public class Digitos {
    static Scanner dataInput = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Introduce un número entero para contar los digitos que tiene el número.");
        System.out.println("El número tiene " + getNumberOfDigits(getValidatedInt()) + " digitos.");
        dataInput.close();
    }

    static int getNumberOfDigits(int number) {
        if (Math.abs(number) < 10) {
            return 1;
        } else {
            return 1 + getNumberOfDigits(number / 10);
        }
    }

    static int getValidatedInt() {
        while(true) {
            try {
                return dataInput.nextInt();
            } catch (Exception e) {
                dataInput.nextLine();
                System.out.println("¡Introduce un número entero!");
            }
        }
    }
}