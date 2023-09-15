/* Luis Fernando Martínez Andreu
Actividad Semana 4 Parte 3
1. Escribir un programa en Java que, dados 2 números con decimales por el usuario, 
muestre por pantalla la suma, resta, multiplicación, división y módulo de los mismos.  
(Subir archivo: Ejercicio1.java) */

import java.util.Scanner; 

public class Ejercicio1 
{   
    public static void main(String[] args) {
        
        System.out.println("Introduce el primer número decimal:");
        double a = getNumberWithDecimals();
        System.out.println("Introduce el segundo número decimal:");
        double b = getNumberWithDecimals();
        
        System.out.printf("Suma = %.1f \n", a + b);
        System.out.printf("Resta = %.1f \n", a - b);
        System.out.printf("Multiplicación = %.1f \n", a * b);
        System.out.printf("Divisón = %.1f \n", a / b);
        System.out.printf("Módulo = %.0f \n", a % b);
    }

    static double getNumberWithDecimals() {

        Scanner in = new Scanner(System.in);
        boolean isDecimal, isNumber;
        isDecimal = isNumber = false;
        double number = 0d;
        String errorMsg = "¡Ha de ser de un número con decimales!";

        while (!isNumber || !isDecimal) {
            isNumber = in.hasNextDouble();
            if(isNumber) {
                number = in.nextDouble();

                if (number % 1 != 0) {
                    isDecimal = true;
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