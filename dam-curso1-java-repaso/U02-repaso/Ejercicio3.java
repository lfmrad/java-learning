/* lfmrad
Actividad Semana 4 Parte 3
3. Implementar un programa en Java que dado un número entero de 5 dígitos, lo escriba al revés. 
Ejemplo: 13578 se muestra como 87531. (Subir archivo: Ejercicio3.java) 
Este es algo avanzado, sólo para el que quiera investigar un poco más. */

import java.util.Scanner;
import java.util.InputMismatchException;

public class Ejercicio3 {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Introduce un número entero positivo de 5 digitos:");
        int num = getValidatedNumber();
        scanner.close();
        System.out.println("El número al revés es: " + invertNumberWithMath(num));
    }

    static int invertNumberWithMath(int num) {
        int reversed = 0;
        while (num != 0) {
            int digit = num % 10;
            reversed = reversed * 10 + digit;
            num /= 10;
        }
        return reversed;
    }

    static int invertNumberWithCode(int num) {
        String reversedText = "";
        char[] textNum = Integer.toString(num).toCharArray();
        for (int i = textNum.length - 1; i >= 0; i--) {
            reversedText += textNum[i];
        }
        return Integer.valueOf(reversedText);
    }

    static int getValidatedNumber() {
        int number;
        while (true) {
            try {
                number = scanner.nextInt();
                if (Integer.toString(Math.abs(number)).length() != 5 || number < 0) throw new NumericalConditionException(
                    "¡El número ha de tener 5 digitos y ser positivo!"
                );
                return number;
            } catch (InputMismatchException e) {
                System.out.println("Introduce un número entero.");
            } catch (NumericalConditionException e) {
                System.out.println(e.getMessage());
            } finally {
                scanner.nextLine();
            }
        }
    }
}

class NumericalConditionException extends Exception {
    public NumericalConditionException(String msg) {
        super(msg);
    }
}