/**
 * The Event class encapsulates information and methods pertaining to a
 * Simulator event.  This is an abstract class that should be subclassed
 * into a specific event in the simulator.  The {@code simulate} method
 * must be written.
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
abstract class Event {
  /** The time this event occurs at. */
  protected double time;

  /**
   * Creates an event and initializes it.
   *
   * @param time The time of occurrence.
   */
  public Event(double time) {
    this.time = time;
  }

  /**
   * The abstract method that simulates this event.
   *
   * @param sim The simulator.
   */
  abstract void simulate(Simulator sim);
}
