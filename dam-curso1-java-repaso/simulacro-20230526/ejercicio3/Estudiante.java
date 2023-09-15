import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Estudiante {
    private String name;
    private int age;
    private double grade1, grade2, grade3;

    public Estudiante (String name, int age, double grade1, double grade2, double grade3) {
        this.name = name;
        this.age = age;
        this.grade1 = grade1;
        this.grade2 = grade2;
        this.grade3 = grade3;
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

    public double getGrade1() {
        return grade1;
    }

    public void setGrade1(int grade1) {
        this.grade1 = grade1;
    }

    public double getGrade2() {
        return grade2;
    }

    public void setGrade2(int grade2) {
        this.grade2 = grade2;
    }

    public double getGrade3() {
        return grade3;
    }

    public void setGrade3(int grade3) {
        this.grade3 = grade3;
    } 

    public double getAverageGrade() {
        return (this.grade1 + this.grade2 + this.grade3) / 3; 
    }

    @Override
    public String toString() {
        String outputText = "\n **** Atributos del estudiante **** ";
        outputText += "\nNombre: " + this.name;
        outputText += "\nEdad: " + this.age;
        outputText += "\nNota 1: " + this.grade1;
        outputText += "\nNota 1: " + this.grade2;
        outputText += "\nNota 1: " + this.grade3;
        outputText += "\nNota media: " + String.format("%.2f", getAverageGrade());
        return outputText;
    } 

    public boolean equals(Estudiante stu) {
        boolean equalName = this.name.equals(stu.getName()) ? true : false;
        boolean equalAge = this.age == stu.getAge() ? true : false;
        boolean equalGrades = this.grade1 == stu.getGrade1() && this.grade2 == stu.getGrade2() && this.grade3 == stu.getGrade3() ? true : false;
        return equalName && equalAge && equalGrades;
    } 

    static ArrayList<Estudiante> leerEstudiantes(String fileName) throws IOException {
            ArrayList<Estudiante> nuevoEstudiantes = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String nextLine;
            while((nextLine = br.readLine()) != null) {
                String[] line = nextLine.split(",");
                String name = line[0];
                int age = Integer.parseInt(line[1]);
                double grade1 = Double.parseDouble(line[2]);
                double grade2 = Double.parseDouble(line[2]);
                double grade3 = Double.parseDouble(line[3]);
                nuevoEstudiantes.add(new Estudiante(name, age, grade1, grade2, grade3));
            }
            br.close();
            return nuevoEstudiantes;
    }

    public static void main(String[] args) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante("Juan", 27, 3.2, 5.6, 8.3));
        estudiantes.add(new Estudiante("Ana", 35, 7.4, 6.2, 9));

        System.out.println("Estudiantes iniciales: ");
        for (Estudiante estudiante : estudiantes) {
            System.out.println(estudiante);
        }

        try {
            ArrayList<Estudiante> nuevosEstudiantes = leerEstudiantes("estudiantes.txt");
            System.out.println("Nuevos estudiantes: ");
            for (Estudiante estudiante : nuevosEstudiantes) {
                System.out.println(estudiante);
            }
        } catch (IOException e) {
            System.out.println("Excepción" + e);
        }
        
    }
} 