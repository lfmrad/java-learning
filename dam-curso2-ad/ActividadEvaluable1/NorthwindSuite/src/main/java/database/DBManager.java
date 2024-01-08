package database;

import model.*;

import java.sql.*;
import java.util.List;

public class DBManager {
    static Connection connection;

    public static void initDB() {
        if (connection == null) {
            createConnection();
            System.out.println("Connection successful to: " + SchemaDB.NAME);
        }
        System.out.println("Connection already established.");
    }

    private static void createConnection() {
        try {
            // not required for new driver (4.0 compliant); in my case: mySQL Connector/J
            // Class.forName("mysql.jdbc.cj.jdbc.Driver");
            String url = String.format("jdbc:mysql://%s/%s", SchemaDB.HOST, SchemaDB.NAME);
            connection = DriverManager.getConnection(url,SchemaDB.USER,SchemaDB.PWD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("The connection hasn't been established yet.");
        }
    }

    public static void clearDB(boolean resetKeys) {
        for (String table : SchemaDB.TAB_ALL) {
            clearTable(table, resetKeys);
        }
        System.out.println("All tables cleared. Ready to test.");
    }

    private static void clearTable(String tableName, boolean resetKeys) {
        try (Statement st = connection.createStatement()) {
            String query = String.format("DELETE FROM %s;", tableName);
            st.execute(query);
            System.out.println("Completed: " + query);
            if (resetKeys) {
                query = String.format("ALTER TABLE %s AUTO_INCREMENT = 1 ", tableName);
                st.execute(query);
                System.out.println("Completed: " + query);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addOrder(Order order) {
        if (order != null) {
            Product associatedProduct = getProductById(order.getAssociatedProductID());
            if (associatedProduct != null) {
                String query = String.format("INSERT INTO %s (%s,%s,%s) VALUE (?,?,?)", SchemaDB.TAB_ORDERS_NAME,
                        SchemaDB.COL_PRODUCT_ID, SchemaDB.COL_INFO, SchemaDB.COL_TOTAL_PRICE);
                try (PreparedStatement ps = connection.prepareStatement(query)) {
                    ps.setInt(1, associatedProduct.getId());
                    ps.setString(2, order.getInfo());
                    ps.setInt(3, order.getTotalPrice());
                    int modifiedRows = ps.executeUpdate();
                    System.out.println("DB insertion successful. Modified: " + modifiedRows + " rows.");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Can't create order. The product does not exist in the DB.");
            }
        } else {
            System.out.println("The provided order is NULL.");
        }
    }

    public static void addProduct(Product product) {
        String query = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUE (?,?,?,?)", SchemaDB.TAB_PRODUCTS_NAME,
                SchemaDB.COL_NAME, SchemaDB.COL_INFO, SchemaDB.COL_AMOUNT, SchemaDB.COL_PRICE);
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getInfo());
            ps.setInt(3, product.getStock());
            ps.setInt(4, product.getPrice());
            int modifiedRows = ps.executeUpdate();
            System.out.println("DB insertion successful. Modified: " + modifiedRows + " rows.");
            if (modifiedRows > 0) {
                product.setId(getGeneratedKeys(ps));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addProduct(List<Product> productList) {
        for (Product product : productList){
            addProduct(product);
        }
    }

    private static int getGeneratedKeys(PreparedStatement ps) {
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int retrievedKey = generatedKeys.getInt(1);
                System.out.println("Auto assigned key: " + retrievedKey);
                return retrievedKey;
            }
        } catch (SQLException e) {
            System.out.println("Key retrieval failed:" + e.getMessage());
        }
        return -1;
    }

    public static Product getProductById(int productId) {
        String query = String.format("SELECT * FROM %s WHERE  %s = ?", SchemaDB.TAB_PRODUCTS_NAME,
                SchemaDB.COL_ID);
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt(SchemaDB.COL_ID),
                            rs.getString(SchemaDB.COL_NAME),
                            rs.getString(SchemaDB.COL_INFO),
                            rs.getInt(SchemaDB.COL_AMOUNT),
                            rs.getInt(SchemaDB.COL_PRICE)
                    );
                } else {
                    System.out.println("There isn't a product for ID: " + productId);
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
