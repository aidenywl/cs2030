import cs2030.simulator.Simulator;
import cs2030.simulator.SimState;
import cs2030.simulator.Event;
import cs2030.util.PriorityQueue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import java.util.stream.Stream;
import java.util.Optional;

/**
 * The LabOFourA class is the entry point into Lab 4a.
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 Lab 4a
 */
class LabFourB {
  /**
   * The main method for Lab 4a. Reads data from file and
   * then run a simulation based on the input data.
   *
   * @param args two arguments, first an integer specifying number of servers
   *     in the shop. Second a file containing a sequence of double values, each
   *     being the arrival time of a customer (in any order).
   */
  public static void main(String[] args) {

    Optional<Scanner> scanner = createScanner(args);
    scanner.ifPresent(
      reader -> {

    // Read the first line of input as number of servers in the shop
    int numOfServers = reader.nextInt();
    Simulator sim = new Simulator(numOfServers);

    Stream<Double> eventParamStream = reader.tokens().map(Double::parseDouble);
    Function<Double, Event> arrivalEventCreator = time -> 
                              new Event(time, sims -> 
                                    sims.simulateArrival(time));
    Stream<Event> eventStream = eventParamStream.map(arrivalEventCreator);


    // what was that about streams only being able to be used once????
    

    // SETTING UP REDUCE PARAMETERS.
    // identity priorityQueue
    // This part can't infer on it's own? It needs me to state <Event>. ???
    PriorityQueue<Event> identity = new PriorityQueue<Event>();
    // accumulator.
    BiFunction<PriorityQueue<Event>, Event, PriorityQueue<Event>> eventAccumulator 
                  = (pq, event) -> pq.add(event);
    // combiner
    BinaryOperator<PriorityQueue<Event>> combiner = (pq1, pq2) -> pq1.addAll(pq2);
    PriorityQueue<Event> arrivalPQ 
                  = eventStream.reduce(identity, eventAccumulator, combiner);

    sim.state = sim.state.addEvents(arrivalPQ);
    /*
    // Binary Operator for the accumulator
    while (scanner.hasNextDouble()) {
      double arrivalTime = scanner.nextDouble();

      // arrivalTime is captured by the lambda expression.
      sim.state = sim.state.addEvent(new Event(arrivalTime,
                        sims -> sims.simulateArrival(arrivalTime)));
    }
    */
    reader.close();

    // After data input is handled, run the simulator


    System.out.println(sim.run());
    });
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
  private static Optional<Scanner> createScanner(String[] args) {
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
    return Optional.of(scanner);
  }
}
