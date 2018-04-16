/**
 * An Optional Class is a Monad and a functor.
 * Left unfinished as the rest are quite easy and follow the same concepts.
 */
class Optional<T> {
  T value;

  /**
   * A private constructor for an Optional that takes in a value.
   */
  private Optional(T newValue) {
    this.value = newValue;
  }
  public static <T> Optional<T> of(T v)
    throws NullPointerException {
    if (v == null) {
      throw new NullPointerException("v is null");
    }
    return new Optional(v);
  }

  public static <T> Optional<T> ofNullable(T v) {
    return new Optional(v);
  }

  public static <T> Optional<T> empty() {
    return new Optional(null);
  }

  public void ifPresent(Consumer<? super T> consumer) i
    throws NullPointerException {
    if (consumer == null) {
      throw new NullPointerException("consumer is null");
    }

    if (value != null) {
      consumer.accept(value);
    }
  }

  public Optional<T> filter(Predicate<? super T> predicate) {
    if (predicate.test(value)) {
      return new Optional(this.value);
    } else {
      return Optional.empty();
  }

  // returns value
  public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
    
  }

  // returns optional
  public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
    :
  }

  public T orElseGet(Supplier<? extends T> other) {
    :
  }

}
