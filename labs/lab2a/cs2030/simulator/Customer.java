package cs2030.simulator;

/**
 * Encapsulates a customer for the shop.
 * Keeps track of the customer ID and the customer done time.
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 LabTwoA
 */

class Customer {
  /** keeps track of the number of customers created so far. */
  private static int Id;
  /** the id of the instantiated customer. */
  protected final int customerId;
  /** the arrival time of the customer. */
  protected final double arrivalTime;
  /** the amount of time required to serve the customer. */
  protected final double serviceTime;
  /** the amount of time the customer spent waiting. */
  protected double waitTime;

  /** 
   * Constructs a customer object.
   * @param arrivalTime the arrival time of the customer
   */
  public Customer(double arrivalTime) {
    this.customerId = Id;
    Id++;
    this.arrivalTime = arrivalTime;
    this.serviceTime = Shop.randomTime.genServiceTime();
  }

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
