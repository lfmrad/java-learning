public class Elemento {
    private String productName;
    private double price;
    private int units;

    public Elemento(String productName, double price, int units) {
        this.productName = productName;
        this.price = price;
        this.units = units;
    }

    public String getName() {
        return this.productName;
    }

    public double getPrice() {
        return this.price;
    }

    public int getUnits() {
        return this.units;
    }
    
}


