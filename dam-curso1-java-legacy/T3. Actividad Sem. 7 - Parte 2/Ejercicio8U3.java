/* Luis Fernando Martínez Andreu
Actividad Semana 7 Parte 2
8. Escribir un programa en Java que calcule si un determinado número que se pasa por teclado es primo. 
(Subir archivo Ejercicio8U3.java)*/

import java.util.Scanner;

public class Ejercicio8U3 {
    static Scanner dataInput = new Scanner(System.in);

    public static void main(String[] args) {
        int numberToCheck;
        String introMsg = "Introduce un número natural mayor que 1 para comprobar si es primo (según su definición matemática):";
        while (true) {
            numberToCheck = getIntNumber(introMsg);
            if (numberToCheck > 1) {
                break;
            }
        }
        dataInput.close();
        
        String finalMsg = "¡El número es primo!";
        for (int i = 2; i < numberToCheck; i++) {
            if (numberToCheck % i == 0) {
                finalMsg = "¡El número no es primo!";
                System.out.println("check" + i);
                break;
            }
        }
        System.out.println(finalMsg);
    }

    static int getIntNumber(String msg) {
        while (true) {
            System.out.println(msg);
            try {
                return dataInput.nextInt();
            } catch (Exception e) {
                dataInput.nextLine();
            } 
        }
    }
}