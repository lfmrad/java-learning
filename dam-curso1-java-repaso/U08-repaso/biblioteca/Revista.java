package biblioteca;

class Revista extends Publicacion {
    private int issueNumber;
    private static int totalReleases = 0;

    Revista (String ISBN, String title, int releaseYear) {
        super(ISBN, title, releaseYear);
        issueNumber = ++totalReleases; 
    }
}
