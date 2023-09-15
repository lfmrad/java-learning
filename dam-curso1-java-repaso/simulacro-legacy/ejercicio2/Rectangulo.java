public class Rectangulo extends Figura2D {
    private double length, height;
    
    public Rectangulo(String name, double length, double height) {
        super(name);
        this.length = length;
        this.height = height;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * (this.length + this.height);
    }

    @Override
    public String toString() {
        String info = "\n=== Atributos del rectángulo ===";
        info += "\nNombre: " + this.getName();
        info += "\nLongitud: " + String.format("%.2f", this.getLength());
        info += "\nAltura: " + String.format("%.2f", this.getHeight());
        info += "\nPerímetro: " + String.format("%.2f",this.calcularPerimetro());
        return info;
    }

    public boolean equals(Rectangulo obj) {
        boolean sameName = this.getName() == obj.getName() ? true : false;
        boolean sameLength = this.getLength() == obj.getLength() ? true : false;
        boolean sameHeight = this.getHeight() == obj.getHeight() ? true : false;
        return sameName && sameLength && sameHeight;
    }
}
