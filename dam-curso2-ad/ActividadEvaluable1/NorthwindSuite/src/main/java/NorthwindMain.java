import database.DBManager;
import database.SchemaDB;
import model.*;
import util.ImportTools;
import java.util.List;

public class NorthwindMain {
    public static void main(String[] args) {
        DBManager.initDB();
        DBManager.clearDB(true);

        // *** PUNTO 2 ***
        // Se importa el JSON a una lista de objetos tipo producto que poseen los atributos deseados
        List<Product> importedProducts = ImportTools.importDummyProductsFromJSON();
        System.out.println("Total items importados:" + importedProducts.size());
//        for (Product product : importedProducts) {
//            System.out.println(product.toString());
//        }
        // Se importa esta lista en la base de datos
        DBManager.addProduct(importedProducts);

        // *** PUNTO 3 - Agregar una serie de empleados y pedidos mediante statement ***
        // (Se tiene en cuenta que los pedidos tienen FK de productos.)
        // Serie de empleados:
        Employee dummyEmpoyee1 = new Employee("Test Name", "Test LastName", "test@email.com");
        DBManager.addEmployee(dummyEmpoyee1);

        // Serie de pedidos:
        Product iphoneX = DBManager.getProductById(2);
        Product hpPavilion = DBManager.getProductById(10);
        Order dummyOrder1 = new Order(iphoneX, "This is a dummy order with: " + iphoneX.getName());
        DBManager.addOrder(dummyOrder1);
        Order dummyOrder2 = new Order(hpPavilion, "This is a dummy order with: " + hpPavilion.getName());
        DBManager.addOrder(dummyOrder2);

        // *** PUNTO 4 ***
        DBManager.printAllDataFrom(SchemaDB.Tabs.EMPLOYEES);
        DBManager.printAllDataFrom(SchemaDB.Tabs.PRODUCTS);
        DBManager.printAllDataFrom(SchemaDB.Tabs.ORDERS);


//        List<Product> allProducts = DBManager.getAllProducts();
//        for (Product product : allProducts) {
//            System.out.println(product.toString());
//        }
//        List<Employee> allEmployees = DBManager.getAllEmployees();
//        for (Employee employee : allEmployees) {
//            System.out.println(employee.toString());
//        }
//        List<Order> allOrders = DBManager.getAllOrders();
//        for (Order order : allOrders) {
//            System.out.println(order.toString());
//        }

        // *** PUNTO 5 ***
        List<Product> productsUnder600 = DBManager.getProducts(600,false);
        for (Product product : productsUnder600) {
            System.out.println(product.toString());
        }

        // *** PUNTO 6 ***
        List<Product> productsOver1000 = DBManager.getProducts(1000,true);
        DBManager.addToFavorites(productsOver1000);

        DBManager.closeConnection();
    }
}
