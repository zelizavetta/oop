package ru.nsu.fit.ezaitseva;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubStringSearch {
    /**
     * root - fixed part of the path
     */
    public static String root = "C:/Users/Elisa/oop/Task_1_3_1/";

    /**
     * function search is used for searching string in file using prefix-function.
     * it calculates prefix-function from prefix-line (p#line).
     * @param p - string that we are searching for
     * @param filename - name of the input file
     * @return array of Integers - array of occurrence indexes of string p
     * @throws IOException when file not found or some other reasons cannot be opened for reading
     */
    public static Integer[] search(String p, String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (p.equals("") || file.length() == 0) {
            Integer[] result = new Integer[] {};
            return result;
        }
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        List<Integer> result = new ArrayList<>();
        int quantSymb = 0;
        while ((line = reader.readLine()) != null) {
            String perfixLine = p + '#' + line;
            int[] prefixFunc = new int[p.length()];
            Arrays.fill(prefixFunc, 0);
            int prefixFuncPrev = 0;
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
}