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

  /** Random number stream for probability of resting. */
  private Random rngRest;

  /** Random number stream for resting period. */
  private Random rngRestPeriod;

  /** Random number stream for waiting limit rate. */
  private Random rngWait;

  /** Random number stream for customer type. */
  private Random rngCustomerType;

  /** The customer arrival rate (lambda). */
  private final double customerArrivalRate;

  /** The customer service rate (mu). */
  private final double customerServiceRate;

  /** The server resting rate (rho). */
  private final double serverRestingRate;

  /**
   * Create a new RandomGenerator object.
   *
   * @param seed The random seed.  New seeds will be derived based on this.
   * @param lambda The arrival rate.
   * @param mu The service rate.
   * @param rho The resting rate.
   */
  RandomGenerator(int seed, double lambda, double mu, double rho) {
    this.rngArrival = new Random(seed);
    this.rngService = new Random(seed + 1);
    this.rngRest = new Random(seed + 2);
    this.rngRestPeriod = new Random(seed + 3);
    this.rngCustomerType = new Random(seed + 4);

    this.customerArrivalRate = lambda;
    this.customerServiceRate = mu;
    this.serverRestingRate = rho;
  }

  /**
   * Generate random inter-arrival time.  The inter-arrival time is modelled as
   * an exponential random variable, characterised by a single parameter --
   * arrival rate.
   *
   * @return inter-arrival time for next event.
   */
  double genInterArrivalTime() {
    return this.timeFunction(rngArrival, this.customerArrivalRate);
  }

  /**
   * Generate random service time.  The service time is modelled as an
   * exponential random variable, characterised by a single parameter - service
   * rate.
   *
   * @return service time for event.
   */
  double genServiceTime() {
    return this.timeFunction(rngService, this.customerServiceRate);
  }

  /**
   * Generate a random number between 0 and 1 to compare to a server's
   * probability of taking a rest. The random number generated is used to
   * determine whether the server takes rest after serving a customer.
   *
   * @return a random number between 0 and 1.
   */
  double genRandomRest() {
    return rngRest.nextDouble();
  }

  /**
   * Generate random resting period for server. The resting period is modelled
   * as an exponential random variable, characterised by a single parameter -
   * resting rate.
   *
   * @return resting period for server.
   */
  double genRestPeriod() {
    return this.timeFunction(rngRestPeriod, this.serverRestingRate);
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
  /**
   * Generate a random number between 0 and 1 to determine the type of
   * customer.
   * @return a random number uniformed drawn from 0 to 1.
   */
  double genCustomerType() {
    return rngCustomerType.nextDouble();
  }
}
