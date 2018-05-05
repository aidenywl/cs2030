import java.util.function.*;

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
    return new Optional<T>(v);
  }

  public static <T> Optional<T> ofNullable(T v) {
    if (v == null) {
      return Optional.empty();
    }
    return new Optional<T>(v);
  }

  public static <T> Optional<T> empty() {
    return new Optional<T>(null);
  }

  public void ifPresent(Consumer<? super T> consumer) 
    throws NullPointerException {
    if (consumer == null) {
      throw new NullPointerException("consumer is null");
    }

    if (value != null) {
      consumer.accept(value);
    }
  }

  public Optional<T> filter(Predicate<? super T> predicate) {
    if (this.value != null && predicate.test(value)) {
      return Optional.ofNullable(this.value);
    } else {
      return Optional.empty();
    }
  }

  // returns value
  public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
    if (this.value != null) {
      return Optional.ofNullable(mapper.apply(this.value));
    }
    return Optional.empty();
  }

  // returns optional
  public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
    if (this.value != null) {
      return mapper.apply(this.value);
    }
    return Optional.empty();
  }

  public T orElseGet(Supplier<? extends T> other) {
    if (this.value != null) {
      return this.value;
    }
    return other.get();
  }

}
