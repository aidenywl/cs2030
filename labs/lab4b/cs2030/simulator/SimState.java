package cs2030.simulator;

import cs2030.util.PriorityQueue;
import cs2030.util.Pair;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class encapsulates all the simulation states.  There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop
 * (the servers) and (iv) the event logs.
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */
public class SimState {
  /** The priority queue of events. */
  private final PriorityQueue<Event> events;

  /** The statistics maintained. */
  private final Statistics stats;

  /** The shop of servers. */
  private final Shop shop;

  /** The final string to be printed. */
  private final String log;

  /** The number of customers that have arrived so far; for customer ID. */
  private final int customerID;

  /**
   * Constructor for creating the simulation state from scratch.
   * 
   * @param numOfServers The number of servers.
   */
  public SimState(int numOfServers) {
    this.shop = new Shop(numOfServers);
    this.stats = new Statistics();
    this.events = new PriorityQueue<Event>();
    this.log = "";
    this.customerID = 0;
  }


  /**
   * @help ??? what is a good way to make this shorter???? 
   * @help This is an intended javadoc infringement for educational purposes. Please help.
   * Private constructor used to create a new Simulation State 
   * whenever any contents of the simulation state has to be changed.
   * 
   * @param  events    the events pq
   * @param  stats     the statistics of the simulation. 
   * @param  shop      the shop containing all the servers.
   * @param  log       the log to be printed.
   * @param  newCustID the ID of the next incoming customer.
   */
  private SimState(PriorityQueue<Event> events, 
                  Statistics stats, Shop shop, 
                  String log, int newCustID) {
    this.events = events;
    this.stats = stats;
    this.shop = shop;
    this.log = log;
    this.customerID = newCustID;

  }

  // private SimState(SimState oldSimState, int newCustID) {
  //   super(oldSimState.events, oldSimState.stats, oldSimState.shop, oldSimState.log);
  //   this.customerID = customerID;
  // }

  /**
   * Add an event to the simulation's event queue.
   * @param  e The event to be added to the queue.
   * @return The new simulation state.
   */
  public SimState addEvent(Event e) {
    PriorityQueue<Event> newEvents = events.add(e);
    return new SimState(newEvents, this.stats, this.shop, this.log, this.customerID);
  }

  /**
   * Adds a list of events to the simulation's event queue.
   * Should be used for arrival events at the start.
   * 
   * @param  events The events to be added.
   * @return        The new SimState.
   */
  public SimState addEvents(PriorityQueue<Event> events) {
    return new SimState(events, this.stats, this.shop, this.log, this.customerID);
  }

  /**
   * Retrieve the next event with earliest time stamp from the
   * priority queue, and a new state.  If there is no more event, an
   * Optional.empty will be returned.
   * @return A pair object with an (optional) event and the new simulation
   *     state.
   */
  public Pair<Event, SimState> nextEvent() {
    // first holds the next event.
    // second holds the new priorityQueue.
    Pair<Event, PriorityQueue<Event>> result = this.events.poll();
    Optional<Event> opEvent = result.first;
    SimState nextSimState = new SimState(result.second, this.stats, this.shop, this.log, this.customerID);
    return new Pair<>(opEvent, nextSimState);
  }

  /**
   * Called when a customer arrived in the simulation.
   * @param time The time the customer arrives.
   * @param c The customer that arrrives.
   * @return A new state of the simulation after the customer arrives.
   */
  private SimState customerArrives(double time, Customer c) {
    return updateLog(String.format("%6.3f %s arrives\n", time, c));
  }

  /**
   * Called when a customer waits in the simulation.  This methods update
   * the logs of simulation.
   * @param time The time the customer starts waiting.
   * @param s The server the customer is waiting for.
   * @param c The customer who waits.
   * @return A new state of the simulation after the customer waits.
   */
  private SimState customerWaits(double time, Server s, Customer c) {
    return updateLog(String.format("%6.3f %s waits for %s\n", time, c, s));  
  }

  /**
   * Called when a customer is served in the simulation.  This methods
   * update the logs and the statistics of the simulation.
   * @param time The time the customer arrives.
   * @param s The server that serves the customer.
   * @param c The customer that is served.
   * @return A new state of the simulation after the customer is served.
   */
  private SimState customerServed(double time, Server s, Customer c) {
    Statistics newStats = stats.serveOneCustomer()
                               .customerWaitedFor(time - c.timeArrived());

    return updateLog(String.format("%6.3f %s served by %s\n", time, c, s))
                    .updateStats(newStats);  
  }

  /**
   * Called when a customer is done being served in the simulation.
   * This methods update the logs of the simulation.
   * @param time The time the customer arrives.
   * @param s The server that serves the customer.
   * @param c The customer that is served.
   * @return A new state of the simulation after the customer is done being
   *     served.
   */
  private SimState customerDone(double time, Server s, Customer c) {
    return updateLog(String.format("%6.3f %s done served by %s\n", time, c, s));  
  }

  /**
   * Called when a customer leaves the shops without service.
   * Update the log and statistics.
   * @param  time  The time this customer leaves.
   * @param  customer The customer who leaves.
   * @return A new state of the simulation.
   */
  private SimState customerLeaves(double time, Customer customer) {
    Statistics newStats = stats.lostOneCustomer();
    return updateLog(String.format("%6.3f %s leaves\n", time, customer))
                    .updateStats(newStats);
  }

