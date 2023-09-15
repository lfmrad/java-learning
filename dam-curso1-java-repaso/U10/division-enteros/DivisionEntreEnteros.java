/* Actividad Semana 26 - Parte 1
Escribe un programa en Java que solicite al usuario dos números enteros y realice la división del primer 
número entre el segundo número. Sin embargo, si el segundo número es igual a cero, el programa debe lanzar 
una excepción personalizada "DivisionPorCeroException". El programa debe manejar esta excepción y mostrar 
un mensaje al usuario indicando que la división entre cero no está permitida. Si el segundo número es 
distinto de cero, el programa debe mostrar el resultado de la división.

Entrega el fichero donde esté creada la excepción (DivisionPorCeroException.java)
y otro en el que contenga tanto el main como la función que dividirá los dos números 
(DivisionEntreEnteros.java).*/

import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.DecimalFormat;

public class DivisionEntreEnteros {
    static Scanner scanner = new Scanner(System.in);
    static DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
            boolean programSuccessful = false;
            do {
                System.out.println("Introduce los números enteros a y b para realizar a/b:");
                System.out.println("Introduce a:");
                int a = getInteger();
                System.out.println("Introduce b:");
                int b = getInteger();
                try {
                    System.out.println("Resultado: " + a + "/" + b + " = " + df.format(divideIntegers(a,b)));
                    programSuccessful = true;
                } catch (DivisionPorCeroException e) {
                    System.out.println(e.getMessage());
                }
            } while (!programSuccessful);
            scanner.close();
    }

    static double divideIntegers(int a, int b) throws DivisionPorCeroException {
        if (b == 0) throw new DivisionPorCeroException("¡No es posible dividir por 0!");
        return (double) a / b;
    }

    static int getInteger() {
        while(true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error de entrada: introduce un número entero.");
            } finally {
                scanner.nextLine();
            }
        }
    }
}