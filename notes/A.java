class A {
  int x = 1;

  void f() {
    int y = 1;

    class B {
      void g() {
        x = y; // accessing x and y is OK.
        x = 100;
      }
    }

    new B().g();
  }

  void g() {
    x++;
  }
}
