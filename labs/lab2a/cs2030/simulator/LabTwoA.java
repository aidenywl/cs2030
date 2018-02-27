package cs2030.simulator;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

/**
 * Lab is the main file that processes all inputs from a file or the user.
 * it also contains the Scanner which has been programmed to receive inputs from
 * a file as well
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 LabTwoA
 */
class LabTwoA {
  // should i put the variables here???
  protected static int numEvents = 0;

  /**
   * The main method for LabOneA.
   * Reads arrival time from either stdin or a file and insert the arrival event into an array
   * in the simulator.  Then, run the simulator.
   * PRECONDITION for main: the inputs are in this order
   * 1) int random seed 2) int number of servers 3) int number of customers
   * 4) double arrival rate 5) double service rate
   */
  public static void main(String[] args) {
    Scanner s = createScanner(args);
    if (s == null) {
      return;
    }
    // obtaining the arrival rate of the servers
    int randomSeed = s.nextInt();
    // getting the number of servers to initialise the shop
    int numServers = s.nextInt();
    // getting the number of events to simulate
    LabTwoA.numEvents = s.nextInt(); 
    // obtaining the arrival rate of the customers
    double arrRate = s.nextDouble();
    // obtaining the service rate
    double serRate = s.nextDouble();

    // creating the random generator for our simulation
    RandomGenerator random = new RandomGenerator(randomSeed, arrRate, serRate);

    // initialising the shop with the maximum number of allowed events
    Shop shop = new Shop(numServers, 100, random);

    // start the shop
    shop.runShop();

    s.close();

    // Then run the simulator

    // Print stats as three numbers:
    // <avg waiting time> <number of served customer> <number of lost customer>
    System.out.println(shop);
  }

  /** helper method to determine if all events have been generated */

  protected static boolean hasNextEvent() {
    return numEvents > 0;
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
