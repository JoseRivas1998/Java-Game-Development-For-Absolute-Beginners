package example4;

public abstract class Quadrilateral extends Shape {

    private double s1;
    private double s2;
    private double s3;
    private double s4;

    public Quadrilateral(String name) {
        super(name);
        s1 = 0;
        s2 = 0;
        s3 = 0;
        s4 = 0;
    }

    public Quadrilateral(String name, double s1, double s2, double s3, double s4) {
        super(name);
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
    }

    @Override
    public double perimeter() {
        return s1 + s2 + s3 + s4;
    }
}
