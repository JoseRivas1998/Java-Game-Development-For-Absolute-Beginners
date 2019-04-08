import java.util.Scanner;

public class Example4 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your age as an integer: ");

        int age = scanner.nextInt();

        if (age >= 21) {
            System.out.println("You can basically do anything!");
        } else if (age >= 18) {
            System.out.println("You can vote, but you can't easily rent a car!");
        } else if (age >= 16) {
            System.out.println("You can get a license!");
        } else if (age >= 13) {
            System.out.println("Have fun in your teen years!");
        }

        scanner.close();

    }

}
