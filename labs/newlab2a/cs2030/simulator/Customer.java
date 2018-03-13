/**
 * Encapsulates a customer for the shop.
 * Keeps track of the customer ID and the customer done time.
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 LabTwoA
 */

class Customer {
  /** keeps track of the number of customers created so far. */
  private static int Id;
  /** the id of the instantiated customer. */
  protected final int customerId;
  /** the arrival time of the customer. */
  protected final double arrivalTime;
  /** the amount of time required to serve the customer. */
  protected final double serviceTime;
  /** the amount of time the customer spent waiting. */
  protected double waitTime;

  /** 
   * Constructs a customer object.
   * @param arrivalTime the arrival time of the customer
   */
  public Customer(double arrivalTime) {
    this.customerId = Id;
    Id++;
    this.arrivalTime = arrivalTime;
    this.serviceTime = Shop.randomTime.genServiceTime();
  }

  /**
   * method used by the server when the customer is served.
   * when called, this method will update the customer done time.
   * @param servedTime the time the customer is served
   */
  
  protected void served(double servedTime) {
    this.waitTime = servedTime - arrivalTime;
  }
 
  @Override
  public String toString() {
    return "" +  customerId;
  }
}
