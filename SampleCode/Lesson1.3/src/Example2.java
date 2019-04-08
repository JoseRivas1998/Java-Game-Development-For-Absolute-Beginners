import java.util.Random;

public class Example2 {

    public static void main(String[] args) {
        // Lets learn about random numbers!
        Random random = new Random();
        System.out.println("I will flip a coin, and will tell you if it is heads!");

        int result = random.nextInt(100);

        if(result < 50) {
            System.out.println("I flipped heads!");
        }

        // Run this a bunch of times, count how many heads you get, and how many you don't, should be around the same for both!

    }

}
