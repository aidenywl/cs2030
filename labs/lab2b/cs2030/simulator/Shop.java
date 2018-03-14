package cs2030.simulator;

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
  /** Number of servers in the instantiated shop. */
  private final int numServers;

  

  public Shop(int numServers) {
    this.numServers = numServers;

  }

  /** 
   * Method to initialise the list of servers; used in the constructor 
   *
   * @param serverRestProbability      rest probability for humans
   */
  public void createHumanServers(double serverRestProbability) {
    for (int i = 0; i < this.numServers; i++) {
      servers.add(new HumanServer(serverRestProbability));
    }
  }

  /**
   * Method to initialise the list of self service servers.
   * 
   * @param selfServiceTimeLimit service time limit for self checkout counters.
   */
  public void createSelfServers(double selfServiceTimeLimit) {
    SelfServer.setServiceTimeLimit(selfServiceTimeLimit);
    for (int i = 0; i < this.numServers; i++) {
      servers.add(new SelfServer());
    }
  }


  /**
   * Finds and returns the first idle server.
   * if none are idle, return an available server.
   * @return availableServer the server that is idle or available.
   */
  /*
  public Server findIdleOrAvailableServer(Customer customer) {
    Server idleServer = this.findIdleServer(customer);
    Server availableServer = this.findAvailableServer(customer);

    if (idleServer != null) {
      return idleServer;
    } else {
      return availableServer;
    }
  }
  */
  /**
   * Return the first server who is free; not serving any customers.
   *
   * @param customer The customer who is seeking a server.
   * @return An idle server. or {@code null} is every server is serving a customer.
   */
    public Server findIdleServer(Customer customer) {
    Server idleServer = null;

    for (int i = 0; i < servers.size(); i++) {
      Server currentServer = servers.get(i);

      // finding a server to serve the customer
      if (!currentServer.customerBeingServed() && validServerForCustomer(currentServer, customer)) {
        idleServer = currentServer;
        break;
      }
    }
    return idleServer;
  }

  /**
   * Return the first server with no waiting customer.
   *
   * @param customer The customer who is seeking a server.
   * @return A server with no waiting customer, or {@code null} is every
   *     server already has a waiting customer.
   */
  public Server findAvailableServer(Customer customer) {
    Server availableServer = null;

    for (int i = 0; i < servers.size(); i++) {
      Server currentServer = servers.get(i);
      // finding a server to wait
      if (currentServer.hasSpaceToWait() && validServerForCustomer(currentServer, customer)) {
        if (!customer.isGreedy()) {
          availableServer = currentServer;
          break; 
        } else {
          // customer is greedy
          if (availableServer == null) {
            availableServer = currentServer;
            continue;
          }
          if(availableServer.shorterQueueThan(currentServer)) {
            continue;
          } else {
            // pick the server with the shorter queue.
            availableServer = currentServer;
          }
        }

      }
    }
    return availableServer;
  }

  /**
   * Checks if the server is a valid server for the customer.
   * This is especially important for Self Checkout servers.
   * 
   * @param  server   the server to check for. Either human or robot.
   * @param  customer The customer we are matching with.
   * @return          true if the server works for the customer; false otherwise.
   */
  private boolean validServerForCustomer(Server server, Customer customer) {
    if (server instanceof HumanServer) {
      return !(((HumanServer) server).isResting());
    }

    return true;
  }

}
