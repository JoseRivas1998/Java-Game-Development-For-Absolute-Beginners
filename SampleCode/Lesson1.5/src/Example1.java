import java.util.Arrays;
import java.util.Random;

public class Example1 {

    // There's been an array here this whole time!!
    public static void main(String[] args) {
        Random random = new Random();
        int[] numbers = new int[20];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
        // Arrays don't have their own to string method
        System.out.println(numbers);
        System.out.println(Arrays.toString(numbers));

        int numEven = 0;
        for (int number : numbers) {
            if(number % 2 == 0) {
                numEven++;
            }
        }

        System.out.println("Even numbers: " + numEven);

    }

}
