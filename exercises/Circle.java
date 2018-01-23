import java.lang.Math;

/**
 * A Circle object encapsulates a circle on a 2D plane.  
 */
class Circle implements Shape, Printable {
  private Point center;  // the (x, y) coordinate  of the center
  private double radius;  // the length of the radius

  /**
   * Create a circle centered on Point p  with given radius
   */
  public Circle(Point p, double radius) {
    this.center = p;
    this.radius = radius;
  }

  /**
   * Return the area of the circle.
   */
  @Override
  public double getArea() {
    return Math.PI * this.radius * this.radius;
  }

  /**
   * Return the circumference of the circle.
   */
  @Override
  public double getPerimeter() {
    return Math.PI * 2 * this.radius;
  }

  /**
   * Move the center of the circle to the new position point newP
   */
  public void moveTo(Point newP) {
    this.center = newP;
  }


  /**
   * Return true if the given point (testX, testY) is within the circle.
   */
  @Override
  public boolean contains(Point givenPoint) {
    return (givenPoint.distance(this.center) < this.radius);
  }

  @Override
  public void print() {
    System.out.printf("radius: %f\n", radius);
    System.out.printf("center:");
    center.print();
  }   
}
