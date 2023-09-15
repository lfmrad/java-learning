import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Estudiante {
    private String name;
    private int age;
    private double[] grades;

    public Estudiante(String name, int age, double grade1, double grade2, double grade3) {
        this.grades = new double[3];
        this.name = name;
        this.age = age;
        this.grades[0] = grade1;
        this.grades[1] = grade2;
        this.grades[2] = grade3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGrade(int gradeNumber) {
        return grades[gradeNumber-1];
    }

    public double getAverageGrade() {
        double sum = 0;
        for (double d : grades) {
            sum += d;
        }
        return sum / grades.length;
    }

    @Override
    public String toString() {
        String objectInfo = "\n=== Datos estudiante ===\n";
        objectInfo += "Nombre: " + this.name + "\n";
        objectInfo += "Edad: " + this.age + "\n";
        objectInfo += "Nota1: " + this.grades[0] + "\n";
        objectInfo += "Nota2: " + this.grades[1] + "\n";
        objectInfo += "Nota3: " + this.grades[2] + "\n";
        objectInfo += "Nota media: " + String.format("%.2f", getAverageGrade()) + "\n";
        return objectInfo;
    }

    public boolean equals(Estudiante candidate) {
        boolean equalName = this.name.equals(candidate.getName()) ? true : false;
        boolean equalAge = this.age == candidate.age ? true : false;
        boolean equalGrades = Arrays.equals(this.grades, candidate.grades) ? true : false;
        return equalName && equalAge && equalGrades;
    }

    static ArrayList<Estudiante> leerEstudiantes(String fileName) throws IOException {
        ArrayList<Estudiante> nuevosEstudiantes = new ArrayList<Estudiante>();
        
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            String data[] = line.split(",");
            String name = data[0];
            int age = Integer.parseInt(data[1]);
            double[] grades = new double[3];
            grades[0] = Double.parseDouble(data[2]);
            grades[1] = Double.parseDouble(data[3]);
            grades[2] = Double.parseDouble(data[4]);
            nuevosEstudiantes.add(new Estudiante(name, age, grades[0], grades[1], grades[2]));
        }
        br.close();
        return nuevosEstudiantes;
    }

    public static void main(String[] args) {
        ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
        estudiantes.add(new Estudiante("Juan", 27, 3.2, 5.6, 8.3));
        estudiantes.add(new Estudiante("Ana", 35, 7.4, 6.5, 9));

        System.out.println("Se muestran los datos de los estudiantes iniciales:");
        for (Estudiante estudiante : estudiantes) {
            System.out.println(estudiante);
        }
        System.out.println("Se lee la lista del archivo y se muestran los estudiantes nuevos:");
        try { 
            for (Estudiante nuevoEstudiante : leerEstudiantes("estudiantes.txt")) {
                System.out.println(nuevoEstudiante);
            }
        } catch (IOException e) {
            System.out.println("Excepción: " + e.getMessage());
        }
    }
}