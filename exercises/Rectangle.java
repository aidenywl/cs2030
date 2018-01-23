import java.awt.Color;

/**
 * A Rectangle object that encapsulates a Rectangle on a 2D plane
 */

class Rectangle extends PaintedShape  implements Shape, Printable {
  private Point  bottomLeftPoint;
  private double length;
  private double width;
  
  /**
   * Create a Rectangle with the bottom left point anchored on bottomLeftPoint with length and width specified
   */
  public Rectangle(Point bottomLeftPoint, double length, double width) {
    super(Color.WHITE, Color.BLACK, 1.0);
    this.bottomLeftPoint = bottomLeftPoint;
    this.length = length;
    this.width = width;
  }

  /**
   * returns the area of the rectangle
   */
  @Override
  public double getArea() {
    return length * width;
  }

  /**
   * returns the perimeter of the rectangle
   */
  @Override
  public double getPerimeter() {
    return 2 * (length + width);
  }
  /**
   *  returns true if the rectangle encompasses Point p and false otherwise
   */
  @Override
  public boolean contains(Point p) {
    double xDiff = this.bottomLeftPoint.xDistanceTo(p);
    double yDiff = this.bottomLeftPoint.yDistanceTo(p);
    double xRange = width;
    double yRange = length;
    
    return (xDiff <= xRange && yDiff <= yRange && xDiff >= 0 && yDiff >= 0);
  }

  @Override
  public void print() {
    System.out.printf("The length of the rectangle is: %f, and the width of the rectangle is: %f\n", this.length, this.width);
    System.out.printf("The bottom left coordinate is: \n");
    this.bottomLeftPoint.print();
  }

  public void setSize(int length, int width) {
    this.length = (double) length;
    this.width = (double) width;
  }

}  
