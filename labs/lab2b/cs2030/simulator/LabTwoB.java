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
 * @version CS2030 AY17/18 Sem 2 LabTwoB
 */
class LabTwoB {

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
    // base seed to the RandomGenerator object
    int randomSeed = s.nextInt();
    // getting the number of human servers to initialise the shop
    int numHumanServers = s.nextInt();
    // getting the number of self checkout counters to initialise the shop
    int numSelfServers = s.nextInt();
    // getting the maximum queue length for servers.
    int maxQueueLength = s.nextInt();
    // getting the number of customers to simulate
    int numCustomers = s.nextInt();
    // obtaining the arrival rate of the customers
    double arrRate = s.nextDouble();
    // obtaining the service rate
    double serRate = s.nextDouble();
    // obtaining the resting rate. Used to generate rest times.
    double restRate = s.nextDouble();
    // probability that a waiter rests.
    double restProbability = s.nextDouble();
    // probability of a greedy customer.
    double greedyProbability = s.nextDouble();
    // service time limit for self checkout counters.
    double serviceTimeLimit = s.nextDouble();

    // creating a simulation
    Simulator sim = new Simulator(numCustomers);

    // setting the simulation's Random Generator parameters
    sim.setRandom(randomSeed, arrRate, serRate, restRate);

    // setting the simulation's shop number of servers
    sim.setShop(numHumanServers, numSelfServers, maxQueueLength, restProbability, serviceTimeLimit);

    // set customer variables
    sim.setCustomer(greedyProbability);
    // Start the simulation
    sim.run();

    s.close();

    // Print statistics
    sim.printStats();
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
