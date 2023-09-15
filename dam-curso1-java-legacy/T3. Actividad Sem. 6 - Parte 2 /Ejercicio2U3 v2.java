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
        System.out.println("Introduce la variable entera:");
        Scanner scanner = new Scanner(System.in);
        int targetIntVar = scanner.nextInt();
        System.out.print("X = " + targetIntVar + "; ");
        int[] sumBuffer = new int[targetIntVar];
        int n = 1;
        int sum = 0;
        while (sum <= targetIntVar) { 
            if (sum < targetIntVar) {
                System.out.print(n + ", ");
            } else {
                System.out.print(n + " -> ");
            }
            sumBuffer[n-1] = n;
            sum = 0;
            for (int number : sumBuffer) {
                sum = sum + number;
            }
            n++;
        }
        for (int i = 0; i < n-1; i++) {
            if (i < n-2) {
                System.out.print(sumBuffer[i] + "+");
            } else {
                System.out.print(sumBuffer[i] + " = " + sum);
            } 
        }
    }
}

