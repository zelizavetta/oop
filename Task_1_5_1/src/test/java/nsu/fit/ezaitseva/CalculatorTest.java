package nsu.fit.ezaitseva;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;


public class CalculatorTest {

    @ParameterizedTest
    @MethodSource("calculateResultNoError")
    void testCalculateResult(String input, Double expectedResult) throws NoSuchFunctionException, WrongPolishNotationException {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(calculator.calculateResult(input), expectedResult);
    }

    @ParameterizedTest
    @MethodSource("calculateResultError")
    void testCalculateResultError(String input, Exception expectedResult) throws NoSuchFunctionException, WrongPolishNotationException {
        Calculator calculator = new Calculator();
        try {
            Double res = calculator.calculateResult(input);
        } catch (Exception er) {
            Assertions.assertEquals(er.getClass(), expectedResult.getClass());
        }
    }

    private static Stream<Arguments> calculateResultNoError() {
        return Stream.of(
                Arguments.of("sin + - 1 2 1", Math.sin(0)),
                Arguments.of("cos - 1 1", Math.cos(0)),
                Arguments.of("pow * - 3 1 2 2", Math.pow(4, 2)),
                Arguments.of("log / 1 2", Math.log(0.5)),
                Arguments.of("sqrt + 2 2", Math.sqrt(4))
        );
    }

    private static Stream<Arguments> calculateResultError() {
        return Stream.of(
                Arguments.of("3 sin + - 1 2 1", new WrongPolishNotationException("Wrong Polish Notation")),
                Arguments.of("sec - 1 1", new NoSuchFunctionException("No such function"))
        );
    }

}