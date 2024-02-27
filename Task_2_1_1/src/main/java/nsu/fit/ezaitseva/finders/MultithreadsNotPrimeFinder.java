package nsu.fit.ezaitseva.finders;


import nsu.fit.ezaitseva.Tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class MultithreadsNotPrimeFinder implements NotPrimeFinderInterface {
    public AtomicInteger atomicInteger = new AtomicInteger(0);
    public int quantThreads;


    public MultithreadsNotPrimeFinder(int quantThreads) {
        this.quantThreads = quantThreads;
    }

    @Override
    public boolean noPrime(Collection<Integer> nums) throws InterruptedException {
        CurThread curThread = new CurThread(nums, new Tools(), atomicInteger);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < quantThreads; i++) {
            Thread thread = new Thread(curThread);
            thread.start();
            threads.add(thread);
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        }
        catch (InterruptedException e) {
            throw new InterruptedException();
        }
        return curThread.fl;
    }

    private static class CurThread implements Runnable {
        private final Integer[] numsArr;
        private final Tools primeChecker;
        private final AtomicInteger atomicInteger;
        public boolean fl = false;

        private CurThread(Collection<Integer> nums,
                          Tools primeNumberChecker,
                          AtomicInteger atomicInteger) {
            this.atomicInteger = atomicInteger;
            this.primeChecker = primeNumberChecker;
            numsArr = nums.toArray(new Integer[0]);
        }

        private Integer getNext() {
            if (fl) {
                return null;
            }
            int i = atomicInteger.getAndIncrement();
            if (i < numsArr.length) {
                return numsArr[i];
            }
            return null;
        }

        @Override
        public void run() {
            Integer number;
            while ((number = getNext()) != null) {
                if (primeChecker.notPrime(number)) {
                    fl = true;
                }
            }
        }


    }
}
