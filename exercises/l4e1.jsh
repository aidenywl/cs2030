/open *.java

Shape[] shapes;
Circle[] circles = new Circle[1];

shapes = circles;
shapes[0] = new Square(3.0);
