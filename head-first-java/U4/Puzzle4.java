public class Puzzle4 {
    public static void main(String[] args) {
        Value[] values = new Value[6];
        int number = 1;
        int i = 0;
        // 1, 10, 100, 1000, 10000, 100000
        while (i < 6) {
            values[i] = new Value();
            values[i].intValue = number;
            number = number * 10;
            i = i + 1;
        }

        // 6 6-1 100000 * 5 = 500000
        // 5 5-1 10000 * 4 = 40000
        // 4 4-1 1000 * 3 = 3000
        // 3 3-1 (!>100) 100 * (5-2) = 300
        // 2 2-1 100 * (5-1) = 400
        // 1 1-0 10 * (5-0) = 50
        int result = 0;
        i = 6;
        while (i > 0) {
            i = i - 1;
            result = result + values[i].doStuff(i);
        }
        System.out.println("result " + result);
    } 
}

class Value {
    int intValue;
    public int doStuff(int factor) {
        if (intValue > 100) {
            return intValue * factor;
        } else {
            return intValue * (5 - factor);
        }
    }
}


