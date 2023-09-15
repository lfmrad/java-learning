/* Luis Fernando Martínez Andreu
Actividad Semana 11 - Parte 2
Crea un programa en Java que convierta un número en base decimal a binario. 
Esto lo realizará un método al que le pasaremos el numero entero como parámetro 
y devolverá un String con el numero convertido a binario. 
Os recuerdo como convertir un número decimal a binario: dividir entre 2 el numero, 
el resultado de esa división se divide entre 2 de nuevo y así sucesivamente hasta que no se 
pueda dividir mas, el resto que obtengamos de cada división formara el numero binario, de abajo a arriba.
Veamos un ejemplo: si introducimos un 8 nos deberá devolver 1000.
Subir el archivo CambioBase.java.*/

import java.util.Scanner;

public class CambioBase {
    static Scanner dataInput = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Introduce un número en base decimal para ser convertido a binario:");
        System.out.println("El número en binario es: " + decToBinary(getValidatedInt()));
        dataInput.close();
    }

    // he implementado el caso para números positivos usando recursividad para practicar el concepto
    static String decToBinary(int decNumber) {
        if (decNumber >= 0) {
            if (decNumber == 1) {
                return "1";
            } else if (decNumber == 0) {
                return "0";
            } else {
                return decToBinary(decNumber / 2) + Integer.toString(decNumber % 2);
            }
        } else { 
            // para no implementar el método complemento a dos pero poder seguir aceptando números
            // negativos he decidido usar el método de la clase envoltorio Integer para este caso:
            return Integer.toBinaryString(decNumber);
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
