package example4;

public class Circle extends Shape{

    private double radius;

    public Circle(String name) {
        super(name);
        radius = 0;
    }

    public Circle(String name, double radius) {
        super(name);
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }
}
