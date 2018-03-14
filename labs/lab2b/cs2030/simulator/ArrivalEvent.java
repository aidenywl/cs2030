package cs2030.simulator;

/**
 * Encapsulates information and methods pertaining to an arrival event.
 * This class does not do much for now, except letting the simulator call
 * {@code simulateArrival} through polymorphism.
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 LabTwoB
 */
class ArrivalEvent extends Event {
  
  /**
   * Constructs a Customer Arrive Event.
   * @param eventTime the arrival time of the customer
   */
  public ArrivalEvent(double eventTime) {
    super(eventTime);
  }

  /**
   * Asks the simulator to simulate an arrival event.
   * @param sim The Simulator
   */
  public void simulate(Simulator sim) {
    sim.simulateArrival(this.eventTime);
  }
  
  @Override
  public String toString() {
    return "CUSTOMER_ARRIVE";
  }
}
