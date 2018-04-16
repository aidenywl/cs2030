/**
 * A Square object encapsulates a square in 2D space.
 * A square object extends a rectangle as it is a special rectangle
 */
class Square {
  private double length;
  private double width;
  public Square (int length, int width) {
    if(length != width) {
      throw new java.lang.Error("Your Square's length must be equal to it's width!");
    } else {
      this.length = (double) length;
      this.width = (double) width;
      //  super(new Point(0,0), length, width);
    }
  }
  public void print() {
    System.out.printf("Your square's length is: %f, and your square's width is: %f.\n", this.length, this.width);
  }
}
