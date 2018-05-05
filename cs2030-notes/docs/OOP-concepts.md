# Inheritance and Polymorphism



## Inteface


```javascript
interface Shape {
  public double getArea();
  public double getPerimeter();
  public boolean contains(Point p);
}
```

!!! note "bad practice"

    It is bad practice to use constants in an interface. This causes the implementation detail to leak into the classes' exported API. Furthermore, enum types are much more suitable for this.

Intefaces can contain default implementations. Such interfaces still cannot be instantiated into object,s but classes that implement such interface need not provide an implementation for a ethod where a defualt implementation exists.

```javascript
interface Shape {
  public double getArea();
  public double getPerimeter();
  public boolean contains(Point p);
  default public boolean cover(Point p) {
    return contains(p);
  }
}
```

Interface implementations can be implemented differently in different classes. The bindings of `print()` to the actual set of instructions will only be done at run time, after the object `o` is instantiated from a class. This is known as *dynamic binding*, or *late binding*, or *dynamic dispatch*.

## Inheritance

A class can only *extend* or "inherit* from one parent class. One way to call to the parent object is to use the `super` keyword. Super can be used to access the immediate parent's constructor, method, or instance.

## Overloading

Two methods in a class can have the same name and still co-exist peacefully together. This is called overloading. However, they must have different signatures. Signatures are based on 

* the name of the method, and
* the number, order, and type of the arguments.

Return type is not part of the method signature. The reason is because there is no way for the compiler to determine which method you are calling just based on the return type alone. 

## Java Annotations

These are the annotations starting with `@`. These do not affect execution but are helpful to compilers and other software tools, and to humans who read your code. It is optional.

## Polymorphism

Polymorphism works the same for subclass and interfaces.

In general, the main consideration when deciding which to use to take advantage of polymorphism is that subclasses should always model a IS-A relationship. Furthermore, interfaces are generally used for when you want to ensure certain *functionalities* in your classes.

