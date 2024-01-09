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

    public static void printAllDataFrom(SchemaDB.Tabs selected) {
        String query = String.format("SELECT * FROM %s", selected.getNameInDB());
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            System.out.println("ALL DATA FROM: " + selected.getNameInDB());
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= columns; i++) {
                sb.append(rsmd.getColumnLabel(i));
                sb.append(SchemaDB.SEPARATOR);
            }
            sb.deleteCharAt(sb.length() - 2); // deletes last separator
            sb.append("\n");
            while(rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    sb.append(rs.getString(i));
                    sb.append(SchemaDB.SEPARATOR);
                }
                sb.deleteCharAt(sb.length() - 2);
                sb.append("\n");
            }
            System.out.println(sb);
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

    public static void addOrder(List<Order> orderList) {
        for (Order order : orderList) {
            addOrder(order);
        }
    }

    public static List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", SchemaDB.TAB_ORDERS_NAME);
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt(SchemaDB.COL_ID),
                        getProductById(rs.getInt(SchemaDB.COL_PRODUCT_ID)),
                        rs.getString(SchemaDB.COL_INFO)
                );
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addEmployee(Employee employee) {
        String query = String.format("INSERT INTO %s (%s,%s,%s) VALUE (?,?,?)", SchemaDB.TAB_EMPLOYEES_NAME,
                SchemaDB.COL_NAME, SchemaDB.COL_LAST_NAME, SchemaDB.COL_MAIL);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getEmail());
            int modifiedRows = ps.executeUpdate();
            System.out.println("DB insertion successful. Modified: " + modifiedRows + " rows.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addEmployee(List<Employee> employeeList) {
        for (Employee employee : employeeList) {
            addEmployee(employee);
        }
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", SchemaDB.TAB_EMPLOYEES_NAME);
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt(SchemaDB.COL_ID),
                        rs.getString(SchemaDB.COL_NAME),
                        rs.getString(SchemaDB.COL_LAST_NAME),
                        rs.getString(SchemaDB.COL_MAIL)
                );
                employeeList.add(employee);
            }
            return employeeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public static List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", SchemaDB.TAB_PRODUCTS_NAME);
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Product product = new Product(
                    rs.getInt(SchemaDB.COL_ID),
                    rs.getString(SchemaDB.COL_NAME),
                    rs.getString(SchemaDB.COL_INFO),
                    rs.getInt(SchemaDB.COL_AMOUNT),
                    rs.getInt(SchemaDB.COL_PRICE)
                );
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Product> getProducts(int price, boolean overPrice) {
        List<Product> productList = new ArrayList<>();
        char condition = overPrice ? '>' : '<';
        String query = String.format("SELECT * FROM %s WHERE %s %c %d", SchemaDB.TAB_PRODUCTS_NAME, SchemaDB.COL_PRICE, condition, price);
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt(SchemaDB.COL_ID),
                        rs.getString(SchemaDB.COL_NAME),
                        rs.getString(SchemaDB.COL_INFO),
                        rs.getInt(SchemaDB.COL_AMOUNT),
                        rs.getInt(SchemaDB.COL_PRICE)
                );
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public static void addToFavorites(Product product) {
        String query = String.format("INSERT INTO %s (%s) VALUE (?)", SchemaDB.TAB_FAVPRODUCTS_NAME,
                SchemaDB.COL_PRODUCT_ID);
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, product.getId());
            int modifiedRows = ps.executeUpdate();
            System.out.println("DB insertion successful. Modified: " + modifiedRows + " rows.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addToFavorites(List<Product> productList) {
        for (Product product : productList) {
            addToFavorites(product);
        }
    }
}
