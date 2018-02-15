import java.util.ArrayList;

class Shop {
  ArrayList<Event> events = new ArrayList<>(); // maintains the list of events
  ArrayList<Server> servers = new ArrayList<>(); // maintains the server list
  private static double currentTime;
  private final int numOfServers;
  private final int MAX_NUMBER_OF_EVENTS;

  public Shop(int numberOfServers,int MAX_NUMBER_OF_EVENTS) {
    this.numOfServers = numberOfServers;
    this.MAX_NUMBER_OF_EVENTS = MAX_NUMBER_OF_EVENTS;
    this.createServers();
    this.currentTime = 0;
  }

  // method used to initialise the Shop with the servers
  private void createServers() {
    double serviceTime = 1;
    for (int i = 0; i < this.numOfServers; i++) {
      servers.add(new Server(serviceTime));
    }
  }

  public void runShop() {
    while (events.size() > 0) {
    // get the earliest event
      Event currentEvent = this.getEarliestEvent();

    // handle the event
      this.handleEvent(currentEvent);
    // do till no events left
    }
  }

  private void handleEvent(Event event) {
    Server currentServer; // the server that willh andle the event
    CustDone custDone; // the event variable to check if a customer is served
    switch (event.getEventType()) {
      case "ARRIVE":
        // updating time first
        this.arrivalUpdateTime( (CustArrive) event);
        // creating the customer
        Customer customer = new Customer(event.eventTime);

        // find idle or available server
        currentServer = findIdleOrAvailableServer();
        if (currentServer == null) {
          // get the first server to reject the customer
          currentServer = servers.get(0);
        }
        custDone = (CustDone) currentServer.handleCustomer(customer, currentTime);
        // serve the customer

        // add customer done event if the customer is served
        this.processCustDone(custDone);
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

  /**
   * Method for updating the shop based on the customer's arrival time
   */
  private void arrivalUpdateTime(CustArrive arriveEvent) {
    double arrivalTime = arriveEvent.eventTime;
    // ensuring that the currentTime is up to date even if there is an 
    // interval between customers
    if (this.currentTime == 0 || arrivalTime >= this.currentTime) {
      this.currentTime = arrivalTime;
    }
  }

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


  // method for processing customer done events
  private void processCustDone(Event custDone) {
    if (custDone != null) {
      events.add(custDone);
    }
  }

  public boolean hasSpaceForEvent() {
    return this.events.size() < MAX_NUMBER_OF_EVENTS;
  }
  public void addEvent(Event event) {
    assert (Event.isValidEvent(event));
    this.events.add(event);
  }

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

  @Override
  public String toString() {
    return String.format("%.3f %d %d\n", Server.Statistics.getAvgWaitTime(),
                        Server.Statistics.getTotalServedCust(),
                        Server.Statistics.getTotalRejectedCust());

  }
}
