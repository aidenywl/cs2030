package cs2030.simulator;
/**
 * BackEvent encapsulates a customer back from rest event. 
 Abstract Event is the parent.
 * 
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 LabTwoA
 */

class BackEvent extends Event {
  private final HumanServer restingServer;

  public BackEvent(double eventTime, HumanServer server) {
    super(eventTime);
    this.restingServer = server;
  }

  @Override
  public void simulate(Simulator sim) {
    sim.simulateBack(this.eventTime, this.restingServer);
  }
}
