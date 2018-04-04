package cs2030.util;

import java.util.Optional;

public class Pair<T,U> {
  public Optional<T> first;
  public U second;

  public Pair(Optional<T> t, U u) {
    this.first = t;
    this.second = u;
  }
}
