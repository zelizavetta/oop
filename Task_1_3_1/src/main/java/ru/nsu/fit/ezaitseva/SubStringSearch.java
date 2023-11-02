package ru.nsu.fit.ezaitseva;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        char[] myBuffer = new char[256];
        int bytesRead = 0;
        StringBuilder line = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8));
            List<Integer> result = new ArrayList<>();
            int quantSymb = 0;
            int prevK = 0;
            boolean flNewLine = false;
            while ((bytesRead = in.read(myBuffer, 0, 256)) != -1) {
                for (char c : myBuffer) {
                    int cInt = c;

                    if (cInt != 0 && cInt != 13 && c != '\n') {
//                        System.out.println(cInt);
                        line.append(c);
                    }
                }
                String prefixLine = p + '#' + line;

                int[] prefixFunc = new int[p.length()];
                int prefixFuncPrev = 0;

                Arrays.fill(prefixFunc, 0);
                for (int i = 1; i < prefixLine.length(); i++) {
                    int k = prefixFuncPrev;
                    while (k > 0 && prefixLine.charAt(k) != prefixLine.charAt(i)) {
                        k = prefixFunc[k - 1];
                    }
                    if (prefixLine.charAt(k) == prefixLine.charAt(i)) {
                        k++;
                    }
                    if (i < p.length()) {
                        prefixFunc[i] = k;
                    }
                    prefixFuncPrev = k;
                    if (k == p.length()) {
                        result.add(i + quantSymb - 2 * p.length());
                    }
                    if (prevK != 0 && k != 0 && k >= p.length() - prevK && flNewLine && i > p.length() + 1 &&
                        !result.contains(quantSymb - 1)) {
                        result.add(quantSymb - 1);
                    }
                    if (i == prefixLine.length() - 1) {
                        prevK = k;
                    }
                    if (i == p.length() + 1 && k == 0) {
                        flNewLine = false;
                    }

                }

                quantSymb += line.length();
                line.delete(0, line.toString().length());
                flNewLine = true;
                char[] newBuff = new char[256];
                myBuffer = newBuff.clone();
            }
            Integer[] arr = new Integer[0];
            arr = result.toArray(arr);
            return arr;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        String searchingStr = "a";
        Integer[] result = search(searchingStr, root + "src/main/java/ru/nsu/fit/ezaitseva/input.txt");
        System.out.println(Arrays.toString(result));

    }
}