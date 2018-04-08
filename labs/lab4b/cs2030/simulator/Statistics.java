package cs2030.simulator;

/**
 * This is an immutable class that stores stats about the simulation.
 * In particular, the average * waiting time, the number of customer
 * who left, and the number of customers who are served, are stored.
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 Lab 4a
 */
class Statistics {
  /** Sum of time spent waiting for all customers. */
  private final double totalWaitingTime;

  /** Total number of customers who were served. */
  private final int totalNumOfServedCustomers;

  /** Total number of customers who left without being served. */
  private final int totalNumOfLostCustomers;

  public Statistics() {
    this.totalWaitingTime = 0;
    this.totalNumOfServedCustomers = 0;
    this.totalNumOfLostCustomers = 0;
  }

  /**
   * Dumb constructor to keep things immutable.
   * @param  waitTime total waiting time.
   * @param  served   total number of served customers.
   * @param  lost     total number of lost customers.
   */
  private Statistics(double waitTime, int served, int lost) {
    this.totalWaitingTime = waitTime;
    this.totalNumOfServedCustomers = served;
    this.totalNumOfLostCustomers = lost;
  }

  /**
   * Mark that a customer is served.
   * @return A new Statistics object with updated stats
   */
  public Statistics serveOneCustomer() {
    return new Statistics(this.totalWaitingTime, this.totalNumOfServedCustomers + 1,
                          this.totalNumOfLostCustomers);
  }

  /**
   * Mark that a customer is lost.
   * @return A new Statistics object with updated stats
   */
  public Statistics lostOneCustomer() {
    return new Statistics(this.totalWaitingTime, this.totalNumOfServedCustomers, 
          this.totalNumOfLostCustomers + 1);
  }

  /**
   * Accumulate the waiting time of a customer.
   * @param time The time a customer waited.
   * @return A new Statistics object with updated stats
   */
  public Statistics customerWaitedFor(double time) {
    return new Statistics(this.totalWaitingTime + time, this.totalNumOfServedCustomers, 
          this.totalNumOfLostCustomers);
  }

  /**
   * Return a string representation of the staistics collected.
   * @return A string containing three numbers: the average
   *     waiting time, followed by the number of served customer,
   *     followed by the number of lost customer.
   */
  public String toString() {
    return String.format("%.3f %d %d",
        totalWaitingTime / totalNumOfServedCustomers,
        totalNumOfServedCustomers, totalNumOfLostCustomers);
  }
}
