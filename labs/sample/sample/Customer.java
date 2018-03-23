/**
 * The Customer class encapsulates information and methods pertaining to a
 * Customer in a simulation.  A customer in the simulator goes through either
 * one of the following two paths: (i) arrives and leaves, or (ii) arrives,
 * waits, starts service, ends service, and leaves.
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
class Customer {
  /** The unique ID of the last created customer.  */
  private static int lastCustomerId = 0;

  /** The unique ID of this customer. */
  private final int id;

  /** The time this customer arrives. */
  private double timeArrived;

  /**
   * Create and initalize a new customer.  The {@code id} of the customer
   * is set.
   */
  public Customer() {
    this.id = Customer.lastCustomerId;
    Customer.lastCustomerId++;
  }

  /**
   * Mark the arrival of the customer at the given time.
   *
   * @param time The time at which this customer arrives.
   */
  public void arrive(double time) {
    this.timeArrived = time;
    System.out.printf("%6.3f %s arrives\n", time, this);
  }

  /**
   * Mark that this customer is waiting for a given server at the given time.
   *
   * @param time The time at which this customer's service begins.
   * @param server The server this customer is waiting for.
   */
  public void waitBegin(double time, Server server) {
    System.out.printf("%6.3f %s waits for %s\n", time, this, server);
  }

  /**
   * Mark the start of this customer's service at the given time.
   *
   * @param time The time at which this customer's service begins.
   * @param server The server that serves this customer.
   */
  public void serveBegin(double time, Server server) {
    Simulator.stats.serveOneCustomer();
    Simulator.stats.customerWaitedFor(time - this.timeArrived);
    System.out.printf("%6.3f %s served by %s\n", time, this, server);
  }

  /**
   * Mark the end of this customer's service at the given time.
   *
   * @param time The time at which this customer's service begins.
   * @param server The server that serves this customer.
   */
  public void serveEnd(double time, Server server) {
    System.out.printf("%6.3f %s done served by %s\n", time, this, server);
  }

  /**
   * Mark that this customer leaves without being served.
   *
   * @param time The time at which this customer leaves.
   */
  public void leave(double time) {
    Simulator.stats.lostOneCustomer();
    System.out.printf("%6.3f %s leaves\n", time, this);
  }

  /**
   * Return a string representation of this customer.
   *
   * @return A string representation of this customer.
   */
  public String toString() {
    return "C" + this.id;
  }
}
