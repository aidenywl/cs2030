package cs2030.util;

import java.util.Optional;

public class PriorityQueue<T> {
  public final java.util.PriorityQueue<T> pq;

  public PriorityQueue() {
    pq = new java.util.PriorityQueue<T>();
  }

  /**
   * Private constructor to make a copy of all references from an input priorityQueue
   * 
   * @param  source the input priorityQueue
   */
  private PriorityQueue(PriorityQueue<T> source) {
    // comment: why do we want to name our own class PriorityQueue 
    // and conflict with java's naming convention?
    this.pq = new java.util.PriorityQueue<T>();
    this.pq.addAll(source.pq);
  }


  /**
   * Adds an object to the priorityQueue without mutating the original.
   * A new priorityQueue is created.
   * 
   * @param  object The object of type T to be added to the priorityQueue.
   * @return        The new priorityQueue.
   */
  public PriorityQueue<T> add(T object) {
    PriorityQueue<T> newPQ = new PriorityQueue<T>(this);
    newPQ.addObject(object);
    return newPQ;
  }

  /**
   * Private helper function used to add objects into a new priorityQueue during
   * the queue creation.
   * 
   * @param object The object to be added.
   */
  private void addObject(T object) {
    this.pq.add(object);
  }

  /**s
   * Retrieves the first object from the priorityQueue according to their natural ordering.
   * Due to the pure functional implementation of PriorityQueue, the object is returned in a pair.
   * The head contains the retrieved object. The tail contains the newly created priorityQueue.
   * 
   * @return A pair containing the first element and the new PriorityQueue.
   */
  public Pair<T, PriorityQueue<T>> poll() {
    PriorityQueue<T> newPQ = new PriorityQueue<T>(this);
    T object = newPQ.pq.poll();
    Optional<T> opObject = Optional.ofNullable(object);
    return new Pair<>(opObject, newPQ);
  }
}
