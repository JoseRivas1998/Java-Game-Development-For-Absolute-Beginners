package example7;

import java.util.Arrays;

public class Example7 {

    public static void main(String[] args) {
        int[] data = {
                7,  12, 4,  1,  14,
                11, 12, 6,  9,  3,
                4,  1,  2,  13, 12,
                8,  3,  15, 14, 7,
                4,  17, 8,  16, 6,
                1,  16, 5,  19, 15,
                20, 6,  14, 14, 8,
                15, 17, 18, 17, 9
        };
        
        ResizableSet set = new ResizableSet();
        for (int datum : data) {
            set.put(datum);
        }
        System.out.println("Set size: " + set.size());
        System.out.println(set);
        System.out.println(Arrays.toString(set.toArray()));
    }

}
