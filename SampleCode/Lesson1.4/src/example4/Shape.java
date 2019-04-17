package example4;

public abstract class Shape {

    private String name;

    public Shape(String name) {
        this.name = name;
    }

    public abstract double area();
    public abstract double perimeter();

    public double semiPerimeter() {
        return perimeter() / 2.0;
    }

    // This is going to be our standard equals method
    @Override
    public boolean equals(Object obj) {
        boolean result;
        if(obj == null || obj.getClass() != this.getClass()) {
            result = false;
        } else {
            Shape s = (Shape) obj;
            result = this.name.equals(s.name); // Strings are non-primitive
        }
        return result;
    }
}
