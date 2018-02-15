class Customer {
  private static int Id; // keep track of number of customers created so far

  public final int customerId; // the ID of the instantiated customer
  public final double arrivalTime;
  public double waitTime;

  public Customer(double arrivalTime) {
    this.customerId = Id;
    Id++;
    this.arrivalTime = arrivalTime;
  }

  public void served(double servedTime) {
    this.waitTime = servedTime - arrivalTime;
  }
  
  @Override
  public String toString() {
    return "" +  customerId;
  }
}
