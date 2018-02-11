class Shop implements EventTypes {
  private Event[] events;
  private Server server;
  private final int MAX_NUMBER_OF_EVENTS;
  private int numOfEvents;
  private int totalArrivedCustomers;



  public Shop(Server server, int maxNumberOfEvents) {
    // when i put static in, this doesn't work?
    MAX_NUMBER_OF_EVENTS = maxNumberOfEvents; 
    this.server = server;
    this.numOfEvents = 0;
    this.events = new Event[MAX_NUMBER_OF_EVENTS];
    this.totalArrivedCustomers = 0;
  }

  /**
   * PRECONDITION: the restaurant must have a server
   * POSTCONDITION: no more events left
   */

  public void startServing() {

    // PRECONDITION
    assert this.server != null;
    assert numOfEvents > 0;

    while (this.numOfEvents > 0 ) {
      // get earliest event
      Event currentEvent = getEarliestEvent();

      switch(currentEvent.eventType) {
        case EventTypes.CUSTOMER_ARRIVE:
          // A customer has arrived. 
          this.customerArrives(currentEvent);
          break;

        case EventTypes.CUSTOMER_DONE:
          // A customer has finished
          this.customerDone(currentEvent);
          break;

        default:
          System.err.printf("Unknown event type %default:\n", 
                                    currentEvent.eventType);
      }

    }



    // POSTCONDITION
    assert numOfEvents == 0;
  }


    /**
     * Method for adding a new customer to a server when he arrives
     * PRECONDITION: the event must be a CUSTOMER_ARRIVE event
     * POSTCONDITION: a customer done event is added into the events list IF th
     * e added customer is served
     */
    private void customerArrives(Event event) {
      assert event.eventType == EventTypes.CUSTOMER_ARRIVE;

      Customer arrivedCustomer = new Customer(totalArrivedCustomers, 
                                              event.eventTime);
      System.out.printf("%6.3f %d arrives\n", arrivedCustomer.arrivalTime, 
                                                arrivedCustomer.customerId);
      this.totalArrivedCustomers++;

      // try to serve the customer
      boolean customerIsCurrentlyServed = this.server.addCustomer(
                                            arrivedCustomer);

      
      // if the customer is served, create a new
      // CUSTOMER_DONE event and add it into the events list
      if (customerIsCurrentlyServed) {
        this.addCustomerDoneEvent(this.server.getCustomerDoneTime());
      } 
    }

    // FOR CUSTOMER_DONE
    private void customerDone(Event event) {
      assert event.eventType == EventTypes.CUSTOMER_DONE;
      boolean waitingCustomerIsServed = false;
      waitingCustomerIsServed = server.finishCustomer();

      // if the customer is served, create a new CUSTOMER_DONE event and add it
      // into the events list
      if (waitingCustomerIsServed) {
        this.addCustomerDoneEvent(this.server.getCustomerDoneTime());

      }

    }

    private void addCustomerDoneEvent(double currentTime) {
      this.addEvent(new Event(server.getCustomerDoneTime(), EventTypes.CUSTOMER_DONE));
    }



  /**
   * Adds an Event event to the array of events.
   * Precondition: The Event array must have size for events
   */
  public void addEvent(Event event) {
      assert this.hasSpaceForEvent();
      this.events[numOfEvents] = event;
      this.numOfEvents++;
  }

  // checks if the number of events stored in the events array is below 
  // MAX_NUMBER_OF_EVENTS
  public boolean hasSpaceForEvent() {
    return this.numOfEvents < MAX_NUMBER_OF_EVENTS;
  }

  /*
   * get the earliest event in the shop events array
   */
  private Event getEarliestEvent() {
    int earliestEventIndex = -1;
    Event earliestEvent;

    double minTime = Double.MAX_VALUE;
    for (int i = 0; i < this.numOfEvents; i++) {
      double currentEventTime = this.events[i].eventTime;
        if (currentEventTime < minTime) {
          earliestEventIndex = i;
          minTime = currentEventTime;
        }
    }

    earliestEvent = this.events[earliestEventIndex];

    // transferring the last event into the completed event index
    if (this.numOfEvents > 0) {
      this.events[earliestEventIndex] = this.events[this.numOfEvents - 1];
    }
    this.numOfEvents--;

    return earliestEvent;
  }

  /**
   * @param PRECONDITION: The arrival time is the time given by the events
   * @return POSTCONDITION: return a customer created based on the shop's assigned Customer ID and the customer's arrival time
   */
  private Customer createCustomer(double arrivalTime) {
    Customer customer = new Customer(this.totalArrivedCustomers, arrivalTime);
    this.totalArrivedCustomers++;
    return customer;
  }
  

  // GETTING ALL THE STATISTICS

  public double getTotalWaitingTime() {
    return server.totalWaitingTime;
  }

  public int getTotalNumOfServedCustomers() {
    return server.totalNumOfServedCustomers;
  }

  public int getTotalNumOfLostCustomers() {
    return server.totalNumOfLostCustomers;
  }
}
