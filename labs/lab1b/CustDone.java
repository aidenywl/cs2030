class CustDone extends Event {
  public Server server; // the server that handles the event

  public CustDone(double eventTime, Server server) {
    super(eventTime);
    this.server = server;
  }

  @Override
  public String toString() {
    return "CUSTOMER_DONE";
  }
}
