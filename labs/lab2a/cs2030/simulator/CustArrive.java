package cs2030.simulator;

class CustArrive extends Event {
  
  /**
   * Constructs a Customer Arrive Event.
   * @param eventTime the arrival time of the customer
   */
  public CustArrive(double eventTime) {
    super(eventTime);
  }
  
  @Override
  public String toString() {
    return "CUSTOMER_ARRIVE";
  }
}
