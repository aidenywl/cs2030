import cs2030.simulator.Simulator;
import cs2030.simulator.SimState;
import cs2030.simulator.Event;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import java.util.stream.Stream;

/**
 * The LabOFourA class is the entry point into Lab 4a.
 *
 * @author atharvjoshi
 * @author weitsang
 * @version CS2030 AY17/18 Sem 2 Lab 4a
 */
class LabFourA {
  /**
   * The main method for Lab 4a. Reads data from file and
   * then run a simulation based on the input data.
   *
   * @param args two arguments, first an integer specifying number of servers
   *     in the shop. Second a file containing a sequence of double values, each
   *     being the arrival time of a customer (in any order).
   */
  public static void main(String[] args) {
    Scanner scanner = createScanner(args);
    if (scanner == null) {
      return;
    }

    // Read the first line of input as number of servers in the shop
    int numOfServers = scanner.nextInt();
    Simulator sim = new Simulator(numOfServers);

    // Stream<String> eventParamStream = scanner.tokens();

    // Binary Operator for the accumulator
    while (scanner.hasNextDouble()) {
      double arrivalTime = scanner.nextDouble();

      // arrivalTime is captured by the lambda expression.
      sim.state = sim.state.addEvent(new Event(arrivalTime,
                        sims -> sims.simulateArrival(arrivalTime)));
    }

    scanner.close();

    // After data input is handled, run the simulator


    System.out.println(sim.run());
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
