package model;

public class Order {
    private String info;
    private Product product;
    private int totalPrice;

    private int id;

    public Order(Product product, String info) {
        if (product != null) {
            this.product = product;
            this.info = info;
            this.totalPrice = product.getPrice();
        } else {
            System.out.println("Product is null. An actual product must be provided to create an order.");
        }
    }

    public Order(int id, Product product, String info) {
        this(product, info);
        this.id = id;
    }

    public int getAssociatedProductID() {
        return product.getId();
    }

    public String getInfo() {
        return info;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Order{" +
                "info='" + info + '\'' +
                ", product=" + product.toString() +
                ", totalPrice=" + totalPrice +
                ", id=" + id +
                '}';
    }
}
