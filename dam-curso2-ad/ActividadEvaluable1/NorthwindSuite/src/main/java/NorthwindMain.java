import model.Product;
import util.ImportTools;

import java.util.List;

public class NorthwindMain {

    public static void main(String[] args) {

        List<Product> importedProducts = ImportTools.importDummyProductsFromJSON();
        for (Product product : importedProducts) {
            System.out.println(product.toString());
        }
    }
}
