package cs2030.simulator;

import java.util.function.Function;
/**
 * The Event class encapsulates information and methods pertaining to a
 * Simulator event.  This is an abstract class that should be subclassed
 * into a specific event in the simulator.  The {@code simulate} method
 * must be written.
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */

public class Event implements Comparable<Event> {
  
  /** The time this event occurs at. */
  private final double time;

  /** The event simulate type. All the event essentials work on variable capture. */
  private final Function<SimState, SimState> simulateType;

  /**
   * Creates an event and initializes it.
   *
   * @param time The time of occurrence.
   * @param simulateType The function to be executed by the event when it is simulated.
   */
  public Event(double time, Function<SimState, SimState> simulateType) {
    this.time = time;
    this.simulateType = simulateType;
  }



  /**
   * Defines natural ordering of events by their time.
   * Events ordered in ascending order of their timestamps.
   *
   * @param other Another event to compare against.
   * @return 0 if two events occur at same time, a positive number if
   *     this event has later than other event, a negative number otherwise.
   */
  public int compareTo(Event other) {
    return (int)Math.signum(this.time - other.time);
  }


  /**
   * The method that simulates this event.
   *
   * @param sim The simulator.
   * @return The updated state after simulating this event.
   */
  public SimState simulate(SimState sim) {
    return simulateType.apply(sim);
  }
}
