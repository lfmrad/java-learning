/*2. Realiza un programa que lea el fichero creado en el ejercicio anterior y 
que muestre los números por pantalla. (PrimosLeer.java) */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrimosLeer {
    
    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader("primos.txt"))) {
            String line;
            while((line = br.readLine()) != null) {
                System.out.print(line + " ");
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
