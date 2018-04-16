class Wrapper {
  public int x;
  public int y;

  static void swap (Wrapper w) {
    int temp = w.x;
    w.x = w.y;
    w.y = temp;
  }
}
