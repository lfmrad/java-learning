import java.util.ArrayList;

public class Prueba {
    
    public static void main(String[] args) {
        
        ArrayList<Figura2D> figuresArray = new ArrayList<>();

        figuresArray.add(new Circulo("Circulo Grande",4));
        figuresArray.add(new Circulo("Circulo Pequeño",2));
        figuresArray.add(new Rectangulo("Rectángulo Grande",6,3));
        figuresArray.add(new Rectangulo("Rectángulo Pequeño",4,1));
        figuresArray.add(new Triangulo("Circulo Grande",5));
        figuresArray.add(new Circulo("Circulo Pequeño",3));

        for (Figura2D figura2d : figuresArray) {
            System.out.println(figura2d);
        }
    }
}
