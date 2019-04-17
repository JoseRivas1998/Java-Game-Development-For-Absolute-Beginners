package example1;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public void print() {
        System.out.println("(" + x + ", " + y + ")");
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    private double distSq(Point point) {
        return Math.pow(point.x - x, 2) + Math.pow(point.y - y, 2);
    }

    public double dist(Point point) {
        return Math.sqrt(distSq(point));
    }

}
