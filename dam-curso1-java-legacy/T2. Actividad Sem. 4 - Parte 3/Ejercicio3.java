/* Luis Fernando Martínez Andreu
Actividad Semana 4 Parte 3
3. Implementar un programa en Java que dado un número entero de 5 dígitos, lo escriba al revés. 
Ejemplo: 13578 se muestra como 87531. (Subir archivo: Ejercicio3.java) 
Este es algo avanzado, sólo para el que quiera investigar un poco más. */

import java.util.Scanner;

public class Ejercicio3 
{
    public static void main(String[] args) {

        System.out.println("Introduce un número entero de 5 digitos:");
        int fiveDigitInteger = getFiveDigitInteger();

        char numberArray[] = String.valueOf(fiveDigitInteger).toCharArray();
        char numberArrayCopy[] = String.valueOf(fiveDigitInteger).toCharArray();

        for (int i = 0; i < numberArray.length; i++) {    
            numberArray[i] = numberArrayCopy[numberArrayCopy.length-1-i];
        }

        System.out.println("El número invertido es: " + String.valueOf(numberArray));
    }

    static int getFiveDigitInteger() {

        Scanner in = new Scanner(System.in);
        boolean isFiveDigits, isInteger;
        isFiveDigits = isInteger = false;
        int number = 0;
        String errorMsg = "¡Ha de ser de un número positivo con 5 digitos!";
    
        while (!isInteger || !isFiveDigits) {
            isInteger = in.hasNextInt();
            if(isInteger) {
                number = in.nextInt();

                if (String.valueOf(number).length() == 5 && number>0) {
                    isFiveDigits = true;
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