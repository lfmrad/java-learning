import java.util.Scanner;

public class Formulario {
    static Scanner sc = new Scanner(System.in);
    static String name, lastName, dni, email;
    static boolean nameFilled, lastNamefilled, dniFilled, emailFilled;
    
    public static void main(String[] args) {
        nameFilled = lastNamefilled = dniFilled = emailFilled = false;

        while(true) { 
            System.out.println("Menú Aplicación:");
            System.out.println("1. Rellenar nombre.");
            System.out.println("2. Rellenar apellidos.");
            System.out.println("3. Rellenar DNI.");
            System.out.println("4. Rellenar e-mail.");
            System.out.println("5. Finalizar.");
            
            int selectedOption = -1;
            while(true) {
                try {
                    selectedOption = Integer.parseInt(sc.next("[1-5]"));
                    break;
                } catch (Exception e) {
                    System.out.println("Elige una opción entre el 1 y el 5");
                } finally {
                    sc.nextLine();
                }
            }

            switch (selectedOption) {
                case 1:
                    fillName();
                    break;
                case 2:
                    lastName();
                    break;
                case 3:
                    fillDNI();
                    break;
                case 4:
                    fillEmail();
                    break;
                case 5:
                    if (nameFilled && lastNamefilled && dniFilled && emailFilled) {
                        sc.close();
                        printInfo();
                        System.exit(0);
                    } else {
                        System.out.println("Por favor, completa todos los campos antes de finalizar.");
                    }
                    break;
            }
        }
    }

    static void printInfo() {
        System.out.println("\n---- Datos Introcudidos ----");
        System.out.println("Nombre: " + name);
        System.out.println("Apellidos: " + lastName);
        System.out.println("DNI: " + dni);
        System.out.println("Email: " + email);
    }

    static void fillName() {
        System.out.println("Introduce el nombre:");
        name = getTextWithOnlyLetters();
        System.out.println("Introducido: " + name);
        nameFilled = true;
    }

    static void lastName() {
        System.out.println("Introduce el apellido:");
        lastName = getTextWithOnlyLetters();
        System.out.println("Introducido: " + lastName);
        lastNamefilled = true;
    }

    static void fillDNI() {
        System.out.println("Introduce el dni:");
        while(true) {
            String candidateText = sc.nextLine();
            try {
                if (candidateText.length()!= 9) {
                    throw new LongitudDNINoValidaException("Longitud no válida.");
                } else if (!Character.isLetter(candidateText.charAt(8))) {
                    throw new UltimoDigitoNoLetraException("El último digito ha de ser una letra.");
                } else if (!candidateText.substring(0, 7).matches("[0-9]")) {
                    throw new NumeracionContieneLetrasException("La numeración no puede contener letras.");
                } else {
                    dni = candidateText;
                    dniFilled = true;
                    System.out.println("Introducido: " + dni);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Excepción: " + e);  
            }
        }
    }

    static void fillEmail() {
        System.out.println("Introduce el email:");
        while(true) {
            try {
                String candidateText = sc.nextLine();
                if (candidateText.contains("@") && candidateText.contains(".")) {
                    email = candidateText;
                    emailFilled = true;
                    System.out.println("Introducido: " + email);
                    break;
                } else {
                    throw new EmailIncorrectoException("El formato del email no es correcto");
                }
            } catch (Exception e) {
                System.out.println("Excepción: " + e);
            } finally {
                sc.nextLine();
            }
        }
    }

    static String getTextWithOnlyLetters() {
        while(true) {
            try {
                String candidateText = sc.nextLine();
                if(!candidateText.matches("[A-Za-zñÑ]+")) {
                    throw new TipoDatoIncorrectoException("El texto introducido solo puede de contener letras.");
                } else {
                    return candidateText;
                }
            } catch (Exception e) {
                System.out.println("Excepción: " + e);   
            } 
        }
    }
}