public abstract class Figura2D implements FiguraGeometrica {
    private String name;

    public Figura2D(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public abstract double calcularPerimetro();
}
