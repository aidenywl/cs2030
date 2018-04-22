import java.util.Optional;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.Iterator;

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
      // get the bus services asynchronously.
      CompletableFuture<Set<BusService>> allServicesHelp = CompletableFuture
              .supplyAsync(() -> stop.getBusServices())
              .handle((result, exception) -> { // handling HTTP exceptions.
                if (result != null) {
                  return result;
                } else {
                  System.err.println(exception);
                  return Collections.<BusService>emptySet();
                }
              });


      // Set<BusService> allServices = stop.getBusServices(); // <- TODO: call this asynchronously
      Map<BusService,Set<BusStop>> validServices = new HashMap<>();

      /** Stores all the asynchronous tasks later on. */
      ArrayList<CompletableFuture<Set<BusStop>>> tasks = new ArrayList<>();
      
      // retrieve the bus services that serve that bus stop.
      Set<BusService> allServices = allServicesHelp.join();



      /* Stream version. Which is better?
      allServices.stream()
           .forEach(bus ->
              {
                tasks.add(CompletableFuture.supplyAsync(() -> bus.findStopsWith(name))
                             .handle((result, exception) -> {
                                if (result != null) {
                                  return result;
                                } else {
                                  System.err.println(exception);
                                  return Collections.<BusStop>emptySet();
                                }
                             }));
              });
      */
      
      // asynchronous request for all the URL API queries.
      for (BusService service : allServices) {
        // Set<BusStop> stops = service.findStopsWith(name);  // <- TODO: call this asynchronously
        CompletableFuture<Set<BusStop>> futureStop = CompletableFuture
            .supplyAsync(() -> service.findStopsWith(name))
            .handle((result, exception) -> { // handling HTTP exceptions
              if (result != null) {
                return result;
              } else {
                System.err.println(exception);
                return Collections.<BusStop>emptySet();
              }
            });
            
        tasks.add(futureStop); // add the future to the queue.

      }
      // iterator to be used to aid the bus Services.
      Iterator<BusService> busServices = allServices.iterator();

      // retrieving the information from each asynchronous task and using them.
      for (CompletableFuture<Set<BusStop>> asyncStop : tasks) {
        BusService s = busServices.next();
        Set<BusStop> stops = asyncStop.join();
        if (!stops.isEmpty()) {
          validServices.put(s, stops);
        }
      }

      return Optional.of(new BusRoutes(stop, name, validServices));
    } catch (CompletionException e) {
      System.err.println("Unable to complete query: " + e);
      return Optional.empty();
    }
  }
}
