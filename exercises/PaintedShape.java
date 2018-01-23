import java.awt.Color;

class PaintedShape {
  private Color  fillColor;
  private Color  borderColor;
  private double borderThickness;

  public PaintedShape(Color fillColor, Color borderColor, double borderThickness) {
    this.fillColor = fillColor;
    this.borderColor = borderColor;
    this.borderThickness = borderThickness;
  }

  public void fillWith(Color c) {
    fillColor = c;
  }

  public void setBorderThickness(double t) {
    borderThickness = t;
  }

  public void setBorderColor(Color c) {
    borderColor = c;
  }
}
