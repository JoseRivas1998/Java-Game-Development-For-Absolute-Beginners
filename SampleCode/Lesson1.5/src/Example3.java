import java.util.Arrays;

public class Example3 {

    public static void main(String[] args) {
        double[] data = {
                17.41, 23.9, 1.07,
                14.35, 19.12, 22.91,
                19.12, 11.05, 6.38,
                7.38, 7.1, 22.6,
                3.28, 10.4, 22.12
        };
        System.out.println(Arrays.toString(data));
        double sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        System.out.println("Sum: " + sum);
        System.out.println("Number of Elements: " + data.length);

        double average = sum / data.length;

        System.out.println("Average: " + average);

        double diffSquareSum = 0;
        for (double datum : data) {
            diffSquareSum += Math.pow(datum - average, 2);
        }

        double standardDeviation = Math.sqrt(diffSquareSum / data.length);
        System.out.println("Standard Deviation: " + standardDeviation);

    }

}
