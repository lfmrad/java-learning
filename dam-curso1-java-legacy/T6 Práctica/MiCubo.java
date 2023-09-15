public class MiCubo {
    int capacidad, contenido;

    MiCubo(int c) {
        this.capacidad = c;
    }

    int getCapacidad() {
        return this.capacidad;
    }

    int getContenido() {
        return this.contenido;
    }

    void setContenido(int litros) {
        this.contenido = litros;
    }
 
    void vacia() {
        this.contenido = 0;
    }

    void llena() {
        this.contenido = this.capacidad;
    }

    void pinta() {
        for (int nivel = this.capacidad; nivel > 0; nivel--) {
            if (nivel > this.contenido) {
                System.out.println("#     #");
            } else {
                System.out.println("#~~~~~#");
            }
        }
        System.out.println("#######");
    }

    public static void main(String[] args) {

        MiCubo cuboXL = new MiCubo(10);
        MiCubo cuboL = new MiCubo(5);

        // cuboXL.setContenido(7);
        cuboXL.pinta();
    }
}