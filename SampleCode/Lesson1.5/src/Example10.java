import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Example10 {

    public static void main(String[] args) {
        List<Integer> integerList = randomIntList(20);
        System.out.println(integerList);

        for (int i = integerList.size() - 1; i >= 0; i--) {
            if(integerList.get(i) % 2 == 0) {
                integerList.remove(i);
            }
        }

        System.out.println(integerList);

        int multiplesOf5 = 0;

        for (int integer : integerList) {
            if(integer % 5 == 0) {
                multiplesOf5++;
            }
        }

        System.out.println("Multiples of 5: " + multiplesOf5);

    }

    private static List<Integer> randomIntList(int length) {
        Random random = new Random();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            result.add(random.nextInt(100));
        }
        return result;
    }

}
