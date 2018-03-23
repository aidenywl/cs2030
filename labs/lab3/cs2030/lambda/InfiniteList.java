package cs2030.lambda;

import java.lang.IllegalStateException;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A InfiniteList is a list that supports functional operations
 * generate, iterate, map, filter, reduce, findFirst, limit, count,
 * and takeWhile.   An InfiniteLIst is immutable and is _lazily_ evaluated.
 */
public class InfiniteList<T> {
  /** The supplier of the head. */
  public Supplier<T> headSupplier;

  /** The supplier of the tail (rest of the list). */
  public Supplier<InfiniteList<T>> tailSupplier;

  /** A cached value of the head. */
  public Optional<T> headValue;

  /** A cached value of the tail. */
  public Optional<InfiniteList<T>> tailValue;

  // TODO: Add private fields here.

  /**
   * InfiniteList has a private constructor to prevent the list
   * from created directly.
   */
  private InfiniteList() { }

  /**
   * Empty is a special private subclass of InfiniteList that
   * corresponds to an empty InfiniteList.  We intentionally
   * violate LSP here, so that it throws an error if we try
   * to use an empty list like a normal list.
   */
  private static class Empty<T> extends InfiniteList<T> {
    @Override
    public T head() {
      throw new IllegalStateException("calling head() on empty list");
    }

    @Override
    public InfiniteList<T> tail() {
      throw new IllegalStateException("calling tail() on empty list");
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public <R> InfiniteList<R> map(Function<? super T, ? extends R> mapper) {
      return InfiniteList.empty();
    }

    @Override
    public InfiniteList<T> limit(int n) {
      return this;
    }

    @Override
    public InfiniteList<T> takeWhile(Predicate<T> predicate) {
      return this;
    }

    @Override
    public InfiniteList<T> filter(Predicate<T> predicate) {
      return this;
    }

    @Override
    public String toString() {
      return "empty";
    }
  }

  // TODO: Add private methods for constructing the list here.

  /**
   * A private constructor that takes in two suppliers.
   * @param head The supplier for the head of the list.
   * @param tail The supplier for the tail of the list.
   */
  InfiniteList(Supplier<T> head, Supplier<InfiniteList<T>> tail) {
    // TODO
    this.headSupplier = head;
    this.tailSupplier = tail;

    // no values generated yet. Cache is 0.
    this.headValue = Optional.empty();
    this.tailValue = Optional.empty();
  }

  /**
   * A private constructor that takes in a tail supplier and a head
   * value.
   * @param head The value of the head of the List.
   * @param tail The supplier of the tail of the list.
   */
  InfiniteList(T head, Supplier<InfiniteList<T>> tail) {
    // TODO
    this.headSupplier = () -> head;
    this.tailSupplier = tail;

    this.headValue = Optional.of(head);
    this.tailValue = Optional.empty();
  }

  /**
   * Return the head of the list.  If the head is not evaluated yet,
   * the supplier is called and the value is cached.
   * @return The head of the list.
   */
  public T head() {
    return this.headValue.orElseGet(() -> {
      T head = this.headSupplier.get();
      this.headValue = Optional.of(head);
      return head;
    });
  }

  /**
   * Return the tail of the list, which is another InfiniteList.
   * If the tail is not evaluated yet, the supplier is called and
   * the value is cached.
   * @return The tail of the list.
   */
  public InfiniteList<T> tail() {
    InfiniteList<T> list = this.tailValue.orElseGet(this.tailSupplier);
    this.tailValue = Optional.of(list);
    return list;
  }

  /**
   * Create an empty InfiniteList.
   * @param <T> The type of the elements in the list.
   * @return An empty InfiniteList.
   */
  public static <T> InfiniteList<T> empty() {
    return new Empty<T>();
  }

  /**
   * Checks if the list is empty.
   * @return true if the list is empty; false otherwise.
   */
  public boolean isEmpty() {
    // TODO
    return this instanceof Empty;
  }

  /**
   * Generate an infinite list of elements, each is generated with
   * the given supplier.
   * @param <T> The type of elements to generate.
   * @param supply A supplier function to generate the elements.
   * @return The new list generated.
   */
  public static <T> InfiniteList<T> generate(Supplier<T> supply) {
    // TODO
    return new InfiniteList<T>(supply, () -> InfiniteList.generate(supply));
  }

  /**
   * Generate an infinite list of elements, starting with {@code init}
   * and with the next element computed with the {@code next} function.
   * @param <T> The type of elements to generate.
   * @param init The value of the head.
   * @param next A function to generate the next element.
   * @return The new list generated.
   */
  public static <T> InfiniteList<T> iterate(T init, Function<T, T> next) {
    // TODO
    return new InfiniteList<T>(() -> init, () -> InfiniteList.iterate(next.apply(init), next));
  }

