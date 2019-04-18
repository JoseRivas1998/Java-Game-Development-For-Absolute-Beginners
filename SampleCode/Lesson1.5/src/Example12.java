import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Example12 {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("John Doe");
        names.add("Foo Bar");
        names.add("David Smith");
        names.add("Lorem Ipsum");

        Iterator<String> iterator = names.iterator();

        // This is actually what a for each loop is!
        while(iterator.hasNext()) {
            String name = iterator.next();
            if(name.charAt(0) == 'F') {
                // Notice we used iterator's remove!
                iterator.remove();
            }
        }

        System.out.println(names);

    }


}
