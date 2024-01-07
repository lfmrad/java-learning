package model;
public class Product {
    private String name;
    private String description;
    private int stock;
    private int price;

    public Product(String name, String description, int stock, int price) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
