/* Luis Fernando Martínez Andreu
Actividad Semana 9 - Parte 3
Crea un programa que pida por pantalla cuatro provincias españolas y a continuación tres ciudades de cada una de ellas. 
Los nombres de ciudades deben almacenarse en un array multidimensional cuyo primer índice sea el número asignado a cada 
provincia y el segundo índice el número asignado a cada ciudad.

Ejemplo de resultados que debe mostrar el programa:
Provincia: Asturias Ciudades: Gijón Oviedo Avilés
Provincia: Madrid Ciudades: Madrid Tres Cantos Getafe
Provincia Galicia Ciudades: A Coruña Lugo Ourense
Provincia: Murcia Ciudades: Lorca Cartagena Murcia
Subir el archivo Geografia.java resultante. */ 

import java.util.Scanner;

public class Geografia {
    static Scanner dataInput = new Scanner(System.in);
    static String[][] geoEsp = new String[4][4];
    static int provIndex, cityIndex;

    public static void main(String[] args) {
        buildGeo();
        printGeo();
    }

    static void buildGeo() {
        for (provIndex = 0; provIndex < geoEsp.length; provIndex++) {
            geoEsp[provIndex][0] = getName(true);
            for (cityIndex = 1; cityIndex < geoEsp[0].length; cityIndex++) {
                geoEsp[provIndex][cityIndex] = getName(false);
            } 
        } 
        dataInput.close();
    }

    static void printGeo() {
        System.out.println();
        for (provIndex = 0; provIndex < geoEsp.length; provIndex++) {
            System.out.print("Provincia: " + geoEsp[provIndex][0] + " Ciudades:");
            for (cityIndex = 1; cityIndex < geoEsp[0].length; cityIndex++) {
                System.out.print(" " + geoEsp[provIndex][cityIndex]);
            } 
            System.out.println();
        }
        System.out.println();
    }

    static String getName(boolean isProvince) {
        String name;

        if(isProvince) {
            System.out.println("Introduce el nombre de la provincia " + (provIndex+1) + ": ");
        } else {
            System.out.println("Introduce el nombre de la ciudad " + cityIndex + " de la provincia " + geoEsp[provIndex][0] + ":");
        }

        while(true) {
            try {
                name = dataInput.nextLine();
                System.out.println("Guardado.");
                return name;
            } catch (Exception e) {
                dataInput.nextLine();
            }
        }
    }
}