import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import EjerciciosAD_T1.*;

public class EjerciciosAD_T1 {
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        Ejercicio1();
    }

    static void Ejercicio1() {
        char selectedOption;
        List<User> createdUsers = new ArrayList<>();
        do {
            createdUsers.add(createNewUser());
            System.out.println("Datos introducidos.\n¿Quieres añadir otro usuario? Escribe 's' para sí; 'n' para no.");
            while(true) {
                try {
                    selectedOption = sc.next("[sn]").charAt(0);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Error. Escribe 's' para sí; 'n' para no.");
                    sc.nextLine();
                }
            }
        } while (selectedOption == 's');

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("usuarios.txt"))) {
            for (User user : createdUsers) {
                bw.write(user.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Ha tenido lugar un error escribiendo el archivo.");
        }
    } 

    private static User createNewUser() {
        String[] requiredData = {"nombre", "apellido", "teléfono", "DNI", "edad"};
        Map<String, String> collectedData = new HashMap<>();
        for (String field : requiredData) {
            while(true) {
                System.out.println("Introduce el campo " + field + ":");
                try {
                    String textInput = null;
                    // minor simplified check to make sure age or phone number can't be string; 
                    // in reality this could be done thoroughly using regex etc; but is not necessary for this exercise 
                    if (field.equals(requiredData[2]) || field.equals(requiredData[3])) {
                        textInput = String.valueOf(sc.nextInt());
                    } else {
                        textInput = sc.next();
                    }
                    collectedData.put(field, textInput);
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Introduce de nuevo; fallo de entrada.");
                    sc.nextLine();
                }
            }
        }
        User newUser = new User(collectedData.get(requiredData[0]),
                                collectedData.get(requiredData[1]),
                                collectedData.get(requiredData[2]),
                                collectedData.get(requiredData[3]),
                                Integer.parseInt(collectedData.get(requiredData[4])));
        return newUser;
    }
}
