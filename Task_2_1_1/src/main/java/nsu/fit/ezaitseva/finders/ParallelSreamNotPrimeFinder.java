package nsu.fit.ezaitseva.finders;

import nsu.fit.ezaitseva.PrimeChecker;

import java.util.Collection;

public class ParallelSreamNotPrimeFinder implements NotPrimeFinderInterface{
    @Override
    public boolean noPrime(Collection<Integer> nums) {
        PrimeChecker primeChecker = new PrimeChecker();
        return nums.parallelStream().anyMatch(primeChecker::notPrime);
    }
}