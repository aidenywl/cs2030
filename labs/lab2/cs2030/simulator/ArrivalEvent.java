class ArrivalEvent extends Event {
  
  /**
   * Constructs a Customer Arrive Event.
   * @param eventTime the arrival time of the customer
   */
  public ArrivalEvent(double eventTime) {
    super(eventTime);
  }

  /**
   * Asks the simulator to simulate an arrival event.
   * @param sim The Simulator
   */
  public void simulate(Simulator sim) {
    sim.simulateArrival(this.eventTime);
  }
  
  @Override
  public String toString() {
    return "CUSTOMER_ARRIVE";
  }
}
