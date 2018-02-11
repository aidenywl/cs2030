class Customer {
  public boolean beingServed;
  public final int customerId;    
  public final double arrivalTime;  
  private double waitingTime;


  /**
   * Encapsulates the customer data type.
   * arrivalTime is provided by the input
   * customer ID is determined by the simulator
   */
  public Customer(int customerId, double arrivalTime) {
    this.beingServed = false;
    this.waitingTime = 0;
    this.arrivalTime = arrivalTime;
    this.customerId = customerId;
  }

  /*
   * static method that takes in a served customer and his served time
   * and calculates the waiting time of the customer
   * Precondition: this customer is not being served
   * the servedTime is greater than or equals to 0.
   * Postcondition: this customer is being served
   */
  public static void served(Customer customer, double servedTime) {
    assert customer.beingServed == false;
    assert servedTime >= 0;
    customer.waitingTime = servedTime - customer.arrivalTime;
    customer.beingServed = true;
    assert customer.beingServed == true;
  }
  /**
   * Gets the waiting time of the customer
   */
  public double getWaitingTime() {
    return this.waitingTime;
  }

  /**
   * Prints the Customer ID and the Waiting Time
   */
  @Override   
    public String toString() {     
      return "Customer ID: " + this.customerId + "\n" + 
        "Waiting Time: " + this.waitingTime;
    }
}

