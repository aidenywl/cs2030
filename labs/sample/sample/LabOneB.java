import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * The LabOneB class is the entry point into Lab 1b.
 * See <a href="https://nus-cs2030.github.io/1718-s2/lab1b/index.html">
 *     Lab 1b</a>
 *
 * @author Ooi Wei Tsang
 * @author Evan Tay
 * @version CS2030 AY17/18 Sem 2 Lab 1b
 */
class LabOneB {
  /**
   * The main method for Lab 1b. Reads data from either stdin or a file and
   * then run a simulation based on the input data.
   *
   * @param args The first line of input is an integer specifying the number
   *     of servers in the shop. Subsequent lines are doubles with each being the
   *     arrival time of a customer (unordered).
   */
  public static void main(String[] args) {
    Scanner scanner = createScanner(args);
    if (scanner == null) {
      return;
    }

    // Read the first line of input as number of servers in the shop
    int numOfServers = scanner.nextInt();
    Simulator sim = new Simulator(numOfServers);

    // Read subsequent lines as doubles with each representing a customer's
    // arrival time.
    while (scanner.hasNextDouble()) {
      double arrivalTime = scanner.nextDouble();
      boolean ok = sim.createArrivalEvent(arrivalTime);
      if (!ok) {
        break;
      }
    }

    scanner.close();

    // After data input is handled, run the simulator
    sim.run();

    // Print the statistics:
    // <avg waiting time> <number of served customer> <number of lost customer>
    System.out.println(Simulator.stats);
  }

  /**
   * Create and return a scanner. If a command line argument is given,
   * treat the argument as a file and open a scanner on the file. Else,
   * create a scanner that reads from standard input.
   *
   * @param args The arguments provided for simulation.
   * @return A scanner or {@code null} if a filename is provided but the file
   *     cannot be open.
   */
  private static Scanner createScanner(String[] args) {
    Scanner scanner = null;

    try {
      // Read from stdin if no filename is given, otherwise read from the
      // given file.
      if (args.length == 0) {
        // If there is no argument, read from standard input.
        scanner = new Scanner(System.in);
      } else {
        // Else read from file
        FileReader fileReader = new FileReader(args[0]);
        scanner = new Scanner(fileReader);
      }
    } catch (FileNotFoundException exception) {
      System.err.println("Unable to open file " + args[0] + " "
          + exception);
    }
    return scanner;
  }
}
