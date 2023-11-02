package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

public class TestPolynomial {

    @ParameterizedTest
    @MethodSource("testPolynomialSum")
    void testPolSum(Polynomial p1, Polynomial p2, Polynomial pSum) {
        Polynomial testPSum = p1.plus(p2);
        Assertions.assertArrayEquals(testPSum.coef, pSum.coef);
    }

    @ParameterizedTest
    @MethodSource("testPolynomialSub")
    void testPolSub(Polynomial p1, Polynomial p2, Polynomial pSub) {
        Polynomial testPSub = p1.minus(p2);
        Assertions.assertArrayEquals(testPSub.coef, pSub.coef);
    }

    @ParameterizedTest
    @MethodSource("testPolynomialMul")
    void testPolMul(Polynomial p1, Polynomial p2, Polynomial pMul) {
        Polynomial testPMul = p1.times(p2);
        Assertions.assertArrayEquals(testPMul.coef, pMul.coef);
    }

    @ParameterizedTest
    @MethodSource("testPolynomialEval")
    void testPolEval(Polynomial p1, int x, int xEval) {
        int testXEval = p1.evaluate(x);
        Assertions.assertEquals(testXEval, xEval);
    }

    @ParameterizedTest
    @MethodSource("testPolynomialDiff")
    void testPolDiff(Polynomial p1, int pow, Polynomial pDiff) {
        try {
            Polynomial testPDif = p1.differentiate(pow);
            Assertions.assertArrayEquals(testPDif.coef, pDiff.coef);
        } catch (Exception e) {
            Exception exception = Assertions.assertThrows(Exception.class, () -> {
                p1.differentiate(pow);
            });

            String expectedMessage = "pow of differentiate is < 0";
            String actualMessage = exception.getMessage();

            Assertions.assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    @ParameterizedTest
    @MethodSource("testPolynomialEquals")
    void testPolEq(Polynomial p1, Polynomial p2) {
        boolean testIsEqual = p1.equals(p2);
        boolean isEq = Arrays.equals(p1.coef, p2.coef);
        Assertions.assertTrue(testIsEqual == isEq);
    }

    @ParameterizedTest
    @MethodSource("testPolynomialToString")
    void testTurnToString(Polynomial p, String pString) {
        String testPString = p.turnToString();
        Assertions.assertEquals(testPString, pString);
    }

    private static Stream<Arguments> testPolynomialSum() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[]{1, 4, 2, 3}),
                        new Polynomial(new int[]{1, 2, 3, 4}),
                        new Polynomial(new int[]{2, 6, 5, 7})),

                Arguments.of(new Polynomial(new int[]{-1, 4, -2, 3}),
                        new Polynomial(new int[]{1, 2, 3, 4}),
                        new Polynomial(new int[]{0, 6, 1, 7})),

                Arguments.of(new Polynomial(new int[]{4, 1, 1}),
                        new Polynomial(new int[]{2, 2, 2, 2}),
                        new Polynomial(new int[]{2, 6, 3, 3})),

                Arguments.of(new Polynomial(new int[]{7, 4, -1, 3}),
                        new Polynomial(new int[]{4}),
                        new Polynomial(new int[]{7, 4, -1, 7})),

                Arguments.of(new Polynomial(new int[]{0, 0, -1, 3}),
                        new Polynomial(new int[]{4, 5}),
                        new Polynomial(new int[]{0, 0, 3, 8})),

                Arguments.of(new Polynomial(new int[]{9, 0, -1, 3}),
                        new Polynomial(new int[]{4, 5, 1}),
                        new Polynomial(new int[]{9, 4, 4, 4})),

                Arguments.of(new Polynomial(new int[]{0}),
                        new Polynomial(new int[]{}),
                        new Polynomial(new int[]{0})),

                Arguments.of(new Polynomial(new int[]{0}),
                        new Polynomial(new int[]{0}),
                        new Polynomial(new int[]{0})),

