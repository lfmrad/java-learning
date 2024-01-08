import database.DBManager;
import model.Product;
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
        DBManager.clearDB();

        //testing
        List<Product> importedProducts = ImportTools.importDummyProductsFromJSON();
        System.out.println("número items importados JSON:" + importedProducts.size());

        for (Product product : importedProducts) {
            System.out.println(product.toString());
        }

        // DBManager.addProduct(importedProducts);

    }
}
