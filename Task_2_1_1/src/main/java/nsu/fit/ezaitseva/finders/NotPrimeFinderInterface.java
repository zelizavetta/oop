package nsu.fit.ezaitseva.finders;

import nsu.fit.ezaitseva.PrimeChecker;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public interface NotPrimeFinderInterface {
    boolean noPrime(Collection<Integer> nums) throws InterruptedException;
}