                Arguments.of(new Polynomial(new int[]{}),
                        new Polynomial(new int[]{}),
                        new Polynomial(new int[]{}))
        );
    }

    private static Stream<Arguments> testPolynomialSub() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[]{1, 4, 2, 3}),
                        new Polynomial(new int[]{1, 2, 3, 4}),
                        new Polynomial(new int[]{0, 2, -1, -1})),

                Arguments.of(new Polynomial(new int[]{-1, 4, -2, 3}),
                        new Polynomial(new int[]{1, 2, 3, 4}),
                        new Polynomial(new int[]{-2, 2, -5, -1})),

                Arguments.of(new Polynomial(new int[]{4, 1, 1}),
                        new Polynomial(new int[]{2, 2, 2, 2}),
                        new Polynomial(new int[]{-2, 2, -1, -1})),

                Arguments.of(new Polynomial(new int[]{7, 4, -1, 3}),
                        new Polynomial(new int[]{4}),
                        new Polynomial(new int[]{7, 4, -1, -1})),

                Arguments.of(new Polynomial(new int[]{0, 4, -1, 3}),
                        new Polynomial(new int[]{4}),
                        new Polynomial(new int[]{0, 4, -1, -1})),

                Arguments.of(new Polynomial(new int[]{7, 0, -1, 3}),
                        new Polynomial(new int[]{0, 4}),
                        new Polynomial(new int[]{7, 0, -1, -1})),

                Arguments.of(new Polynomial(new int[]{}),
                        new Polynomial(new int[]{0}),
                        new Polynomial(new int[]{0})),

                Arguments.of(new Polynomial(new int[]{0}),
                        new Polynomial(new int[]{0}),
                        new Polynomial(new int[]{0})),

                Arguments.of(new Polynomial(new int[]{}),
                        new Polynomial(new int[]{}),
                        new Polynomial(new int[]{}))

                );
    }

    private static Stream<Arguments> testPolynomialMul() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[]{1, 4, 2}),
                        new Polynomial(new int[]{1, 2, 3}),
                        new Polynomial(new int[]{1, 6, 13, 16, 6})),

                Arguments.of(new Polynomial(new int[]{4, -2, 3}),
                        new Polynomial(new int[]{3, 4}),
                        new Polynomial(new int[]{12, 10, 1, 12})),

                Arguments.of(new Polynomial(new int[]{0, 4, 8, 6, 7}), new Polynomial(new int[]{7, 2, 4}),
                        new Polynomial(new int[]{0, 28, 64, 74, 93, 38, 28})),

                Arguments.of(new Polynomial(new int[]{3, 1}), new Polynomial(new int[]{0, 5}),
                        new Polynomial(new int[]{0, 15, 5})),

                Arguments.of(new Polynomial(new int[]{0}), new Polynomial(new int[]{0}),
                        new Polynomial(new int[]{0})),

                Arguments.of(new Polynomial((new int[]{})), new Polynomial(new int[]{0}),
                        new Polynomial(new int[]{})),

                Arguments.of(new Polynomial(new int[]{7}),
                        new Polynomial(new int[]{1, 4, 9}),
                        new Polynomial(new int[]{7, 28, 63})),

                Arguments.of(new Polynomial(new int[]{}),
                        new Polynomial(new int[]{}),
                        new Polynomial(new int[]{}))
                );
    }

    private static Stream<Arguments> testPolynomialEval() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[]{2, 6, 2, 4}), 2, 48),
                Arguments.of(new Polynomial(new int[]{5, -2, 1}), 3, 40),
                Arguments.of(new Polynomial(new int[]{0, 1, 3, 2}), 1, 6),
                Arguments.of(new Polynomial(new int[]{4, 0, 3, 1}), -1, -6),
                Arguments.of(new Polynomial(new int[]{4, 9, 51}), 0, 51),
                Arguments.of(new Polynomial(new int[]{-12}), 3, -12),
                Arguments.of(new Polynomial(new int[]{}), 6, 0)
        );
    }

    private static Stream<Arguments> testPolynomialDiff() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[]{6, 2, 1, 3, 4}), 2, new Polynomial(new int[]{72, 12, 2})),
                Arguments.of(new Polynomial(new int[]{-34, 21, 3}), 1, new Polynomial(new int[]{-68, 21})),
                Arguments.of(new Polynomial(new int[]{2}), 1, new Polynomial(new int[]{})),
                Arguments.of(new Polynomial(new int[]{3, -2, 4}), 0, new Polynomial(new int[]{3, -2, 4})),
                Arguments.of(new Polynomial(new int[]{0, 0, 21, 4, 2}), 1, new Polynomial(new int[]{0, 0, 42, 4})),
                Arguments.of(new Polynomial(new int[]{5, 0, 21, 0, 9}), 2, new Polynomial(new int[]{60, 0, 42})),
                Arguments.of(new Polynomial(new int[]{5, -1, 3, 2}), -4, null),
                Arguments.of(new Polynomial(new int[]{}), 5, new Polynomial(new int[]{})),
                Arguments.of(new Polynomial(new int[]{}), -2, null)
        );
    }

    private static  Stream<Arguments> testPolynomialEquals() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[]{3, 2, 4, 5}), new Polynomial(new int[]{3, 2, 4, 5})),
                Arguments.of(new Polynomial(new int[]{4, 3, 2, 1}), new Polynomial(new int[]{4, 3, 2, 1, 6})),
                Arguments.of(new Polynomial(new int[]{7, 5, 6, 3}), new Polynomial(new int[]{7, 2, 6, 3})),
                Arguments.of(new Polynomial(new int[]{0, 0, 6, 3}), new Polynomial(new int[]{0, 0, 6, 3})),
                Arguments.of(new Polynomial(new int[]{0, 0, 6, 3}), new Polynomial(new int[]{6, 3})),
                Arguments.of(new Polynomial(new int[]{0, 0, 6, 3}), new Polynomial(new int[]{0, 0, 6, 4})),
                Arguments.of(new Polynomial(new int[]{0, 0, 6, 3}), new Polynomial(new int[]{4, 3})),
                Arguments.of(new Polynomial(new int[]{7, 5, 0, 3}), new Polynomial(new int[]{7, 5, 0, 3})),
                Arguments.of(new Polynomial(new int[]{0, 0, 6, 3}), new Polynomial(new int[]{7, 0, 6, 3})),
                Arguments.of(new Polynomial(new int[]{4, 2, 3}), new Polynomial(new int[]{3, 5, 1})),
                Arguments.of(new Polynomial(new int[]{}), new Polynomial(new int[]{4, 2, 6, 3})),
                Arguments.of(new Polynomial(new int[]{9, 6, 8}), new Polynomial(new int[]{})),
                Arguments.of(new Polynomial(new int[]{}), new Polynomial(new int[]{}))
        );
    }

    private static  Stream<Arguments> testPolynomialToString() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[]{8, 19, -33, 2, 5}), "8x^4 + 19x^3 - 33x^2 + 2x + 5"),
                Arguments.of(new Polynomial(new int[]{-2, 43, 54, 1}), "-2x^3 + 43x^2 + 54x + 1"),
                Arguments.of(new Polynomial(new int[]{5, -2, 0, 3, 1}), "5x^4 - 2x^3 + 3x + 1"),
                Arguments.of(new Polynomial(new int[]{0, 0, 4, 4}), "4x + 4"),
                Arguments.of(new Polynomial(new int[]{-32}), "-32"),
                Arguments.of(new Polynomial(new int[]{9}), "9"),
                Arguments.of(new Polynomial(new int[]{}), "")
                );
    }
}