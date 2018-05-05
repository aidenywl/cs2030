public LongStream factors(long x) {
  LongStream a = LongStream.rangeClosed(1, x);

  LongPredicate factorPred = n -> x % n == 0 ;

  return a.filter(factorPred);
}

boolean isPrime(long x) {
  return LongStream.range(2, x - 1)
      .noneMatch(i -> x % i == 0);
}

public LongStream primeFactors(long x) {

  /**
  LongPredicate isPrime = n -> {
    int ceil = Math.sqrt(n);

    if (n == 1) {
      return false;
    }

    for (int i = 1; i < ceil; i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }
  */
  return factors(x).filter(i -> i > 1 && isPrime(i));
}

public LongStream omega(int n) {
  return LongStream.iterate(1, x -> x + 1)
    .map(x -> primeFactors(x).count())
    .limit(n);
}

/**
public Stream<T, U, R> Stream<R> product(List<T> list1, List<U> list2, 
    BiFunction<? super T, ? super U, ? extends R> ) {
    Stream<T> stream1 = list1.stream();
    return stream1.flatMap( x -> list2.stream().map( y -> f.apply(x,y)));

}
*/
public Stream<BigInteger> fib(int n) {
  class Pair {
    public BigInteger x;
    public BigInteger y;
    Pair(BigInteger x, BigInteger y) {
      this.x = x;
      this.y = y;
    }
  }

  return Stream.iterate(new Pair(BigInteger.ONE, BigInteger.ONE), p -> new Pair(p.y, p.x.add(p.y))).map(p -> p.x).limit(n);
}