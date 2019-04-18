import java.util.Arrays;
import java.util.Random;

public class Example2 {

    public static void main(String[] args) {
        Random random = new Random();
        int[] numbers = new int[20];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
        System.out.println(Arrays.toString(numbers));

        // Set all the even index numbers to 0
        for (int i = 0; i < numbers.length; i += 2) {
            numbers[i] = 0;
        }
        System.out.println(Arrays.toString(numbers));

    }

}
