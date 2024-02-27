package nsu.fit.ezaitseva;

import java.util.ArrayList;

public class Tools {
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

    public ArrayList<Integer> generateNums(int maxNum) {
        ArrayList<Integer> arrList = new ArrayList<>();

        for (int i = 1; i <= maxNum; i++) {
            if (!notPrime(i)) {
                arrList.add(i);
            }
        }

        return arrList;
    }
}