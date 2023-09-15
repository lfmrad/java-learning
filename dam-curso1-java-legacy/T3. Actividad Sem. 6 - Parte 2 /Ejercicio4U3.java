/* Luis Fernando Martínez Andreu
Actividad Semana 6 Parte 2
4. Dado un número, realiza un programa que indique por pantalla en una frase sus propiedades, las cuales pueden ser:
Par o impar. Positivo o negativo. Entero o decimal. 
(Subir archivo Ejercicio4U3.java)*/

import java.util.Scanner;

public class Ejercicio4U3 {
    static Scanner dataInput = new Scanner(System.in);
    public static void main(String[] args) {
        double number = 0;
        boolean dataValidated = false;
        while (!dataValidated) {
            System.out.println("Introduce un número: ");
            try {
                number = dataInput.nextDouble();
                dataValidated = true;
            } catch (Exception e) {
                dataInput.nextLine();
            } 
        }
        dataInput.close();
        System.out.println("Número introducido: " + number);
        System.out.println(checkParity(number));
        System.out.println(checkPosNeg(number));
        System.out.println(checkIntOrDec(number));
    }

    static String checkParity(double number) {
        if (checkIntOrDec(number) == "Entero") {
            if (number % 2 == 0) {
                return "Par";
            } else {
                return "Impar";
            }
        } else {
            return "Sin paridad (no es número entero)";
        }
    }

    static String checkPosNeg(double number) {
        if (number >= 0) {
            return "Positivo";
        } else {
            return "Negativo";
        }
    }

    static String checkIntOrDec(double number) {
        if (number == (int) number) {
            return "Entero";
        } else {
            return "Decimal";

        }
    }
}