// Luis Fernando Martínez Andreu
// Actividad Semana 21 - Parte 3
// Las revistas tienen un número. 

public final class Revista extends Publicacion {
    private int numRevista;
    private static int revistasCreadas = 0;

    public Revista(String title, String ISBN, int publicationYear) {
        super(title, ISBN, publicationYear);
        this.numRevista = ++revistasCreadas;
    }

    public int getNumRevista() {
        return this.numRevista;
    }
}