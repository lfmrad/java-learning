import java.util.Scanner;

public class Factorial {


    public static void main(String[] args) {
        int fact = calcFact(5);
        System.out.println("El resultado del factorial es: " + fact);       
    }

    static int calcFact(int number) {
        System.out.println(number);
        if(number == 1) {
            return 1;
        } else {
            return number * calcFact(number-1);
        }
    }
}

// 5*4=20
// 20*3=60
// 60*2=120
// 120*1=120
