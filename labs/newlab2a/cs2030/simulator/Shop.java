import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A Shop object maintains the time, events for that shop, and the list of servers
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Semester 2
 */

class Shop {
  /** Array of Servers */
  ArrayList<Server> servers = new ArrayList<>();
  /** Number of servers in the instantiated shop */
  private final int numOfServers;
  /** maximum number of events supported by the shop */
  private final int MAX_NUMBER_OF_EVENTS;
  /** the random generator used for server and customer times */
  protected static RandomGenerator randomTime;
  

  public Shop(int numberOfServers) {
    this.numOfServers = numberOfServers;
    this.MAX_NUMBER_OF_EVENTS = MAX_NUMBER_OF_EVENTS;
    this.createServers();

  }

  /** Method to initialise the list of servers; used in the constructor 
   * PRECONDITION: the {@code RandomGenerator randomTime} must be initialised
   */
  private void createServers() {
    for (int i = 0; i < this.numOfServers; i++) {
      servers.add(new Server());
    }
  }


  /**
   * Finds and returns the first idle server.
   * if none are idle, return an available server.
   * @return availableServer the server that is idle or available.
   */
  private Server findIdleOrAvailableServer() {
    Server idleServer = this.findIdleServer();
    Server availableServer = this.findAvailableServer();

    if (idleServer != null) {
      return idleServer;
    } else {
      return availableServer;
    }
  }

  private Server findIdleServer() {
    Server idleServer = null;

    for (int i = 0; i < servers.size(); i++) {
      Server currentServer = servers.get(i);

      // finding a server to serve the customer
      if (currentServer.idle) {
        idleServer = currentServer;
        break;
      }
    }
    return idleServer;
  }

  private Server findAvailableServer() {
    Server availableServer = null;

    for (int i = 0; i < servers.size(); i++) {
      Server currentServer = servers.get(i);

      // finding a server to wait
      if (currentServer.serverAvailable) {
        availableServer = currentServer;
        break;
      }
    }
    return availableServer;
  }

}
