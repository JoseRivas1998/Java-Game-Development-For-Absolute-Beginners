import java.util.HashMap;
import java.util.Map;

public class Example13 {

    public static void main(String[] args) {
        Map<String, Integer> people = new HashMap<>();

        people.put("Jim", 25);
        people.put("David", 14);
        people.put("Luke", 23);
        people.put("Sammy", 18);
        people.put("Jane", 34);

        System.out.println("Jim is " + people.get("Jim") + " years old.");

        // Iterate through keys:
        System.out.println("People in database:");
        for (String person : people.keySet()) {
            System.out.println(person);
        }

        // Iterate through values
        System.out.println("Ages in database");
        for (int age : people.values()) {
            System.out.println(age);
        }

        // Iterate through key value pairs:
        System.out.println("Database: ");
        for (String person : people.keySet()) {
            System.out.println(person + " is " + people.get(person) + " years old.");
        }

        System.out.println();
        System.out.println();

        System.out.println("Jane is " + people.get("Jane") + " years old.");

        people.put("Jane", 10);

        System.out.println("Jane is " + people.get("Jane") + " years old.");

        System.out.println();
        System.out.println();

        // Iterate through key value pairs:
        System.out.println("Database: ");
        for (String person : people.keySet()) {
            System.out.println(person + " is " + people.get(person) + " years old.");
        }

        people.put("Jose", 20);

        // Iterate through key value pairs:
        System.out.println("Database: ");
        for (String person : people.keySet()) {
            System.out.println(person + " is " + people.get(person) + " years old.");
        }

    }

}
