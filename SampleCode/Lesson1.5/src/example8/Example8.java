package example8;

public class Example8 {

    public static void main(String[] args) {
        GenericBox<String> stringBox = new GenericBox<>("Hello!");

        // No casting needed!
        System.out.println(stringBox.get().toUpperCase());

        //GenericBox<int> intBox; This is not allowed

        // This uses the Integer wrapper class
        GenericBox<Integer> intBox = new GenericBox<>(3);
        System.out.println(intBox.get());
        intBox.set(100);
        System.out.println(intBox.get());

    }

}
