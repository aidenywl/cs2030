class A {
  private int x;
  public void changeSelf() {
    x = 1;
  }
  public void changeAnother(A a) {
    a.x = 1;
  }

}

class B {
  public void changeAnother(A a) {
    a.x = 1;
  }
}

