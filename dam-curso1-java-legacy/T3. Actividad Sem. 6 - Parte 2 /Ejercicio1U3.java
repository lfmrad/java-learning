/* Luis Fernando Martínez Andreu
Actividad Semana 6 Parte 2
1. Escribir por pantalla los números del 1 al 100 que sean impares. 
(Subir archivo Ejercicio1U3.java) */

public class Ejercicio1U3 {
    public static void main(String[] args) {
        for (int n = 1; n <= 100; n++) {
            if (n % 2 != 0) {
                System.out.print(n + " ");
            }
        }
    }
}