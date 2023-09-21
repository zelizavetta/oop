package ru.nsu.fit.ezaitseva;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestSort {

    @ParameterizedTest
    @MethodSource("checkHeapSort")
    void testSort(int[] arr, int[] sortedArr) {
        Heapsort.sort(arr);
        Assertions.assertArrayEquals(arr, sortedArr);
    }

    private static Stream<Arguments> checkHeapSort() {
        return Stream.of(
                Arguments.of(new int[]{0, 1, 4, 2, 3}, new int[]{0, 1, 2, 3, 4}),
                Arguments.of(new int[]{5, 6, 9, 23}, new int[]{5, 6, 9, 23}),
                Arguments.of(new int[]{10, 10, 10, 10, 10}, new int[]{10, 10, 10, 10, 10}),
                Arguments.of(new int[]{-4, -2, -76, -2}, new int[]{-76, -4, -2, -2}),
                Arguments.of(new int[]{-4, 56, -76, 23}, new int[]{-76, -4, 23, 56}),
                Arguments.of(new int[]{5}, new int[]{5}),
                Arguments.of(new int[]{}, new int[]{})
        );
    }

}