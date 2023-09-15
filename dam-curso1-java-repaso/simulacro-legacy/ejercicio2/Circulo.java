public class Circulo extends Figura2D {
    private double radius;
    
    public Circulo(String name, double radio) {
        super(name);
        this.radius = radio;
    }

    public double getRadio() {
        return radius;
    }

    public void setRadio(double radio) {
        this.radius = radio;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * Math.PI * this.radius;
    }

    @Override
    public String toString() {
        String info = "\n=== Atributos del círculo ===";
        info += "\nNombre: " + this.getName();
        info += "\nRadio: " + String.format("%.2f", this.getRadio());
        info += "\nPerímetro: " + String.format("%.2f",this.calcularPerimetro());
        return info;
    }

    public boolean equals(Circulo obj) {
        boolean sameName = this.getName() == obj.getName() ? true : false;
        boolean sameRadius = this.getRadio() == obj.getRadio() ? true : false;
        return sameName && sameRadius;
    }
}
