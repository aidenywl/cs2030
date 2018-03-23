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
  private final int customerId;
  /** the arrival time of the customer. */
  private final double arrivalTime;
  /** the amount of time required to serve the customer. */
  private final double serviceTime;
  /** the amount of time the customer spent waiting. */
  private double waitTime;
  /** the time the customer will be done */
  private double doneTime;

  /** 
   * Constructs a customer object.
   * @param arrivalTime the arrival time of the customer
   */
  public Customer(double arrivalTime) {
    this.customerId = Id;
    Id++;
    this.arrivalTime = arrivalTime;
    this.serviceTime = Simulator.randomGen.genServiceTime();
    this.arrive();
  }

  /**
   * Mark the arrival of the customer at the given time.
   *
   * @param time The time at which this customer arrives.
   */
  private void arrive() {
    System.out.printf("%6.3f %s arrives\n", this.arrivalTime, this);
  }

  /**
   * Mark that this customer is waiting for a given server at the given time.
   *
   * @param time The time at which this customer's service begins.
   * @param server The server this customer is waiting for.
   */
  public void waitBegin(Server server) {
    System.out.printf("%6.3f %s waits for %s\n", this.arrivalTime, this, server);
  }

  /**
   * Mark the start of this customer's service at the given time.
   *
   * @param time The time at which this customer's service begins.
   * @param server The server that serves this customer.
   */
  public void serveBegin(double time, Server server) {
    Simulator.stats.servedOneCustomer();
    this.waitTime = time - this.arrivalTime;
    Simulator.stats.customerWaitedFor(this.waitTime);
    // update the donetime
    this.doneTime = time + this.serviceTime;
    System.out.printf("%6.3f %s served by %s\n", time, this, server);
  }

  public double getDoneTime() {
    return this.doneTime;
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
 
  @Override
  public String toString() {
    return "C" +  customerId;
  }
}
