import java.util.Arrays;
import java.util.Random;

public class Example5 {

    public static void main(String[] args) {
        Random random = new Random();
        int[] numbers = new int[5];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
        System.out.println("Original array: " + Arrays.toString(numbers));

        changeArray(numbers);

        System.out.println("Modified array: " + Arrays.toString(numbers));
    }

    // Here we are modifying elements
    private static void changeArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] += 3;
        }
    }

}
