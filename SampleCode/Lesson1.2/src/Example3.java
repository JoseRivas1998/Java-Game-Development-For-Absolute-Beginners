import java.util.Scanner;

public class Example3 {

    public static void main(String[] args) {
        // This is example will introduce how to read user input from the console!

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a radius: ");

        // System will wait for user to enter a radius
        double radius = scanner.nextDouble();

        double area = Math.PI * radius * radius;
        double circumference = 2 * Math.PI * radius;

        System.out.println("A circle with radius " + radius + " has an area of " + area + " and a circumference of " + circumference);

        scanner.close(); // This is important

    }

}
