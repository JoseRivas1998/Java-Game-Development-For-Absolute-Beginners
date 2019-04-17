package example4;

public class Rectangle extends Quadrilateral {

    // This isn't the most memory efficient but whatever
    private double length;
    private double width;

    public Rectangle(String name) {
        super(name);
        this.length = 0;
        this.width = 0;
    }

    public Rectangle(String name, double length, double width) {
        super(name, length, length, width, width);
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return length * width;
    }
}
