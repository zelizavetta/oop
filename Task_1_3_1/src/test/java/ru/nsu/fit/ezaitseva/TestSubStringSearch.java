package ru.nsu.fit.ezaitseva;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class TestSubStringSearch {

    @ParameterizedTest
    @MethodSource("searchSubString")
    void testSearchSubString(String searchingString, String filename, Integer[] expectedResult) throws IOException {
        Integer[] result = SubStringSearch.search(searchingString, filename);
        Assertions.assertArrayEquals(result, expectedResult);
    }

    private static Stream<Arguments> searchSubString() {
        return Stream.of(
                Arguments.of("a", "src/test/java/ru/nsu/fit/ezaitseva/test1.txt",
                        new Integer[] {0, 3, 8}),
                Arguments.of("a", "src/test/java/ru/nsu/fit/ezaitseva/test2.txt",
                        new Integer[] {0, 4, 5, 6, 7})

//                Arguments.of("b", "src/test/java/ru/nsu/fit/ezaitseva/test3.txt", new Integer[] {3, 4}),
//                Arguments.of("b", "src/test/java/ru/nsu/fit/ezaitseva/test4.txt", new Integer[] {3, 4})
//
//                Arguments.of("rrrrr", "test5.txt", new Integer[] {7}),
//                Arguments.of("rrrrr", "test6.txt", new Integer[] {7})

//                Arguments.of("ff", "TestFiles/test7.txt", []),
//                Arguments.of("ff", "TestFiles/test8.txt", [13]),
//
//                Arguments.of("", "TestFiles/test9.txt", [])
//                Arguments.of("", "TestFiles/test10.txt", [])
        );
    }
}