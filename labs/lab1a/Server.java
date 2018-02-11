class Server implements ServerStatistics {
  private double serviceTime;
  public final int serverId;

  // details of the customer being served
  private Customer servedCustomer;
  public boolean customerBeingServed;

  // details of the customer waiting
  private Customer waitingCustomer;
  public boolean customerWaiting;

  // required statistics
  public double totalWaitingTime;
  public int totalNumOfServedCustomers;
  public int totalNumOfLostCustomers;

  // current time
  private double currentTime;


  public Server(int serverId, double serviceTime) {
    this.serviceTime = serviceTime;
    this.serverId = serverId;
    this.servedCustomer = null;
    this.waitingCustomer = null;
    this.customerBeingServed = false;
    this.customerWaiting = false;
    this.currentTime = 0;

    // statistics
    this.totalWaitingTime = 0;
    this.totalNumOfServedCustomers = 0;
    this.totalNumOfLostCustomers = 0;
  }

  /**
   * public method for the simulator to attempt to add customers.
   * 
   */
  public boolean addCustomer(Customer customer) {
    boolean servingCustomer = false;
    if(!this.customerBeingServed) {

      // updating the time
      if(this.currentTime == 0 || this.currentTime < customer.arrivalTime) {
        currentTime = customer.arrivalTime;
      }
      this.serveCustomer(customer, currentTime);

      // update the total waiting time for the server
      servingCustomer = true;
      // includes conditons for checking the waiters. 
    } else if (this.customerBeingServed && !this.customerWaiting) {
      this.makeCustomerWait(customer);

    } else { // case for when there is a customer being served and a customer already waiting
      this.makeCustomerLeave(customer);
    }

    return servingCustomer;
  }

  public boolean finishCustomer() {
    boolean servingCustomer = false;
    this.currentTime += serviceTime;
    System.out.printf("%6.3f %d done\n", currentTime, this.servedCustomer.customerId);
    this.deleteServedCustomer();

    if (this.customerWaiting) {
      this.serveCustomer(this.waitingCustomer, currentTime);
      this.deleteWaitingCustomer();
      servingCustomer = true;
    }
    return servingCustomer;
  }

  private void deleteServedCustomer() {
    this.customerBeingServed = false;
    this.servedCustomer = null;
  }

  private void deleteWaitingCustomer() {
    this.customerWaiting = false;
    this.waitingCustomer = null;
  }

  /*
   * Serves the current customer and provides the customer object with the served time
   * Precondition: the waiter must be free
   * the servedTime must be a real value greater than or equals to 0
   * Postcondition: the waiter must be serving someone
   */
  private void serveCustomer(Customer customer, double servedTime) {
    assert customer.beingServed ==  false;
    assert this.customerBeingServed == false;
    assert servedTime >= 0;
    this.servedCustomer = customer;
    this.customerBeingServed = true;

    // update the waiting time in the customer object
    Customer.served(customer, servedTime);

    updateWaitingTime(customer);

    // print a line that says the customer is served with time and id
    System.out.printf("%6.3f %d served\n", servedTime, customer.customerId);
    // I HAVE SERVED ONE MORE CUSTOMER! increments total number of served customer
    this.totalNumOfServedCustomers += 1;

    assert this.customerBeingServed == true;
    assert customer.beingServed == true;
  }

  private void makeCustomerWait(Customer customer) {
    assert customer.beingServed == false;
    assert this.customerBeingServed == true;
    this.waitingCustomer = customer;
    this.customerWaiting = true;

    // print a line that says the customer is waiting with time and id
    System.out.printf("%6.3f %d waits\n", customer.arrivalTime, customer.customerId);
  }

  private void makeCustomerLeave(Customer customer) {
    assert customer.beingServed == false;
    assert this.customerBeingServed == true;
    assert this.customerWaiting == true;


    System.out.printf("%6.3f %d leaves\n", customer.arrivalTime, customer.customerId);

    // I LOST ONE MORE CUSTOMER :( increments total number of lost customers
    this.totalNumOfLostCustomers += 1;


  }






  private void updateWaitingTime(Customer servedCustomer) {
    this.totalWaitingTime += servedCustomer.getWaitingTime();
  }



  @Override
    public double averageWaitingTime() {
      return totalWaitingTime / totalNumOfServedCustomers;
    }

  @Override
    public int numOfServedCustomers() {
      return totalNumOfServedCustomers;
    }

  @Override
    public int numOfLostCustomers() {
      return totalNumOfLostCustomers;
    }

  public double getCustomerDoneTime() {
    return this.currentTime + serviceTime;
  }



}
