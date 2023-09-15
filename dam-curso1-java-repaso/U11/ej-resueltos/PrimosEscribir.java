/* U11 Ejercicios resueltos
1. Escribe un programa que guarde en un fichero con nombre primos.txt los números primos que hay entre 1 y 500. (PrimosEscribir.java) */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class PrimosEscribir {
    public static void main(String[] args) {
        try (PrintWriter pw = new PrintWriter(new File("primos.txt"))) {
            for (int n = 1; n <= 500; n++) {
                if (isPrime(n)) {
                    pw.println(n);
                    System.out.println("Es primo. Añadido: " + n);
                } else {
                    System.out.println("No es primo. Descartado: " + n);
                }
            }
        } catch (IOException e) {
            System.out.println("Excepción: " + e.getMessage());
        }
    }

    static boolean isPrime(int candidate) {
        // primer number def. >1
        if (candidate <= 1) {
            return false;
        }
        // we only need to check up to <=Math.sqrt(cantidate) because of math wizardry 
        for (int i = 2; i <= Math.sqrt(candidate); i++) {
            if (candidate % i == 0) {
                return false;
            }
        }
        return true;
    }
}