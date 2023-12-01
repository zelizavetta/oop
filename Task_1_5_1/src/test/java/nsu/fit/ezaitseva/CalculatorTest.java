package nsu.fit.ezaitseva;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;


public class CalculatorTest {

    @ParameterizedTest
    @MethodSource("calculateResult")
    void testCalculateResult(String input, Double expectedResult) throws NoSuchFunctionException, WrongPolishNotationException {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(calculator.calculateResult(input), expectedResult);
    }

    private static Stream<Arguments> calculateResult() {
        return Stream.of(
                Arguments.of("sin + - 1 2 1", 0.0)
        );
    }

}