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
    String[] TAB_ALL = {TAB_PRODUCTS_NAME, TAB_EMPLOYEES_NAME, TAB_ORDERS_NAME, TAB_FAVPRODUCTS_NAME};
    String COL_ID = "id";
    String COL_NAME = "nombre";
    String COL_PRICE = "precio";
    String COL_AMOUNT = "cantidad";
    String COL_INFO = "descripción";
}
