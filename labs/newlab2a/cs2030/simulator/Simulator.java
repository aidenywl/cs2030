/**
 * Simulator class encapsulates information and methods pertaining to a
 * event and shop simulator.
 * It contains a shop and the events for the shop to talk to.
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 Lab2b
 */

import java.util.PriorityQueue;


class Simulator {
  /** Maximum number of events that the simulator can store at a given time. */
  private static final int MAX_NUMBER_OF_EVENTS = 100;

  /** The priority queue of events. Events are ordered according to their
    natural ordering */
  private PriorityQueue<Event> events;

  /** The shop of servers being simulated. */
  private Shop shop;

  /**
   * Creates a simulatior and initialises it.
   * @param  numOfServers The number of servers to be created in the shop.
   */
  public Simulator(int numOfServers) {
    // initialising the Shop with the number of servers needed
    this.shop = new Shop(numOfServers);
  }

  public void run() {
    do {
      Event currentEvent = events.poll();
      currentEvent.simulate(this);

    } while (hasEventLeft());
  }

  /**
   * Inner class that stores statistics about the simulation.
   * In particular, the average waiting time, the number of customers who left,
   * and the nbumber of customers who are served.
   */

  class Statistics {
    /** Sum of time spent waiting for all customers*/
    private double totalWaitTime = 0;

    /** Total number of customers who were served */
    private int totalNumServedCust = 0;

    /** Total number of customers who were rejected */
    private int totalNumLostCust = 0;

    /**
     * Mark a customer as served.
     */
    public void servedOneCustomer() {
      totalNumServedCust++;
    }

    /**
     * Mark a customer as rejected
     */
    public void lostOneCustomer() {
      totalNumLostCust++;
    }

    /**
     * Accumulat the waiting time of a customer.
     * @param time The time a customer waited.
     */
    public void customerWaitedFor(double time) {
      totalWaitingTime += time;
    }

    private double avgWaitTime() {
      return this.totalWaitingTime / this.totalNumServedCust;
    }

    /**
     * Return a string representation of all the statistics collected.
     * @return A string containing three numbers: the avg waititng time,
     * followed by the number of served customers, followed by the number of
     * lost customers.
     */
    @Override
    public String toString() {
      return String.format("%.3f %d %d\n", this.avgWaitTime(),
          this.totalNumServedCust, this.totalNumLostCust);
    }
  }
}
