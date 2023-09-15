import java.util.InputMismatchException;
import java.util.Scanner;


public class Formulario {
    static Scanner scanner = new Scanner(System.in);
    static String name, lastName, dni, email;
    static boolean nameAdded, lastNameAdded, dniAdded, emailAdded;
    public static void main(String[] args) {
        nameAdded = lastNameAdded = dniAdded = emailAdded = false;

        while (true) {
            System.out.println("\nEjercicio 1 - Opciones:");
            System.out.println("1. Rellenar nombre.");
            System.out.println("2. Rellenar apellidos.");
            System.out.println("3. Rellenar DNI.");
            System.out.println("4. Rellenar e-mail.");
            System.out.println("5. Finalizar.");

            System.out.println("\nEscoge una opción del 1 al 5:");

            int selectedOption;
            while (true) {
                try {
                    selectedOption = Integer.parseInt(scanner.next("[1-5]"));
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("La opción escogida no es un número del 1 al 5.");
                } finally {
                    scanner.nextLine();
                }
            }

            switch (selectedOption) {
                case 1:
                    fillName();
                    break;
                case 2:
                    fillLastName();
                    break;
                case 3:
                    fillDni();
                    break;
                case 4:
                    fillEmail();
                    break;
                case 5:
                    if (formFilled()) {
                        printForm();
                        scanner.close();
                        System.out.println("Finalizando programa...");
                        System.exit(0);
                    } else {
                        System.out.println("El formulario aún no se ha sido completado. Por favor, completa el formulario antes de finalizar.");
                    }
                default:
            }
        }
    }

    static void fillName() {
        System.out.println("Rellena el nombre:");
        name = getText();
        nameAdded = true;
        System.out.println("Introducido:" + name);
    }

    static void fillLastName() {
        System.out.println("Rellena el apellido:");
        lastName = getText();
        lastNameAdded = true;
        System.out.println("Introducido:" + lastName);
    }

    static void fillDni() {
        while (true) {
            System.out.println("Rellena el DNI:");
            try {
                String candidateDni;
                candidateDni = scanner.nextLine();
                if (candidateDni.length() != 9) {
                    throw new LongitudDNINoValidaException("La longitud del DNI es incorrecta.");
                } else if (!Character.isLetter(candidateDni.charAt(8))) {
                    throw new UltimoDigitoNoLetraException("El último carácter ha de ser una letra.");
                } else if (candidateDni.substring(0, 7).matches("[0-9]")) {
                    throw new NumeracionContieneLetrasException("La numeración del DNI contiene letras.");
                } else {
                    dni = candidateDni;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Excepción: " + e);
            }
        }
        dniAdded = true;
        System.out.println("Introducido:" + dni);
    }

    static void fillEmail() {
        while(true) {
            System.out.println("Rellena el email:");
            try {
                String candidateEmail;
                candidateEmail = scanner.nextLine();
                if (candidateEmail.contains("@") && candidateEmail.contains(".")) {
                    email = candidateEmail;

                    break;
                } else {
                    throw new EmailIncorrectoException("El email ha de contener una @ y un .");
                }
            } catch (Exception e) {
                System.out.println("Excepción: " + e);
            }
        }
        emailAdded = true;
        System.out.println("Introducido:" + email);
    }

    static String getText() {
        while(true) {
            try {
                String candidateText = scanner.nextLine();
                if(candidateText.matches("[0-9]")) {
                    throw new TipoDatoIncorrectoException("El nombre y apellidos no pueden contener números.");
                } else {
                    return candidateText;
                }
            } catch (Exception e) {
                System.out.println("Excepción: " + e);
            }
        }
    }

    static boolean formFilled() {
        if (nameAdded && lastNameAdded && dniAdded && emailAdded) {
            return true;
        } else {
            return false;
        }
    }

    static void printForm() {
        System.out.println("Los datos introducidos son:");
        System.out.println("Nombre: " + name);
        System.out.println("Apellidos: " + lastName);
        System.out.println("DNI: " + dni);
        System.out.println("Email: " + email);
    }
}
