package nsu.fit.ezaitseva;


public class PrimeChecker {
    public boolean notPrime(int num) {
        if (num < 0) {
            throw new IllegalArgumentException();
        }
        if (num < 2) {
            return true;
        }
        for (int i = 2; (long) i * i <= (long) num; i++) {
            if (num % i == 0) {
                return true;
            }
        }
        return false;
    }

}