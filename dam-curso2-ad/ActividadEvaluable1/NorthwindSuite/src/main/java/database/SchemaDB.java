package database;

public interface SchemaDB {
    String NAME = "ALMACEN";
    String HOST = "localhost";
    String USER = "root";
    String PWD = "";
    String TAB_PRODUCTS_NAME = "Productos";
    String TAB_EMPLOYEES_NAME = "Empleados";
    String TAB_ORDERS_NAME = "Pedidos";
    String TAB_FAVPRODUCTS_NAME = "Productos_Fav";
    // THE ORDER MATTERS FOR DELETE (child tables first OR:
    // java.sql.SQLIntegrityConstraintViolationException: Cannot delete or update a parent row: a foreign key constraint fails
    String[] TAB_ALL = {TAB_EMPLOYEES_NAME, TAB_ORDERS_NAME, TAB_FAVPRODUCTS_NAME, TAB_PRODUCTS_NAME};
    enum Tabs {
        EMPLOYEES(TAB_EMPLOYEES_NAME), ORDERS(TAB_ORDERS_NAME), FAVPRODUCTS(TAB_FAVPRODUCTS_NAME), PRODUCTS(TAB_PRODUCTS_NAME);
        private final String nameInDB;
        private Tabs(String nameInDB) {
            this.nameInDB = nameInDB;
        }
        public String getNameInDB() {
            return nameInDB;
        }
    }
    String COL_ID = "id";
    String COL_NAME = "nombre";
    String COL_LAST_NAME = "apellidos";
    String COL_MAIL = "correo";
    String COL_PRICE = "precio";
    String COL_AMOUNT = "cantidad";
    String COL_INFO = "descripción";
    String COL_PRODUCT_ID = "id_producto";
    String COL_TOTAL_PRICE = "precio_total";
    String SEPARATOR = " | ";
}
