class CustArrive extends Event {
  
  public CustArrive(double eventTime) {
    super(eventTime);
  }
  
  @Override
  public String toString() {
    return "CUSTOMER_ARRIVE";
  }
}
