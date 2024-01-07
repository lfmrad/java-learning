package util;

import model.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public final class ImportTools {

    public static List<Product> importDummyProductsFromJSON() {
        List<Product> retrievedProducts = new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(SchemaUtil.IMPORT_URL);
            connection = (HttpURLConnection) url.openConnection();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuffer sb = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                JSONObject response = new JSONObject(sb.toString());
                JSONArray products = response.getJSONArray(SchemaUtil.JSON_PRODUCTS_KEY);

                for (int i = 0; i < products.length(); i++) {
                    JSONObject product = products.getJSONObject(i);
                    Product newProduct = new Product(
                            product.getString(SchemaUtil.JSON_PRODUCTS_NAME_KEY),
                            product.getString(SchemaUtil.JSON_PRODUCTS_DESCRIPTION_KEY),
                            product.getInt(SchemaUtil.JSON_PRODUCTS_AMOUNT_KEY),
                            product.getInt(SchemaUtil.JSON_PRODUCTS_PRICE_KEY)
                    );
                    retrievedProducts.add(newProduct);
                }
            }
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        } catch (JSONException e) {
            System.out.println("JSON can't be parsed: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return retrievedProducts;
    }
}
