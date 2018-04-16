boolean isPrime (int x) {
  int n = (int) Math.ceil(Math.sqrt(x)) ;
  for (int i = 2; i <= n; i++) {
    int d = x % i;
    if (d == 0) {
      return false;
    }
  }
  return true;
}

