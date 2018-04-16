interface I {
  void f();
  default void g() {}
}

abstract class A implements I {
  abstract void h();
  abstract void h(int x);
  void j() {}
}

class B extends A {
  @Override
  public void h() {}
  @Override
  public void h(int x) {}
  @Override
  public void f();

}
