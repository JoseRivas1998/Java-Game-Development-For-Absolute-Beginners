import java.util.Scanner;

public class Example5 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a grade as a decimal: ");

        double grade = scanner.nextDouble();

        char letterGrade;
        if (grade >= 90) {
            letterGrade = 'A';
        } else if (grade >= 80) {
            letterGrade = 'B';
        } else if (grade >= 70) {
            letterGrade = 'C';
        } else if (grade >= 60) {
            letterGrade = 'D';
        } else {
            letterGrade = 'F';
        }

        System.out.println("Your grade is: " + letterGrade);

        scanner.close();
    }

}
