import java.util.Arrays;

public class Example6 {

    public static void main(String[] args) {

        int[] numbers = {1, 5, 6, 2, 9, 7};

        System.out.println(Arrays.toString(numbers));

        swap(numbers, 2, 5);
        // The values in the array actually changed!
        System.out.println(Arrays.toString(numbers));

    }

    // This is an incredibly useful method in Computer Science, you WILL see it again!
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
