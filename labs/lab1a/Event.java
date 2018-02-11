class Event implements EventTypes {
  public int eventType;
  public double eventTime;

  /**
   * Event encapsulates the time the event happens and the event type
   * Precondition: the eventType must be supported
   *               the eventTime must be >= 0
   */

  public Event(double eventTime, int eventType) {
    assert Event.eventSupported(eventType);
    assert eventTime >= 0;
    this.eventTime = eventTime;
    this.eventType = eventType;
  } 

  /**
   * checks if the event type is supported
   */ 
  private static boolean eventSupported(int eventType) {
    return (eventType == EventTypes.CUSTOMER_ARRIVE) ||
      (eventType == EventTypes.CUSTOMER_DONE);
  }
}
