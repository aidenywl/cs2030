package cs2030.simulator;


/**
 * A SelfServer object encapsulates how a self checkout counter works, including the
 * customer service time limits imposed by the self checkout counter.
 * @author  Low Yew Woei
 * @version  CS2030 Semester 2 LabTwoB.
 */

class SelfServer extends Server {
  /** The service time limit for a customer to be able to use the self checkout counter. */
  private static double serviceTimeLimit;

  /**
   * Method to check if a customer's service time is
   * @param  time [description]
   * @return      [description]
   */
  public static boolean withinServiceTimeLimit(Customer customer) {
    return customer.getServiceTime() < SelfServer.serviceTimeLimit;
  }

  public static void setServiceTimeLimit(double time) {
    SelfServer.serviceTimeLimit = time;
  }

  @Override
  public String toString() {
    return "C" + super.toString();
  }

}
