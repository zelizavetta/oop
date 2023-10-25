package ru.nsu.fit.ezaitseva;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
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
                        new Integer[] {0, 4, 5, 6, 7}),

                Arguments.of("b", "src/test/java/ru/nsu/fit/ezaitseva/test3.txt", new Integer[] {3, 4}),
                Arguments.of("b", "src/test/java/ru/nsu/fit/ezaitseva/test4.txt", new Integer[] {3, 4}),

                Arguments.of("rrrr", "src/test/java/ru/nsu/fit/ezaitseva/test5.txt", new Integer[] {7}),
                Arguments.of("rrrr", "src/test/java/ru/nsu/fit/ezaitseva/test6.txt", new Integer[] {7}),

                Arguments.of("ff", "src/test/java/ru/nsu/fit/ezaitseva/test7.txt", new Integer[0]),
                Arguments.of("ff", "src/test/java/ru/nsu/fit/ezaitseva/test8.txt", new Integer[0]),

                Arguments.of("", "src/test/java/ru/nsu/fit/ezaitseva/test9.txt", new Integer[0]),
                Arguments.of("", "src/test/java/ru/nsu/fit/ezaitseva/test10.txt", new Integer[0]),

                Arguments.of("er", "src/test/java/ru/nsu/fit/ezaitseva/test11.txt", new Integer[0]),
                Arguments.of("n", "src/test/java/ru/nsu/fit/ezaitseva/test11.txt", new Integer[0]),

                Arguments.of("it", "src/test/java/ru/nsu/fit/ezaitseva/test12.txt", new Integer[] {19, 54,
                        413, 592, 612, 822, 959, 1018, 1050, 1115, 1182, 1257, 1288, 1352, 1568, 1762, 2067, 2115,
                        2193, 2353, 2373, 2417, 2517, 2589, 2709, 2724, 2818, 2914, 3037, 3055, 3076, 3097, 3139, 3165,
                        3272, 3512, 3837, 4019, 4048, 4068, 4093, 4163, 4301, 4407, 4457, 4483, 4670, 5057, 5153, 5351,
                        5422, 5511, 5580, 5768, 5816, 5891, 6102, 6259, 6270, 6539, 6618, 6661, 6690}),

                Arguments.of("", "src/test/java/ru/nsu/fit/ezaitseva/test12.txt", new Integer[0])
        );
    }
}