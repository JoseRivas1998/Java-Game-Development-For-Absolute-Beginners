import java.util.ArrayList;
import java.util.List;

public class Example9 {

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            intList.add((int) Math.pow(2, i));
        }
        // ArrayList has a toString!
        System.out.println(intList);

        int i = intList.get(3);

        System.out.println(i);

        intList.set(3, 6);

        System.out.println(intList);

        intList.add(1, 100);

        System.out.println(intList);

        intList.remove(1);

        System.out.println(intList);

        System.out.println(intList.size());
        while(intList.size() > 0) {
            intList.remove(0);
            System.out.println(intList);
        }

        System.out.println(intList.size());

    }

}
