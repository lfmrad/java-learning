/* Luis Fernando Martínez Andreu
 * Actividad Semana 15
 * Crea una función con la siguiente cabecera: 
 * public static String convierteEnMorse(int n) 
 * Esta función convierte el número n al sistema Morse y lo devuelve en una cadena de caracteres. 
 * Por ejemplo, el 213 es el . . _ _ _ . _ _ _ _ . . . _ _ en Morse. 
 * Utiliza esta función en un programa para comprobar que funciona bien. 
 * Desde la función no se debe mostrar nada por pantalla, solo se debe usar print desde el programa principal. 
 * 0 _ _ _ _ _
 * 1 . _ _ _ _ 
 * 2 . . _ _ _ 
 * 3 . . . _ _ 
 * 4 . . . . _ 
 * 5 . . . . . 
 * 6 _ . . . . 
 * 7 _ _ . . . 
 * 8 _ _ _ . .
 * 9 _ _ _ _ . 
 * Subir archivo Morse.java.
 */

import java.util.Scanner;

public class Morse {
    static Scanner dataInput = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Introduce un número entero positivo para ser convertido a morse.");
        System.out.println("El número convertido a morse es: \n" + convierteEnMorse(getValidatedInt()));
        dataInput.close();
    }

    public static String convierteEnMorse(int n) {
        // System.out.println("hello" + n);
        String morseToken = "";
        switch (n % 10) {
            case 0:
                morseToken = "_ _ _ _ _";
                break;
            case 1:
                morseToken = ". _ _ _ _";
                break;
            case 2:
                morseToken = ". . _ _ _";
                break;
            case 3:
                morseToken = ". . . _ _";
                break;
            case 4:
                morseToken = ". . . . _ ";
                break;
            case 5:
                morseToken = ". . . . .";
                break;
            case 6:
                morseToken = "_ . . . .";
                break;
            case 7:
                morseToken = "_ _ . . . ";
                break;
            case 8:
                morseToken = "_ _ _ . . ";
                break;
            case 9:
                morseToken = "_ _ _ _ . ";
                break;
            default:
        }
        if (n / 10 == 0) {
            return morseToken;
        } else {
            return convierteEnMorse(n / 10) + " " + morseToken;
        }
    }

    static int getValidatedInt() {
        String errMsg = "¡Introduce un número entero positivo!";
        while (true) {
            try {
                int number = dataInput.nextInt();
                if (number >= 0) {
                    return number;
                }
            } catch (Exception e) { }
            System.out.println(errMsg);
            dataInput.nextLine();
        }
    }
}
