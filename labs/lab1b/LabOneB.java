import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

class LabOneB {
  
  /**
   * Event encapsulations information about the time an event is supposed to occur, and its type.
   */

  /**
   * The main method for LabOneA.
   * Reads arrival time from either stdin or a file and insert the arrival event into an array
   * in the simulator.  Then, run the simulator.
   */
  public static void main(String[] args) {;
    Scanner s = createScanner(args);
    if (s == null) {
      return;
    }

    // getting the number of servers to initialise the shop
    int numberOfServers = s.nextInt();

    Shop shop = new Shop(numberOfServers, 100);

    // The input file consists of a sequence of arrival timestamp
    // (not necessary in order).
    while (s.hasNextDouble()) {
      // add events
      double arrivalTime = s.nextDouble();
      if (shop.hasSpaceForEvent()) {
        shop.addEvent(new CustArrive(arrivalTime));
      } else {
        break; // stop if the Shop is full. i.e. events has reached 100
      }
    }

    // start the shop
    shop.runShop();

    s.close();

    // Then run the simulator

    // Print stats as three numbers:
    // <avg waiting time> <number of served customer> <number of lost customer>
    System.out.println(shop);
  }

  /**
   * Create and return a scanner.  If a command line arguement is given,
   * treat the argument as a file, and open a scanner on the file.  Else,
   * open a scanner that reads from standard input.
   *
   * @return a scanner or `null` if a filename is given but cannot be open.
   */
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
