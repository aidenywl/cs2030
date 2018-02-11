import java.lang.*;
import java.io.*;
import java.util.*;

/**
 * Solver for Maximum Disc Coverage.
 * Given a set of points,  find the maximum number of points
 * contained in the unit disc.  The input is read from the standard
 * input: the first line indicates the number of points n.
 * The next n lines contains the x and y coodinates of the points,
 * separated by a space, one point per line.
 *
 * @author Ooi Wei Tsang
 * @author Low Yew Woei (students: put your name here)
 * @version CS2030 AY17/18 Sem 2 Lab 0
 */
public class MaxDiscCover {


  /**
   * Find the unit disc that contains the most points,
   * and return the number of points.
   */
  public static int solve(Point[] points) {
    // TODO: Add your code here
    int maxNumberContained = 0;
    int numberContained = 0;
    for(int i = 0; i < points.length; i++) {
      Point firstPoint = points[i];
      for(int j = 0; j < points.length; j++) {

        Point secondPoint = points[j];
        // Constructing the circle
        Circle circle = new Circle(firstPoint, secondPoint, 1);
        if (Circle.isValid(circle)) {
          // checking the number of points contained
          numberContained = numberOfPoints(circle, points);
          if (maxNumberContained < numberContained) {
            maxNumberContained = numberContained;
          }
        }
      }
    }
    return maxNumberContained;
    // Loop through all points
    //  loop through all points
    //    use 2 points to construct a Circle
    //      loop through all points
    //        check how many points are inside the circle
    //          record down max coverage so far


    // Hint: if the code gets too verbose, start think about
    // how you can maintain an abstraction barrier and ecapsulate
    // logics inside each respective class.

    }
  
  /**
   * Returns the number of points contained within a circle
   */
  private static int numberOfPoints(Circle circle, Point[] points) {
    int numberContained = 0;
    for(int i = 0; i < points.length; i++) {
      if (circle.contains(points[i])) {
          numberContained += 1;
      }
    }
    return numberContained;
  }


}
