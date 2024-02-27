package nsu.fit.ezaitseva.finders;


import java.util.Collection;

import nsu.fit.ezaitseva.Tools;

public class SequentiallyNotPrimeFinder implements NotPrimeFinderInterface {
    @Override
    public boolean noPrime(Collection<Integer> nums) {
        Tools primeChecker = new Tools();
        for (Integer num : nums) {
            if (primeChecker.notPrime(num)) {
                return true;
            }
        }

        return false;
    }

}