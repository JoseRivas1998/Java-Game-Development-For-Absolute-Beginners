import java.util.Random;
import java.util.Scanner;

public class Example6 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int guess = -1;
        int number = random.nextInt(101); // Upper bound is exclusive

        int tries = 0;

        System.out.println("I am thinking of a number between 0 and 100");
        while (guess != number) {
            System.out.print("What number am I thinking? ");
            guess = scanner.nextInt();
            if(guess > number) {
                System.out.println("Too high!");
            } else if (guess < number) {
                System.out.println("Too low!");
            }
            tries++;
        }

        System.out.println("You guessed the number right!");
        if(tries == 1) {
            System.out.println("Wow! Only one try!");
        } else {
            System.out.println("That took you " + tries + " tries.");
        }
        scanner.close();

    }

}
