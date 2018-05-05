class A {
  private int x;
  public A(int i) {
    this.x = i;
  }
  public A f(Function<Integer,Integer> func) {
    if (this.x > 0) {
      return new A(func.apply(x));
    } else {
      return new A(0);
    }
  }
  public boolean isSameAs(A a) {
    return this.x == a.x;
  }
}
