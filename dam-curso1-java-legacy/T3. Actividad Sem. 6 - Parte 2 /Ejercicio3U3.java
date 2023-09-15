/* Luis Fernando Martínez Andreu
Actividad Semana 6 Parte 2
3. Dados un dividendo y un divisor, indique por pantalla si la división de ambos es exacta. 
(Subir archivo Ejercicio3U3.java)*/

import java.util.Scanner;
import java.text.DecimalFormat;

public class Ejercicio3U3 {
    static Scanner dataInput = new Scanner(System.in);
    // para obtener una salida más limpia (máx. 3 decimales o solo si significativos):
    static DecimalFormat df = new DecimalFormat("#.###"); 
    public static void main(String[] args) {
        double dividendo = getDouble("Introduce el dividendo: _ / b");
        double divisor = getDouble("Introduce el divisor: a / _:");
        dataInput.close();

        double division = dividendo / divisor;
        int resto = (int) (dividendo % divisor);

        System.out.println("División = " + df.format(dividendo) + " / " + df.format(divisor));
        System.out.println("Resultado = " + df.format(division));

        if (resto == 0) {
            System.out.println("La división es EXACTA");
        } else {
            System.out.println("La división NO es EXACTA");
            System.out.println("Resto = " + df.format(resto));
        }
    }

    static double getDouble(String msg) {
        double userInput = 0;
        boolean dataValidated = false;
        while (!dataValidated) {
            System.out.println(msg);
            try {
                userInput = dataInput.nextDouble();
                dataValidated = true;
            } catch (Exception e) {
                dataInput.nextLine();
            } 
        }
        return userInput;
    }
}