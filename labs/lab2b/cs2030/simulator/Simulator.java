package cs2030.simulator;

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

  /** The shop of human servers being simulated. */
  private Shop humanShop;
  /** The shop of self checkout servers being simulated. */
  private Shop selfCheckoutShop;

  /** The object to keep track of statistics for the simulator. */
  protected static Statistics stats = new Statistics();

  /** The random generator to be used for servers and customers */
  protected static RandomGenerator randomGen;

  /** The number of events to be simulated */
  private int numArrivingCustomers;

  /**
   * Creates a simulatior and initialises it.
   * @param  numOfServers The number of servers to be created in the shop.
   */
  public Simulator(int numCustomers) {
    // initialising the Shop with the number of servers needed
    this.numArrivingCustomers = numCustomers;
    this.events = new PriorityQueue<>(100);

    // creating the first event
    Event firstEvent = createArrivalEvent(0);
    scheduleEvent(firstEvent);
  }

/**
 * Sets parameters for the shop and initialises it.
 * @param numHumanServers      number of human servers
 * @param numSelfService       number of self checkout counters
 * @param maxQueueLength       maximum queue length
 * @param restProbability      rest probability for humans
 * @param selfServiceTimeLimit service time limit for self checkout counters.
 */
  public void setShop(int numHumanServers, int numSelfService, int maxQueueLength, double restProbability, double selfServiceTimeLimit) {
    Server.setQueueLimit(maxQueueLength);
    this.humanShop = new Shop(numHumanServers);
    this.humanShop.createHumanServers(restProbability);
    this.selfCheckoutShop = new Shop(numSelfService);
    this.selfCheckoutShop.createSelfServers(selfServiceTimeLimit);
  }

  /**
   * Sets the parameters for the random generator.
   * 
   * @param seed   The seed to be used for the random generator.
   * @param lambda The customer arrival rate.
   * @param mu     The service rate.
   * @param rho The resting rate.
   */
  public void setRandom(int seed, double lambda, double mu, double rho) {
    Simulator.randomGen = new RandomGenerator(seed, lambda, mu, rho);
  }

  /**
   * Sets the parameters for the customer class.
   * @param probability Probability of a greedy customer.
   */
  public void setCustomer(double probability) {
    Customer.setGreedyProbability(probability);
  }

  public void run() {
    do {
      Event currentEvent = events.poll();
      currentEvent.simulate(this);

    } while (hasEventsLeft());
  }

  /**
   * Schedules an event into the priority queue if there are spaces left
   * 
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
  /**
   * Simulate the arrival of a customer.
   *
   * @param time The time this event is scheduled to occur.
   */
  public void simulateArrival(double time) {
    // create a customer
    Customer customer = new Customer(time);
    Server currentServer = null;
    boolean withinTimeLimit = SelfServer.withinServiceTimeLimit(customer);
    // find an idle or free waiter.
    if (withinTimeLimit) {
      // look among idle checkount counter
      currentServer = selfCheckoutShop.findIdleServer(customer);
    }
    if (currentServer == null) {
        // look among idle humans
      currentServer = humanShop.findIdleServer(customer);
    }

    // if no idle, join a self checkout counter.
    if (withinTimeLimit && currentServer == null) {
      currentServer = selfCheckoutShop.findAvailableServer(customer);
    }
    // if checkout counters are not available, find a human server to wait at.
    if (currentServer == null) {
      currentServer = humanShop.findAvailableServer(customer);
    }
    // if still no luck, make the customer leave.
    
    if (currentServer == null) {
      customer.leave(time);
    } else {
      // if the person is served, return a customer done event.
      Event customerDone = currentServer.handleCustomer(customer, time);

      if (customerDone != null) {
        scheduleEvent(customerDone);
      }
    }

    // add events if there are any left to simulate.
    if (hasArrivingCustomers()) {
      double nextArrivalTime = time + randomGen.genInterArrivalTime();
      Event nextArrival = createArrivalEvent(nextArrivalTime);
      scheduleEvent(nextArrival);
    }
  }
  /**
   * Simulate that the server is done serving a customer.
   *
   * @param time The time this event is scheduled to occur.
   * @param server The server to serve the customer.
   */
  public void simulateDone(double time, Server server) {
    // make the server finish the customer
    server.finishCustomer(time);

    boolean didServerRest = false;
    // check whether it is time to rest, if it is a human server.
    if (server instanceof HumanServer) {
      didServerRest = checkHumanServerRest(time, (HumanServer) server);
      if(didServerRest) {
        return;
      }
    }
    // serve a waiting customer, if any.
    serveWaitingCustomer(time, server);

  }
  /**
   * Simulate that the server is back from a rest.
   *
   * @param time The time this event is scheduled to occur.
   * @param server The server to get back from a rest.
   */
  public void simulateBack(double time, HumanServer server) {
    server.backToWork();
    serveWaitingCustomer(time, server);
  }
  /**
   * Makes the server serve a waiting customer if there is any.
   * 
   * @param time   Time of serving.
   * @param server The server performing the serving.
   */
  private void serveWaitingCustomer(double time, Server server) {
    Event customerDone = null;
    // serve the waiting customer.
    if (server.hasWaitingCustomer()) {
      // get waiting customers for the waiter if any.
      Customer waitingCustomer = server.getWaitingCustomer();
      customerDone = server.handleCustomer(waitingCustomer, time);
    }
    // schedule done event if any.
    if (customerDone != null) {
      scheduleEvent(customerDone);
    }
  }

  private boolean checkHumanServerRest(double time, HumanServer server) {
    Event backEvent = server.checkTimeToRest(time);

    if (backEvent != null) {
      // make the server rest.
      server.rest();
      scheduleEvent(backEvent);
      return true;      
    }

    return false;
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
