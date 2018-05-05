# 1. Abstraction and Encapsulation

## Types

**Dynamically Typed** languages: Python, Javascript, etc. The same variable canhold values of different types, and checking if the right type is used is done during the execution of the program. *Type is associated with the values that the variable holds.*

**Statically Typed** languages: C, Java, etc. Every variable has to be declared with its type specified. The type is checked for correctness during compilation of the program.

## Rationale behind Maintaining the Abstraction Barrier

We can imagine some *abstraction barrier* between the code that 

uses a composite data type along with its associated set of functions (Visible to the client).

----------- Abstraction Barrier ----------

the code that defines the data type along with the implementation of the functions (Visible only to the implementer).

**WHY do we care so much about the abstraction barrier?**

1. By maintaining the abstraction barrier, it makes our program safer because a random person cannot simply modify the internal program state however they wish. The barrier ensures that the program will only be modified in the ways allowed so other areas of the program will not break if something is modified. This prevents random people from accidentally planting bugs or errors that may surface later on.

2. The resulting code will be more maintainable and extensible. We can change the implementation however we wish as long as the program's functionality is maintained.

## Maintaining the abstraction barrier with Access Modifiers

Access Modifier | Class | Package | Subclass | World
---------------- | ----- | ------- | -------- | ------- 
public  | Y | Y | Y | Y
protected | Y | Y | Y | N
no modifier (default), also known as package-private | Y | Y | N | N
private | Y | N | N | N

## Constructors, Accessors, Mutators.

A method that initializes an object is called a constructor, and a method that retrieves or modifies the properties of the object is called the accessor (or getter) or mutator (or setter).

*In Java, a constructor method has the same name as the class and has no return type.*

!!! note

    The use of accessors and mutator methods are quite controversial. Suppose that we provide an accessor method and a mutator method for every private field, then we are actually exposing the internal representation, therefore breaking the encapsulation. As much as possible try to add the functionality that is needed as methods (functions).

## Class fields and methods.

Static fields and methods are called class fields and methods.

non-static fields belong to objects and are instance fields and methods.

## Primitive or Reference Type?

All objects are stored as references in Java.

Primitive type variables stores the value instead of a reference to the value. Java supports eight primitive types: `byte`, `short`, `int`, `long`, `float`, `double`, `boolean`, and `char`.
