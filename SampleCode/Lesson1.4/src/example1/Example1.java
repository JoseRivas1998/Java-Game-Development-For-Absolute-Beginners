package example1;

public class Example1 {

    public static void main(String[] args) {
        Point p1 = new Point();
        p1.print();

        Point p2 = new Point();
        p2.print();
        // These are both 0,0 right?

        // What will this be?
        boolean b1 = p1 == p2;
        System.out.println(b1); // Why is this false?

        Point p3 = p1;
        boolean b2 = p1 == p3;
        System.out.println(b2); // Why is this one true?

        boolean b3 = p2 == p3;
        System.out.println(b3);

        p1.move(1, 3);
        p2.move(2, 5);

        p1.print();
        p2.print();
        p3.print(); // Why did p3 move as well?

        double dist = p1.dist(p2);
        System.out.println("Dist: " + dist);

    }

}
