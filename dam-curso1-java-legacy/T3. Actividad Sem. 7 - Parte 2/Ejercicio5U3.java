/* Luis Fernando Martínez Andreu
Actividad Semana 7 Parte 2
5. Escribir un programa en Java en el que pida al usuario la inserción de números hasta que inserte un 0. 
Una vez terminado, mostrar en orden los números insertados por el usuario.
(Subir archivo Ejercicio5U3.java)*/

import java.util.Scanner;

public class Ejercicio5U3 {
    static Scanner dataInput = new Scanner(System.in);

    public static void main(String[] args) {
        double number = -1;
        String inputNumbers = "Números introducidos: ";
        while (number != 0) {
            number = getNumber();
            inputNumbers += Double.toString(number) + ", ";
        }
        dataInput.close();
        System.out.println(inputNumbers);  
    }

    static double getNumber() {
        while (true) {
            System.out.println("Introduce un número: ");
            try {
                return dataInput.nextDouble();
            } catch (Exception e) {
                dataInput.nextLine();
                System.out.println("¡El valor introducido ha de ser un número!");
            } 
        }
    }
}