  /**
   * Simulates the logic of what happened when a customer arrives.
   * The customer is either served, waiting to be served, or leaves.
   * @param time The time the customer arrives.
   * @return A new state of the simulation.
   */
  public SimState simulateArrival(double time) {
    Customer customer = new Customer(time, customerID);
    SimState newSimState =  incrementCustomer().customerArrives(time, customer)
                                               .servedOrLeave(time, customer);
    // System.out.println("endofarrival");
    return newSimState;
  }

  /**
   * Called from simulateArrival.  Handles the logic of finding
   * idle servers to serve the customer, or a server that the customer
   * can wait for, or leave.
   * @param time The time the customer arrives.
   * @param customer The customer to be served.
   * @return A new state of the simulation.
   */
  private SimState servedOrLeave(double time, Customer customer) {
    Optional<Server> s = shop.findIdleServer();
    // System.out.println("servedOrLeave");
    // not lazily evaluated?
    // Function<Server, SimState> servingCustomer = server -> 
    // serveCustomer(time, server, customer);
    // System.out.println(s.map(servingCustomer));
    Supplier<Optional<SimState>> waitSupplier = 
              () -> shop.findServerWithNoWaitingCustomer()
                        .map(server -> makeCustomerWait(time, server, customer));

    SimState newSimState = s.map(server -> serveCustomer(time, server, customer))
                .or(waitSupplier)
                .or(() -> Optional.of(customerLeaves(time, customer))).get(); 
    return newSimState;
    // WHY can't i use two orElse at the same level???
  }

  /**
   * Simulates the logic of what happened when a customer is done being
   * served.  The server either serve the next customer or becomes idle.
   * @param time The time the service is done.
   * @param server The server serving the customer.
   * @param customer The customer being served.
   * @return A new state of the simulation.
   */
  public SimState simulateDone(double time, Server server, Customer customer) {
    Server currentServerState = shop.findRecentServer(server);
    SimState newSimState = customerDone(time, currentServerState, customer)
                              .serveNextOrIdle(time, currentServerState);
    return newSimState;
  }

  /**
   * Called from simulateDone.  Handles the logic of checking if there is
   * a waiting customer, if so serve the customer, otherwise make the
   * server idle.
   * @param time The time the service is done.
   * @param server The server serving the next customer.
   * @return A new state of the simulation.
   */
  private SimState serveNextOrIdle(double time, Server server) {
    // get the waiting customer if any.
    Optional<Customer> c = server.getWaitingCustomer();

    // either return a simState where the server serves a customer or where the server is made
    // idle.
    return c.map(customer -> 
                    serveCustomer(time, server.removeWaitingCustomer(), customer))
            .orElse(updateServer(server.makeIdle())); // idle server
  }

  /**
   * Handle the logic of server serving customer.  A new done event
   * is generated and scheduled.
   * @param  time  The time this customer is served.
   * @param  server The server serving this customer.
   * @param  customer The customer being served.
   * @return A new state of the simulation.
   */
  private SimState serveCustomer(double time, Server server, Customer customer) {
    double doneTime = time + Simulator.SERVICE_TIME;
    // creating a done event.
    Event doneEvent = new Event(doneTime, sims -> sims.simulateDone(doneTime, server, customer));
    // the new simulation state.
    SimState newSimState = addEvent(doneEvent)
                   .updateServer(server.serve(customer))
                   .customerServed(time, server, customer);
    return newSimState;
  }

  /**
   * Handle the logic of queueing up customer for server.   Make the
   * customer waits for server.
   * @param  time  The time this customer started waiting.
   * @param  server The server this customer is waiting for.
   * @param  customer The customer who waits.
   * @return A new state of the simulation.
   */
  private SimState makeCustomerWait(double time, Server server, Customer customer) {
    return customerWaits(time, server, customer)
            .updateServer(server.askToWait(customer));
  }

  /**
   * Updates the simState when a server has been changed.
   * @param  s the server that has changed.
   * @return   the new simulation state.
   */
  private SimState updateServer(Server s) {
    return new SimState(this.events, this.stats, this.shop.updateShop(s), 
            this.log, this.customerID);
  }

  /**
   * Updates the simState when the statistics changed.
   * @param  newStats the stats that has changed.
   * @return       The new simulation state.
   */
  private SimState updateStats(Statistics newStats) {
    return new SimState(this.events, newStats, this.shop, this.log, this.customerID);
  }

  /**
   * Updates the log to be printed to the console when the simulation is over.
   * @param  moreLog the new updated String.
   * @return        an updated Simulation State.
   */
  private SimState updateLog(String moreLog) {
    return new SimState(this.events, this.stats, this.shop, this.log + moreLog, this.customerID);
  }

  /**
   * Return a string representation of the simulation state, which
   * consists of all the logs and the stats.
   * @return A string representation of the simulation.
   */
  public String toString() {
    return this.log + this.stats.toString();
  }

  private SimState incrementCustomer() {
    return new SimState(this.events, this.stats, this.shop, this.log, this.customerID + 1);
  }


}
