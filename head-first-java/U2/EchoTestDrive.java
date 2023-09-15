public class EchoTestDrive {
    
    public static void main(String[] args) {
        Echo e1 = new Echo();
        Echo e2 = e1; // Echo e2 = new Echo(); 10 answer, correct answer
        int x = 0;

        while (x < 4) {
            e1.hello();
            e1.count = e1.count + 1;
            if (x == 3) {
                e2.count = e2.count + 1;
            }
            if (x > 0) {
                e2.count = e2.count + e1.count;
            }
            x++;
        }
        System.out.println(e2.count);
    }
}

class Echo {
    int count = 0;

    void hello() {
        System.out.println("helloooo...");
    }
}

// for output 10 x>0, x>1 ORIGINAL
// hello, 0, e1 = 1
// hello, 1, e1 = 2, e2 = 1
// hello, 2, e1 = 3, e2 = 2, 5
// hello, 3, e1 = 4, e2 = 5+1, 4+6=10

// for output 24 
// hello, 0, e1 = 1, e2 = 2
// hello, 1, e1 = 2, e2 = 4
// hello, 2, e1 = 5, e2 = 10
// hello, 3, e1 = 11, e2 = 12, e2 = 24

