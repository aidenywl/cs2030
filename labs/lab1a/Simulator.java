import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

class Simulator implements EventTypes {
  private final int MAX_NUMBER_OF_EVENTS; // maximum number of events in a shop
  private final double SERVICE_TIME;
  private int totalNumOfServers;

  public Simulator() {
    MAX_NUMBER_OF_EVENTS = 100; // for a shop
    this.totalNumOfServers = 0;
    this.SERVICE_TIME = 1.0;
  }


  public static void main(String[] args) {

    // influx Scanner directly reads from a File file
    Scanner incomingCustomers = createScanner(args); 
    // taking in inputs
    if (incomingCustomers == null) {
      return;
    }

    Simulator sim = new Simulator();

    sim.runSimulator(incomingCustomers);

    /**
     * If there are customers incoming, intialise the Simulator with a 
     * Server and a Shop
     */

  }

  private void runSimulator(Scanner incomingCustomers) {
    Server firstServer = new Server(this.totalNumOfServers, SERVICE_TIME);
    this.totalNumOfServers++;
    Shop shop = new Shop(firstServer, MAX_NUMBER_OF_EVENTS);

    // add all the incoming customers to the shop

    while (incomingCustomers.hasNextDouble()) {
      if (shop.hasSpaceForEvent()) {
        Event newEvent = new Event(incomingCustomers.nextDouble(), 
                                EventTypes.CUSTOMER_ARRIVE);
        shop.addEvent(newEvent);
      } else {
        System.out.println("Warning: too many events. SKipping the rest.");
      }
    }

    incomingCustomers.close();
    shop.startServing();

    System.out.printf("%.3f %d %d\n", shop.getTotalWaitingTime() /
                                  shop.getTotalNumOfServedCustomers(),
        shop.getTotalNumOfServedCustomers(), shop.getTotalNumOfLostCustomers());

  }



  private static Scanner createScanner(String[] args) {
    Scanner s = null;
    try {
      // Read from stdin if no filename is given, otherwise
      // read from the given file.
      if (args.length == 0) {
        s = new Scanner(System.in);
      } else {
        FileReader f = new FileReader(args[0]);
        s = new Scanner(f);
      }
    } catch (FileNotFoundException ex) {
      System.err.println("Unable to open file " + args[0] + " " + ex + "\n");
    } finally {
      return s;
    }
  }

}
