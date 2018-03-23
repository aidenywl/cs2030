/**
 * The Simulator class encapsulates information and methods pertaining to a
 * Simulator.
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
class Simulator {
  /** Maximum number of events that the simulator can store at a given time. */
  private static final int MAX_NUMBER_OF_EVENTS = 100;

  /** The time a server takes to serve a customer. */
  private static final double SERVICE_TIME = 1.0;

  /** The array of events. Events may not be ordered according to time . */
  private Event[] events;
  /** The number of events in the array. */
  private int numOfEvents;

  /** The shop of servers being simulated. */
  private final Shop shop;

  /** The statistics being maintained. */
  public static Statistics stats = new Statistics();

  /**
   * Create a Simulator and initializes it.
   *
   * @param numOfServers Number of servers to be created for simulation.
   */
  public Simulator(int numOfServers) {
    this.shop = new Shop(numOfServers);

    // Initialize an array to store events.
    this.events = new Event[Simulator.MAX_NUMBER_OF_EVENTS];
    this.numOfEvents = 0;
  }

  /**
   * Schedules an event with this simulator. The simulator maintains an array
   * of events (in arbitrary order). This method appends the given event at
   * the end of the array.
   *
   * @param event The event to be scheduled for simulation.
   * @return Returns true if the event is added successfully; false otherwise.
   */
  public boolean scheduleEvent(Event event) {
    if (this.numOfEvents >= Simulator.MAX_NUMBER_OF_EVENTS) {
      return false;
    } else {
      // Append event as the last element in this.events.
      this.events[this.numOfEvents] = event;
      this.numOfEvents++;
      return true;
    }
  }

  /**
   * Run the Simulator until all scheduled events are simulated.
   */
  public void run() {
    while (this.numOfEvents > 0) {
      Event event = getNextEarliestEvent();
      event.simulate(this);
    }
  }

  /**
   * Get the next event with the earliest timestamp, breaking ties between
   * events happening at the same time arbitrarily. The event is then deleted
   * from the array. This is an O(n) algorithm and better algorithms exists.
   * This will be improved in later labs using a min heap.
   *
   * @return The next event with the earliest timestamp.
   */
  private Event getNextEarliestEvent() {
    // Scan linearly through the array to find the event with the earliest
    // (smallest) timestamp.
    int nextEventIndex = -1;
    double minTime = Double.MAX_VALUE;
    for (int i = 0; i < this.numOfEvents; i++) {
      if (this.events[i].time < minTime) {
        // If earlier than minTime, update minTime and index to event with
        // earliest timestamp so far
        minTime = this.events[i].time;
        nextEventIndex = i;
      }
    }

    // Get the earliest event
    Event event = this.events[nextEventIndex];
    // Replace the earliest event with the last element in the array
    this.events[nextEventIndex] = this.events[this.numOfEvents - 1];
    // Update number of events scheduled for simulation
    this.numOfEvents--;
    return event;
  }


  /**
   * Create a DoneEvent for the given server and customer to be simulated.
   *
   * @param time The time this event is scheduled to occur.
   * @param server The server to serve the customer.
   * @param customer The customer being served.
   * @return true if event is scheduled successfully, false otheriwise.
   */
  public boolean createDoneEvent(double time, Server server, Customer customer) {
    Event event = new DoneEvent(time + Simulator.SERVICE_TIME,
        server, customer);
    boolean ok = scheduleEvent(event);
    if (!ok) {
      System.err.println("Warning: Too many events.  Simulation result "
          + "will not be correct.");
    }
    return ok;
  }

  /**
   * Create an ArrivalEvent to mark the arrival of a customer.
   *
   * @param time The time this event is scheduled to occur.
   * @return true if event is scheduled successfully, false otheriwise.
   */
  public boolean createArrivalEvent(double time) {
    Event event = new ArrivalEvent(time);
    boolean ok = this.scheduleEvent(event);
    if (!ok) {
      System.err.println("Warning: Too many events.  Skipping the rest.");
    }
    return ok;
  }

  /**
   * Simulate the arrival of a customer.
   *
   * @param time The time this event is scheduled to occur.
   */
  public void simulateArrival(double time) {
    Customer customer = new Customer();
    customer.arrive(time);

    Server server = shop.findIdleServer();
    if (server != null) {
      this.serveCustomer(time, server, customer);
      return;
    }

    // If server with no customer waiting is found, wait for this server
    server = shop.findServerWithNoWaitingCustomer();
    if (server != null) {
      this.makeCustomerWait(time, server, customer);
      return;
    }

    // If idle server not found, and if server with no customer waiting not
    // found, customer leaves (maximum of one waiting customer per server).
    this.customerLeaves(time, customer);
  }

  /**
   * Simulate that the server is done serving a customer.
   *
   * @param time The time this event is scheduled to occur.
   * @param server The server to serve the customer.
   * @param customer The customer being served.
   */
  public void simulateDone(double time, Server server, Customer customer) {
    customer.serveEnd(time, server);
    if (server.customerWaiting()) {
      // Someone is waiting, serve this waiting someone
      this.serveWaitingCustomer(time, server);
    } else {
      // Server idle
      server.makeIdle();
    }
  }

  /**
   * Simulate a server starting to serve a customer at the given time.
   * Precondition: No one else must be served at this time.
   *
   * @param time The {@code time} at which the {@code server} serves the
   *     {@code customer}.
   * @param server The {@code server} who would be serving the {@code customer}
   *     at {@code time}.
   * @param customer The {@code customer} to be served by the {@code server} at
   *     {@code time}.
   */
  public void serveCustomer(double time, Server server, Customer customer) {
    server.serve(customer);
    customer.serveBegin(time, server);
    this.createDoneEvent(time, server, customer);
  }

  /**
   * Simulate a customer starting to wait for a server at time.
   * Precondition: All customers are busy serving and there is at least one
   * server with no customer waiting for him.
   *
   * @param time The {@code time} at which the {@code customer} starts
   *     waiting for the {@code server}.
   * @param server The server whom the customer is waiting for at time.
   * @param customer The customer who is waiting for the server at time.
   */
  public void makeCustomerWait(double time, Server server, Customer customer) {
    server.askToWait(customer);
    customer.waitBegin(time, server);
  }

  /**
   * Simulate a server serving his waiting customer at time.
   *
   * @param time The {@code time} at which the {@code server} serves
   *     his waiting customer.
   * @param server The {@code server} who is to serve his waiting customer.
   */
  public void serveWaitingCustomer(double time, Server server) {
    Customer customer = server.removeWaitingCustomer();
    this.serveCustomer(time, server, customer);
  }

  /**
   * Simulate a customer leaving because all servers are busy serving and
   * every server has a customer waiting.
   *
   * @param time The {@code time} at which the {@code customer} leaves.
   * @param customer The {@code customer} which leaves at {@code time}.
   */
  public void customerLeaves(double time, Customer customer) {
    customer.leave(time);
  }
}
