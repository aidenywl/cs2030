abstract class Event {
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
  
}

