public LongStream factors(long x) {
  LongStream a = LongStream.rangeClosed(1, x);

  LongPredicate factorPred = n -> x % n == 0 ;

  return a.filter(factorPred);
}
