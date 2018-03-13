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

  /** The object to keep track of statistics for the simulator. */
  protected static Statistics stats = new Statistics();

  /** The random generator to be used for servers and customers */
  public static RandomGenerator randomGen;

  /** The number of events to be simulated */
  private int numArrivingCustomers;

  /**
   * Creates a simulatior and initialises it.
   * @param  numOfServers The number of servers to be created in the shop.
   */
  public Simulator(int numEvents) {
    // initialising the Shop with the number of servers needed
    this.numArrivingCustomers = numEvents;
    this.events = new PriorityQueue<>(100);

    // creating the first event
    Event firstEvent = createArrivalEvent(0);
    scheduleEvent(firstEvent);
  }

  /**
   * Sets the parameters for the shop and initialises it.
   * @param numServers Number of servers for the shop
   */
  public void setShop(int numServers) {
    this.shop = new Shop(numServers);
  }
  /**
   * Sets the parameters for the random generator.
   * @param seed   The seed to be used for the random generator.
   * @param lambda The customer arrival rate.
   * @param mu     The service rate.
   */
  public void setRandom(int seed, double lambda, double mu) {
    Simulator.randomGen = new RandomGenerator(seed, lambda, mu);
  }

  public void run() {
    do {
      Event currentEvent = events.poll();
      currentEvent.simulate(this);

    } while (hasEventsLeft());
  }

  /**
   * Schedules an event into the priority queue if there are spaces left
   * @param event The event to be added into the priorityQueue
   */
  private void scheduleEvent(Event event) {
    if (events.size() < 100) {
      events.offer(event);
    } else {
      System.out.println("too many events. Event not added.");
    }
  }

  /**
   * Creates a new arrival event.
   * @param  time The time the customer arrives.
   * @return      The arrival event.
   */
  private Event createArrivalEvent(double time) {
    this.numArrivingCustomers--;
    Event event = new ArrivalEvent(time);
    return event;
  }

  /**
   * Creates a new customer done event.
   * @param  time   The time the server finishes serving the customer.
   * @param  server The server that is working on the customer.
   * @return        A customer done event.
   */
  public static Event createDoneEvent(double time, Server server) {
    return new DoneEvent(time, server);
  }

  public void simulateArrival(double time) {
    // create a customer
    Customer customer = new Customer(time);
    // find an idle or free waiter.
    Server currentServer = shop.findIdleOrAvailableServer();
    // if no waiter available, make the customer leave.
    if (currentServer == null) {
      customer.leave(time);
    } else {
      Event customerDone = currentServer.handleCustomer(customer, time);

      if (customerDone != null) {
        scheduleEvent(customerDone);
      }
    }
    // if the person is served, return a customer done event.
   

    // add events if there are any left to simulate.
    if (hasArrivingCustomers()) {
      double nextArrivalTime = time + randomGen.genInterArrivalTime();
      Event nextArrival = createArrivalEvent(nextArrivalTime);
      scheduleEvent(nextArrival);
    }
  }

  public void simulateDone(double time, Server server) {
    // make the server finish the customer
    Event customerDone = server.finishCustomer(time);
    // schedule done event if any.
    if (customerDone != null) {
      scheduleEvent(customerDone);
    }
  }

  /**
   * Get the next event with the earliest timestamp. 
   * Ties between events happening at the same time are broken arbitrarily.
   * Event is removed from the queue.
   * @return The next event with the earliest timestamp.
   */
  private Event getNextEarliestEvent() {
    return this.events.poll();
  }
  /**
   * Checks if there are events left to be simulated.
   * @return true if the events queue still has events left. False otherwise.
   */
  private boolean hasEventsLeft() {
    return this.events.peek() != null;
  }

  /**
   * Checks if there are still incoming customers to simulate.
   * @return True if there are still arriving customers. False otherwise.
   */
  private boolean hasArrivingCustomers() {
    return this.numArrivingCustomers > 0;
  }

  /**
   * Prints statistics.
   */
  public void printStats() {
    System.out.println(stats);
  }

  /**
   * Inner class that stores statistics about the simulation.
   * In particular, the average waiting time, the number of customers who left,
   * and the nbumber of customers who are served.
   * @UNCERTAIN: Difference between static and non-static class. Please ignore this comment.
   */

  static class Statistics {
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
      this.totalWaitTime += time;
    }

    private double avgWaitTime() {
      return this.totalWaitTime / this.totalNumServedCust;
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
