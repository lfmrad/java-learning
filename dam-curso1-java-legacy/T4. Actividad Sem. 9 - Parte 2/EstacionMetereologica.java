/* Luis Fernando Martínez Andreu
Actividad Semana 9 - Parte 2
En una estación meteorológica se registra la temperatura (en grados centígrados) en cada hora durante el día. 
Almacenamos el resultado en un array de 24 elementos. 
Diseña un programa que muestre por pantalla un menú con las siguientes opciones (en negrita) y que realice las acciones que se describen: 
a) Rellenar el vector de forma manual. Esta funcionalidad permite al usuario rellenar manualmente cada uno de los 24 elementos. 
b) Rellenar el vector de forma aleatoria. Los 24 elementos del vector se rellenan de forma aleatoria, con valores entre 0 y 40. 
c) Mostrar datos. Muestra los datos almacenados en el vector.
d) Obtener máximos y mínimos. Muestra las temperaturas máxima y mínima del día, así como la hora en la que se dan. 
e) Temperatura media. Calcula la temperatura media del día. 
f) Salir. Sale del programa.
Subir el archivo EstacionMetereologica.java resultante. */

import java.util.Scanner;
import java.util.Random;

public class EstacionMetereologica {
    static Scanner dataInput = new Scanner(System.in);
    static double[] temp = new double[24];

    public static void main(String[] args) {
        startMenu(true);
    }

    static void startMenu(boolean firstLoad) {
        if (!firstLoad) {
            System.out.print("\n\n... volviendo al menú ...\n");
        }
        System.out.println("");
        System.out.println("Estación Meteorológica. Funciones:");
        System.out.println("");
        System.out.println("a) Rellenar el vector de forma manual.");
        System.out.println("b) Rellenar el vector de forma aleatoria.");
        System.out.println("c) Mostrar datos.");
        System.out.println("d) Obtener máximos y mínimos.");
        System.out.println("e) Temperatura media.");
        System.out.println("f) Salir.");
        System.out.print("\n\n");

        char selectedOption;
   
        while (true) {
            try {
                selectedOption = dataInput.next("[a-f]").charAt(0);
                System.out.println();
                break;
            } catch (Exception e) {
                dataInput.nextLine();
                System.out.println("Introduce una opción válida! a, b, c...");
            }
        }

        switch (selectedOption) {
            case 'a':
                manualFill();
                break;
            case 'b': 
                randomFill();
                break;
            case 'c':
                printData();   
                break;
            case 'd':
                getMinMax();
                break;
            case 'e':
                getAverageTemp();
                break;
            case 'f':
                dataInput.close();
                System.out.println("Apagando estación meteorológica...\n");
                break;
            default:
        }
    }

    static void manualFill() {
        for (int i = 0; i < temp.length; i++) {
                temp[i] = getTempValue(i);
        }
        System.out.printf("Datos introducidos con éxito.");
        startMenu(false);
    }

    static double getTempValue(int index) {
        while(true) {
            try {
                System.out.println("Introduce el valor de la temperatura (°C) de la hora número " + (index+1) + " registrada:");
                return dataInput.nextDouble();
            } catch (Exception e) {
                dataInput.nextLine();
                System.out.println("Introduce un valor de temperatura númerico!");
            }
        }
    }

    static void randomFill() {
        Random randomSeed = new Random();
        for (int i = 0; i < temp.length; i++) {
                temp[i] = randomSeed.nextFloat() * 40;
        }
        System.out.println("Datos generados con éxito (valores de la temp. entre 0 °C y 40 °C).");
        startMenu(false);
    }

    static void printData() {
        System.out.println("Los datos de temperatura (°C) son:\n");
        for (int i = 0; i < temp.length; i++) {
                if (i == temp.length - 1) {
                    System.out.printf("y %.1f", temp[i]);
                } else {
                    System.out.printf("%.1f, ", temp[i]);
                }
                
        }
        startMenu(false);
    }

    static void getMinMax() {
        double min, prevMin, max, prevMax;
        max = prevMax = 0;
        min = prevMin = 100; // para que no se escoja como temp. mínima el 0 de incialización en la primera comparación del bucle:

        for (int i = 0; i < temp.length-1; i++) {
            max = Math.max(prevMax,Math.max(temp[i],temp[i+1]));
            min = Math.min(prevMin,Math.min(temp[i],temp[i+1]));
            prevMax = max;
            prevMin = min;
        }
        System.out.printf("Temperatura máxima registrada: %.1f °C\n", max);
        System.out.printf("Temperatura mínima registrada: %.1f °C", min);
        startMenu(false);
    }

    static void getAverageTemp() {
        double averageTemp = 0;

        for (int i = 0; i < temp.length; i++) {
            averageTemp += temp[i];
        }
        averageTemp /= temp.length;
        System.out.printf("Temperatura media: %.1f °C", averageTemp);
        startMenu(false);
    }
}

