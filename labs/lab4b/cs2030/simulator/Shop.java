package cs2030.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

/**
 * A shop object maintains the list of servers and support queries
 * for server.
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY17/18 Sem 2 Lab 4b
 */
class Shop {
  /** List of servers. */
  private final List<Server> servers;

  /**
   * Create a new shop with a given number of servers.
   * @param numOfServers The number of servers.
   */
  Shop(int numOfServers) {
    // creating the first server (as k >= 1)
    Server firstServer = new Server(0);
    // generating a server list with a stream.
    Stream<Server> serverStream = Stream.iterate(firstServer, 
                      initialServer -> new Server(initialServer.hashCode() + 1));
    Stream<Server> finalServerStream = serverStream.limit(numOfServers);

    this.servers = finalServerStream.collect(Collectors.toList());
  }

  private Shop(Server server, Shop oldShop) {
    this.servers = new ArrayList<>();
    this.servers.addAll(oldShop.servers);
    this.servers.set(server.hashCode(), server);

  }

  public Shop updateShop(Server updatedServer) {
    return new Shop(updatedServer, this);
  }

  /**
   * Finds a server based on the predicate given. It does not mutate the shop's server list,
   * and is free of side-effects.
   * @param  predicate Finds a server in the stream based on the predicate.
   * @return           the server according to the predicate.
   */
  private Optional<Server> findServer(Predicate<Server> predicate) {
    return this.servers.stream()
        .filter(predicate)
        .findFirst();
  }

  /**
   * Return the first idle server in the list.
   *
   * @return An idle server, or {@code null} if every server is busy.
   */
  public Optional<Server> findIdleServer() {
    Predicate<Server> idleTest = s -> s.isIdle();
    return findServer(idleTest);
  }

  /**
   * Return the first server with no waiting customer.
   * @return A server with no waiting customer, or {@code null} is every
   *     server already has a waiting customer.
   */
  public Optional<Server> findServerWithNoWaitingCustomer() {
    Predicate<Server> availableTest = s -> s.hasSpaceToWait();
    return findServer(availableTest);
  }

  /**
   * Return a string representation of this shop.
   * @return A string reprensetation of this shop.
   */
  public String toString() {
    return servers.toString();
  }
}
