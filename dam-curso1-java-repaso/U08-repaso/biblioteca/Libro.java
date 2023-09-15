package biblioteca;

class Libro extends Publicacion implements Prestable {

    private boolean prestado;

    Libro(String ISBN, String title, int releaseYear) {
        super(ISBN, title, releaseYear);
        this.prestado = false;
    }

    @Override
    public void presta() {
        this.prestado = true;
    }

    @Override
    public void devuelve() {
        this.prestado = false;
    }

    @Override
    public boolean estaPrestado() {
        return this.prestado;
    }
}