# FP Patterns

Lambda expressions are ways to declare functional methods without going through the syntax to create a new class in Java. They are a way to express computation. 

!!! note "lambda"

    Alonzo Church invented lambda calculus (
    λ
    -calculus) in 1936, before electronic computers, as a way to express computation. In 
    λ
    -calculus, all functions are anonymous. The term lambda expression originated from there.

## Function Interface

The `Function` interface is a generic inteface with two type parameters. It only has one abstract method R apply(T t) that applies the function to a given argument.

```javascript
class Square implements Function<Integer, Integer> {
  public Integer apply(Integer x) {
    return x * x;
  }
}

int x = new Square().apply(4);
```

is equivalent to:

```javascript
Function<Integer, Integer> f = new Function<Integer, Integer>() {
  public Integer apply(Integer x) {
    return x * x;
  }
}

int x = f.apply(4);
```
is equivalent to:

```javascript
Function<Integer, Integer> square = x -> x * x;

int x = square.apply(4);
```

## Single Abstract Methods

We can use a lambda expression as a short hand to an anonymous class that implements any interface with a *single abstract method*. The reason there can only be one abstract method is so that the compiler can infer which method body the lambda expression implements. Such interfaces are known as a *SAM interface*.

A SAM interface can contain multiple methods, but only one needs to be abstract. Others can contain `default` implementations.

## Functional Patterns.

Knowing what is a monad or functor is useful because you can immediately recognise that it supports certain interfaces.

## Functor

A functor can be thought of as something that takes in a function and returns another functor.

We can think of it like the interface below.

```javascript
interface Functor<T> {
  public <R> Functor<R> f(Function<T, R> func);
}
```

### Functor Laws

Matching the patterns syntactically, however, is not enough to be a functor. A functor has to semantically obey the functor laws, which are:

* if `func` is an identity function x -> x, then it should not change the functor.
* if `func` is a composition of two functions $g•h$, then the resulting functor should be the same as calling `f` with $h$ and then with $g$.

A functor should have a method that has a method that takes in a function and returns itself.

*Another way to think of a functor, in the OO-way, is that it is a variable wrapped within a class in some context. Instead of manipulating the variable directly, we pass in a function to the class to manipulate the variable.* The variable must then interact with the function as if it is not wrapped, and the class should not interfere with the function. We use lambda expressions for *cross-abstraction barrier manipulation* with functors.

!!! tip "Functors we've learnt"

    * Stream
    * InfiniteList
    * Optional
    * LambdaList.

## Monad

A monad also takes in a function and returns a monad. But unlike functor, it takes in a **function that returns a monad.**

```javascript
interface Monad<T> {
  public <R> Monad<R> f(Function<T, Monad<R>> func);
}

// we've seen this before

interface Stream<T> {
  public <R> Stream<R> flatMap(Function<T, Stream<R>> Mapper);
}
```

### Monad Laws

* There should be an `of` operation that takes in an object (or multiple objects) and wrap it/them into a monad. Furthermore,
  * `Monad.of(x).flatMap(f)` should be equal to `f(x)` (called the *left identity law*)
  * `monad.flatmap(x -> Monad.of(x))` should be equal to `monad` (called the *right identity law*)

* the `flatMap` operation should be associative (associative law): `monad.flatMap(f).flatMap(g)` should be equal to `monad.flatMap(x -> f(x)).flatMap(g))`.

!!! note "in other languages"
    
    The `flatMap` and `of` operations are sometimes known as the `bind` and `unit` operaitons respectively (e.g. in Haskell).


!!! tip "Monads we've learnt"

    * Optional
    * CompletableFuture
    * Stream