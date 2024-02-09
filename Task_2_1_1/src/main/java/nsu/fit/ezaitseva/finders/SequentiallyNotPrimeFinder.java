package nsu.fit.ezaitseva.finders;


import nsu.fit.ezaitseva.PrimeChecker;
import nsu.fit.ezaitseva.finders.NotPrimeFinderInterface;

import java.util.Collection;

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