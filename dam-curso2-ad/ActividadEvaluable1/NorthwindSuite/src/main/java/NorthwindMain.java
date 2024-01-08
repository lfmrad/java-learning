import database.DBManager;
import model.*;
import util.ImportTools;
import java.util.List;

/* pendientes:
- db connection
- db manager (i/o)
    - métodos para diferentes imp / output?
- agregar una serie de empleados y pedidos mediante statement
    - dummy objects
- mostrar por consola: todos los empleados, productos y pedidos
- productos inferiores con precio <600
- insertar en product_fav aquellos que precio  1000e
 */

public class NorthwindMain {
    public static void main(String[] args) {
        DBManager.initDB();
        DBManager.clearDB(true);

        // *** PUNTO 2 ***
        // Se importa el JSON a una lista de objetos tipo producto que poseen los atributos deseados
        List<Product> importedProducts = ImportTools.importDummyProductsFromJSON();
        System.out.println("Total items importados:" + importedProducts.size());
        for (Product product : importedProducts) {
            System.out.println(product.toString());
        }
        // Se importa esta lista en la base de datos
        DBManager.addProduct(importedProducts);

        // *** PUNTO 3 - Agregar una serie de empleados y pedidos mediante statement ***
        // (Se tiene en cuenta que los pedidos tienen FK de productos.)
        // Serie de empleados:

        // Serie de pedidos:
        Product iphoneX = DBManager.getProductById(2);
        Product hpPavilion = DBManager.getProductById(10);
        Order dummyOrder1 = new Order(iphoneX, "This is a dummy order with: " + iphoneX.getName());
        DBManager.addOrder(dummyOrder1);
        Order dummyOrder2 = new Order(hpPavilion, "This is a dummy order with: " + hpPavilion.getName());
        DBManager.addOrder(dummyOrder2);
    }
}
