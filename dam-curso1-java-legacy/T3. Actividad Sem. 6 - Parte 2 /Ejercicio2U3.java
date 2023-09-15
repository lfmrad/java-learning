/* Luis Fernando Martínez Andreu
Actividad Semana 6 Parte 2
2. Escribir por pantalla los números incrementando desde 1 hasta que la suma de 
todos esos números sea mayor a una variable entera x. Ejemplos:
X = 10; 1, 2, 3, 4, 5 -> 1+2+3+4+5 = 15 
X = 25; 1, 2, 3, 4, 5, 6, 7 -> 1+2+3+4+5+6+7 = 28
(Subir archivo Ejercicio2U3.java)*/

import java.util.Scanner;

public class Ejercicio2U3 {
    public static void main(String[] args) {
        int intVarX = -1;
        Scanner scanner = new Scanner(System.in);
        while (intVarX < 0) {
            System.out.println("Introduce la variable entera:");
            try {
                intVarX = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
            }
        }
        scanner.close();
        System.out.print("X = " + intVarX + "; ");

        int n = 1;
        int sum = 0;
        while (sum <= intVarX) { 
            sum = sum + n;
            if (sum <= intVarX) {
                System.out.print(n + ", ");
            } else {
                System.out.print(n + " -> ");
            }
            n++;
        }
        for (int i = 1; i < n; i++) {
            if (i < n-1) {
                System.out.print(i + "+");
            } else {
                System.out.print(i + " = " + sum + "\n");
            } 
        }
    }
}

