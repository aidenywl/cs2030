class Server {
  private static int Id; // keeps track of the number of servers created so every
                     // server has a unique id  
  private final double serviceTime;   // the time the server takes to serve a customer
  private final int serverId; // the ID of the instantiated server

  public boolean idle;
  public boolean serverAvailable; // false if server is serving and customers wai
                                // ting
  private Customer servedCustomer;
  private Customer waitingCustomer;

  public Server(double serviceTime) {
    this.serverId = Id;
    Id++;
    
    this.serviceTime = serviceTime;
    this.idle = true;
    this.serverAvailable = true;

    this.servedCustomer = null;
    this.waitingCustomer = null;
  }


  public Event handleCustomer(Customer customer, double servedTime) {
    // serve customer if server is available
    Event customerDoneEvent = null;
    if (this.idle) {
      customerDoneEvent = this.serveCustomer(customer, servedTime);
      updateStatistics(customer, "SERVED");

      // return a customer done event to be processed in the future
      return customerDoneEvent;
    } 
    // make customer wait
    else if (!this.idle && this.serverAvailable) {
      this.delayCustomer(customer);
      return customerDoneEvent;
    } 
    // reject customer
    else {
      this.rejectCustomer(customer);
      updateStatistics(customer, "REJECTED");
      return customerDoneEvent;
    }
   
  }
  /**
   * serve the customer and return a CustDone event
   */
  private Event serveCustomer(Customer customer, double servedTime) {
    this.idle = false;
    this.servedCustomer = customer;
    // update the waiting time in the Customer object
    customer.served(servedTime);

    // print a line that says the customer is served with time and ID
    System.out.printf("%6.3f %s served\n", servedTime, customer); 

    return new CustDone(this.customerFinishTime(servedTime), this);
   
  }
  
  private void delayCustomer(Customer customer) {
    this.waitingCustomer = customer;
    this.serverAvailable = false;

    // print a line that says the customer is waiting
    System.out.printf("%6.3f %s waits\n", customer.arrivalTime, customer);
  }

  /**
   * PRECONDITION: the waiter is serving a customer and is unavailable
   */
  private void rejectCustomer(Customer customer) {
    assert this.serverAvailable == false;
    assert this.idle == false;
    System.out.printf("%6.3f %s leaves\n", customer.arrivalTime,
                       customer);
  }

  // method for customer done events
  public Event finishCustomer(double finishTime) {
    Event customerDoneEvent = null;
    System.out.printf("%6.3f %s done\n", finishTime, this.servedCustomer);
    this.servedCustomer = null;
    this.idle = true;
    if (waitingCustomer != null) {
      customerDoneEvent = this.serveCustomer(waitingCustomer, finishTime);
      updateStatistics(waitingCustomer, "SERVED");
      this.waitingCustomer = null;
      this.serverAvailable = true;
    }

    return customerDoneEvent;
  }
  
  @Override
  public String toString() {
    return "The Server ID is: " + this.serverId;
  }


  // method that calculates the customer done time for customer done events
  private double customerFinishTime(double servedTime) {
    return servedTime + serviceTime;
  }

  private static void updateStatistics(Customer customer, String status) {
    switch (status) {
      case "SERVED": 
        Statistics.totalWaitTime += customer.waitTime;
        Statistics.totalServedCust += 1;
        break;
      case "REJECTED":
        Statistics.totalRejectedCust += 1;
        break;
      default:
        System.out.println("Wrong use of updating Statistics");
    }
  }


  
  static class Statistics {
    private static double totalWaitTime = 0;
    private static int totalServedCust = 0;
    private static int totalRejectedCust = 0;

    public static double getAvgWaitTime() {
      return totalWaitTime / totalServedCust;
    }
    
    public static int getTotalServedCust() {
      return totalServedCust;
    }
    public static int getTotalRejectedCust() {
      return totalRejectedCust;
    }
  }

}