  /**
   * Return the first element that matches the given predicate, or
   * Optional.empty() if none of the elements matches.
   * @param  predicate A predicate to apply to each element to determine
   *     if it should be returned.
   * @return An Optional object containing either the first element
   *     that matches, or is empty if none of the element matches.
   */
  public Optional<T> findFirst(Predicate<T> predicate) {
    // TODO
    InfiniteList<T> list = this;
    while (true) {
      if (this.isEmpty()) {
        return Optional.empty();
      } else {
        T next = list.head();
        if (predicate.test(next)) {
          return Optional.of(next);
        }
        list = list.tail();
      }
    }
  }

  /**
   * Returns a list consisting of the results of applying the given function
   * to the elements of this list.
   * @param <R> The type of elements returned.
   * @param mapper The function to apply to each element.
   * @return The new list.
   */
  public <R> InfiniteList<R> map(Function<? super T, ? extends R> mapper) {
    // TODO
    if (this.isEmpty()) {
      return InfiniteList.empty();
    }

    return new InfiniteList<R>(() -> mapper.apply(this.head()),
                                    () -> this.tail().map(mapper));
  }

  /**
   * Reduce the elements of this stream to a single value, by successively
   * "accumuating" the elements using the given accumulation function.
   *
   * @param <U> The type of the value the list is reduced into.
   * @param identity The identity (initial accumulated values)
   * @param accumulator A function that accumulates elements in the stream.
   * @return The accumulated value.
   */
  public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator) {
    // TODO
    U accumulatedValue = identity;
    InfiniteList<T> infList = this;

    while (!infList.isEmpty()) {
      accumulatedValue = accumulator.apply(accumulatedValue, infList.head());
      infList = infList.tail();
    }


    return accumulatedValue;
  }

  /**
   * Truncate the list to up to n elements.  If the list has less than n
   * elements, then the original list is returned.
   * @param n The number of items to limit the list to.
   * @return The truncated list.
   */
  
  // why is n == 1 required?
  public InfiniteList<T> limit(int n) {
    // TODO
    if (n == 0) {
      return InfiniteList.empty();
    } else if (n == 1) {
      return new InfiniteList<T>(this.headSupplier, () -> InfiniteList.empty());
    } else {
      return new InfiniteList<T>(this.headSupplier, () -> this.tail().limit(n - 1));
    }
  }

  /**
   * Return a new list consisting of elements from this list
   * by successively copying the elements, until the predicate
   * becomes false.  All elements in the returned list passed
   * the predicate.
   * @param predicate A predicate where elements in the returned
   *     list must satisfied.
   * @return The new list.
   */
  public InfiniteList<T> takeWhile(Predicate<T> predicate) {
    // TODO
    InfiniteList<T> infList = this;
    if (infList.isEmpty()) {
      return InfiniteList.empty();
    }
    T head = infList.head();

    if (!predicate.test(head)) {
      return InfiniteList.empty();
    } 
    return new InfiniteList<T>(infList.headSupplier, () -> infList.tail().takeWhile(predicate));

  }

  /**
   * Returns a list consisting of the elements of this list that
   * match the given predicate.
   * @param  predicate A predicate to apply to each element to
   *     determine if it should be included
   * @return The new list.
   */
  public InfiniteList<T> filter(Predicate<T> predicate) {
    // TODO
    while(!this.isEmpty()) {
      T head = this.head();
      if (predicate.test(head)) {
        return new InfiniteList<T>(this.headSupplier, () -> this.tail().filter(predicate));
      } else if (this.tail().isEmpty()) {
        return InfiniteList.empty();
      } else {
        return new InfiniteList<T>(() -> this.tail().filter(predicate).head(), () -> this.tail().filter(predicate).tail());
      }
    }

    return InfiniteList.empty();
  }

  /**
   * Return the number of elements in this list.
   * @return The number of elements in the list.
   */
  public int count() {
    int n = 0;

    InfiniteList<T> infList = this;
    while (!infList.isEmpty()) {
      n = n + 1;
      infList = infList.tail();

    }
    return n;
  }

  /**
   * Return an array containing the elements in the list.
   * @return The array containing the elements in the list.
   */
  public Object[] toArray() {
    List<Object> list = new ArrayList<>();
    // TODO
    InfiniteList<T> infList = this;

    while (!infList.isEmpty()) {
      list.add(infList.head());
      infList = infList.tail();
    }
    return list.toArray();
  }

  /**
   * Return this infinite list in string format.
   */
  public String toString() {
    // TODO: You may need to modifiy this
    if (this.isEmpty()) {
      return "-";
    }
    String tail = this.tailValue
        .map(x -> x.toString())
        .orElse("?");
    String head = this.headValue
        .map(x -> x.toString())
        .orElse("?");

    return head + "," + tail;
  }
}
