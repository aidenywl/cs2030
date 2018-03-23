import java.lang.Math;
import java.util.Random;

/**
 * Encapsulates multiple streams of pseudo-random numbers
 * specifically for use with the shop simulator.  the
 * RandomGenerator is initialized with a seed, the arrival
 * rate (lambda) and the service rate (mu).   There are
 * two streams of random numbers here, one for inter
 * arrival time, and the other for service time.
 *
 * @author Low Yew Woei
 * @version CS2030 AY17/18 Sem 2 Lab 2b
 */
public class RandomGenerator {
  /** Random number stream for arrival rate. */
  private Random rngArrival;

  /** Random number stream for service rate. */
  private Random rngService;

  /** The customer arrival rate (lambda). */
  private final double customerArrivalRate;

  /** The customer service rate (mu). */
  private final double customerServiceRate;

  /**
   * Create a new RandomGenerator object.
   *
   * @param seed The random seed.  New seeds will be derived based on this.
   * @param lambda The arrival rate.
   * @param mu The service rate.
   */
  RandomGenerator(int seed, double lambda, double mu) {
    this.rngArrival = new Random(seed);
    this.rngService = new Random(seed + 1);
    this.customerArrivalRate = lambda;
    this.customerServiceRate = mu;
  }

  /**
   * Generate random inter-arrival time.  The inter-arrival time is modelled as
   * an exponential random variable, characterised by a single parameter --
   * arrival rate.
   *
   * @return inter-arrival time for next event.
   */
  double genInterArrivalTime() {
    return timeFunction(rngArrival, this.customerArrivalRate);
  }

  /**
   * Generate random service time.  The service time is modelled as an
   * exponential random variable, characterised by a single parameter - service
   * rate.
   *
   * @return service time for event.
   */
  double genServiceTime() {
    return timeFunction(rngService, this.customerServiceRate);
  }

  /**
   * Generates the required time for server or arrival times.
   * @param seed the random seed used for the calculation.
   * @param rate the rate for calculation.
   * @return the time for the service.
   */
  private double timeFunction(Random seed, double rate) {
    return -Math.log(seed.nextDouble()) / rate;
  }
  
}
