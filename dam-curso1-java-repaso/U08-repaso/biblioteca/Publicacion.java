package biblioteca;

import java.util.regex.Pattern;

class Publicacion {
    private String ISBN;
    private String title;
    private int releaseYear;

    private Pattern validISBN = Pattern.compile("\\d{3}-\\d{1}-\\d{2}-\\d{6}-\\d{1}");
    private Pattern validYear = Pattern.compile("\\d{4}");

    Publicacion(String ISBN, String title, int releaseYear) {
        this.ISBN = ISBN;
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
