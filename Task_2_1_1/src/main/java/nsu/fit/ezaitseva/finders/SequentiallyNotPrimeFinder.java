package nsu.fit.ezaitseva.finders;


import java.util.Collection;

import nsu.fit.ezaitseva.PrimeChecker;

public class SequentiallyNotPrimeFinder implements NotPrimeFinderInterface {
    @Override
    public boolean noPrime(Collection<Integer> nums) {
        PrimeChecker primeChecker = new PrimeChecker();
        for (Integer num : nums) {
            if (primeChecker.notPrime(num)) {
                return true;
            }
        }
        return false;
    }

}