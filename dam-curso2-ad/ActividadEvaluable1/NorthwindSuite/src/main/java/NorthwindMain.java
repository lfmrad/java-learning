import database.DBManager;
import database.SchemaDB;
import model.*;
import util.ImportTools;
import util.SchemaUtil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class NorthwindMain {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        launchDemo();
    }

    private static void launchDemo() {
        boolean exitProgram = false;

        System.out.println("Northwind Suite - Demostración de funcionamiento: ");

        do {
            System.out.println("1. Conectar base de datos y purgar (para empezar de 0).");
            System.out.println("2. Purgar base de datos (vacía tablas y reinicia claves).");
            System.out.println("3. Agregar los productos ubicados en " + SchemaUtil.IMPORT_URL + " a la tabla de productos.");
            System.out.println("4. Agregar empleados y pedidos automáticamente a partir de ejemplos.");
            System.out.println("5. Agregar empleados manualmente.");
            System.out.println("6. Agregar pedidos manualmente.");
            System.out.println("7. Mostrar por consola: todos los empleados.");
            System.out.println("8. Mostrar por consola: todos los productos.");
            System.out.println("9. Mostrar por consola: todos los pedidos.");
            System.out.println("10. Mostrar por consola pedidos con precio inferior a 600€.");
            System.out.println("11. Insertar en la tabla de productos_fav aquellos con valor >1000€.");
            System.out.println("12. FINALIZAR DEMO.");

            int selectedOption = getUserSelection("Escoge una opción del 1 al 12: ", 12);

            switch (selectedOption) {
                case 1:
                    DBManager.initDB();
                    DBManager.clearDB(true);
                    break;
                case 2:
                    DBManager.clearDB(true);
                    break;
                case 3:
                    addJSONtoDB(false);
                    break;
                case 4:
                    addEmployeesAndOrdersFromTemplate();
                    break;
                case 5:
                    createAndAddEmployees();
                    break;
                case 6:
                    createAndAddOrders();
                    break;
                case 7:
                    listALlEmployees(false);
                    break;
                case 8:
                    listAllProducts(false);
                    break;
                case 9:
                    listALlOrders(false);
                    break;
                case 10:
                    List<Product> productsUnder600 = DBManager.getProducts(600,false);
                    for (Product product : productsUnder600) {
                        System.out.println(product.toString());
                    }
                    break;
                case 11:
                    List<Product> productsOver1000 = DBManager.getProducts(1000,true);
                    DBManager.addToFavorites(productsOver1000);
                    break;
                default:
                    System.out.println("Finalizando demo...");
                    exitProgram = true;
                    break;
            }
        } while (!exitProgram);
        sc.close();
        DBManager.closeConnection();
    }

    private static void addJSONtoDB(boolean printProducts) {
        List<Product> importedProducts = ImportTools.importDummyProductsFromJSON();
        System.out.println("Importados " + importedProducts.size() + " productos desde " + SchemaUtil.IMPORT_URL);
        if (printProducts) {
            for (Product product : importedProducts) {
                System.out.println(product.toString());
            }
        }
        DBManager.addProduct(importedProducts);
        System.out.println("Productos añadidos a la BD.");
    }

    private static void addEmployeesAndOrdersFromTemplate() {
        Employee dummyEmpoyee1 = new Employee("EmployeTest1", "LastName1", "test1@email.com");
        DBManager.addEmployee(dummyEmpoyee1);
        System.out.println("Creado: " + dummyEmpoyee1.toString());
        Employee dummyEmpoyee2 = new Employee("EmployeTest2", "LastName2", "test2@email.com");
        DBManager.addEmployee(dummyEmpoyee2);
        System.out.println("Creado: " + dummyEmpoyee2.toString());

        Product iphoneX = DBManager.getProductById(2);
        Product hpPavilion = DBManager.getProductById(10);
        Order dummyOrder1 = new Order(iphoneX, "This is a dummy order with: " + iphoneX.getName());
        DBManager.addOrder(dummyOrder1);
        System.out.println("Creado: " + dummyOrder1.toString());
        Order dummyOrder2 = new Order(hpPavilion, "This is a dummy order with: " + hpPavilion.getName());
        DBManager.addOrder(dummyOrder2);
        System.out.println("Creado: " + dummyOrder2.toString());
    }

    private static void createAndAddEmployees() {
        List<Employee> employeesToAdd = new ArrayList<>();
        boolean dataInputFinished = false;
        do {
            System.out.println("Introduce un nuevo empleado: ");
            Employee employee = new Employee(
                    getUserInput("Nombre: "),
                    getUserInput("Apellidos: "),
                    getUserInput("Email: ")
            );
            System.out.println("Creado: " + employee.toString());
            employeesToAdd.add(employee);
            dataInputFinished = getUserSelection("Añadir más? 1. Sí / 2. No", 2) == 2;
        } while (!dataInputFinished);
        DBManager.addEmployee(employeesToAdd);
        System.out.println("Nuevos empleados añadidos.");
    }

    private static void createAndAddOrders() {
        List<Order> ordersToAdd = new ArrayList<>();
        boolean dataInputFinished = false;
        do {
            System.out.println("Introduce un nuevo pedido: ");
            Product productToAdd = null;
            while (productToAdd == null) {
                productToAdd = DBManager.getProductById(getUserSelection("Introduce el ID del producto a añadir: ", 99));
            }
            Order order = new Order(
                    productToAdd,
                    getUserInput("Descripción pedido: ")
            );
            System.out.println("Creado: " + order.toString());
            ordersToAdd.add(order);
            dataInputFinished = getUserSelection("Añadir más? 1. Sí / 2. No", 2) == 2;
        } while (!dataInputFinished);
        DBManager.addOrder(ordersToAdd);
        System.out.println("Nuevos pedidos añadidos.");
    }

    private static void listALlOrders(boolean printFromDB) {
        if (printFromDB) {
            DBManager.printAllDataFrom(SchemaDB.Tabs.ORDERS);
        } else {
            List<Order> allOrders = DBManager.getAllOrders();
            for (Order order : allOrders) {
                System.out.println(order.toString());
            }
        }
    }

    private static void listALlEmployees(boolean printFromDB) {
        if (printFromDB) {
            DBManager.printAllDataFrom(SchemaDB.Tabs.EMPLOYEES);
        } else {
            List<Employee> allEmployees = DBManager.getAllEmployees();
            for (Employee employee : allEmployees) {
                System.out.println(employee.toString());
            }
        }
    }

    private static void listAllProducts(boolean printFromDB) {
        if (printFromDB) {
            DBManager.printAllDataFrom(SchemaDB.Tabs.PRODUCTS);
        } else {
            List<Product> allProducts = DBManager.getAllProducts();
            for (Product product : allProducts) {
                System.out.println(product.toString());
            }
        }
    }

    private static int getUserSelection(String msg, int numberOfOptions) {
        while(true) {
            System.out.println(msg);
            try {
                int selectedOption = sc.nextInt();
                sc.nextLine();
                if (selectedOption > 0 && selectedOption <= numberOfOptions) {
                    return selectedOption;
                } else {
                    System.out.println("Fuera de rango. Introduce un número del 1 al " + numberOfOptions + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida: " + e.getMessage());
            }
        }
    }
    private static String getUserInput(String msg) {
        System.out.println(msg);
        return sc.nextLine();
    }

    private static void waitForAnyAky(String msg) {
        System.out.println(msg);
        sc.nextLine();
    }
}
