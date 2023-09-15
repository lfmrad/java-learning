/* Luis Fernando Martínez Andreu
Actividad Semana 7 Parte 2
7. Escribir un programa en Java que dado por teclado un número, escriba lo siguiente: 
a. “A” si su módulo con 5 es 0 
b. “B” si su módulo con 5 es 1 
c. “C” si su módulo con 5 es 2 
d. “D” si su módulo con 5 es 3 
e. “E” si su módulo con 5 es 4 
f. “F” en otro caso 
(Subir archivo Ejercicio7U3.java)*/

import java.util.Scanner;

public class Ejercicio7U3 {
    static Scanner dataInput = new Scanner(System.in);

    public static void main(String[] args) {
        char msg;
        switch (getIntNumber() % 5) {
            case 0:
                msg = 'A';
                break;
            case 1:
                msg = 'B';
                break;
            case 2:
                msg = 'C';
                break;
            case 3:
                msg = 'D';
                break;
            case 4:
                msg = 'E';
                break;
            default:
                msg = 'F';
        }
        dataInput.close();
        System.out.println(msg);
    }

    static int getIntNumber() {
        while (true) {
            System.out.println("Introduce un número: ");
            try {
                return dataInput.nextInt();
            } catch (Exception e) {
                dataInput.nextLine();
                System.out.println("¡El valor introducido ha de ser un número entero!");
            } 
        }
    }
}