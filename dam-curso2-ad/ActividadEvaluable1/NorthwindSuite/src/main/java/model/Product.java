package model;
public class Product {
    private String name;
    private String info;
    private int stock;
    private int price;

    public Product(String name, String info, int stock, int price) {
        this.name = name;
        this.info = info;
        this.stock = stock;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String description) {
        this.info = info;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + info + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
