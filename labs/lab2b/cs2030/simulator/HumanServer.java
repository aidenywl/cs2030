
package cs2030.simulator;

/**
 * A HumanServer object encapsulates how a human server and his resting capabilities.
 *
 * @author  Low Yew Woei
 * @version  CS2030 Semester 2 LabTwoB.
 */

class HumanServer extends Server {
  private double probabilityOfRest;
  /** indicates whether the server is resting or not. */
  private boolean resting;
  // implement rest stuff
  HumanServer(double probability) {
    super(); // is this required? no?
    this.probabilityOfRest = probability;
    this.resting = false;
  }

  /**
   * Checks if it is time for the server to rest. Used after the server is finished
   * serving a customer.
   * @return True if is time for the waiter to rest, false otherwise.
   */
  private boolean timeToRest() {
    double restVariable = Simulator.randomGen.genRandomRest();
    return restVariable < this.probabilityOfRest; 
  }

  /**
   * Checks if it is time to rest.
   * @param  currentTime [the current time]
   * @return             Null if it is not time to rest. a Back Event if it is time to rest.  
   */
  public Event checkTimeToRest(double currentTime) {
    if (!timeToRest()) {
      return null;
    }
    double restingTime = Simulator.randomGen.genRestPeriod();
    Event backEvent = new BackEvent(currentTime + restingTime, this);
    return backEvent;
  }

  public boolean isResting() {
    return resting;
  }

  public void rest() {
    this.resting = true;
  }

  public void backToWork() {
    this.resting = false;
  }


  @Override
  public String toString() {
    return "H" + super.toString();
  }
}
