package ru.nsu.fit.ezaitseva;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubStringSearch {
    public static String root = "C:/Users/Elisa/oop/Task_1_3_1/";

    public static Integer[] search(String p, String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        List<Integer> result = new ArrayList<>();
        int[] prefixFunc = new int[p.length()];
        Arrays.fill(prefixFunc, 0);
        int prefixFuncPrev = 0;
        int quantSymb = 0;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            String perfixLine = p + '#' + line;
            for (int i = 1; i < perfixLine.length(); i++) {
                int k = prefixFuncPrev;
                while (k > 0 && perfixLine.charAt(k) != perfixLine.charAt(i)) {
                    k = prefixFunc[k - 1];
                }
                if (perfixLine.charAt(k) == perfixLine.charAt(i)) {
                    k++;
                }
                if (i < p.length()) {
                    prefixFunc[i] = k;
                }
                prefixFuncPrev = k;
                if (k == p.length()) {
                    result.add(i + quantSymb - 2 * p.length());
                }
            }
            quantSymb += line.length();
        }
        Integer[] arr = new Integer[0];
        arr = result.toArray(arr);
        return arr;
    }


    public static void main(String[] args) throws IOException {
        String searchingStr = "a";
        Integer[] result = search(searchingStr, root + "src/main/java/ru/nsu/fit/ezaitseva/input.txt");
        System.out.println(Arrays.toString(result));
    }
}