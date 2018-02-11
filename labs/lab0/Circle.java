import java.lang.Math;

/**
 * Encapsulates a circle on a 2D plane.
 * @author Ooi Wei Tsang
 * @author Low Yew Woei A0121969W (students: put your name here)
 * @version CS2030 AY17/18 Sem 2 Lab 0
 */
public class Circle {
  private Point center;
  private double radius;

  /**
   * <p>Create a circle that passes through both points p and q.
   * Given any two points, there are exactly two circles that passes
   * through both points.  Imagine you are walking from p to q, one
   * of the circles would have the center on the left.  The other have
   * the center on the right. In this lab, we will only conside the circle
   * on the left because the circle on the right will be considered
   * when you walk from q to p.</p>
   *
   * <p>If p and q are at the same point, then an infinite number of such
   * circles exist.  If p and q are more than 2*radius apart, then
   * no such circle exist.  In such case, create a circle with radius
   * equals to Double.NaN.</p>
   *
   * @param p the point where the created circle will passed through
   * @param q another point where the created circle will passed through
   * @param radius the radius of the circle created
   */
    // Rather, the job is to intialize the fields of this object,
    // that is, the two private fields `center` and `radius`.
    //
    // Here are the steps:
    // Step 1: Construct a Point, m, as the mid point of p and q

    // Step 2: Find distance from p to m

    // Step 3: Find distance from m to new center, nc, by pythagoras theorem
    // m->nc = sqrt( p->m ^2 , radius ^2 )

    // Step 4: Get the angle of p to q, theta.

    // Step 5: move m to the nc with the help of the method defined
    // in `Point` class.  The distance to move is calculated
    // in Step 3.  The moving direction is given by theta + PI/2.

  /**
   * Creates a circle centered at center with given radius.
   *
   * @param center the point where the created circle is centered at
   * @param radius the radius of the circle created
   */
  public Circle(Point center, double radius) {
    this.center = center;
    this.radius = radius;
  }
  public Circle(Point p, Point q, double radius) {
    // TODO: Add you code here
		double distancePQ = p.distanceTo(q);
	if(distancePQ > (2 * radius) || distancePQ == 0) {
			this.center = new Point(0,0);
			this.radius = Double.NaN;
	} else {
		// Point m is the midpoint of p and q
		Point m = Point.midPoint(p,q);

		// finding the distance from p to m
		double baseDist = p.distanceTo(m);

		// step 3: finding distance from m to new center, nc, by pythagoras therem

		double distToCenter = Math.sqrt( radius * radius - baseDist * baseDist);

		// step 4: get the angle of p to q, theta which is equivalent to the ange from m to center
		double angle = p.angleTo(q);

		// step 5: moving m to the circle center
		m.move(angle + Math.PI / 2, distToCenter);
		
		this.center = m;
		this.radius = radius;
	}
}

  /**
   * Move the center of the centered to new position
   *
   * @param center the new position of the circle
   */
  public void moveTo(Point center) {
    this.center = center;
  }

  /**
   * Return the area of the circle
   *
   * @return the area of the circle
   */
  public double getArea() {
    return Math.PI * radius * radius;
  }

  /**
   * Checks if point p is contained within this circle (including
   * on the edge of the circle, up to the accuracy of 0.00001).
   *
   * @param p a point to check for containment
   * @return true if this circle contains p; false otherwise.
   */
  public boolean contains(Point p) {
    return p.distanceTo(center) < radius + 0.00001;
  }

  public Point getCenter() {
    return this.center;
  }

  /**
   * Return the perimeter of the circle.
   *
   * @return the perimeter of the circle.
   */
  public double getPerimeter() {
    return Math.PI * 2 * radius;
  }

  /**
   * Return a string representation of the circle, prefixing the
   * coordinates of the center with "c:" and the radius with "r:"
   *
   * @return the string representation of this circle.
   */
  @Override
  public String toString() {
    return "[" + super.toString() + " c: " + center + " r: " + radius + "]";
  }

  /**
   * Check if a given circle has the same center and same radius.
   *
   * @param obj the circle to check
   *
   * @return true if obj is a circle and has the same center and
   *     radius as this circle; false otherwise or if obj is not
   *     a circle.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Circle) {
      Circle c = (Circle)obj;
      return ((c.radius == radius) && (c.center.equals(center)));
    } else {
      return false;
    }
  }

  public static boolean isValid(Circle circle) {
		return !Double.isNaN(circle.radius);
	}	
}
