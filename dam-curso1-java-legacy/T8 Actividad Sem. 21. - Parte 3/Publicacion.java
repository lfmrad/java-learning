// Luis Fernando Martínez Andreu
// Actividad Semana 21 - Parte 3
// Las características comunes de las revistas, las publicaciones y los libros son 
// el código ISBN, el título, y el año de publicación. 

import java.util.regex.Pattern;

public class Publicacion {
    private String title, ISBN, publicationYear;
    private Pattern validISBN = Pattern.compile("\\d{3}-\\d{1}-\\d{2}-\\d{6}-\\d{1}");
    private Pattern validYear = Pattern.compile("\\d{4}");

    public Publicacion (String title, String ISBN, int publicationYear) {
        this.title = title;
        this.ISBN = validateFormat(ISBN, validISBN);
        this.publicationYear = validateFormat(Integer.toString(publicationYear), validYear);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setISBN(String ISBN) {
        this.ISBN = validateFormat(ISBN, validISBN);
    }

    public String getISBN() {
        return this.ISBN;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = validateFormat(Integer.toString(publicationYear), validYear);
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    private String validateFormat(String textToValidate, Pattern validationPattern) {
        if (validationPattern.matcher(textToValidate).matches()) {
             return textToValidate;
        } else {
            return "[EL CAMPO NO SE HA PODIDO INICIALIZAR CORRECTAMENTE: FORMATO ERRÓNEO.]";
        }
    }
}