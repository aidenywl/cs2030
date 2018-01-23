
/**
 * A SolidCuboid object encapsulates a Cuboid in a 3D plane
 */

class SolidCuboid extends Cuboid implements Solid3D {
  private double density; // Density of the cuboid
  
  /**
   * Create a Cuboid with  density, and the usual constants
   */

  public SolidCuboid(double density, double length, double height, double breadth) {
    super(length, height, breadth);
    this.density = density;
  }

  public SolidCuboid(double length, double height, double breadth) {
    super(length, height, breadth);
    this.density = 1.0;
  }


  public double getDensity() {
    return this.density;
  }

  public double getMass() {
    return this.density * this.getVolume();
  }
}


