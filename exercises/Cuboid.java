class Cuboid implements Shape3D {
  private double length;
  private double height;
  private double breadth;

  public Cuboid(double length, double height, double breadth) {
    this.length = length;
    this.height = height;
    this.breadth = breadth;
  }
  
  public double getVolume() {
    return length * height * breadth;
  }

}

