/* Luis Fernando Martínez Andreu
Actividad Semana 4 Parte 3
4. Diseñar un programa en Java que convierta una cantidad positiva de segundos, 
a su equivalente en horas, minutos y segundos. (Subir archivo: Ejercicio4.java) */

import java.util.Scanner;

public class Ejercicio4 
{
    public static void main(String[] args) {
        
        System.out.println("Introduce una cantidad positiva en segundos: ");
        double seconds = getNumberOfSeconds();

        double minutes = seconds / 60d;
        double hours = minutes / 60d;

        System.out.printf("%.0f segundos son %.1f minutos y %.1f horas. \n", seconds, minutes, hours);
    }

    static double getNumberOfSeconds() {

        Scanner in = new Scanner(System.in);
        boolean isPositive, isNumber;
        isPositive = isNumber = false;
        double number = 0d;
        String errorMsg = "¡Ha de ser de un número positivo!";

        while (!isNumber || !isPositive) {
            isNumber = in.hasNextDouble();
            if(isNumber) {
                number = in.nextDouble();

                if (number > 0) {
                    isPositive = true;
                } else {
                    System.out.println(errorMsg);
                }
            } else {
                System.out.println(errorMsg);
                in.next();
            }
        }
        return number;
    }
}