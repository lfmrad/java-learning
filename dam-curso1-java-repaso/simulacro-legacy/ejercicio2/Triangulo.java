public class Triangulo extends Figura2D {
    private double sideLength;

    public Triangulo(String name, double sideLength) {
        super(name);
        this.sideLength = sideLength;
    }

    public double getSideLength() {
        return sideLength;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public double calcularPerimetro() {
        return 3 * this.sideLength;
    }

    @Override
    public String toString() {
        String info = "\n=== Atributos del triángulo ===";
        info += "\nNombre: " + this.getName();
        info += "\nLongitud lado: " + String.format("%.2f", this.getSideLength());
        info += "\nPerímetro: " + String.format("%.2f",this.calcularPerimetro());
        return info;
    }

    public boolean equals(Triangulo obj) {
        boolean sameName = this.getName() == obj.getName() ? true : false;
        boolean sameSide = this.getSideLength() == obj.getSideLength() ? true : false;
        return sameName && sameSide;
    }
}
