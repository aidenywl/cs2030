package cs2030.simulator;

/**
 * CustDone encapsulates a customer done event. Abstract Event is the parent.
 * 
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 LabTwoA
 */

class CustDone extends Event {
  public Server server; // the server that handles the event
  
  /**
   * Constructs a customer done object.
   * @param eventTime the time the customer will be finished by the server
   * @param server the server that serves the customer
   */
  public CustDone(double eventTime, Server server) {
    super(eventTime);
    this.server = server;
  }

  @Override
  public String toString() {
    return "CUSTOMER_DONE";
  }
}
