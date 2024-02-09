package nsu.fit.ezaitseva;

import nsu.fit.ezaitseva.finders.SequentiallyNotPrimeFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;


public class TestNotPrimeFinder {
    @ParameterizedTest
    @MethodSource("finder")
    void testSeqFinder(ArrayList<Integer> numsList, boolean result){
        SequentiallyNotPrimeFinder finder = new SequentiallyNotPrimeFinder();
        Assertions.assertEquals(finder.noPrime(numsList), result);

    }
    @ParameterizedTest
    @MethodSource("finder")
    void testParallFinder(ArrayList<Integer> numsList, boolean result){
        SequentiallyNotPrimeFinder finder = new SequentiallyNotPrimeFinder();
        Assertions.assertEquals(finder.noPrime(numsList), result);

    }
    @ParameterizedTest
    @MethodSource("finder")
    void testThreadsFinder(ArrayList<Integer> numsList, boolean result){
        SequentiallyNotPrimeFinder finder = new SequentiallyNotPrimeFinder();
        Assertions.assertEquals(finder.noPrime(numsList), result);

    }

    private static Stream<Arguments> finder() {
        return Stream.of(
                Arguments.of(new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7)), true),
                Arguments.of(new ArrayList<>(Arrays.asList(2,3,5,7)), false));
    }

}