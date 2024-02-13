package nsu.fit.ezaitseva.finders;

import java.util.Collection;

import nsu.fit.ezaitseva.PrimeChecker;


public class ParallelSreamNotPrimeFinder implements NotPrimeFinderInterface {
    @Override
    public boolean noPrime(Collection<Integer> nums) {
        PrimeChecker primeChecker = new PrimeChecker();
        return nums.parallelStream().anyMatch(primeChecker::notPrime);
    }
}