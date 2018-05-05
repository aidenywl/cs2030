public static <T> InfiniteList<T> generate(Supplier<T> supply)_ {
  return new InifniteList<T>(siupply, () -> InfiniteList.generate(supply));
}

// infinitelist.iterate

// generates a list with the intiial value and uses the next to generate the rest of the list
public static <T> InfiniteList<T> iterate(T init, Function<T,T> next) {
  return new InfiniteList<T> //.........
