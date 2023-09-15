// Luis Fernando Martínez Andreu
// Actividad Semana 21 - Parte 3
// Los libros tienen además un atributo prestado. 
// Cuando se crean los libros, no están prestados. 

public final class Libro extends Publicacion implements Prestable {
    private boolean prestado;

    public Libro(String title, String ISBN, int publicationYear) {
        super(title, ISBN, publicationYear);
        this.prestado = false;
    }

    public void presta() {
        this.prestado = true;
    }

    public void devuelve() {
        this.prestado = false;
    }

    public String estaPrestado() {
        if (this.prestado) {
            return "Prestado";
        } else {
            return "No";
        }
    }
}
