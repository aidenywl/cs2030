/**
 * Stores stats about the simulation.  In particular, the average
 * waiting time, the number of customer who left, and the number
 * of customers who are served.
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
class Statistics {
  /** Sum of time spent waiting for all customers. */
  private double totalWaitingTime;

  /** Total number of customers who were served. */
  private int totalNumOfServedCustomer;

  /** Total number of customers who left without being served. */
  private int totalNumOfLostCustomer;

  /**
   * Mark that a customer is served.
   */
  public void serveOneCustomer() {
    totalNumOfServedCustomer++;
  }

  /**
   * Mark that a customer is lost.
   */
  public void lostOneCustomer() {
    totalNumOfLostCustomer++;
  }

  /**
   * Accumulate the waiting time of a customer.
   * @param time The time a customer waited.
   */
  public void customerWaitedFor(double time) {
    totalWaitingTime += time;
  }

  /**
   * Return a string representation of the staistics collected.
   * @return A string containing three numbers: the average
   *     waiting time, followed by the number of served customer,
   *     followed by the number of lost customer.
   */
  public String toString() {
    return String.format("%.3f %d %d",
        totalWaitingTime / totalNumOfServedCustomer,
        totalNumOfServedCustomer, totalNumOfLostCustomer);
  }
}
