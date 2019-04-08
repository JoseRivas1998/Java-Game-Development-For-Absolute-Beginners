public class Example2 {

    public static void main(String[] args) {

        int n1 = 2;
        System.out.println(n1);
        int n2 = 3;
        System.out.println(n2);

        // What will the result be, will it round?
        int n3 = n1 / n2;
        System.out.println(n3);

        // Now that we are storing in an integer, we shouldn't get zero right?
        double x1 = n1 / n2;
        System.out.println(x1);

        // Maybe because n1 and n2 are ints?
        double x2 = 2 / 3;
        System.out.println(x2);

        // We can easily convert an int to a double
        double x3 = n1;
        // Notice the difference
        System.out.println(x3);

        double x4 = 3.6;
        System.out.println(x4);
        // We cannot easily go from a double to an int, casting is needed, how will it round?
        int n4 = (int) x4;
        System.out.println(n4); // Simply removes the part after the decimal!

        double x5 = (double) n1 / n2; // using an inline cast
        System.out.println(x5); // Finally gives us the answer!

        double x6 = 1.0 / 3; // As long as one is a real, then the result will be a real
        System.out.println(x6);

        double x7 = 1 / 3; // However, if both operands are integers, the result will be an integer
        System.out.println(x7);

    }

}
