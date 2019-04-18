package example5;

import java.util.Random;

public class Example5 {

    public static void main(String[] args) {
        Server server = new Server();
        for(int i = 0; i < 100; i++) {
            server.serve(randomIP());
        }
        log(server);

        System.out.println();

        StudentGPA studentGPA = new StudentGPA();

        studentGPA.addStudent("Bill Smith", 3.0);
        studentGPA.addStudent("Jimmy Scott", 2.6);
        studentGPA.addStudent("John Doe", 3.7);
        studentGPA.addStudent("Foo Bar", 1.6);

        log(studentGPA);

    }

    private static void log(Loggable loggable) {
        loggable.log();
    }

    private static String randomIP() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 4; i++) {
            int num = random.nextInt(255);
            sb.append(num);
            if(i < 3) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

}
