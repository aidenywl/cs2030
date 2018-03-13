/**
 * Encapsulates information and methods pertaining to an arrival event.
 * Event objects have a total order. The Comparable method is implemented
 * and Event objects can be sorted based on their eventTime
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem2 Lab2b
 */
abstract class Event implements Comparable<Event> {
  public final double eventTime;

  public Event(double eventTime) {
    this.eventTime = eventTime;
  }
  
  /**
   * The abstract method that simulates this event.
   *
   * @param sim The simulator.
   */
  abstract void simulate(Simulator sim);

  /**
   * Overrides the compareTo method for the Comparable interface.
   * Compares based on the Event's eventTime.
   */
  @Override
  public int compareTo(Event other) {
    return Double.compare(this.eventTime, other.eventTime);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other instanceof Event) {
      Event otherEvent = (Event) other;
      return otherEvent.eventTime == otherEvent.eventTime;
    } else {
      return false;
    }
  }

  /**
   * Overriding the hashcode to ensure that equal events are put into the 
   * same bin.
   */
  @Override
  public int hashCode() {
    return ((Double)eventTime).hashCode();
  }
}

