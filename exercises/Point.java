import java.lang.Math;

/**
 * A point object encapsulates a point on a 2D plane.
 */

class Point implements Printable  {
  private double x; // x-coordinate of the point
  private double y; // y-coordinate of the point
  
  /**
   * Create a point with the following coordinates (x, y)
   */
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double  distance(Point otherPoint) {
    return Math.sqrt(
            (Math.pow(
              (this.x - otherPoint.x), 2)
           + Math.pow(
              (this.y - otherPoint.y), 2)));
  }
  
  /*
   * returns the distance in one dimension on the x and y axis
   */
  public double xDistanceTo(Point otherPoint) {
    return otherPoint.x - this.x;
  }
  public double yDistanceTo(Point otherPoint) {
    return otherPoint.y - this.y;
  }

  @Override
  public void print() {
    System.out.printf("The center is: (%f, %f).", this.x, this.y);
  }
}
