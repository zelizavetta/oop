package ru.nsu.fit.ezaitseva;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestSort {
    //    private static Stream<int[]> numbers() {
//        int[][] a = {{1,2},{3,4}, {-5,5}};
//        return Stream.of(
//                a
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("numbers")
//    void testSum(int[] ar){
//        Assertions.assertEquals(SomeClass.sum(ar[0],ar[1]), ar[0]+ar[1]);
//    }
    @Test
    public void testSort() {
        int[] arr = {5, 4, 1, 2, 10, -5};
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        Heapsort.sort(arr);
        Assertions.assertArrayEquals(arr, sorted);
    }
}
