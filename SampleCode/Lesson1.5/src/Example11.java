import java.util.ArrayList;
import java.util.List;

public class Example11 {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("John Doe");
        names.add("Foo Bar");
        names.add("David Smith");
        names.add("Lorem Ipsum");

        // This breaks! How would we do this?
        for (String name : names) {
            if(name.charAt(0) == 'J') {
                names.remove(name);
            }
        }

    }

}
