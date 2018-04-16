class Search {
  
  public static boolean search (Object[] a, Object target) {
    for (int i = 0; i < a.length; i++) {
      if (a[i].equals(target)) {
        return true;
      }
    }
    return false;
  }

}
