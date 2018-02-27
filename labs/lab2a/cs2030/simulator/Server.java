package cs2030.simulator;

/**
 * Encapsulates the unique attributes for a server – id and serviceTime –
 * and the methods that are necessary to handle a customer in a shop.
 * A server can service, wait, and reject customers.
 * It relies on another class to keep track of time.
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 LabTwoA
 */
class Server {
  /** class field to keep track of the number of servers created. Used for ID */
  private static int Id; // keeps track of the number of servers created so e
  /** the ID of the instantiated server */
  private final int serverId; // the ID of the instantiated server

  /** {@code true} if the server is not serving anyone and no customer is 
   * waiting. {@code false} otherwise */
  public boolean idle;
  /** {@code true} if server has space for a customer to wait {@code false}
   * otherwise */
  public boolean serverAvailable;
  /** stores the reference to the object of the currently served customer */
  private Customer servedCustomer;
  /** stores the reference to the object of the currently waiting customer */
  private Customer waitingCustomer;
  
  /**
   * Create a new Server object
   * 
   */
  public Server() {
    this.serverId = Id;
    Id++;
    this.idle = true;
    this.serverAvailable = true;

    this.servedCustomer = null;
    this.waitingCustomer = null;
  }

  /**
   * makes the server handle the customer either serving, making the customer
   * wait, or rejecting him.
   * @param customer the customer to be handled
   * @param servedTime the time the customer is handled by the server
   * @return the customer done event, if any.
   */
  public Event handleCustomer(Customer customer, double handleTime) {
    // serve customer if server is available
    Event customerDoneEvent = null;
    if (this.idle) {
      customerDoneEvent = this.serveCustomer(customer, handleTime);
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
   * serve the customer and return a CustDone event.
   * @param customer the customer being served
   * @param servedTime the time the customer is served
   * @return the customer done event
   */

  private Event serveCustomer(Customer customer, double servedTime) {
    this.idle = false;
    this.servedCustomer = customer;
    // update the waiting time in the Customer object
    customer.served(servedTime);

    // print a line that says the customer is served with time and ID
    System.out.printf("%6.3f %s served\n", servedTime, customer); 

    return new CustDone(Server.customerFinishTime(customer, servedTime), this);
   
  }
  
  /**
   * adds the customer to the server's wait queue.
   * @param customer the customer that is to be delayed
   */

  private void delayCustomer(Customer customer) {
    this.waitingCustomer = customer;
    this.serverAvailable = false;

    // print a line that says the customer is waiting
    System.out.printf("%6.3f %s waits\n", customer.arrivalTime, customer);
  }

  /**
   * rejects a customer and prints the line.
   * @param customer the customer to be rejected
   * PRECONDITION: the waiter is serving a customer and is unavailable
   */

  private void rejectCustomer(Customer customer) {
    assert this.serverAvailable == false;
    assert this.idle == false;
    System.out.printf("%6.3f %s leaves\n", customer.arrivalTime,
                       customer);
  }

  /**
   * Used to finish a customer after he has been served.
   * will serve a waiting customer if there is any.
   * @param finishTime the time the customer is finished.
   */
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
  private static double customerFinishTime(Customer customer, double servedTime) {
    return customer.serviceTime + servedTime;
  }

  /**
   * update the statistics for the customer that has been served or rejected.
   * Stats updated: total waiting time, total customers served, total customers
   * rejected
   * @param customer the customer whose attributes will be used to update data
   * @param status whether the customer has been served or rejected
   */
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


  /**
   * inner nested static class to keep track of the information for all servers
   * encapsulates all necessary information for the simulation
   */
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
