/* Luis Fernando Martínez Andreu
Actividad Semana 10 - Parte 1
Crear una matriz 4×4 de números enteros inicialmente vacía. Realizar un menú con las siguientes opciones:
1 Rellenar toda la matriz de números, el usuario ira introduciendo uno a uno los valores.
2 Suma de una fila que se pedirá al usuario (controlar que elija una correcta).
3 Suma de una columna que se pedirá al usuario (controlar que elija una correcta).
4 Sumar la diagonal principal de la matriz.
5 Sumar la diagonal inversa de la matriz.
6 La media de todos los valores de la matriz.
Hasta que no se haga la primera opción, el resto de opciones no se deberán de ejecutar, 
simplemente mostrar un mensaje donde diga que se debe rellenar la matriz. */

import java.util.Scanner;

public class OperacionesMatriz {
    static Scanner dataInput = new Scanner(System.in);
    static int[][] matrix = new int[4][4];
    static boolean isMatrixInitialized = false;
    // FOR TESTING:
    // static int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
    // static boolean isMatrixInitialized = true;

    public static void main(String[] args) {
        startMenu();
    }

    static void startMenu() {
        System.out.println("");
        System.out.println("Operaciones con una matrix 4x4 de enteros. Funciones:");
        System.out.println("");
        System.out.println("1) Rellenar toda la matriz de números.");
        System.out.println("2) Suma de una fila.");
        System.out.println("3) Suma de una columna.");
        System.out.println("4) Suma de la diagonal principal.");
        System.out.println("5) Suma de la diagonal inversa.");
        System.out.println("6) Media de todos los valores de la matriz.");
        System.out.println("7) Salir.");
        System.out.println("");

        int selectedOption = 0;         
        while (true) {
            selectedOption = getValidatedInt("Introduce una opción entre el 1 y el 7:", false, 1, 7);
            if (selectedOption == 1 || selectedOption == 7 || isMatrixInitialized) {
                break;
            } else {
                System.out.println("La matriz ha de ser rellenada antes de poder ejecutar otras operaciones. Seleccione la opción 1");
            }
        }

        switch (selectedOption) {
            case 1:
                matrixFill();
                break;
            case 2:
                rowSum();
                break;
            case 3:
                colSum();
                break;
            case 4:
                diagSum();
                break;
            case 5:
                invDiagSum();
                break;
            case 6:
                matrixAverage();
                break;
            case 7:
                dataInput.close();
                System.out.println("Finalizando programa...\n");
                break;
            default:
        }
    }

    static void matrixFill() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = getValidatedInt("Introduce el valor [" + i + "] [" + j + "]:",true,0,0);
            }
        }
        System.out.println("La matriz ha sido generada:");
        isMatrixInitialized = true;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print("[" + matrix[i][j] + "]");
            }
            System.out.println();
        }
        startMenu();
    }

    static void rowSum() {
        int selectedRow = getValidatedInt("Selecciona una fila entre 1 y 4:",false,1,4);
        int rowSum = 0;

        System.out.println("La suma de los valores de la fila " + selectedRow + "...");
        for (int j = 0; j < matrix[1].length; j++) {
            System.out.print("[" + matrix[selectedRow-1][j] + "]");
            rowSum += matrix[selectedRow-1][j];
        }
        System.out.print("\n ...es: " + rowSum + "\n");
        startMenu();
    }

    static void colSum() {
        int selectedCol = getValidatedInt("Selecciona una columna entre 1 y 4:",false,1,4);
        int colSum = 0;
        System.out.println("La suma de los valores de la columna " + selectedCol + "...");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println("[" + matrix[i][selectedCol-1] + "]");
            colSum += matrix[i][selectedCol-1];
        }
        System.out.println("...es: " + colSum);
        startMenu();
    }

    static void diagSum() {
        int diagSum = 0;
        String whiteSpace = ""; // para imprimir la diagonal de forma visualmente correcta
        System.out.println("La suma de los valores de la diagonal principal...");
        for (int d = 0; d < matrix.length; d++) {
            System.out.println(whiteSpace + "[" + matrix[d][d] + "]");
            whiteSpace += "   ";
            diagSum += matrix[d][d];
        }
        System.out.println("...es: " + diagSum);
        startMenu();
    }

    static void invDiagSum() {
        int invDiagSum = 0;
        String whiteSpace = "   ";
        System.out.println("La suma de los valores de la diagonal principal inversa...");
        for (int d = 0; d < matrix.length; d++) {
            for (int w = 0; w < matrix[0].length-1-d; w++) {
                System.out.print(whiteSpace);
            }
            System.out.println("[" + matrix[d][matrix[0].length-1-d] + "]");
            invDiagSum += matrix[d][matrix[0].length-1-d];
        }
        System.out.println("...es: " + invDiagSum);
        startMenu();
    }

    static void matrixAverage() {
        double matrixAverage = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrixAverage += matrix[i][j];
            }
        }
        matrixAverage /= matrix.length * matrix[0].length;
        System.out.printf("La media de todos los valores de la matriz es: %.1f \n", matrixAverage);
        startMenu();
    }

    static int getValidatedInt(String msg, boolean ignoreRange, int minValue, int maxValue) {
        while (true) {
            System.out.println(msg);
            try { 
                int intInRange = dataInput.nextInt();
                if ((intInRange >= minValue && intInRange <= maxValue) || ignoreRange) {
                    return intInRange;
                }
            } catch (Exception e) {
                System.out.println("Introduce un número entero.");
                dataInput.nextLine();
            }
        }
    }
}