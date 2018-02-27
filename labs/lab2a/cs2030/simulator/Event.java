package cs2030.simulator;

/**
 * Encapsulates information and methods pertaining to an arrival event
 * Event objects have a total order. The Comparable method is implemented
 * and Event objects can be sorted based on their eventTime
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 SEm 2 Lab2a
 */
abstract class Event implements Comparable<Event> {
  public final double eventTime;

  public Event(double eventTime) {
    this.eventTime = eventTime;
  }

  /**
   * Checks if the event is of the type stated
   * I can't force this to be static?
   */
  public String getEventType() {
    if (this instanceof CustArrive) {
      return "ARRIVE";
    } else if (this instanceof CustDone) {
      return "DONE";
    } else {
      return "WRONG EVENT";
    }
  }
  public static  boolean isValidEvent(Event event) {
    return event instanceof CustArrive ||
      event instanceof CustDone;
  }

  /**
   * Overrides the compareTo method for the Comparable interface
   * Compares based on the Event's eventTime
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
}

