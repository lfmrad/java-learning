/* Luis Fernando Martínez Andreu
* Actividad Semana 21 - Parte 3
* Queremos realizar una aplicación para una biblioteca. 
* Crea las clases Publicacion, Libro y Revista. Las clases deben estar implementadas con la jerarquía correcta. 
* Las características comunes de las revistas, las publicaciones y los libros son 
* el código ISBN, el título, y el año de publicación. 
* Los libros tienen además un atributo prestado. 
* Cuando se crean los libros, no están prestados. 
* Las revistas tienen un número. 
* La clase Libro debe implementar la interfaz Prestable que tiene los métodos presta, devuelve y estaPrestado. 
* Crea un archivo con un programa principal en el que se pruebe la funcionalidad (archivo Biblioteca.java).
* Tendréis que subir los archivos Publicacion.java, Libro.java, Revista.java , Prestable.java y Biblioteca.java.
*/

public class Biblioteca {
    public static void main(String[] args) {
        // Se crean publicaciones, libros y revistas ejemplo.
        Publicacion pubActividad = new Publicacion("Actividad Sem. 21 - Parte 3", "978-3-16-148410-0", 2023);
        Libro libroProg = new Libro("Introducción a Java","879-3-16-148410-1",1997);
        Revista revistaProg1 = new Revista("Programando - Ed. 1","657-1-16-148410-1",2021);
        Revista revistaProg2 = new Revista("Programando - Ed. 2","658-2-16-148410-1",2021);
        // Si el año o el ISBN no cumplen un formato adecuado los campos no se inicializan correctamente y se les asigna un mensaje de error:
        Libro libroFallo = new Libro("Introducción a C++","879--148410-1",19997);

        // Se comprueba la funcionalidad de cada uno:
        // TESTs PUBLICACIÓN
        System.out.println("PRUEBA FUNCIONALIDAD PUBLICACIÓN: ");
        System.out.println("La publicación \"" + pubActividad.getTitle() + "\" tiene ISBN " + pubActividad.getISBN() + " y fue publicada en " + pubActividad.getPublicationYear());

        // TESTs LIBRO
        System.out.println("\n\nPRUEBA FUNCIONALIDAD LIBRO: ");
        System.out.println("El libro \"" + libroProg.getTitle() + "\" tiene ISBN " + libroProg.getISBN() + " y fue publicado en " + libroProg.getPublicationYear());
        System.out.println("¿Se encuentra prestado? " + libroProg.estaPrestado());
        libroProg.presta();
        System.out.println("Tras prestarse -> ¿Se encuentra prestado? " + libroProg.estaPrestado());
        libroProg.devuelve();
        System.out.println("Tras devolverse -> ¿Se encuentra prestado? " + libroProg.estaPrestado());

        // TESTs REVISTA
        System.out.println("\n\nPRUEBA FUNCIONALIDAD REVISTA: ");
        System.out.println("La revista \"" + revistaProg1.getTitle() + "\" tiene ISBN " + revistaProg1.getISBN() + " y fue publicada en " + revistaProg1.getPublicationYear());
        System.out.println("El número de la revista es el: " + revistaProg1.getNumRevista());
        System.out.println("La revista \"" + revistaProg2.getTitle() + "\" tiene ISBN " + revistaProg2.getISBN() + " y fue publicada en " + revistaProg2.getPublicationYear());
        System.out.println("El número de la segunda revista continua con el: " + revistaProg2.getNumRevista());

        // TEST EJEMPLO LIBRO CON DATOS INCORRECTOS
        System.out.println("\n\nPRUEBA FUNCIONALIDAD LIBRO FALLO: ");
        System.out.println("El libro \"" + libroFallo.getTitle() + "\" tiene ISBN " + libroFallo.getISBN() + " y fue publicado en " + libroFallo.getPublicationYear());
    }
}