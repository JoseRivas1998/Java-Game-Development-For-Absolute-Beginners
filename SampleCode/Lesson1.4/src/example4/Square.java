package example4;

public class Square extends Quadrilateral {

    private double side;

    public Square(String name) {
        super(name);
        this.side = 0;
    }

    public Square(String name, double side) {
        super(name, side, side, side, side);
        this.side = side;
    }

    @Override
    public double area() {
        return this.side * this.side;
    }

}
