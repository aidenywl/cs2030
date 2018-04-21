import java.util.Optional;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionException;

/**
 * A BusSg class encapsulate the data related to the bus services and
 * bus stops in Singapore, and supports queries to the data.
 */
class BusSg {

  /**
   * Given a bus stop and a name, find the bus services that serve between
   * the given stop and any bus stop with matching mame.
   * @param  stop The bus stop
   * @param  name The (partial) name of other bus stops.
   * @return The (optional) bus routes between the stops.
   */
  public static Optional<BusRoutes> findBusServicesBetween(BusStop stop, String name) {
    if (stop == null || name == null) {
      return Optional.empty();
    }
    try {
      Set<BusService> allServices = stop.getBusServices(); // <- TODO: call this asynchronously
      Map<BusService,Set<BusStop>> validServices = new HashMap<>();

      for (BusService service : allServices) {
        Set<BusStop> stops = service.findStopsWith(name);  // <- TODO: call this asynchronously
        if (!stops.isEmpty()) {
          validServices.put(service, stops);
        }
      }
      return Optional.of(new BusRoutes(stop, name, validServices));
    } catch (CompletionException e) {
      System.err.println("Unable to complete query: " + e);
      return Optional.empty();
    }
  }
}
