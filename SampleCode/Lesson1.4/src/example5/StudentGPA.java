package example5;

public class StudentGPA implements Loggable {

    private String bestName;
    private double bestGPA;

    public StudentGPA() {
        bestName = "";
        bestGPA = -1;
    }

    public void addStudent(String name, double gpa) {
        // In the real world the student will be added to some database but that's not the point
        if(gpa > bestGPA) { // Always use Float.compare or Double.compare when checking floating points for equality
            bestName = name;
            bestGPA = gpa;
        }
    }

    @Override
    public void log() {
        System.out.println("Best student, " + bestName + ", has a gpa of " + bestGPA);
    }
}
