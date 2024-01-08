package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;
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

    public static void clearDB() {
        for (String table : SchemaDB.TAB_ALL) {
            clearTable(table);
        }
        System.out.println("All tables cleared. Ready to test.");
    }

    private static void clearTable(String tableName) {
        try {
            Statement st = connection.createStatement();
            String query = String.format("DELETE FROM %s;", tableName);
            st.execute(query);
            System.out.println("Completed: " + query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addProduct(Product newProduct) {
        String query = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUE (?,?,?,?)", SchemaDB.TAB_PRODUCTS_NAME,
                SchemaDB.COL_NAME, SchemaDB.COL_INFO, SchemaDB.COL_AMOUNT, SchemaDB.COL_PRICE);
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, newProduct.getName());
            ps.setString(2, newProduct.getInfo());
            ps.setInt(3, newProduct.getStock());
            ps.setInt(4, newProduct.getPrice());
            ps.execute();
            System.out.println("DB insertion successful.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addProduct(List<Product> productList) {
        for (Product product : productList){
            addProduct(product);
        }
    }
}
