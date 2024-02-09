package nsu.fit.ezaitseva;


import java.util.ArrayList;
import java.util.List;

public class PrimeCheckerPreprocessing {
    final List<Integer> primeDividers = new ArrayList<>();
    final int maxN;
    PrimeCheckerPreprocessing(int maxN) {
        this.maxN = maxN;
        int[] arr = new int[(int) (Math.sqrt(maxN) + 2)];
        arr[0] = 1;
        arr[1] = 1;
        for (int i = 2; i < arr.length; i++) {
            if (arr[i] == 0) {
                primeDividers.add(i);
                if (i * i > arr.length) continue;
                for (int j = i + i; j < arr.length; j += i) {
                    arr[j] = 1;
                }
            }
        }
    }

    public boolean notPrimePreprocessing(int num) {
        if (num < 0 || num > maxN) {
            throw new IllegalArgumentException();
        }
        if (num < 2) {
            return true;
        }
        for (Integer pDiv : primeDividers) {
            if (pDiv >= num) {
                break;
            }
            if (num % pDiv == 0) {
                return true;
            }
        }
        return false;
    }

}