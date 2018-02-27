package cs2030.simulator;

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
 * @author atharvjoshi
 * @version CS2030 AY17/18 Sem 2 Lab 2a
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
    return -Math.log(rngArrival.nextDouble()) / this.customerArrivalRate;
  }

  /**
   * Generate random service time.  The service time is modelled as an
   * exponential random variable, characterised by a single parameter - service
   * rate.
   *
   * @return service time for event.
   */
  double genServiceTime() {
    return -Math.log(rngService.nextDouble()) / this.customerServiceRate;
  }
}
