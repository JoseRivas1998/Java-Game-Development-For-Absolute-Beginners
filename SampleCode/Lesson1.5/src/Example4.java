import java.util.Arrays;
import java.util.Random;

public class Example4 {

    public static void main(String[] args) {
        Random random = new Random();
        int[] numbers = new int[20];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
        System.out.println(Arrays.toString(numbers));

        int min = findMin(numbers);
        System.out.println("Minimum: " + min);

    }

    // Here we are only accessing the elements
    private static int findMin(int[] arr) {
        int min = arr[0];
        for (int num : arr) {
            min = Math.min(min, num);
        }
        return min;
    }

}
