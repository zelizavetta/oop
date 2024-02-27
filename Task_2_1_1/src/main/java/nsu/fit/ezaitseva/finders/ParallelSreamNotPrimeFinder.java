package nsu.fit.ezaitseva.finders;

import java.util.Collection;

import nsu.fit.ezaitseva.Tools;


public class ParallelSreamNotPrimeFinder implements NotPrimeFinderInterface {
    @Override
    public boolean noPrime(Collection<Integer> nums) {
        Tools primeChecker = new Tools();
        return nums.parallelStream().anyMatch(primeChecker::notPrime);
    }
}