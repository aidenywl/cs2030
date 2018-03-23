/**
 * The Server class keeps track of who is the customer being served (if any)
 * and who is the customer waiting to be served (if any).
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
class Server {
  /** The unique ID of the last created server. */
  private static int lastServerId = 0;

  /** The unique ID of this server. */
  private final int id;

  /** The ID of the customer currently being served, if any. */
  private Customer currentCustomer;

  /** The ID of the customer currently waiting, if any. */
  private Customer waitingCustomer;

  /**
   * Creates a server and initalizes it with a unique id.
   */
  public Server() {
    this.currentCustomer = null;
    this.waitingCustomer = null;
    this.id = Server.lastServerId;
    Server.lastServerId++;
  }

  /**
   * Change this server's state to idle by removing its current customer.
   */
  public void makeIdle() {
    this.currentCustomer = null;
  }

  /**
   * Checks if there is a customer being served by this server.
   *
   * @return true if a customer is being served by this server; false otherwise.
   */
  public boolean customerBeingServed() {
    return this.currentCustomer != null;
  }

  /**
   * Checks if there is a customer waiting for this server.
   *
   * @return true if a customer is waiting for this server; false otherwise.
   */
  public boolean customerWaiting() {
    return this.waitingCustomer != null;
  }

  /**
   * Removes the customer waiting for this server and return it.
   *
   * @return The customer waiting for this server.
   */
  public Customer removeWaitingCustomer() {
    Customer customer = waitingCustomer;
    this.waitingCustomer = null;
    return customer;
  }

  /**
   * Serve a customer.
   *
   * @param customer The customer to be served.
   */
  public void serve(Customer customer) {
    this.currentCustomer = customer;
  }

  /**
   * Make a customer wait for this server.
   *
   * @param customer The customer who will wait for this server.
   */
  public void askToWait(Customer customer) {
    this.waitingCustomer = customer;
  }

  /**
   * Return a string representation of this server.
   *
   * @return A string representation of this server.
   */
  public String toString() {
    return "S" + this.id + " (Q: " + waitingCustomer + ")";
  }
}
