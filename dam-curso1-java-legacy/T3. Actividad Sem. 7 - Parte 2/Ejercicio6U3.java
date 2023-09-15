/* Luis Fernando Martínez Andreu
Actividad Semana 7 Parte 2
6. Escribir un programa en Java que dado un número por teclado, escriba “El número es válido” si: 
a. El número se encuentra entre 1000 y 10000 
b. El número no es divisible por 5 ni termina en 0 En caso contrario, el texto debe ser “El número NO es válido”. 
(Subir archivo Ejercicio6U3.java)*/

import java.util.Scanner;

public class Ejercicio6U3 {
    static Scanner dataInput = new Scanner(System.in);

    public static void main(String[] args) {
        double number = getNumber();
        dataInput.close();

        boolean rangeIsOk = number >= 1000 && number <=10000 ? true : false;
        boolean isNotDivByFive = number % 5 != 0 ? true : false;
        boolean lastDigitIsNotZero = number % 10 != 0 ? true : false;

        if (rangeIsOk && isNotDivByFive && lastDigitIsNotZero) {
            System.out.println("El número es válido");
        } else {
            System.out.println("El número NO es válido");
        }
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