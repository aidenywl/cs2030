package cs2030.simulator;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A Shop object maintains the time, events for that shop, and the list of servers
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Semester 2
 */

class Shop {
  /** List of Events */
  PriorityQueue<Event> events;
  /** Array of Servers */
  ArrayList<Server> servers = new ArrayList<>();
  /** Current Time */
  private double currentTime;
  /** Number of servers in the instantiated shop */
  private final int numOfServers;
  /** maximum number of events supported by the shop */
  private final int MAX_NUMBER_OF_EVENTS;
  /** the random generator used for server and customer times */
  protected static RandomGenerator randomTime;
  

  public Shop(int numberOfServers,int MAX_NUMBER_OF_EVENTS,
              RandomGenerator random) {
    this.numOfServers = numberOfServers;
    this.MAX_NUMBER_OF_EVENTS = MAX_NUMBER_OF_EVENTS;
    this.createServers();
    this.currentTime = 0;
    this.events = new PriorityQueue<Event>(); // Comparator not required as
                                              // Event already imp Comparable
    Shop.randomTime = random; // setting the randomgenerator for time
  }

  /** Method to initialise the list of servers; used in the constructor 
   * PRECONDITION: the {@code RandomGenerator randomTime} must be initialised
   */
  private void createServers() {
    for (int i = 0; i < this.numOfServers; i++) {
      servers.add(new Server());
    }
  }

  /** processes all events after the servers have been initialised */
  public void runShop() {
    // generate the first event
    this.events.add(Shop.genCustArrive(0));
    // subtracting one event from the num of required simulations
    Shop.eventGenerated();
    // processing the events
    while (hasEvent()) {
      // get the earliest event
      Event currentEvent = this.events.poll();
      // handle the event
      this.handleEvent(currentEvent);

    }
  }
  /**
   * HELPER METHOD TO add a new customer arrival event to the list
   * PRECONDITION: CAN ONLY BE RUN AFTER A CUSTOMER ARRIVE METHOD IS PROCESSED
   */
  private void addCustArrive() {
    if (LabTwoA.hasNextEvent()) {
      // create a new customer arrive event
      Event nextEvent = genCustArrive(this.currentTime +
                                        randomTime.genInterArrivalTime());
      this.events.add(nextEvent);

      // subtracting one event from the number of required simulations
      Shop.eventGenerated();
    }
  }
  /**
   * subtracts one event from the simulation
   * after the event has been created
   */
  private static void eventGenerated() {
    LabTwoA.numEvents--;
  }

  private static Event genCustArrive(double arriveTime) {
    return new CustArrive(arriveTime);
  }

  /**
   * Checks if there are still events left for the shop to process
   * @return returns true if there are still events left, false otherwise
   */
  private boolean hasEvent() {
    return this.events.peek() != null;
  }

  /**
   * Processes both customer arrive and done events
   * @param event the event to be processed
   */
  private void handleEvent(Event event) {
    Server currentServer; // the server that willh andle the event
    CustDone custDone; // the event variable to check if a customer is served
    switch (event.getEventType()) {
      case "ARRIVE":
        // updating time first
        this.arrivalUpdateTime((CustArrive) event);
        // creating the customer
        Customer customer = new Customer(event.eventTime);

        // print a line saying that customer has arrived
        System.out.printf(" %.3f %s arrives\n", event.eventTime, customer);

        // find idle or available server
        currentServer = findIdleOrAvailableServer();
        if (currentServer == null) {
          // get the first server to reject the customer
          currentServer = servers.get(0);
        }
        // handle the customer
        custDone = (CustDone) currentServer.handleCustomer(customer, currentTime);
        // add customer done event if the customer is served
        this.processCustDone(custDone);
        this.addCustArrive();
        break;

      case "DONE":
        // update the shop's current time to the event done time
        this.currentTime = event.eventTime;
        // casting the event to CustDone to find the server in charge
        CustDone custDoneEvent = (CustDone) event;
        currentServer = custDoneEvent.server;
        // get the server to finish off the customer
        custDone = (CustDone) currentServer.finishCustomer(currentTime);
        // add customer done event if the customer is served
        this.processCustDone(custDone);
        break;
      default:
        System.out.println("The Event is not an accepted type");
    }
  }

  // method for processing customer done events
  private void processCustDone(Event custDone) {
    if (custDone != null) {
      events.add(custDone);
    }
  }

  /**
   * Method for updating the shop based on the customer's arrival time
   * @param arriveEvent the customer arrive event
   */
  private void arrivalUpdateTime(CustArrive arriveEvent) {
    double arrivalTime = arriveEvent.eventTime;
    // ensuring that the currentTime is up to date even if there is an 
    // interval between customers
    if (this.currentTime == 0 || arrivalTime >= this.currentTime) {
      this.currentTime = arrivalTime;
    }
  }

  /**
   * Finds and returns the first idle server.
   * if none are idle, return an available server.
   * @return availableServer the server that is idle or available.
   */
  private Server findIdleOrAvailableServer() {
    Server idleServer = this.findIdleServer();
    Server availableServer = this.findAvailableServer();

    if (idleServer != null) {
      return idleServer;
    } else {
      return availableServer;
    }
  }

  private Server findIdleServer() {
    Server idleServer = null;

    for (int i = 0; i < servers.size(); i++) {
      Server currentServer = servers.get(i);

      // finding a server to serve the customer
      if (currentServer.idle) {
        idleServer = currentServer;
        break;
      }
    }
    return idleServer;
  }

  private Server findAvailableServer() {
    Server availableServer = null;

    for (int i = 0; i < servers.size(); i++) {
      Server currentServer = servers.get(i);

      // finding a server to wait
      if (currentServer.serverAvailable) {
        availableServer = currentServer;
        break;
      }
    }
    return availableServer;
  }



  public boolean hasSpaceForEvent() {
    return this.events.size() < MAX_NUMBER_OF_EVENTS;
  }

  public void addEvent(Event event) {
    assert (Event.isValidEvent(event));
    this.events.add(event); // adding to the priority queue
  }

/* Not needed as we use a priortyqueue
  private Event getEarliestEvent() {
    int earliestEventIndex = 0;
    double earliestEventTime = Double.MAX_VALUE;

    for (int i = 0; i < events.size(); i++) {
      double currentEventTime = events.get(i).eventTime;
      if (currentEventTime < earliestEventTime) {
        earliestEventIndex = i;
        earliestEventTime = currentEventTime;
      }
    }
    Event earliestEvent = events.get(earliestEventIndex);
    // delete the event from the arraylist
    events.remove(earliestEventIndex);
    return earliestEvent;
  }
*/
  @Override
  public String toString() {
    return String.format("%.3f %d %d\n", Server.Statistics.getAvgWaitTime(),
        Server.Statistics.getTotalServedCust(),
        Server.Statistics.getTotalRejectedCust());
  }
}
